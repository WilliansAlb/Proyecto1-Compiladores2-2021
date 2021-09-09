/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica1;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author willi
 */
public class Lista implements Serializable{

    private String id;
    private ArrayList<String> canciones;
    private boolean circular;
    private boolean random;
    public final String IMG_LISTA = "lista.png";
    public final String IMG_CIRCULAR = "circular.png";
    public final String IMG_RANDOM = "aleatorio.png";

    public Lista() {

    }

    public boolean agregar_id(String id) {
        if (this.id != null) {
            return false;
        } else {
            this.id = id;
            return true;
        }
    }

    public String agregar_canciones(ArrayList<String> canciones1) {
        if (this.canciones != null) {
            return "tiene";
        } else {
            if (canciones1 != null) {
                if (!canciones1.isEmpty()) {
                    for (int j = 0; j < canciones1.size()-1; j++) {
                        for (int i = j+1; i < canciones1.size(); i++) {
                            System.out.println(i);
                            if (canciones1.get(j).equalsIgnoreCase(canciones1.get(i))) {
                                return "repetida";
                            }
                        }
                    }
                }
            }
            this.canciones = canciones1;
            return "exito";
        }
    }

    public Lista(String id, ArrayList<String> canciones, boolean circular, boolean random) {
        this.id = id;
        this.canciones = canciones;
        this.circular = circular;
        this.random = random;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getCanciones() {
        return canciones;
    }

    public void setCanciones(ArrayList<String> canciones) {
        this.canciones = canciones;
    }

    public boolean isCircular() {
        return circular;
    }

    public void setCircular(boolean circular) {
        this.circular = circular;
    }

    public boolean isRandom() {
        return random;
    }

    public void setRandom(boolean random) {
        this.random = random;
    }

}
