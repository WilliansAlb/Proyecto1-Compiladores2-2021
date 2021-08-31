/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolo;
import Tablas.Simbolos;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author willi
 */
public class Declaracion extends Instruccion {

    List<String> ids;
    String tipo;
    List<Expresion> dimensiones;
    Expresion dato;
    List<Item> items;
    boolean esArreglo;
    boolean keep;
    int linea;
    int columna;

    public Declaracion() {
        this.ids = new LinkedList<>();
        this.tipo = "";
        this.dimensiones = new LinkedList<>();
        this.dato = null;
        this.keep = false;
        this.linea = -1;
        this.columna = -1;
    }

    public Declaracion(List<String> ids, String tipo, List<Expresion> dimensiones, Expresion dato, List<Item> items, boolean esArreglo, boolean keep, int linea, int columna) {
        this.ids = ids;
        this.tipo = tipo;
        this.dimensiones = dimensiones;
        this.dato = dato;
        this.items = items;
        this.esArreglo = esArreglo;
        this.keep = keep;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public void interpretar(Simbolos tabla) {
        List<Integer> dim = new LinkedList<>();
        List<Object> valores = new LinkedList<>();
        boolean solo_numeros = true;
        boolean solo_valores = true;
        if (esArreglo) {
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
            List<Expresion> lista_datos = new LinkedList<>();
            if (solo_numeros) {
                for (Item item : items) {
                    
                }
            }
            if (solo_numeros && solo_valores) {
                for (String id : ids) {
                    if (keep) {
                        Simbolo nuevo = new Simbolo(id, tipo, dim, valores, tabla.ambitos);
                        tabla.agregar(nuevo);
                    } else {
                        Simbolo nuevo = new Simbolo(id, tipo, dim, valores, tabla.ambitos);
                        tabla.agregar(nuevo);
                    }
                }
            }
        } else {
            dim.add(1);
            if (solo_numeros) {
                if (dato != null) {
                    Termino ab = dato.ejecutar(tabla);
                    if (ab instanceof Primitivo) {
                        Primitivo a = (Primitivo) ab;
                        if (a.asignar(tipo)) {
                            valores.add(a.getValor());
                        } else {
                            System.out.println(a.getValor());
                            solo_valores = false;
                        }
                    } else {
                        solo_valores = false;
                    }
                } else {
                    valores.add(null);
                }
            }
            if (solo_numeros && solo_valores) {
                for (String id : ids) {
                    if (keep) {
                        Simbolo nuevo = new Simbolo(id, tipo, dim, valores, tabla.ambitos);
                        tabla.agregar(nuevo);
                    } else {
                        Simbolo nuevo = new Simbolo(id, tipo, dim, valores, tabla.ambitos);
                        tabla.agregar(nuevo);
                    }
                }
            }
        }
    }

    @Override
    public Simbolos getTabla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Expresion> getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(List<Expresion> dimensiones) {
        this.dimensiones = dimensiones;
    }

    public Expresion getDato() {
        return dato;
    }

    public void setDato(Expresion dato) {
        this.dato = dato;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public boolean isEsArreglo() {
        return esArreglo;
    }

    public void setEsArreglo(boolean esArreglo) {
        this.esArreglo = esArreglo;
    }


    public boolean isKeep() {
        return keep;
    }

    public void setKeep(boolean keep) {
        this.keep = keep;
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
