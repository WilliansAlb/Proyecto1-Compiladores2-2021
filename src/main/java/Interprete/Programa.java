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
                pista.interpretar(tabla, reproducir);
            } else {
                pista.interpretar(tabla);
            }
        }
    }
}
