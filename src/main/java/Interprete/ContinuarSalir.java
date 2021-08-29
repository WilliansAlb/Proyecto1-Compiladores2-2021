/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

/**
 *
 * @author willi
 */
public class ContinuarSalir {
    private boolean continuar;
    private int linea;
    private int columna;

    public ContinuarSalir(boolean continuar, int linea, int columna) {
        this.continuar = continuar;
        this.linea = linea;
        this.columna = columna;
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

    
    
    
}
