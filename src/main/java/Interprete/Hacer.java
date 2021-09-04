/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolo;
import Tablas.Simbolos;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author willi
 */
public class Hacer extends Instruccion {

    private Expresion condicion;
    private List<Instruccion> instrucciones;
    private int linea;
    private int columna;

    public Hacer(Expresion condicion, List<Instruccion> instrucciones, int linea, int columna) {
        this.condicion = condicion;
        this.instrucciones = instrucciones;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public void interpretar(Simbolos tabla) {
        if (((Primitivo) condicion.ejecutar(tabla)).getTipo().equalsIgnoreCase("boolean")) {
            int conteo = 0;
            do {
                tabla.ambitos++;
                for (Instruccion ins : instrucciones) {
                    if (ins instanceof ContinuarSalir) {
                        ContinuarSalir c = (ContinuarSalir) ins;
                        if (c.isContinuar()) {
                            break;
                        } else {
                            tabla.eliminar_ambito();
                            return;
                        }
                    } else if (ins instanceof Retorno) {
                        tabla.eliminar_ambito();
                        return;
                    } else {
                        if (ins != null) {
                            ins.interpretar(tabla);
                        }
                        if (tabla.tieneBreak()) {
                            tabla.eliminar_ambito();
                            return;
                        }
                        if (tabla.tieneRetorno()){
                            tabla.eliminar_ambito();
                            return;
                        }
                    }
                }
                if (conteo>20){
                    tabla.eliminar_ambito();
                    break;
                }
                conteo++;
                tabla.eliminar_ambito();
            } while ((boolean) ((Primitivo) condicion.ejecutar(tabla)).getValor());
        } else {
            System.out.println("La condición no es un valor booleano");
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
