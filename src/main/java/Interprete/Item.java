/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolos;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author willi
 */
public class Item {

    private List<Item> hijos;
    private boolean esHoja;
    private Expresion dato;

    public Item(List<Item> hijos, boolean esHoja, Expresion dato) {
        this.hijos = hijos;
        this.esHoja = esHoja;
        this.dato = dato;
    }

    public List<Item> getHijos() {
        return hijos;
    }

    public void setHijos(List<Item> hijos) {
        this.hijos = hijos;
    }

    public boolean isEsHoja() {
        return esHoja;
    }

    public void setEsHoja(boolean esHoja) {
        this.esHoja = esHoja;
    }

    public Expresion getDato() {
        return dato;
    }

    public void setDato(Expresion dato) {
        this.dato = dato;
    }

    public void interpretar(List<Object> elementos, String tipo, int dimension, List<Integer> dimensiones, Simbolos tabla) {
        if (dimension <= dimensiones.size()) {
            if (esHoja) {
                if (dato != null) {
                    Termino data = dato.ejecutar(tabla);
                    if (data instanceof Primitivo) {
                        Primitivo d = (Primitivo) data;
                        if (d.asignar(tipo)) {
                            elementos.add(d.getValor());
                        } else {
                            System.out.println("error");
                        }
                    }
                }
            } else {
                if (!hijos.isEmpty()) {
                    boolean iguales = hijos.get(0).isEsHoja();
                    boolean pasa = true;
                    for (Item hijo : hijos) {
                        if (iguales != hijo.isEsHoja()) {
                            pasa = false;
                            break;
                        }
                    }
                    if (pasa) {
                        if (hijos.size() <= dimensiones.get(dimension)) {
                            for (Item hijo : hijos) {
                                hijo.interpretar(elementos, tipo, dimension + 1, dimensiones, tabla);
                            }
                            if (!(dimensiones.get(dimension) - (hijos.size()) == 0)) {
                                int cuantos = dimensiones.get(dimension) - hijos.size();
                                if (dimension + 1 != dimensiones.size()) {
                                    for (int i = 0; i < cuantos; i++) {
                                        agregarNulls(dimension + 1, elementos, dimensiones);
                                    }
                                } else {
                                    for (int i = 0; i < cuantos; i++) {
                                        elementos.add(null);
                                    }
                                }
                            }
                        } else {
                            System.out.println("excedido el limite del arreglo");
                        }
                    } else {
                        System.out.println("error");
                    }
                } else {
                    System.out.println("error2");
                }
            }
        } else {
            //System.out.println("dimension " + dimension + " numero: " + dimensiones.size());
            //System.out.println("te pasaste");
        }
    }

    private void agregarNulls(int dimension, List<Object> elementos, List<Integer> dimensiones) {
        if (dimension < dimensiones.size()) {
            int cuantos = dimensiones.get(dimension);
            System.out.println("cuantos agregar " + cuantos);
            for (int i = 0; i < cuantos; i++) {
                agregarNulls(dimension + 1, elementos, dimensiones);
            }
        } else if (dimension == dimensiones.size()) {
            elementos.add(null);
        } else {
            System.out.println("no tendria");
        }
    }

    private boolean sumarizar(List<Object> elementos, List<Object> propiedades,Simbolos tabla) {
        obtener_arreglo(elementos, tabla, propiedades, 0);
        if ((boolean) propiedades.get(ERRORES)) {
            return false;
        } else {
            if ((int) propiedades.get(DIMENSIONES) == 1) {
                return true;
            } else {
                return false;
            }
        }
    }
    public static final int TIPO = 0;
    public static final int DIMENSIONES = 1;
    public static final int ERRORES = 2;

    public void obtener_arreglo(List<Object> elementos, Simbolos tabla, List<Object> propiedades, int dimension) {
        if (esHoja) {
            if (dato != null) {
                Termino data = dato.ejecutar(tabla);
                if (propiedades.get(TIPO).toString().isEmpty()) {
                    propiedades.set(TIPO, data.getTipo());
                }
                if (data instanceof Primitivo) {
                    Primitivo d = (Primitivo) data;
                    if (d.asignar(propiedades.get(TIPO).toString())) {
                        elementos.add(d.getValor());
                        if ((int) propiedades.get(DIMENSIONES) < dimension) {
                            propiedades.set(DIMENSIONES, dimension);
                        }
                    } else {
                        propiedades.set(ERRORES, true);
                        tabla.agregar_error("Semantico", "El elemento " + d.getValor() + " no coincide con el tipo del primer elemento del arreglo (" + propiedades.get(TIPO) + ")",data.getLinea(),data.getColumna());
                    }
                }
            }
        } else {
            if (!hijos.isEmpty()) {
                dimension = dimension+1;
                for (Item hijo : hijos) {
                    if (!(boolean) propiedades.get(ERRORES)) {
                        hijo.obtener_arreglo(elementos, tabla, propiedades, dimension);
                    }
                }
            }
        }
    }
}
