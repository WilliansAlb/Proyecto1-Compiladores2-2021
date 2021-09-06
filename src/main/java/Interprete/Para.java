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
public class Para extends Instruccion implements Serializable{

    private Asigna asigna;
    private Expresion condicion;
    private Paso paso;
    private List<Instruccion> instrucciones;
    private int linea;
    private int columna;

    public Para(Asigna asigna, Expresion condicion, Paso paso, List<Instruccion> instrucciones, int linea, int columna) {
        this.asigna = asigna;
        this.condicion = condicion;
        this.paso = paso;
        this.instrucciones = instrucciones;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public void interpretar(Simbolos tabla) {
        tabla.ambitos++;
        if (asigna.interpretar(tabla)) {
            Termino c = condicion.ejecutar(tabla);
            if (c instanceof Primitivo) {
                Primitivo co = (Primitivo) c;
                if (co.getTipo().equalsIgnoreCase("boolean")) {
                    while ((boolean) ((Primitivo) condicion.ejecutar(tabla)).getValor()) {
                        for (Instruccion instruccion : instrucciones) {
                            if (instruccion instanceof ContinuarSalir) {
                                ContinuarSalir cs = (ContinuarSalir) instruccion;
                                if (cs.isContinuar()) {
                                    break;
                                } else {
                                    tabla.eliminar_ambito();
                                    return;
                                }
                            } else {
                                if (instruccion != null) {
                                    instruccion.interpretar(tabla);
                                }
                                if (tabla.tieneBreak()) {
                                    tabla.eliminar_ambito();
                                    return;
                                }
                                if (tabla.tieneRetorno()) {
                                    tabla.eliminar_ambito();
                                    return;
                                }
                            }
                        }
                        paso.interpretar(tabla);
                    }
                }
            }
        } else {
            tabla.agregar_error("Semantico", "No se pudo ejecutar el para, dado que la asignacion/declaracion está mal", linea, columna);
        }
        tabla.eliminar_ambito();
    }

    @Override
    public Simbolos getTabla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(List<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
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

    public Asigna getAsigna() {
        return asigna;
    }

    public void setAsigna(Asigna asigna) {
        this.asigna = asigna;
    }

    public Expresion getCondicion() {
        return condicion;
    }

    public void setCondicion(Expresion condicion) {
        this.condicion = condicion;
    }

    public Paso getPaso() {
        return paso;
    }

    public void setPaso(Paso paso) {
        this.paso = paso;
    }

}
