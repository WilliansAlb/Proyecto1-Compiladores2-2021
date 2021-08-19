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
public class Caso {
    Tipo valor;
    List<Instruccion> instrucciones;
    boolean salir;
    int linea;
    int columna;

    public Caso(Tipo valor, List instrucciones, boolean salir, int linea, int columna) {
        this.valor = valor;
        this.instrucciones = instrucciones;
        this.salir = salir;
        this.linea = linea;
        this.columna = columna;
    }
    
    
}
