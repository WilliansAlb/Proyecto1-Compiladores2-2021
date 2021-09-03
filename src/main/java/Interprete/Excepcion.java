/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolos;
import java_cup.runtime.Symbol;

/**
 *
 * @author willi
 */
public class Excepcion extends Termino {
    
    private static final String TIPO = "excepcion";
    private String id;
    private String razon;
    private int linea;
    private int columna;

    public Excepcion(String id, String razon, int linea) {
        this.id = id;
        this.razon = razon;
        this.linea = linea;
    }
    

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTipo() {
        return TIPO;
    }

    @Override
    public int getLinea() {
        return this.linea;
    }

    @Override
    public int getColumna() {
        return this.columna;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }
    
    
        
}
