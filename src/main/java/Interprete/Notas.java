/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Interprete.Metodos_Nativos.Reproducir;
import java.io.Serializable;

/**
 *
 * @author willi
 */
public class Notas implements Serializable{
    public int notas[][] = {
        {0,24,36,48,60,72,84,96,108},
        {0,25,37,49,61,73,85,97,0},
        {0,26,38,50,62,74,86,98,0},
        {0,27,39,51,63,75,87,99,0},
        {0,28,40,52,64,76,88,100,0},
        {0,29,41,53,65,77,89,101,0},
        {0,30,42,54,66,78,90,102,0},
        {0,31,43,55,67,79,91,103,0},
        {0,32,44,56,68,80,92,104,0},
        {21,33,45,57,69,81,93,105,0},
        {22,34,46,58,70,82,94,106,0},
        {23,35,47,59,71,83,95,107,0}
    };
    
    public int obtener_nota(String nota, int octava){
        switch(nota){
            case "do":
                return notas[0][octava];
            case "re":
                return notas[2][octava];
            case "mi":
                return notas[4][octava];
            case "fa":
                return notas[5][octava];
            case "sol":
                return notas[7][octava];
            case "la":
                return notas[9][octava];
            case "si":
                return notas[11][octava];
            case "do#":
                return notas[1][octava];
            case "re#":
                return notas[3][octava];
            case "mi#":
                return 0;
            case "fa#":
                return notas[6][octava];
            case "sol#":
                return notas[8][octava];
            case "la#":
                return notas[10][octava];
            case "si#":
                return 0;
            default:
                return 0;
        }
    }
}
