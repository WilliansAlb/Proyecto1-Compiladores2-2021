/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tablas;

import java.util.List;

/**
 *
 * @author willi
 */
public class Simbolo {
    private String id;
    private String tipo;
    private List<Integer> dimensiones;
    private List<Object> datos;
    private int ambito;

    public Simbolo(String id, String tipo, List<Integer> dimensiones, List<Object> datos, int ambito) {
        this.id = id;
        this.tipo = tipo;
        this.dimensiones = dimensiones;
        this.datos = datos;
        this.ambito = ambito;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Integer> getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(List<Integer> dimensiones) {
        this.dimensiones = dimensiones;
    }

    public List<Object> getDatos() {
        return datos;
    }

    public void setDatos(List<Object> datos) {
        this.datos = datos;
    }

    public int getAmbito() {
        return ambito;
    }

    public void setAmbito(int ambito) {
        this.ambito = ambito;
    }
    
}
