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
public class Mientras extends Instruccion {

    private Expresion condicion;
    private List<Instruccion> instrucciones;
    private int linea;
    private int columna;

    public Mientras(Expresion condicion, List<Instruccion> instrucciones, int linea, int columna) {
        this.condicion = condicion;
        this.instrucciones = instrucciones;
        this.linea = linea;
        this.columna = columna;
    }
    
    @Override
    public void interpretar(Simbolos tabla) {
        if (((Primitivo)condicion.ejecutar(tabla)).getTipo().equalsIgnoreCase("boolean")){
            int conteo = 0;
            while((boolean)((Primitivo)condicion.ejecutar(tabla)).getValor()){
                for (Instruccion ins:instrucciones){
                    ins.interpretar(tabla);
                }
                if (conteo==20){
                    System.out.println("No tiene condicion de salida");
                    break;
                }
                conteo++;
            }
        } else {
            System.out.println("La condición no tiene un valor booleano aceptable");
        }
    }

    @Override
    public Simbolos getTabla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Expresion getCondicion() {
        return condicion;
    }

    public void setCondicion(Expresion condicion) {
        this.condicion = condicion;
    }

    public List<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(List<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
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
    
    
}
