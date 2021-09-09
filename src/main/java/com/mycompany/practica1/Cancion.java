/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica1;

import Interprete.Reproduccion;
import Tablas.Simbolos;
import java.io.Serializable;


/**
 *
 * @author willi
 */
public class Cancion implements Serializable {
    private String id;
    private String duracion;
    private Reproduccion reproductor;
    private String img = "cancion.png";
    private String texto;
    private Simbolos sims;

    /**
     * 
     * @param id
     * @param duracion
     * @param reproductor
     * @param texto 
     */
    public Cancion(String id, String duracion, Reproduccion reproductor, String texto) {
        this.id = id;
        this.duracion = duracion;
        this.reproductor = reproductor;
        this.texto = texto;
    }

    public Cancion(String id, String duracion, Reproduccion reproductor, String texto, Simbolos sims) {
        this.id = id;
        this.duracion = duracion;
        this.reproductor = reproductor;
        this.texto = texto;
        this.sims = sims;
    }
    
    

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public Reproduccion getReproductor() {
        return reproductor;
    }

    public void setReproductor(Reproduccion reproductor) {
        this.reproductor = reproductor;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Simbolos getSims() {
        return sims;
    }

    public void setSims(Simbolos sims) {
        this.sims = sims;
    }
    
    
    
}
