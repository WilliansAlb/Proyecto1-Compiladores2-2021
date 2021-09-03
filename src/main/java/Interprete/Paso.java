/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolo;
import Tablas.Simbolos;

/**
 *
 * @author willi
 */
public class Paso extends Instruccion {

    public static enum TIPO {
        INCREMENTO,
        DECREMENTO,
        ASIGNACION,
        SIMPLIFICADA
    }
    public String id;
    public Expresion expresion;
    public TIPO tipo;
    public int linea;
    public int columna;

    public Paso(String id, Expresion expresion, TIPO tipo, int linea, int columna) {
        this.id = id;
        this.expresion = expresion;
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public void interpretar(Simbolos tabla) {
        if (tabla.existe(id)) {
            if (!tabla.obtener(id).getTipo().equalsIgnoreCase("metodo")) {
                Simbolo s = tabla.obtener(id);
                if (s.getDatos() != null) {
                    if (s.getDimensiones() == null) {
                        if (s.getTipo().equalsIgnoreCase("numero")) {
                            int numero = (int) s.getDatos().get(0);
                            if (tipo == TIPO.INCREMENTO) {
                                numero++;
                                s.getDatos().set(0, numero);
                                tabla.cambiar(id, s.getDatos());
                            } else if (tipo == TIPO.DECREMENTO) {
                                numero--;
                                s.getDatos().set(0, numero);
                                tabla.cambiar(id, s.getDatos());
                            } else if (tipo == TIPO.SIMPLIFICADA) {
                                Termino a = expresion.ejecutar(tabla);
                                if (a instanceof Primitivo) {
                                    Primitivo ab = (Primitivo) a;
                                    if (ab.asignar("numero")) {
                                        numero += (int) ab.getValor();
                                        s.getDatos().set(0, numero);
                                        tabla.cambiar(id, s.getDatos());
                                    } else {
                                        tabla.agregar_error("Semantico", "No se puede ejecutar la suma simplificada porque no son datos compatibles", linea, columna);
                                    }
                                } else {
                                    tabla.agregar_error("Semantico", "El resultado de la expresion no es valido", linea, columna);
                                }
                            } else {
                                tabla.agregar_error("Semantico", "Operacion de incremento/decremento desconocida", linea, columna);
                            }
                        } else {
                            tabla.agregar_error("Semantico", "No es una variable del tipo entero como para realizar un incremento/decremento", linea, columna);
                        }
                    } else {
                        tabla.agregar_error("Semantico", "La variable " + id + " corresponde a un arreglo, los arreglos no pueden tener incrementos/decrementos", linea, columna);
                    }
                } else {
                    tabla.agregar_error("Semantico", "La variable " + id + " no está inicializada", linea, columna);
                }
            }
        } else {
            tabla.agregar_error("Semantico", "No has declarado la variable " + id, linea, columna);
        }
    }

    @Override
    public Simbolos getTabla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Expresion getExpresion() {
        return expresion;
    }

    public void setExpresion(Expresion expresion) {
        this.expresion = expresion;
    }

    public TIPO getTipo() {
        return tipo;
    }

    public void setTipo(TIPO tipo) {
        this.tipo = tipo;
    }

}
