/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolos;
import java_cup.runtime.Symbol;

/**
 *
 * @author willi
 */
public class Primitivo extends Termino {

    private String tipo;
    private Symbol simbolo;
    private Object valor;

    public Primitivo(String tipo, Symbol simbolo, Object valor) {
        this.tipo = tipo;
        this.simbolo = simbolo;
        this.valor = valor;
    }
    
    @Override
    public Simbolos interpretar(Simbolos tabla) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "Primitivo tipo "+tipo+" con valor "+valor.toString()+" en la linea "+simbolo.left+" y columna "+simbolo.right;
    }

    @Override
    public String getTipo() {
        return tipo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
    
    @Override
    public Symbol getSymbol() {
        return simbolo;
    }
    
}
