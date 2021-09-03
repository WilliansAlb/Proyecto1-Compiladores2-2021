/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica1;

import Interprete.Reproduccion;


/**
 *
 * @author willi
 */
public class Cancion {
    private String id;
    private String duracion;
    private Reproduccion reproductor;
    private String img = "cancion.png";

    public Cancion(String id, String duracion, Reproduccion reproductor) {
        this.id = id;
        this.duracion = duracion;
        this.reproductor = reproductor;
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
    
    
}
