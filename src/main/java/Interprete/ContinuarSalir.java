/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolos;
import java.io.Serializable;

/**
 *
 * @author willi
 */
public class ContinuarSalir extends Instruccion implements Serializable{
    private boolean continuar;
    private String tipo;
    private int linea;
    private int columna;

    public ContinuarSalir(boolean continuar, String tipo, int linea, int columna) {
        this.continuar = continuar;
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
    }

    public ContinuarSalir() {
        
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isContinuar() {
        return continuar;
    }

    public void setContinuar(boolean continuar) {
        this.continuar = continuar;
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

    @Override
    public void interpretar(Simbolos tabla) {
        
    }

    @Override
    public Simbolos getTabla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
