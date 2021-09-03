/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import com.mycompany.practica1.Synth;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static jm.constants.Durations.C;
import jm.music.data.CPhrase;
import jm.music.data.Part;
import jm.music.data.Score;
import jm.util.Play;

/**
 *
 * @author willi
 */
public class Reproduccion {

    private static Score s = new Score("CPhrase class example");
    private List<Canal> canales;
    private static Part p = new Part("Piano", 0, 0);
    private String id;

    public Reproduccion(List<Canal> canales, String id) {
        this.canales = canales;
        this.id = id;
    }

    public Reproduccion() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    
    public boolean agregar(int nota, int tiempo, int canal) {
        if (canales == null) {
            canales = new LinkedList<>();
        }
        for (Canal canale : canales) {
            if (canale.getId() == canal) {
                canale.agregar(nota, tiempo);
                return true;
            }
        }
        Canal nuevo = new Canal(canal);
        nuevo.agregar(nota, tiempo);
        canales.add(nuevo);
        return true;
    }
    
    public int max(){
        int max = 0;
        for (Canal canale : canales) {
            if (canale.getTiempo() > max) {
                max = canale.getTiempo();
            }
        }
        return max;
    }

    public List<Canal> getCanales() {
        return canales;
    }

    public void setCanales(List<Canal> canales) {
        this.canales = canales;
    }
}
