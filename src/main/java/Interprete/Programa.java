/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolos;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author willi
 */
public class Programa {
    List<Pista> pistas;

    public Programa() {
        this.pistas = new LinkedList<>();
    }
    
    public Programa(List<Pista> pistas) {
        this.pistas = pistas;
    }
   
    public void interpretar(){
        for (Pista pista:pistas) {
            pista.interpretar();
        }
    }
}
