/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolos;
import java.io.Serializable;
import java.util.List;
import java_cup.runtime.Symbol;

/**
 *
 * @author willi
 */
public class Identificador extends Termino implements Serializable{

    private String id;
    private boolean isArreglo;
    private List<Expresion> dimensiones;
    private int linea;
    private int columna;

    public Identificador(String id, List<Expresion> dimensiones, boolean isArreglo, int linea, int columna) {
        this.id = id;
        this.dimensiones = dimensiones;
        this.linea = linea;
        this.isArreglo = isArreglo;
        this.columna = columna;
    }
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Expresion> getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(List<Expresion> dimensiones) {
        this.dimensiones = dimensiones;
    }
    
    @Override
    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }
    
    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTipo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public boolean isIsArreglo() {
        return isArreglo;
    }

    public void setIsArreglo(boolean isArreglo) {
        this.isArreglo = isArreglo;
    }

    @Override
    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    
}
