/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolo;
import Tablas.Simbolos;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author willi
 */
public class Metodo implements Serializable {

    String id;
    String tipo;
    List<Parametro> parametros;
    List<Instruccion> instrucciones;
    boolean retorno;
    boolean keep;
    boolean principal;
    Object objeto_retorno;
    String tipo_retorno;

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

    }

    public void interpretar(Simbolos tabla) {
        tabla.ambitos++;
        if (retorno) {
            tabla.agregar_sistema("$retorno"+id, tipo, null);
        } else {
            tabla.agregar_sistema("$retorno"+id, "void", null);
        }
        for (Instruccion instruccion : instrucciones) {
            instruccion.interpretar(tabla);
        }
        if (retorno) {
            if (tabla.ultimo_retorno() == null) {
                tabla.agregar_error("Semantico", "Falta el retorno del metodo "+id, -1, -1);
            } else {
                Simbolo ultimo = tabla.ultimo_retorno();
                if (ultimo != null) {
                    if (!ultimo.getTipo().equalsIgnoreCase("excepcion")) {
                        objeto_retorno = ultimo.getDatos().get(0);
                        tipo_retorno = ultimo.getTipo();
                    }
                }
            }
        }
        System.out.println("Ambito del metodo " + id);
        tabla.forEach(tabla1 -> {
            if (tabla1.getDatos() != null) {
                if (!tabla1.getDatos().isEmpty()) {
                    System.out.println("id: " + tabla1.getId() + " valor: " + tabla1.getDatos().get(0));
                } else {
                    System.out.println(tabla1.getId());
                }
            } else {
                System.out.println(tabla1.getId());
            }
        });
        for (int i = 0; i < 2; i++) {
            System.out.println("------------------------------------------------------------");
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
