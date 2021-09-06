/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolo;
import Tablas.Simbolos;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author willi
 */
public class Asignacion extends Instruccion implements Serializable{

    String id;
    Expresion datos;
    List<Expresion> dimensiones;
    int linea;
    int columna;

    public Asignacion(String id, Expresion datos, List<Expresion> dimensiones, int linea, int columna) {
        this.id = id;
        this.datos = datos;
        this.dimensiones = dimensiones;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public void interpretar(Simbolos tabla) {
        List<Integer> dim = new LinkedList<>();
        List<Object> valores = new LinkedList<>();
        boolean solo_numeros = true;
        boolean solo_valores = true;
        Simbolo buscado = tabla.obtener(id);
        if (buscado != null) {
            if (dimensiones != null) {
                if (buscado.getDimensiones() != null) {
                    for (Expresion dimensione : dimensiones) {
                        Termino ab = dimensione.ejecutar(tabla);
                        if (ab instanceof Primitivo) {
                            Primitivo a = (Primitivo) dimensione.ejecutar(tabla);
                            if (!a.getTipo().equalsIgnoreCase("excepcion")) {
                                if (a.getTipo().equalsIgnoreCase("numero")) {
                                    dim.add((int) a.getValor());
                                } else {
                                    System.out.println("La dimensión no puede ser un no numero");
                                    solo_numeros = false;
                                    break;
                                }
                            } else {
                                System.out.println("error");
                                solo_numeros = false;
                                break;
                            }
                        } else {
                            solo_numeros = false;
                            break;
                        }
                    }
                    if (solo_numeros) {
                        List<Integer> di = buscado.getDimensiones();
                        if (di.size() == dim.size()) {
                            boolean correcto = true;
                            for (int i = 0; i < di.size(); i++) {
                                if (!(di.get(i) > dim.get(i))) {
                                    correcto = false;
                                    break;
                                }
                            }
                            if (correcto) {
                                int posicion = tabla.obtener(id).insertarEn(dim, di);
                                Termino ab = datos.ejecutar(tabla);
                                if (ab instanceof Primitivo) {
                                    Primitivo a = (Primitivo) datos.ejecutar(tabla);
                                    if (a.asignar(buscado.getTipo())) {
                                        tabla.cambiar_arreglo(id, a.getValor(),posicion);
                                    } else {
                                        solo_valores = false;
                                    }
                                } else {
                                    solo_valores = false;
                                }
                            }
                        }
                    }
                }
            } else {
                if (solo_numeros) {
                    Termino ab = datos.ejecutar(tabla);
                    if (ab instanceof Primitivo) {
                        Primitivo a = (Primitivo) ab;
                        if (a.asignar(buscado.getTipo())) {
                            valores.add(a.getValor());
                        } else {
                            solo_valores = false;
                        }
                    } else {
                        solo_valores = false;
                    }
                }
                if (solo_numeros && solo_valores) {
                    tabla.cambiar(id, valores);
                }
            }

        } else {
            System.out.println("No existe la variable a la que le quiere asignar valor");
        }
    }

    @Override
    public Simbolos getTabla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isArreglo() {
        return dimensiones != null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Expresion getDatos() {
        return datos;
    }

    public void setDatos(Expresion datos) {
        this.datos = datos;
    }

    public List<Expresion> getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(List<Expresion> dimensiones) {
        this.dimensiones = dimensiones;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

}
