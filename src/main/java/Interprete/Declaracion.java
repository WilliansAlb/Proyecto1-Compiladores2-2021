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
public class Declaracion extends Instruccion {
    List<String> ids;
    int tipo;
    Expresion expresion;
    boolean keep;
    int linea;
    int columna;

    public Declaracion(List<String> ids, int tipo, Expresion expresion, boolean keep, int linea, int columna) {
        this.ids = ids;
        this.tipo = tipo;
        this.expresion = expresion;
        this.keep = keep;
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
