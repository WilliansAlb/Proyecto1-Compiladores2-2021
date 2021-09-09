/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import java.util.ArrayList;

/**
 *
 * @author willi
 */
public class Keep {
    private String id_pista;
    private ArrayList<Metodo> metodos_keep = new ArrayList<>();
    private ArrayList<Declaracion> declaracion_keep = new ArrayList<>();

    public Keep(String id_pista, ArrayList<Metodo> metodos_keep, ArrayList<Declaracion> declaracion_keep) {
        this.id_pista = id_pista;
        this.metodos_keep = metodos_keep;
        this.declaracion_keep = declaracion_keep;
    }

    public String getId_pista() {
        return id_pista;
    }

    public void setId_pista(String id_pista) {
        this.id_pista = id_pista;
    }

    public ArrayList<Metodo> getMetodos_keep() {
        return metodos_keep;
    }

    public void setMetodos_keep(ArrayList<Metodo> metodos_keep) {
        this.metodos_keep = metodos_keep;
    }

    public ArrayList<Declaracion> getDeclaracion_keep() {
        return declaracion_keep;
    }

    public void setDeclaracion_keep(ArrayList<Declaracion> declaracion_keep) {
        this.declaracion_keep = declaracion_keep;
    }
    
    public boolean tieneMetodos(){
        return !metodos_keep.isEmpty();
    }
    
    public boolean tieneDeclaraciones(){
        return !declaracion_keep.isEmpty();
    }
}
