/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author willi
 */
public class Canal implements Serializable{
    private int id;
    private List<Integer> notas;
    private int tiempo;

    public Canal(int id) {
        this.id = id;
        this.notas = new LinkedList<>();
        this.tiempo = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getNotas() {
        return notas;
    }

    public void setNotas(List<Integer> notas) {
        this.notas = notas;
    }

    public void agregar(int nota, int mili){
        int tiempos = (int)Math.round(mili/50);
        notas.add(nota);
        for (int i = 1; i < tiempos; i++) {
            notas.add(-nota);
        }
        this.tiempo += tiempos;
    }
    
    public int nota(int rep){
        if (rep<notas.size())
            return notas.get(rep);
        else
            return 0;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }
    
    
    
}
