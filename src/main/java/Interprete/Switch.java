/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolo;
import Tablas.Simbolos;
import java.util.List;

/**
 *
 * @author willi
 */
public class Switch extends Instruccion {

    private String id;
    private List<Caso> casos;
    private List<Instruccion> defecto;
    private int linea;
    private int columna;

    public Switch(String id, List<Caso> casos, List<Instruccion> defecto, int linea, int columna) {
        this.id = id;
        this.casos = casos;
        this.defecto = defecto;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public void interpretar(Simbolos tabla) {
        Simbolo valor = tabla.obtener(id);
        if (casos == null) {
            tabla.ambitos++;
            for (Instruccion defecto1 : defecto) {
                defecto1.interpretar(tabla);
            }
            tabla.eliminar_ambito();
        } else {
            for (Caso caso : casos) {
                Primitivo val = caso.getValor();
                if (val.asignar(valor.getTipo())) {
                    if (val.toString().equalsIgnoreCase(valor.getDatos().get(0).toString())){
                        for (Instruccion inst : caso.getInstrucciones()) {
                            inst.interpretar(tabla);
                        }
                    }
                } else {
                    System.out.println("Problema: no puedes comparar ese tipo de dato");
                    break;
                }
            }
        }
    }

    @Override
    public Simbolos getTabla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Caso> getCasos() {
        return casos;
    }

    public void setCasos(List<Caso> casos) {
        this.casos = casos;
    }

    public List<Instruccion> getDefecto() {
        return defecto;
    }

    public void setDefecto(List<Instruccion> defecto) {
        this.defecto = defecto;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

}
