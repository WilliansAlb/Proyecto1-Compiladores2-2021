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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author willi
 */
public class Pista {
    List<Metodo> metodos;
    List<Declaracion> declaraciones;
    List<String> extiende;
    String id;

    public Pista(List<Metodo> metodos, List<Declaracion> declaraciones, List<String> extiende, String id) {
        this.metodos = metodos;
        this.declaraciones = declaraciones;
        this.extiende = extiende;
        this.id = id;
    }
    
    public void interpretar() {
        Simbolos tabla = new Simbolos();
        tabla.ambitos = 0;
        Reproduccion nuevo = new Reproduccion();
        tabla.agregar_sistema("$reproducir","$reproducir", nuevo);
        tabla.agregar_sistema("$mensaje", "$mensaje", "");
        for (Declaracion declaracione : declaraciones) {
            declaracione.interpretar(tabla);
        }
        int principales = 0;
        Metodo principal = new Metodo();
        for (Metodo metodo : metodos) {
            if(metodo.isPrincipal()){
                principal = metodo;
                principales++;
            }
        }
        if (principales==1){
            principal.interpretar(tabla,metodos,null);
        } else {
            System.out.println("tienes muchos Principal, solo puedes tener uno");
        }
        Simbolo prueba = tabla.obtener("$reproducir");
        Reproduccion pru = (Reproduccion)prueba.getDatos().get(0);
        try {
            pru.crearCancion();
        } catch (InterruptedException ex) {
            Logger.getLogger(Pista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
