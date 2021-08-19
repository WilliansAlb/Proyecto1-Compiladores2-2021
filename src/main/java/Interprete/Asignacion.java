/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolos;

/**
 *
 * @author willi
 */
public class Asignacion extends Instruccion {

    String id;
    int tipo;
    Expresion expresion;
    Instruccion instruccion;
    int linea;
    int columna;

    public Asignacion(String id, int tipo, Expresion expresion, Instruccion instruccion, int linea, int columna) {
        this.id = id;
        this.tipo = tipo;
        this.expresion = expresion;
        this.instruccion = instruccion;
        this.linea = linea;
        this.columna = columna;
    }
    
    @Override
    public void interpretar(Simbolos tabla) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Simbolos getTabla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
