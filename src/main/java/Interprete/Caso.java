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
public class Caso {
    private Primitivo valor;
    private List<Instruccion> instrucciones;
    private int linea;
    private int columna;

    public Caso(Primitivo valor, List<Instruccion> instrucciones, int linea, int columna) {
        this.valor = valor;
        this.instrucciones = instrucciones;
        this.linea = linea;
        this.columna = columna;
    }
    
    public boolean tieneBreak(Simbolos tabla, Object valor, String tipo){
        
        return false;
    }

    public Primitivo getValor() {
        return valor;
    }

    public void setValor(Primitivo valor) {
        this.valor = valor;
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
    
}
