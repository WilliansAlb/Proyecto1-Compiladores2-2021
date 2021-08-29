/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Metodos_Nativos;

import Interprete.Expresion;
import Interprete.Instruccion;
import Interprete.Notas;
import Interprete.Primitivo;
import Interprete.Reproduccion;
import Interprete.Termino;
import Tablas.Simbolo;
import Tablas.Simbolos;

/**
 *
 * @author willi
 */
public class Esperar extends Instruccion{
    
    private Expresion tiempo;
    private Expresion canal;
    private int linea;
    private int columna;

    public Esperar(Expresion tiempo, Expresion canal, int linea, int columna) {
        this.tiempo = tiempo;
        this.canal = canal;
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
    
    public Expresion getTiempo() {
        return tiempo;
    }

    public void setTiempo(Expresion tiempo) {
        this.tiempo = tiempo;
    }

    public Expresion getCanal() {
        return canal;
    }

    public void setCanal(Expresion canal) {
        this.canal = canal;
    }

    @Override
    public void interpretar(Simbolos tabla) {
        Termino ti = tiempo.ejecutar(tabla);
        Termino ca = canal.ejecutar(tabla);
        if (ti instanceof Primitivo && ca instanceof Primitivo){
            Object tiem = ((Primitivo)ti).getValor();
            Object cana = ((Primitivo)ca).getValor();
            if (tiem instanceof Integer &&
                    cana instanceof Integer){
                int tiempa = (int)tiem;
                int canas = (int)cana;
                Notas ns = new Notas();
                Simbolo s = tabla.obtener("$reproducir");
                String respuesta = (((Reproduccion)s.getDatos().get(0)).agregar(0, tiempa, canas))?"Agregada correctamente":"No se pudo agregar";
                tabla.cambiar("$reproducir", s.getDatos());
                System.out.println(respuesta);
            } else {
                System.out.println("Uno de los parametros no es un numero");
            }
        } else {
            System.out.println("no sé que ocurrió master");
        }
    }

    @Override
    public Simbolos getTabla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
