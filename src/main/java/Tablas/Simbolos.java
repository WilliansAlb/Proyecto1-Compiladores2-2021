/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tablas;

import Interprete.Metodo;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author willi
 */
public class Simbolos {
    private List<Simbolo> tabla;
    private List<Metodo> metodos;
    
    public Simbolos(){
        this.tabla = new LinkedList<>();
    }
    
    public void agregar(Simbolo agregar){
        tabla.add(agregar);
    }

    public List<Simbolo> getTabla() {
        return tabla;
    }

    public void setTabla(List<Simbolo> tabla) {
        this.tabla = tabla;
    }

    public List<Metodo> getMetodos() {
        return metodos;
    }

    public void setMetodos(List<Metodo> metodos) {
        this.metodos = metodos;
    }
    
}
