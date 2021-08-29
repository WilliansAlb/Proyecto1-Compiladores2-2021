/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Metodos_Nativos;

import Interprete.Expresion;
import Interprete.Instruccion;
import Interprete.Notas;
import Interprete.Primitivo;
import Interprete.Reproduccion;
import Interprete.Termino;
import Tablas.Simbolo;
import Tablas.Simbolos;
import jm.music.data.Note;
import jm.util.Play;

/**
 *
 * @author willi
 */
public class Reproducir extends Instruccion {

    @Override
    public Simbolos getTabla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static enum Nota {
        DO,
        RE,
        MI,
        FA,
        SOL,
        LA,
        SI,
        DOS,
        RES,
        MIS,
        FAS,
        SOLS,
        LAS,
        SIS
    }
    public String notas;
    public Nota nota;
    public Expresion octava;
    public Expresion tiempo;
    public Expresion canal;
    public int linea;
    public int columna;

    public Reproducir(String notas, Expresion octava, Expresion tiempo, Expresion canal, int linea, int columna) {
        this.notas = notas;
        this.octava = octava;
        this.tiempo = tiempo;
        this.canal = canal;
        this.linea = linea;
        this.columna = columna;
        actualizar_nota();
    }

    @Override
    public void interpretar(Simbolos tabla) {
        Termino oc = octava.ejecutar(tabla);
        Termino ti = tiempo.ejecutar(tabla);
        Termino ca = canal.ejecutar(tabla);
        if (oc instanceof Primitivo && ti instanceof Primitivo && ca instanceof Primitivo){
            Object octa = ((Primitivo)oc).getValor();
            Object tiem = ((Primitivo)ti).getValor();
            Object cana = ((Primitivo)ca).getValor();
            if (octa instanceof Integer &&
                    tiem instanceof Integer &&
                    cana instanceof Integer){
                int octavo = (int)octa;
                int tiempa = (int)tiem;
                int canas = (int)cana;
                Notas ns = new Notas();
                int note = ns.obtener_nota(notas, octavo);
                Simbolo s = tabla.obtener("$reproducir");
                String respuesta = (((Reproduccion)s.getDatos().get(0)).agregar(note, tiempa, canas))?"Agregada correctamente":"No se pudo agregar";
                tabla.cambiar("$reproducir", s.getDatos());
                System.out.println(respuesta);
            } else {
                System.out.println("Uno de los parametros no es un numero");
            }
        } else {
            System.out.println("no sé que ocurrió master");
        }
    }
    
    

    public final Nota actualizar_nota() {
        switch (notas.toLowerCase()) {
            case "do":
                nota = Reproducir.Nota.DO;
                return nota;
            case "re":
                nota = Reproducir.Nota.RE;
                return nota;
            case "mi":
                nota = Reproducir.Nota.MI;
                return nota;
            case "fa":
                nota = Reproducir.Nota.FA;
                return nota;
            case "sol":
                nota = Reproducir.Nota.SOL;
                return nota;
            case "la":
                nota = Reproducir.Nota.LA;
                return nota;
            case "si":
                nota = Reproducir.Nota.SI;
                return nota;
            case "do#":
                nota = Reproducir.Nota.DOS;
                return nota;
            case "re#":
                nota = Reproducir.Nota.RES;
                return nota;
            case "mi#":
                nota = Reproducir.Nota.MIS;
                return nota;
            case "fa#":
                nota = Reproducir.Nota.FAS;
                return nota;
            case "sol#":
                nota = Reproducir.Nota.SOLS;
                return nota;
            case "la#":
                nota = Reproducir.Nota.LAS;
                return nota;
            case "si#":
                nota = Reproducir.Nota.SIS;
                return nota;
            default:
                return Reproducir.Nota.DO;
        }
    }
}
