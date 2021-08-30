/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolo;
import Tablas.Simbolos;

/**
 *
 * @author willi
 */
public class Retorno extends Instruccion {

    private String tipo = "retorno";
    private Expresion valor;
    private int linea;
    private int columna;

    public Retorno(Expresion valor, int linea, int columna) {
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Expresion getValor() {
        return valor;
    }

    public void setValor(Expresion valor) {
        this.valor = valor;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    @Override
    public void interpretar(Simbolos tabla) {
        Simbolo retorno = tabla.obtener("$retorno");
        Termino a = valor.ejecutar(tabla);
        if (a instanceof Primitivo) {
            Primitivo p = (Primitivo) a;
            if (p.asignar(retorno.getTipo())) {
                tabla.obtener("$retorno").getDatos().set(0, p.getValor());
            } else {
                tabla.obtener("$retorno").getDatos().set(0, p.getValor());
                tabla.obtener("$retorno").setTipo("excepcion");
            }
        } else {
            tabla.obtener("$retorno").getDatos().set(0,"Error en la linea "+linea+", no es un tipo de dato primitivo el return");
            tabla.obtener("$retorno").setTipo("excepcion");
        }
    }

    @Override
    public Simbolos getTabla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
