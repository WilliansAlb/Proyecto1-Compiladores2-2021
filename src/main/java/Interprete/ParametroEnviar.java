/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author willi
 */
public class ParametroEnviar implements Serializable{
    public static enum Tipo{
        ARREGLO,
        EXPRESION,
        LLAMADA,
        NOTA,
        ORDEN
    }
    private Tipo tipo;
    private Item arreglo;
    private Llamada llama;
    private String nota_orden;
    private Expresion expresion;

    public ParametroEnviar(Tipo tipo, Item arreglo, Llamada llama, String nota_orden, Expresion expresion) {
        this.tipo = tipo;
        this.arreglo = arreglo;
        this.llama = llama;
        this.nota_orden = nota_orden;
        this.expresion = expresion;
    }

    public Expresion getExpresion() {
        return expresion;
    }

    public void setExpresion(Expresion expresion) {
        this.expresion = expresion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Item getArreglo() {
        return arreglo;
    }

    public void setArreglo(Item arreglo) {
        this.arreglo = arreglo;
    }

    public Llamada getLlama() {
        return llama;
    }

    public void setLlama(Llamada llama) {
        this.llama = llama;
    }

    public String getNota_orden() {
        return nota_orden;
    }

    public void setNota_orden(String nota_orden) {
        this.nota_orden = nota_orden;
    }
    
}
