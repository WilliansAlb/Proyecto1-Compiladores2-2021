/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolos;
import java.util.List;
import java_cup.runtime.Symbol;

/**
 *
 * @author willi
 */
public class Metodo_Retorno extends Termino {

    private String tipo;
    private String id;
    private List<Termino> parametros;
    private int linea;

    public Metodo_Retorno(String tipo, String id, List<Termino> parametros, int linea) {
        this.tipo = tipo;
        this.id = id;
        this.parametros = parametros;
        this.linea = linea;
    }

    @Override
    public Simbolos interpretar(Simbolos tabla) {
        for (Metodo metodo : tabla.getMetodos()) {
            if (metodo.getId().equalsIgnoreCase(id)) {
                boolean excepcion = false;
                for (int i = 0; i < metodo.getParametros().size(); i++) {
                    if (!metodo.getParametros().get(i).getTipo().equalsIgnoreCase(parametros.get(i).getTipo())) {
                        excepcion = true;
                    }
                }
            }
        }
        return tabla;
    }


    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTipo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Symbol getSymbol() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Termino> getParametros() {
        return parametros;
    }

    public void setParametros(List<Termino> parametros) {
        this.parametros = parametros;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

}
