/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolo;
import Tablas.Simbolos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author willi
 */
public class Pista implements Serializable {

    List<Metodo> metodos;
    List<Declaracion> declaraciones;
    List<String> extiende;
    String id;
    Reproduccion rep;

    public Pista(List<Metodo> metodos, List<Declaracion> declaraciones, List<String> extiende, String id) {
        this.metodos = metodos;
        this.declaraciones = declaraciones;
        this.extiende = extiende;
        this.id = id;
    }

    public void interpretar(Simbolos tabla) {
        Reproduccion nuevo = new Reproduccion(null, id);
        List<Errores> listado = new LinkedList<>();
        ArrayList<Metodo> listado_metodos = new ArrayList<>();
        tabla.agregar_sistema("$reproducir", "$reproducir", nuevo);
        tabla.agregar_sistema("$mensaje", "$mensaje", "");
        tabla.agregar_sistema("$errores", "$errores", listado);
        agregar_extiende(tabla);
        agregar_declaraciones(tabla);
        int principales = 0;
        Metodo principal = new Metodo();
        for (Metodo metodo : metodos) {
            if (metodo.isPrincipal()) {
                principal = metodo;
                principales++;
            } else {
                System.out.println("agrega metodo" + metodo.id);
                listado_metodos.add(metodo);
            }
        }
        tabla.agregar_sistema("$metodos", "$metodos", listado_metodos);
        if (principales == 1) {
            principal.interpretar(tabla);
        } else {
            System.out.println("tiene más de un método principal");
        }
    }

    public void interpretar(Simbolos tabla, boolean reproducir) {
        Reproduccion nuevo = new Reproduccion(null, id);
        List<Errores> listado = new LinkedList<>();
        ArrayList<Metodo> listado_metodos = new ArrayList<>();
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
            if (reproducir) {
                tabla.ambitos++;
                tabla.agregar_sistema("$reproducir", "$reproducir", nuevo);
                tabla.agregar_sistema("$mensaje", "$mensaje", "");
                tabla.agregar_sistema("$errores", "$errores", listado);
                ArrayList<Metodo> m = agregar_extiende(tabla);
                agregar_declaraciones(tabla);
                if (!m.isEmpty()) {
                    listado_metodos.addAll(m);
                }
                tabla.agregar_sistema("$metodos", "$metodos", listado_metodos);
                principal.interpretar(tabla);
                Simbolo s = tabla.obtener("$reproducir");
                ((Pista) tabla.obtener("$pistas").getDatos().get(tabla.obtener("$pistas").getDatos().size() - 1)).setRep((Reproduccion) s.getDatos().get(0));
                tabla.eliminar_ambito();
            }
        } else {
            ((Pista) tabla.obtener("$pistas").getDatos().get(tabla.obtener("$pistas").getDatos().size() - 1)).setRep(null);
        }
    }

    public void obtener_keeps(Simbolos tabla) {
        ArrayList<Metodo> listado_metodos = new ArrayList<>();
        ArrayList<Declaracion> listado_declaraciones = new ArrayList<>();
        for (Metodo metodo : metodos) {
            if (!metodo.isPrincipal() && metodo.isKeep()) {
                listado_metodos.add(metodo);
            }
        }
        for (Declaracion declaracion : declaraciones) {
            if (declaracion.isKeep()) {
                listado_declaraciones.add(declaracion);
            }
        }
        Keep n = new Keep(id, listado_metodos, listado_declaraciones);
        tabla.obtener("$keep").getDatos().add(n);
    }

    public void interpretar2(Simbolos tabla) {
        Reproduccion nuevo = new Reproduccion(null, id);
        ArrayList<Metodo> listado_metodos = new ArrayList<>();
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
            tabla.agregar_sistema("$metodos", "$metodos", listado_metodos);
            agregar_declaraciones(tabla);
            tabla.agregar_sistema("$reproducir", "$reproducir", nuevo);
            tabla.agregar_sistema("$mensaje", "$mensaje", "");
            tabla.ambitos++;
            ArrayList<Metodo> m = agregar_extiende2(tabla);
            if (!m.isEmpty()) {
                listado_metodos.addAll(m);
            }
            principal.interpretar(tabla);
            Simbolo s = tabla.obtener("$reproducir");
            ((Pista) tabla.obtener("$pista").getDatos().get(0)).setRep((Reproduccion) s.getDatos().get(0));
            tabla.eliminar_ambito();
        } else {
            tabla.agregar_error("Semantico", "Tiene más de un metodo principal", 0, 0);
        }
    }

    public void agregar_declaraciones(Simbolos tabla) {
        for (Declaracion declaracione : declaraciones) {
            declaracione.interpretar(tabla);
        }
    }

    public ArrayList<Metodo> agregar_extiende(Simbolos tabla) {
        List<Object> pistas = (List) tabla.obtener("$pistas").getDatos();
        ArrayList<Metodo> met = new ArrayList<>();
        if (extiende != null) {
            for (int i = 0; i < pistas.size(); i++) {
                if (extiende.contains(((Pista) pistas.get(i)).getId())) {
                    for (Declaracion declaracion : ((Pista) pistas.get(i)).getDeclaraciones()) {
                        if (declaracion.isKeep()) {
                            declaraciones.add(declaracion);
                        }
                    }
                    for (Metodo metodo : ((Pista) pistas.get(i)).getMetodos()) {
                        if (metodo.isKeep() && !metodo.isPrincipal()) {
                            System.out.println("metodo agregado " + metodo.getId());
                            met.add(metodo);
                        }
                    }
                }
            }
        }
        return met;
    }

    public ArrayList<Metodo> agregar_extiende2(Simbolos tabla) {
        List<Object> pistas = (List) tabla.obtener("$keep").getDatos();
        ArrayList<Metodo> met = new ArrayList<>();
        if (extiende != null) {
            int conteo = 0;
            for (int i = 0; i < pistas.size(); i++) {
                Keep k = (Keep) pistas.get(i);
                if (extiende.contains(k.getId_pista())) {
                    if (k.tieneDeclaraciones()) {
                        for (Declaracion declaracion : k.getDeclaracion_keep()) {
                            declaraciones.add(declaracion);
                        }
                    }
                    if (k.tieneMetodos()) {
                        for (Metodo metodo : k.getMetodos_keep()) {
                            met.add(metodo);
                        }
                    }
                    conteo++;
                }
            }
            if (conteo!=extiende.size()){
                tabla.agregar_error("Semantico","Se solicitaron metodos de pistas que no existen", 0, 0);
            }
        }
        return met;
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
                        if (!rep) {
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

    public String obtener_duracion() {
        if (rep != null) {
            int tiempo = rep.max() * 50;
            int ntiempo = tiempo / 1000;
            int minutos = (int) (ntiempo / 60);
            int sec = (int) (ntiempo % 60);
            return (sec > 9) ? minutos + ":" + sec : minutos + ":0" + sec;
        }
        return "--:--";
    }

    public List<Metodo> getMetodos() {
        return metodos;
    }

    public void setMetodos(List<Metodo> metodos) {
        this.metodos = metodos;
    }

    public List<Declaracion> getDeclaraciones() {
        return declaraciones;
    }

    public void setDeclaraciones(List<Declaracion> declaraciones) {
        this.declaraciones = declaraciones;
    }

    public List<String> getExtiende() {
        return extiende;
    }

    public Reproduccion getRep() {
        return rep;
    }

    public void setRep(Reproduccion rep) {
        this.rep = rep;
    }

    public void setExtiende(List<String> extiende) {
        this.extiende = extiende;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
