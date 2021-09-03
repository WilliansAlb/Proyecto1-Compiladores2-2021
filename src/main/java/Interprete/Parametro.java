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
public class Parametro {
    private String id;
    private String tipo;
    private boolean arreglo;
    private int linea;
    private int columna;

    public Parametro(String id, String tipo, boolean arreglo, int linea, int columna) {
        this.id = id;
        this.tipo = tipo;
        this.arreglo = arreglo;
        this.linea = linea;
        this.columna = columna;
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
    
    

    public boolean isArreglo() {
        return arreglo;
    }

    public void setArreglo(boolean arreglo) {
        this.arreglo = arreglo;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
}
