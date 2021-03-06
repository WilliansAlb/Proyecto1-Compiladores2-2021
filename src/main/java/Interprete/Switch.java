/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolo;
import Tablas.Simbolos;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author willi
 */
public class Switch extends Instruccion implements Serializable {

    private String id;
    private List<Caso> casos;
    private List<Instruccion> defecto;
    private int linea;
    private int columna;

    public Switch(String id, List<Caso> casos, List<Instruccion> defecto, int columna, int linea) {
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
                if (defecto1 instanceof ContinuarSalir) {
                    ContinuarSalir cs = (ContinuarSalir) defecto1;
                    if (!cs.isContinuar()) {
                        tabla.eliminar_ambito();
                        return;
                    }
                } else if (defecto1 instanceof Retorno) {
                    defecto1.interpretar(tabla);
                    tabla.eliminar_ambito();
                    return;
                } else {
                    if (defecto1 != null) {
                        defecto1.interpretar(tabla);
                    }
                    if (tabla.tieneRetorno()) {
                        System.out.println("sube el retorno");
                        tabla.eliminar_ambito();
                        return;
                    }
                }
            }
            tabla.eliminar_ambito();
        } else {
            boolean sale = true;
            for (Caso caso : casos) {
                Primitivo val = caso.getValor();
                if (sale) {
                    if (val.asignar(valor.getTipo())) {
                        if (val.getValor().toString().equalsIgnoreCase(valor.getDatos().get(0).toString())) {
                            tabla.ambitos++;
                            for (Instruccion inst : caso.getInstrucciones()) {
                                if (inst instanceof ContinuarSalir) {
                                    ContinuarSalir cs = (ContinuarSalir) inst;
                                    if (!cs.isContinuar()) {
                                        tabla.eliminar_ambito();
                                        return;
                                    }
                                } else if (inst instanceof Retorno) {
                                    inst.interpretar(tabla);
                                    tabla.eliminar_ambito();
                                    return;
                                } else {
                                    if (inst != null) {
                                        inst.interpretar(tabla);
                                    }
                                    if (tabla.tieneRetorno()) {
                                        System.out.println("sube el retorno");
                                        tabla.eliminar_ambito();
                                        return;
                                    }
                                }
                            }
                            sale = false;
                            tabla.eliminar_ambito();
                        }
                    } else {
                        tabla.agregar_error("Semantico", "No puedes comparar estos valores", linea + 1, columna + 1);
                        break;
                    }
                } else {
                    tabla.ambitos++;
                    for (Instruccion inst : caso.getInstrucciones()) {
                        if (inst instanceof ContinuarSalir) {
                            ContinuarSalir cs = (ContinuarSalir) inst;
                            if (!cs.isContinuar()) {
                                tabla.eliminar_ambito();
                                return;
                            }
                        } else if (inst instanceof Retorno) {
                            inst.interpretar(tabla);
                            tabla.eliminar_ambito();
                            return;
                        } else {
                            if (inst != null) {
                                inst.interpretar(tabla);
                            }
                            if (tabla.tieneRetorno()) {
                                System.out.println("sube el retorno");
                                tabla.eliminar_ambito();
                                return;
                            }
                        }
                    }
                    tabla.eliminar_ambito();
                }
            }
            if (defecto != null) {
                tabla.ambitos++;
                for (Instruccion defecto1 : defecto) {
                    if (defecto1 instanceof ContinuarSalir) {
                        ContinuarSalir cs = (ContinuarSalir) defecto1;
                        if (!cs.isContinuar()) {
                            tabla.eliminar_ambito();
                            return;
                        }
                    } else if (defecto1 instanceof Retorno) {
                        defecto1.interpretar(tabla);
                        tabla.eliminar_ambito();
                        return;
                    } else {
                        if (defecto1 != null) {
                            defecto1.interpretar(tabla);
                        }
                        if (tabla.tieneRetorno()) {
                            System.out.println("sube el retorno");
                            tabla.eliminar_ambito();
                            return;
                        }
                    }
                }
                tabla.eliminar_ambito();
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
