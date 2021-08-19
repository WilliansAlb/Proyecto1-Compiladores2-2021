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
public class Si extends Instruccion{
    Condicion condicion;
    List<Instruccion> instrucciones;
    List<Instruccion> sino;
    Si sinosi;
    int linea;
    int columna;

    public Si(Condicion condicion, List<Instruccion> instrucciones, List<Instruccion> sino, Si sinosi, int linea, int columna) {
        this.condicion = condicion;
        this.instrucciones = instrucciones;
        this.sino = sino;
        this.sinosi = sinosi;
        this.linea = linea;
        this.columna = columna;
    }
    
    @Override
    public void interpretar(Simbolos tabla) {
        
    }

    @Override
    public Simbolos getTabla() {
        return null;
    }

    
}
