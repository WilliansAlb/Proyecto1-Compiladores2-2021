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
public class Switch extends Instruccion{
    Condicion condicion;
    List<Caso> casos;
    List<Instruccion> defecto;
    int linea;
    int columna;

    public Switch(Condicion condicion, List<Caso> casos, List<Instruccion> defecto, int linea, int columna) {
        this.condicion = condicion;
        this.casos = casos;
        this.defecto = defecto;
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
