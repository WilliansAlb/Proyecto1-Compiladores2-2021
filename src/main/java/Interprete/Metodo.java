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
public class Metodo {
    List<Instruccion> instrucciones;
    Simbolos tabla;
    //List<Token>
    String id;
    boolean retorno;
    String tipo;
    boolean keep;
    Tipo retornando;

    public Metodo(List<Instruccion> instrucciones, Simbolos tabla, String id, boolean retorno, String tipo, boolean keep, Tipo retornando) {
        this.instrucciones = instrucciones;
        this.tabla = tabla;
        this.id = id;
        this.retorno = retorno;
        this.tipo = tipo;
        this.keep = keep;
        this.retornando = retornando;
    }
    
    public void interpretar(){
        
    }
    
}
