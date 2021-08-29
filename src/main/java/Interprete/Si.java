/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

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
                        inst.interpretar(tabla);
                    }
                    tabla.eliminar_ambito();
                    return;
                } else {
                    if (sinosi != null) {
                        tabla.ambitos++;
                        sinosi.interpretar(tabla);
                        tabla.eliminar_ambito();
                        return;
                    } else {
                        if (sino != null) {
                            tabla.ambitos++;
                            for (Instruccion inst : sino) {
                                inst.interpretar(tabla);
                            }
                            tabla.eliminar_ambito();
                            return;
                        }
                    }
                }
            } else {
                System.out.println("La condición no es un valor booleano");
            }
        } else {
            System.out.println("La condición no es un valor booleano");
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
