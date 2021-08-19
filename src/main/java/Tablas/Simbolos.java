/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tablas;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author willi
 */
public class Simbolos {
    List<Simbolo> tabla;
    
    public Simbolos(){
        this.tabla = new LinkedList<>();
    }
    
    public void agregar(Simbolo agregar){
        tabla.add(agregar);
    }
    
}
