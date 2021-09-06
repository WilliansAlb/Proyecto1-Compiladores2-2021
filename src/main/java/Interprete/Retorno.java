/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolo;
import Tablas.Simbolos;
import java.io.Serializable;

/**
 *
 * @author willi
 */
public class Retorno extends Instruccion implements Serializable{

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
        Simbolo retorno = tabla.ultimo_retorno();
        Termino a = valor.ejecutar(tabla);
        if (a instanceof Primitivo) {
            Primitivo p = (Primitivo) a;
            if (retorno != null) {
                if (!retorno.getTipo().equalsIgnoreCase("void")) {
                    if (p.asignar(retorno.getTipo())) {
                        tabla.ultimo_retorno().getDatos().set(0, p.getValor());
                    } else {
                        tabla.ultimo_retorno().getDatos().set(0, p.getValor());
                        tabla.ultimo_retorno().setTipo("excepcion");
                    }
                } else {
                    if (retorno.getDatos().get(0)==null){
                        System.out.println("acá");
                        tabla.ultimo_retorno().getDatos().set(0, "excepcion");
                        tabla.agregar_error("Semantico", "El metodo es de tipo void y se le está intentando retornar un valor", linea, columna);
                    }
                }
            } else {
                tabla.agregar_error("Semantico", "No se ha declarado el retorno", linea, columna);
            }
        } else {
            tabla.obtener("$retorno").getDatos().set(0, "Error en la linea " + linea + ", no es un tipo de dato primitivo el return");
            tabla.obtener("$retorno").setTipo("excepcion");
        }
    }

    @Override
    public Simbolos getTabla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
