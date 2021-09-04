/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolo;
import Tablas.Simbolos;
import java.util.List;

/**
 *
 * @author willi
 */
public class Si extends Instruccion {

    Expresion condicion;
    List<Instruccion> instrucciones;
    List<Instruccion> sino;
    Si sinosi;
    int linea;
    int columna;

    public Si(Expresion condicion, List<Instruccion> instrucciones, List<Instruccion> sino, Si sinosi, int linea, int columna) {
        this.condicion = condicion;
        this.instrucciones = instrucciones;
        this.sino = sino;
        this.sinosi = sinosi;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public void interpretar(Simbolos tabla) {
        Termino con = condicion.ejecutar(tabla);
        if (con instanceof Primitivo) {
            Primitivo cond = (Primitivo) con;
            if (cond.getTipo().equalsIgnoreCase("boolean")) {
                if ((boolean) cond.getValor()) {
                    tabla.ambitos++;
                    for (Instruccion inst : instrucciones) {
                        if (inst instanceof ContinuarSalir) {
                            ContinuarSalir cs = (ContinuarSalir) inst;
                            if (cs.isContinuar()) {
                                break;
                            } else {
                                tabla.agregar(new Simbolo("$break", "$break", null, null, tabla.ambitos - 1));
                                break;
                            }
                        } else if (inst instanceof Retorno) {
                            inst.interpretar(tabla);
                            break;
                        } else {
                            if (inst != null) {
                                inst.interpretar(tabla);
                            }
                            if (tabla.tieneBreak()) {
                                System.out.println("sube el break");
                                tabla.subir_break();
                                break;
                            }
                            if (tabla.tieneRetorno()) {
                                System.out.println("sube el retorno");
                                break;
                            }
                        }
                    }
                    tabla.eliminar_ambito();
                } else {
                    if (sinosi != null) {
                        sinosi.interpretar(tabla);
                    } else {
                        if (sino != null) {
                            tabla.ambitos++;
                            for (Instruccion inst : sino) {
                                if (inst instanceof ContinuarSalir) {
                                    ContinuarSalir cs = (ContinuarSalir) inst;
                                    if (cs.isContinuar()) {
                                        break;
                                    } else {
                                        tabla.agregar(new Simbolo("$break", "$break", null, null, tabla.ambitos - 1));
                                        break;
                                    }
                                } else if (inst instanceof Retorno) {
                                    inst.interpretar(tabla);
                                    break;
                                } else {
                                    if (inst != null) {
                                        inst.interpretar(tabla);
                                    }
                                    if (tabla.tieneBreak()) {
                                        System.out.println("subir break");
                                        tabla.subir_break();
                                        break;
                                    }
                                    if (tabla.tieneRetorno()) {
                                        System.out.println("sube el retorno");
                                        break;
                                    }
                                }
                            }
                            tabla.eliminar_ambito();
                        }
                    }
                }
            } else {
                tabla.agregar_error("Semantico", "La condición no es del tipo booleano", linea, columna);
            }
        } else {
            tabla.agregar_error("Semantico", "La condición no es de un tipo valido", linea, columna);
        }
    }

    @Override
    public Simbolos getTabla() {
        return null;
    }

    public Expresion getCondicion() {
        return condicion;
    }

    public void setCondicion(Expresion condicion) {
        this.condicion = condicion;
    }

    public List<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(List<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public List<Instruccion> getSino() {
        return sino;
    }

    public void setSino(List<Instruccion> sino) {
        this.sino = sino;
    }

    public Si getSinosi() {
        return sinosi;
    }

    public void setSinosi(Si sinosi) {
        this.sinosi = sinosi;
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
