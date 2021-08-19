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
public class Pista {
    List<Metodo> metodos;
    List<Declaracion> declaraciones;
    Simbolos tabla;
    List<Pista> extiende;
    String id;

    public Pista(List<Metodo> metodos, List<Declaracion> declaraciones, Simbolos tabla, List<Pista> extiende, String id) {
        this.metodos = metodos;
        this.declaraciones = declaraciones;
        this.tabla = tabla;
        this.extiende = extiende;
        this.id = id;
    }
    
    public void interpretar(){
    
    }
}
