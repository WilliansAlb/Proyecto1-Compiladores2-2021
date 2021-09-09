/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

/**
 *
 * @author willi
 */
public class Solicitud {

    public static enum TIPO {
        PISTA,
        LISTA,
        PISTANUEVA
    }

    private TIPO tipo;
    private String nombre;
    private Reproduccion r;

    public Solicitud() {
        this.r = new Reproduccion();
    }

    public boolean agregarTipo(String type) {
        System.out.println(type);
        if (tipo == null) {
            switch (type.toLowerCase()) {
                case "pista":
                    tipo = Solicitud.TIPO.PISTA;
                    return true;
                case "lista":
                    tipo = Solicitud.TIPO.LISTA;
                    return true;
                case "pistanueva":
                    tipo = Solicitud.TIPO.PISTANUEVA;
                    return true;
                default:
                    return false;
            }
        } else {
            return false;
        }
    }
    
    public boolean agregarNombre(String name) {
        if (nombre == null) {
            nombre = name;
            return true;
        } else {
            return false;
        }
    }
    
    public boolean agregarNota(String nota, int canal, int octava, int duracion){
        Notas ns = new Notas();
        int nueva_nota = ns.obtener_nota(nota, octava);
        return r.agregar(nueva_nota, duracion, canal);
    }

    public TIPO getTipo() {
        return tipo;
    }

    public void setTipo(TIPO tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Reproduccion getR() {
        return r;
    }

    public void setR(Reproduccion r) {
        this.r = r;
    }

}
