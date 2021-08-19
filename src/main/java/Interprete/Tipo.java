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
public class Tipo {
    String id;
    int tipo;
    Object valor;
    int dimension;
    List<Object> valores;
    int linea;
    int columna;

    public Tipo(String id, int tipo, Object valor, int dimension, List<Object> valores, int linea, int columna) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.dimension = dimension;
        this.valores = valores;
        this.linea = linea;
        this.columna = columna;
    }
}
