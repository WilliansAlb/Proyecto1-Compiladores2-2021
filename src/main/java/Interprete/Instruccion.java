/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolos;
import java.io.Serializable;

/**
 *
 * @author willi
 */
public abstract class Instruccion{
    
    public abstract void interpretar(Simbolos tabla);
    
    public abstract Simbolos getTabla();
}
