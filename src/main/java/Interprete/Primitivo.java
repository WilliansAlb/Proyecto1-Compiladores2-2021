/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Asignar;
import Tablas.Simbolos;
import java.io.Serializable;
import java_cup.runtime.Symbol;

/**
 *
 * @author willi
 */
public class Primitivo extends Termino{

    private Asignar as = new Asignar();
    private String tipo;
    private int linea;
    private int columna;
    private Object valor;

    public Primitivo(String tipo, int linea, int columna, Object valor) {
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
        this.valor = valor;
    }

    public Asignar getAs() {
        return as;
    }

    public void setAs(Asignar as) {
        this.as = as;
    }

    @Override
    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    @Override
    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public boolean asignar(String tipo_correcto){
        if (tipo_correcto.equalsIgnoreCase(tipo)){
            return true;
        } else if (tipo.equalsIgnoreCase("excepcion")) {
            return false;
        } else {
            int cambio = as.cambiar(tipo, tipo_correcto);
            if (cambio==Tipos.EXCEPCION){
                valor = "No se le puede asignar un "+tipo+" a un "+tipo_correcto;
                tipo = "excepcion";
                return false;
            } else if (cambio==Tipos.NUMERO){
                if (valor instanceof Boolean){
                    boolean a = (Boolean) valor;
                    int nuevo = (a)?1:0;
                    valor = nuevo;
                } else if (valor instanceof Double){
                    double a = (Double) valor;
                    int nuevo = (int) Math.round(a);
                    valor = nuevo;
                } else if (valor instanceof String){
                    valor = "No le puedes asignar un string a un entero";
                    tipo = "excepcion";
                    return false;
                } else {
                    char c = (char) valor;
                    int nuevo = (int)c;
                    valor = nuevo;
                }
                tipo = "numero";
            } else if (cambio==Tipos.STRING){
                String nuevo = valor.toString();
                valor = nuevo;
                tipo = "string";
            } else if (cambio==Tipos.DECIMAL){
                if (valor instanceof Boolean){
                    valor = "No le puedes asignar un boolean a un doble";
                    tipo = "excepcion";
                    return false;
                } else if (valor instanceof Integer){
                    double a = (int) valor;
                    valor = a;
                } else if (valor instanceof String){
                    valor = "No le puedes asignar un string a un entero";
                    tipo = "excepcion";
                    return false;
                } else {
                    char c = (char) valor;
                    double nuevo = (int)c;
                    valor = nuevo;
                }
                tipo = "decimal";
            } else if (cambio==Tipos.CARACTER){
                if (valor instanceof Integer){
                    int c = (int) valor;
                    if (c<255){
                        valor = (char)c;
                        tipo = "caracter";
                    } else {
                        valor = "El numero que se intento convertir a caracter excedio el limite de 255";
                        tipo = "excepcion";
                        return false;
                    }
                }
            } else {
                valor = "No se le puede asignar un "+tipo+" a un "+tipo_correcto;
                tipo = "excepcion";
                return false;
            }
            return true;
        }
    }
    
    @Override
    public String toString() {
        return "Primitivo tipo "+tipo+" con valor "+valor.toString()+" en la linea "+linea+" y columna "+columna;
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
}
