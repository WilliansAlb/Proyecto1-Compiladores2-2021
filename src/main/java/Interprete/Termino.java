/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolos;
import java.io.Serializable;
import java_cup.runtime.Symbol;

/**
 *
 * @author willi
 */
public abstract class Termino implements Serializable{
    @Override
    public abstract String toString();
    
    public abstract String getTipo();
    
    public abstract int getLinea();
    
    public abstract int getColumna();
}
