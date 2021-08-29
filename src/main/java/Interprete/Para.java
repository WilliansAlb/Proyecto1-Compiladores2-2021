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
public class Para {
    private String id;
    private int valor_inicial;
    private Expresion condicion;
    private Paso paso;

    public Para(String id, int valor_inicial, Expresion condicion, Paso paso) {
        this.id = id;
        this.valor_inicial = valor_inicial;
        this.condicion = condicion;
        this.paso = paso;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getValor_inicial() {
        return valor_inicial;
    }

    public void setValor_inicial(int valor_inicial) {
        this.valor_inicial = valor_inicial;
    }

    public Expresion getCondicion() {
        return condicion;
    }

    public void setCondicion(Expresion condicion) {
        this.condicion = condicion;
    }

    public Paso getPaso() {
        return paso;
    }

    public void setPaso(Paso paso) {
        this.paso = paso;
    }
    
    
}
