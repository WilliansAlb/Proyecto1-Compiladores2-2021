/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Metodos_Nativos;

import Interprete.Expresion;
import Interprete.Instruccion;
import Interprete.Primitivo;
import Interprete.Termino;
import Tablas.Simbolos;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 *
 * @author willi
 */
public class Mensaje extends Instruccion {

    private Expresion mensaje;
    private int linea;
    private int columna;

    public Mensaje(Expresion mensaje, int linea, int columna) {
        this.mensaje = mensaje;
        this.linea = linea;
        this.columna = columna;
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

    public Expresion getMensaje() {
        return mensaje;
    }

    public void setMensaje(Expresion mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public void interpretar(Simbolos tabla) {
        Termino a = mensaje.ejecutar(tabla);
        if (a instanceof Primitivo) {
            Primitivo s = (Primitivo) a;
            if (!s.getTipo().equalsIgnoreCase("excepcion")) {
                String agregar = (tabla.obtener("$mensaje").getDatos().get(0).toString().isEmpty()) ? "" : tabla.obtener("$mensaje").getDatos().get(0).toString() + "\n";
                agregar += s.getValor().toString();
                tabla.obtener("$mensaje").getDatos().set(0, agregar);
                Platform.runLater(() -> {
                    ((TextArea) tabla.obtener("$consola").getDatos().get(0)).setText(tabla.obtener("$mensaje").getDatos().get(0).toString());
                });
            }
        }
    }

    @Override
    public Simbolos getTabla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
