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
    List<Expresion> datos;
    boolean keep;
    int linea;
    int columna;

    public Declaracion() {
        this.ids = new LinkedList<>();
        this.tipo = "";
        this.dimensiones = new LinkedList<>();
        this.datos = new LinkedList<>();
        this.keep = false;
        this.linea = -1;
        this.columna = -1;
    }

    public Declaracion(List<String> ids, String tipo, List<Expresion> dimensiones, List<Expresion> datos, boolean keep, int linea, int columna) {
        this.ids = ids;
        this.tipo = tipo;
        this.dimensiones = dimensiones;
        this.datos = datos;
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
        if (dimensiones != null) {
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
        } else {
            dim.add(1);
        }
        if (solo_numeros) {
            if (datos!=null) {
                for (Expresion dato : datos) {
                    Termino ab = dato.ejecutar(tabla);
                    if (ab instanceof Primitivo) {
                        Primitivo a = (Primitivo) ab;
                        if (a.asignar(tipo)) {
                            valores.add(a.getValor());
                        } else {
                            System.out.println(a.getValor());
                            solo_valores = false;
                            break;
                        }
                    } else {
                        solo_valores = false;
                        break;
                    }
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

    public List<Expresion> getDatos() {
        return datos;
    }

    public void setDatos(List<Expresion> datos) {
        this.datos = datos;
    }

    public boolean isInicializada() {
        return this.datos != null;
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
