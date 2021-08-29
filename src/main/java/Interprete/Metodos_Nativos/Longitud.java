/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Metodos_Nativos;

import Interprete.Expresion;
import Interprete.Instruccion;
import Interprete.Primitivo;
import Tablas.Simbolos;
import java.util.List;

/**
 *
 * @author willi
 */
public class Longitud extends Instruccion {
    private Primitivo cadena;
    private String id_cadena;
    private List<Expresion> arreglo;
    private String id_arreglo;
    private int linea;
    private int columna;

    public Longitud(Primitivo cadena, String id_cadena, List<Expresion> arreglo, String id_arreglo, int linea, int columna) {
        this.cadena = cadena;
        this.id_cadena = id_cadena;
        this.arreglo = arreglo;
        this.id_arreglo = id_arreglo;
        this.linea = linea;
        this.columna = columna;
    }

    public String getId_cadena() {
        return id_cadena;
    }

    public void setId_cadena(String id_cadena) {
        this.id_cadena = id_cadena;
    }

    public String getId_arreglo() {
        return id_arreglo;
    }

    public void setId_arreglo(String id_arreglo) {
        this.id_arreglo = id_arreglo;
    }

    

    public Primitivo getCadena() {
        return cadena;
    }

    public void setCadena(Primitivo cadena) {
        this.cadena = cadena;
    }

    public List<Expresion> getArreglo() {
        return arreglo;
    }

    public void setArreglo(List<Expresion> arreglo) {
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
