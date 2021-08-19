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
public class Programa {
    List<Pista> pistas;
    List<Metodo> metodos;
    Simbolos tabla;

    public Programa(List<Pista> pistas, List<Metodo> metodos, Simbolos tabla) {
        this.pistas = pistas;
        this.metodos = metodos;
        this.tabla = tabla;
    }
   
    public void interpretar(){
    
    }
}
