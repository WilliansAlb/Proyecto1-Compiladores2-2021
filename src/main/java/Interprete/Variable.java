/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolos;
import java.util.List;
import java_cup.runtime.Symbol;

/**
 *
 * @author willi
 */
public class Variable extends Termino{

    private String id;
    private String tipo;
    private List<Expresion> dimensiones;
    private List<Expresion> datos;
    private int linea;

    public Variable(String id, String tipo, List<Expresion> dimensiones, List<Expresion> datos, int linea) {
        this.id = id;
        this.tipo = tipo;
        this.dimensiones = dimensiones;
        this.datos = datos;
        this.linea = linea;
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

    public List<Expresion> getDatos() {
        return datos;
    }

    public void setDatos(List<Expresion> datos) {
        this.datos = datos;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }
    
    @Override
    public Simbolos interpretar(Simbolos tabla) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTipo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Symbol getSymbol() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
