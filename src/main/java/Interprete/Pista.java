/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolo;
import Tablas.Simbolos;
import java.util.ArrayList;
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

    public void interpretar(Simbolos tabla) {
        Reproduccion nuevo = new Reproduccion(null,id);
        List<Errores> listado = new LinkedList<>();
        ArrayList<Metodo> listado_metodos = new ArrayList<>();
        tabla.agregar_sistema("$reproducir", "$reproducir", nuevo);
        tabla.agregar_sistema("$mensaje", "$mensaje", "");
        tabla.agregar_sistema("$errores", "$errores", listado);
        for (Declaracion declaracione : declaraciones) {
            declaracione.interpretar(tabla);
        }
        int principales = 0;
        Metodo principal = new Metodo();
        for (Metodo metodo : metodos) {
            if (metodo.isPrincipal()) {
                principal = metodo;
                principales++;
            } else {
                listado_metodos.add(metodo);
            }
        }
        if (principales == 1) {
            principal.interpretar(tabla, metodos, null);
        } else {
            //
        }
    }

    public ArrayList<Metodo> verificar_repetidos(ArrayList<Metodo> met, Simbolos tabla) {
        ArrayList<Metodo> n = new ArrayList<>();
        n.add(met.get(0));
        for (int i = 1; i < met.size(); i++) {
            Metodo temp = met.get(i);
            for (int j = 0; j < n.size(); j++) {
                Metodo temp2 = n.get(j);
                if (temp.getId().equalsIgnoreCase(temp2.getId())) {
                    List<Parametro> param1 = temp.getParametros();
                    List<Parametro> param2 = temp.getParametros();
                    if (param1.size() == param2.size()) {
                        boolean rep = false;
                        for (int k = 0; k < param1.size(); k++) {
                            if (param1.get(k).getTipo().equalsIgnoreCase(param2.get(k).getTipo())) {
                                if (param1.get(k).isArreglo() == param2.get(k).isArreglo()) {
                                    rep = true;
                                    break;
                                }
                            }
                        } 
                        if (!rep){
                            n.add(temp);
                        }
                    } else {
                        n.add(temp);
                    }
                }
            }
        }
        return n;
    }
}
