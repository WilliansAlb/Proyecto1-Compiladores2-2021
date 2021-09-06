/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolos;
import java.io.Serializable;
import java.util.List;
import java_cup.runtime.Symbol;

/**
 *
 * @author willi
 */
public class Metodo_Retorno extends Termino implements Serializable{

    private Llamada llamada;

    public Metodo_Retorno(Llamada llamada) {
        this.llamada = llamada;
    }

    public Termino interpretar(Simbolos tabla) {
        llamada.interpretar(tabla);
        if (llamada.retorno != null) {
            return new Primitivo(llamada.retorno_tipo,llamada.getLinea(), llamada.getColumna(), llamada.retorno);
        } else {
            return new Primitivo("excepcion", llamada.getLinea(), llamada.getColumna(), "Este método no devuelve ningun valor");
        }
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTipo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Instruccion getLlamada() {
        return llamada;
    }

    public void setLlamada(Llamada llamada) {
        this.llamada = llamada;
    }

    @Override
    public int getLinea() {
        return llamada.getLinea();
    }

    @Override
    public int getColumna() {
        return llamada.getColumna();
    }

}
