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
    List<Variable> parametros;
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
    
    public boolean interpretar(Simbolos tabla){
        for (Instruccion instruccione : instrucciones) {
            if (instruccione instanceof Declaracion){
                
            } else if (instruccione instanceof Asignacion){
                
            } else if (instruccione instanceof Si){
                Si condicional = (Si)instruccione;
                condicional.interpretar(tabla);
            }
        }
        return true;
    }

    public List<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(List<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public Simbolos getTabla() {
        return tabla;
    }

    public void setTabla(Simbolos tabla) {
        this.tabla = tabla;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isRetorno() {
        return retorno;
    }

    public void setRetorno(boolean retorno) {
        this.retorno = retorno;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isKeep() {
        return keep;
    }

    public void setKeep(boolean keep) {
        this.keep = keep;
    }

    public Tipo getRetornando() {
        return retornando;
    }

    public void setRetornando(Tipo retornando) {
        this.retornando = retornando;
    }

    public List<Variable> getParametros() {
        return parametros;
    }

    public void setParametros(List<Variable> parametros) {
        this.parametros = parametros;
    }
    
    public String acepta(){
        String retorno = "(";
        for (Variable parametro : parametros) {
            retorno+=parametro.getTipo()+",";
        }
        return retorno+")";
    }
}
