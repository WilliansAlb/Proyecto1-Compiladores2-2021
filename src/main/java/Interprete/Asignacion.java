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
public class Asignacion extends Instruccion {

    String id;
    List<Expresion> datos;
    List<Expresion> dimensiones;
    int linea;
    int columna;

    public Asignacion(String id, List<Expresion> datos, List<Expresion> dimensiones, int linea, int columna) {
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
                for (Expresion dato : datos) {
                    Termino ab = dato.ejecutar(tabla);
                    if (ab instanceof Primitivo) {
                        Primitivo a = (Primitivo) dato.ejecutar(tabla);
                        if (a.asignar(buscado.getTipo())){
                            valores.add(a.getValor());
                        } else {
                            solo_valores = false;
                            break;
                        }
                    } else {
                        solo_valores = false;
                        break;
                    }
                }
            }
            if (solo_numeros && solo_valores) {
                tabla.cambiar(id, valores);
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

    public List<Expresion> getExpresion() {
        return datos;
    }

    public void setExpresion(List<Expresion> datos) {
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
