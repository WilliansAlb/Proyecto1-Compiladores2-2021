/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tablas;

import Interprete.*;

/**
 *
 * @author willi
 */
public class Asignacion {
    
    public final static int[][] asigna = {
        {Tipos.ENTERO,Tipos.EXCEPCION,Tipos.ENTERO,Tipos.ENTERO,Tipos.ENTERO},
        {Tipos.STRING,Tipos.STRING,Tipos.STRING,Tipos.STRING,Tipos.STRING},
        {Tipos.EXCEPCION,Tipos.EXCEPCION,Tipos.BOOLEAN,Tipos.EXCEPCION,Tipos.EXCEPCION},
        {Tipos.DECIMAL,Tipos.EXCEPCION,Tipos.EXCEPCION,Tipos.DECIMAL,Tipos.EXCEPCION},
        {Tipos.EXCEPCION,Tipos.EXCEPCION,Tipos.EXCEPCION,Tipos.EXCEPCION,Tipos.CARACTER}
    };
    
    public final static int[][] division = {
        {Tipos.ENTERO,Tipos.EXCEPCION,Tipos.ENTERO,Tipos.ENTERO,Tipos.ENTERO},
        {Tipos.STRING,Tipos.STRING,Tipos.STRING,Tipos.STRING,Tipos.STRING},
        {Tipos.EXCEPCION,Tipos.EXCEPCION,Tipos.BOOLEAN,Tipos.EXCEPCION,Tipos.EXCEPCION},
        {Tipos.DECIMAL,Tipos.EXCEPCION,Tipos.EXCEPCION,Tipos.DECIMAL,Tipos.EXCEPCION},
        {Tipos.EXCEPCION,Tipos.EXCEPCION,Tipos.EXCEPCION,Tipos.EXCEPCION,Tipos.CARACTER}
    };
    
    public int convertir_char_a_int(char convertir){
        return (int)convertir;
    }
    
    public int convertir_boolean_a_int(boolean convertir){
        return (convertir)?1:0;
    }
    
    public int convertir_decimal_a_int(double convertir){
        return (int)convertir;
    }
    
    public int convertir_int_a_char(int convertir){
        return (char)convertir;
    }
}
