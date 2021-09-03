/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Metodos_Nativos;

import Interprete.Expresion;
import Interprete.Instruccion;
import Interprete.Item;
import Tablas.Simbolos;
import java.util.List;

/**
 *
 * @author willi
 */
public class Sumarizar extends Instruccion {
    private String id_arreglo;
    private Item arreglo;
    private int linea;
    private int columna;

    public Sumarizar(String id_arreglo, Item arreglo, int linea, int columna) {
        this.id_arreglo = id_arreglo;
        this.arreglo = arreglo;
        this.linea = linea;
        this.columna = columna;
    }

    public String getId_arreglo() {
        return id_arreglo;
    }

    public void setId_arreglo(String id_arreglo) {
        this.id_arreglo = id_arreglo;
    }

    public Item getArreglo() {
        return arreglo;
    }

    public void setArreglo(Item arreglo) {
        this.arreglo = arreglo;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Simbolos getTabla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
