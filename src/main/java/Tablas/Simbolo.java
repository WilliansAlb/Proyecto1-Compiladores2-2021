/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tablas;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author willi
 */
public class Simbolo implements Serializable {
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

    /**
     * Método que dice la posicion donde se debe insertar el valor
     * @param coordenadas
     * @param dimension
     * @return la posicion en la lista donde se debe insertar el elemento
     */
    public int insertarEn(List<Integer> coordenadas, List<Integer> dimension) {
        if (coordenadas.size()>1){
            int coordenadaA = coordenadas.get(0);
            for (int i = 1; i < dimension.size(); i++) {
                coordenadaA*=dimension.get(i);
            }
            coordenadas.remove(0);
            dimension.remove(0);
            return coordenadaA+insertarEn(coordenadas,dimension);
        } else {
            return coordenadas.get(0);
        }
    }
    
}
