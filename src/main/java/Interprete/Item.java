/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import java.util.List;

/**
 *
 * @author willi
 */
public class Item {
    private List<Item> hijos;
    private boolean esHoja;
    private Expresion dato;

    public Item(List<Item> hijos, boolean esHoja, Expresion dato) {
        this.hijos = hijos;
        this.esHoja = esHoja;
        this.dato = dato;
    }
    
    public List<Item> getHijos() {
        return hijos;
    }

    public void setHijos(List<Item> hijos) {
        this.hijos = hijos;
    }

    public boolean isEsHoja() {
        return esHoja;
    }

    public void setEsHoja(boolean esHoja) {
        this.esHoja = esHoja;
    }

    public Expresion getDato() {
        return dato;
    }

    public void setDato(Expresion dato) {
        this.dato = dato;
    }

    
    public void interpretar(List elementos){
    
    }
    
    
}
