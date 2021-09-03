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
public class Asigna {

    private String id;
    private Expresion inicializado;
    private int linea;
    private int columna;

    public Asigna(String id, Expresion inicializado, int linea, int columna) {
        this.id = id;
        this.inicializado = inicializado;
        this.linea = linea;
        this.columna = columna;
    }

    public boolean interpretar(Simbolos tabla) {
        if (getInicializado() != null) {
            if (tabla.existe(getId())) {
                Simbolo temp = tabla.obtener(getId());
                if (temp.getTipo().equalsIgnoreCase("numero")) {
                    List<Object> objeto = new LinkedList<>();
                    Termino a = getInicializado().ejecutar(tabla);
                    if (a instanceof Primitivo) {
                        Primitivo ab = (Primitivo) a;
                        if (ab.asignar("numero")) {
                            objeto.add(ab.getValor());
                            tabla.cambiar(getId(), objeto);
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    tabla.agregar_error("Semantico", "La variable que colocaste no es de tipo entero " + getId(), linea, columna);
                    return false;
                }
            } else {
                List<Object> objeto = new LinkedList<>();
                Termino a = getInicializado().ejecutar(tabla);
                if (a instanceof Primitivo) {
                    Primitivo ab = (Primitivo) a;
                    if (ab.asignar("numero")) {
                        objeto.add(ab.getValor());
                        tabla.agregar(new Simbolo(getId(), "numero", null, objeto, tabla.ambitos));
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } else {
            if (tabla.existe(getId())) {
                Simbolo temp = tabla.obtener(getId());
                if (temp.getTipo().equalsIgnoreCase("numero")) {
                    return true;
                } else {
                    tabla.agregar_error("Semantico", "La variable que colocaste no es de tipo entero " + getId(), linea, columna);
                    return false;
                }
            } else {
                tabla.agregar_error("Semantico", "No está declarada la variable " + getId(), linea, columna);
                return false;
            }
        }
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Expresion getInicializado() {
        return inicializado;
    }

    public void setInicializado(Expresion inicializado) {
        this.inicializado = inicializado;
    }

}
