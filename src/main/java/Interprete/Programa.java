/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolo;
import Tablas.Simbolos;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author willi
 */
public class Programa {

    List<Pista> pistas;

    public Programa() {
        this.pistas = new LinkedList<>();
    }

    public Programa(List<Pista> pistas) {
        this.pistas = pistas;
    }

    public void interpretar(Simbolos tabla, boolean reproducir) {
        tabla.ambitos = 0;
        List<Object> ls = new LinkedList<>();
        tabla.agregar(new Simbolo("$pistas", "$pistas", null, ls, tabla.ambitos));
        for (Pista pista : pistas) {
            tabla.obtener("$pistas").getDatos().add(pista);
            if (reproducir) {
                System.out.println("MOSTRANDO PISTA " + pista.id);
                tabla.ambitos++;
                pista.interpretar(tabla, reproducir);
                tabla.eliminar_ambito();
            } else {
                pista.interpretar(tabla);
            }
        }
    }

    public String interpretar_unica_cancion(Simbolos tabla) {
        tabla.ambitos = 0;
        List<Object> ls = new LinkedList<>();
        if (tabla.obtener("$pistas") == null) {
            tabla.agregar(new Simbolo("$pistas", "$pistas", null, ls, tabla.ambitos));
        }
        if (pistas.size() == 1) {
            tabla.obtener("$pistas").getDatos().add(pistas.get(0));
            pistas.get(0).interpretar(tabla, true);
            return "interpretada";
        } else {
            if (!pistas.isEmpty()) {
                pistas.get(0).interpretar(tabla);
            }
            return "mas";
        }
    }
    
    public String interpretar2(Simbolos tabla){
        if (pistas.size() == 1) {
            tabla.obtener("$pista").setDatos(new LinkedList<>());
            tabla.obtener("$pista").getDatos().add(pistas.get(0));
            pistas.get(0).interpretar2(tabla);
            return "interpretada";
        } else {
            return "mas";
        }
    }

    public void obtener_keeps(Simbolos tabla) {
        pistas.get(0).obtener_keeps(tabla);
    }
}
