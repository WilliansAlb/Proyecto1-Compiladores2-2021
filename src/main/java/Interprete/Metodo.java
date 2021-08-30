/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolo;
import Tablas.Simbolos;
import java.util.List;

/**
 *
 * @author willi
 */
public class Metodo {

    String id;
    String tipo;
    List<Parametro> parametros;
    List<Instruccion> instrucciones;
    boolean retorno;
    boolean keep;
    boolean principal;

    public Metodo(String id, String tipo, List<Parametro> parametros, List<Instruccion> instrucciones, boolean retorno, boolean keep, boolean principal) {
        this.id = id;
        this.tipo = tipo;
        this.parametros = parametros;
        this.instrucciones = instrucciones;
        this.retorno = retorno;
        this.keep = keep;
        this.principal = principal;
    }

    public Metodo() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void interpretar(Simbolos tabla, List<Metodo> metodos, List<Expresion> param) {
        tabla.ambitos++;
        if (retorno){
            tabla.agregar_sistema("$retorno", tipo, null);
        }
        if (param == null) {
            for (Instruccion instruccion : instrucciones) {
                instruccion.interpretar(tabla);
            }
        } else {
            System.out.println("codigo cuando si hay parametros");
        }
        if (retorno){
            if (tabla.obtener("$retorno").getDatos().get(0)==null){
                System.out.println("falta retorno");
            }
        }
        tabla.eliminar_ambito();
    }

    public List<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(List<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
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

    public List<Parametro> getParametros() {
        return parametros;
    }

    public void setParametros(List<Parametro> parametros) {
        this.parametros = parametros;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

}
