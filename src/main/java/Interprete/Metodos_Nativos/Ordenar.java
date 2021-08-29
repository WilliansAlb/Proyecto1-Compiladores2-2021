/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Metodos_Nativos;

import Interprete.Expresion;
import Interprete.Instruccion;
import Tablas.Simbolos;
import java.util.List;

/**
 *
 * @author willi
 */
public class Ordenar extends Instruccion {

    @Override
    public void interpretar(Simbolos tabla) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Simbolos getTabla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static enum Ordenamiento{
        ASCENDENTE,
        DESCENDENTE,
        PARES,
        IMPARES,
        PRIMOS
    }
    
    public String id_arreglo;
    public List<Expresion> arreglo;
    public Ordenamiento orden;

    public Ordenar(String id_arreglo, List<Expresion> arreglo, Ordenamiento orden) {
        this.id_arreglo = id_arreglo;
        this.arreglo = arreglo;
        this.orden = orden;
    }

    public String getId_arreglo() {
        return id_arreglo;
    }

    public void setId_arreglo(String id_arreglo) {
        this.id_arreglo = id_arreglo;
    }

    public List<Expresion> getArreglo() {
        return arreglo;
    }

    public void setArreglo(List<Expresion> arreglo) {
        this.arreglo = arreglo;
    }

    public Ordenamiento getOrden() {
        return orden;
    }

    public void setOrden(Ordenamiento orden) {
        this.orden = orden;
    }
    
    
}
