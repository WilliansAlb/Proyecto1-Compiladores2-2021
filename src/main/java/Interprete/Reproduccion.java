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

    public Reproduccion(List<Canal> canales) {
        this.canales = canales;
    }

    public Reproduccion() {
    }

    
    
    public boolean agregar(int nota, int tiempo, int canal) {
        if (canales == null) {
            canales = new LinkedList<>();
        }
        for (Canal canale : canales) {
            if (canale.getId() == canal) {
                System.out.println("sigue");
                canale.agregar(nota, tiempo);
                return true;
            }
        }
        System.out.println("inicia");
        Canal nuevo = new Canal(canal);
        nuevo.agregar(nota, tiempo);
        canales.add(nuevo);
        return true;
    }

    public void crearCancion() throws InterruptedException {
        int max = 0;
        int cantidad_canales = canales.size();
        for (Canal canale : canales) {
            if (canale.getTiempo() > max) {
                max = canale.getTiempo();
            }
        }
        Synth s = new Synth();
        s.setInstrument(56);
        System.out.println(max);
        for (int i = 0; i < max; i++) {
            ArrayList<Integer> n = new ArrayList<>();
            for (int j = 0; j < cantidad_canales; j++) {
                int no = canales.get(j).nota(i);
                System.out.println("La nota es "+no);
                if (no > 0) {
                    n.add(no);
                    s.noteOn(no);
                } else {
                    n.add(0);
                }
            }
            Thread.sleep(500);
        }
        s.allNotesOff();
        System.out.println("termino");
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
    
    public void reproducir(){
        System.out.println(" numero "+canales.size());
        s.add(p);
        Play.midi(s);
    }

    public List<Canal> getCanales() {
        return canales;
    }

    public void setCanales(List<Canal> canales) {
        this.canales = canales;
    }
}
