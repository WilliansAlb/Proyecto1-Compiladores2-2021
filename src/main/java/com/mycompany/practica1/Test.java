/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica1;

import Analizador.LexerSolicitud;
import Analizador.parserSolicitud;
import Interprete.Solicitud;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author willi
 */
public class Test {

    public static final String TEXTO = "<solicitud>\n" +
"	<tipo>pistanueva</tipo>\n" +
"	<nombre>\"LaCucaracha\"</nombre>\n" +
"	<datos>\n" +
"		<canal>5</canal>\n" +
"		<nota>do</nota>\n" +
"		<octava>2</octava>\n" +
"		<duracion>976</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>5</canal>\n" +
"		<nota>re</nota>\n" +
"		<octava>2</octava>\n" +
"		<duracion>664</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>5</canal>\n" +
"		<nota>mi</nota>\n" +
"		<octava>2</octava>\n" +
"		<duracion>672</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>5</canal>\n" +
"		<nota>do#</nota>\n" +
"		<octava>2</octava>\n" +
"		<duracion>824</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>5</canal>\n" +
"		<nota>fa</nota>\n" +
"		<octava>2</octava>\n" +
"		<duracion>664</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>5</canal>\n" +
"		<nota>fa#</nota>\n" +
"		<octava>2</octava>\n" +
"		<duracion>504</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>5</canal>\n" +
"		<nota>sol</nota>\n" +
"		<octava>2</octava>\n" +
"		<duracion>1064</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>5</canal>\n" +
"		<nota>re#</nota>\n" +
"		<octava>2</octava>\n" +
"		<duracion>616</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>5</canal>\n" +
"		<nota>sol#</nota>\n" +
"		<octava>2</octava>\n" +
"		<duracion>520</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>5</canal>\n" +
"		<nota>la</nota>\n" +
"		<octava>2</octava>\n" +
"		<duracion>1025</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>5</canal>\n" +
"		<nota>la#</nota>\n" +
"		<octava>2</octava>\n" +
"		<duracion>432</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>5</canal>\n" +
"		<nota>si</nota>\n" +
"		<octava>2</octava>\n" +
"		<duracion>1199</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>10</canal>\n" +
"		<nota>do#</nota>\n" +
"		<octava>7</octava>\n" +
"		<duracion>816</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>10</canal>\n" +
"		<nota>re</nota>\n" +
"		<octava>7</octava>\n" +
"		<duracion>752</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>10</canal>\n" +
"		<nota>mi</nota>\n" +
"		<octava>7</octava>\n" +
"		<duracion>592</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>10</canal>\n" +
"		<nota>re#</nota>\n" +
"		<octava>7</octava>\n" +
"		<duracion>553</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>10</canal>\n" +
"		<nota>fa</nota>\n" +
"		<octava>7</octava>\n" +
"		<duracion>927</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>10</canal>\n" +
"		<nota>fa#</nota>\n" +
"		<octava>7</octava>\n" +
"		<duracion>440</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>10</canal>\n" +
"		<nota>sol</nota>\n" +
"		<octava>7</octava>\n" +
"		<duracion>1488</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>10</canal>\n" +
"		<nota>si</nota>\n" +
"		<octava>7</octava>\n" +
"		<duracion>712</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>10</canal>\n" +
"		<nota>do</nota>\n" +
"		<octava>7</octava>\n" +
"		<duracion>1296</duracion>\n" +
"	</datos>\n" +
"	<datos>\n" +
"		<canal>10</canal>\n" +
"		<nota>do#</nota>\n" +
"		<octava>7</octava>\n" +
"		<duracion>888</duracion>\n" +
"	</datos>\n" +
"</solicitud>";

    public static void main(String[] args) {
        parserSolicitud par = new parserSolicitud(new LexerSolicitud(new StringReader(TEXTO)));
        try {
            par.parse();
            Solicitud s = par.soli;
            if (s.getTipo() != null) {
                if (s.getTipo() == Solicitud.TIPO.LISTA) {
                    if (s.getNombre() != null) {
                        ArrayList<Lista> listemp = obtener();
                        ArrayList<Cancion> canstemp = obtener_canciones();
                        if (listemp != null) {
                            String retorno = "";
                            for (int i = 0; i < listemp.size(); i++) {
                                if (listemp.get(i).getId().equals(s.getNombre())) {
                                    String opcion1 = (listemp.get(i).isCircular()) ? "\"SI\"" : "\"NO\"";
                                    String opcion2 = (listemp.get(i).isRandom()) ? "\"SI\"" : "\"NO\"";
                                    retorno += "< lista nombre = \"" + s.getNombre() + "\" aleatoria = " + opcion2 + " circular = " + opcion1 + " >";
                                    if (listemp.get(i).getCanciones() != null) {
                                        if (!listemp.get(i).getCanciones().isEmpty()) {
                                            for (int j = 0; j < listemp.get(i).getCanciones().size(); j++) {
                                                for (int k = 0; k < canstemp.size(); k++) {
                                                    if (listemp.get(i).getCanciones().get(j).equals(canstemp.get(k).getId())) {
                                                        long duracion = (canstemp.get(k).getReproductor() != null) ? canstemp.get(k).getReproductor().max() * 50 : 0;
                                                        retorno += "\n< pista nombre = \"" + canstemp.get(k).getId() + "\" duracion = " + duracion + " >";
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    retorno += "\n</ lista >";
                                    System.out.println(retorno);
                                }
                            }
                            retorno = "< mensaje texto =\"La lista que solicitas no existe\" />";
                            System.out.println(retorno);
                        } else {
                            String retorno = "< mensaje texto = \"Aún no se han creado listas\" />";
                            System.out.println(retorno);
                        }
                    } else {
                        ArrayList<Lista> listemp = obtener();
                        if (listemp != null) {
                            String retorno = "<listas>\n";
                            for (int i = 0; i < listemp.size(); i++) {
                                int tem = (listemp.get(i).getCanciones() != null) ? listemp.get(i).getCanciones().size() : 0;
                                retorno += "< lista nombre = \"" + listemp.get(i).getId() + "\" pistas = " + tem + " >\n";
                            }
                            retorno += "</listas>";
                            System.out.println(retorno);
                        } else {
                            String retorno = "< mensaje texto = \"Aún no se han creado listas\" />";
                            System.out.println(retorno);
                        }
                    }
                } else if (s.getTipo() == Solicitud.TIPO.PISTA) {
                    if (s.getNombre() != null) {
                        ArrayList<Cancion> canstemp = obtener_canciones();
                        if (canstemp != null) {
                            String retorno = "";
                            for (int i = 0; i < canstemp.size(); i++) {
                                if (canstemp.get(i).getId().equals(s.getNombre())) {
                                    retorno += "< pista nombre = \"" + s.getNombre() + "\" >";
                                    if (canstemp.get(i).getReproductor().getCanales() != null) {
                                        for (int j = 0; j < canstemp.get(i).getReproductor().getCanales().size(); j++) {
                                            retorno += " \n\t< canal numero = " + canstemp.get(i).getReproductor().getCanales().get(j).getId() + " >";
                                            List<Integer> li = canstemp.get(i).getReproductor().getCanales().get(j).getNotas();
                                            List<Integer> diferentes = new LinkedList<>();
                                            List<Integer> repeticiones = new LinkedList<>();
                                            int conteo = 0;
                                            for (int k = 0; k < li.size(); k++) {
                                                if (li.get(k) != 0) {
                                                    if (conteo != 0) {
                                                        diferentes.add(0);
                                                        repeticiones.add(conteo);
                                                        conteo = 0;
                                                    }
                                                    diferentes.add(li.get(k));
                                                    repeticiones.add(1);
                                                } else {
                                                    conteo++;
                                                }
                                            }
                                            conteo = 0;
                                            int ultima = -1;

                                            List<Integer> definitivaNota = new LinkedList<>();
                                            List<Integer> definitivaRepeticion = new LinkedList<>();
                                            for (int k = 0; k < diferentes.size(); k++) {
                                                if (definitivaNota.isEmpty()) {
                                                    definitivaNota.add(diferentes.get(k));
                                                    conteo++;
                                                } else {
                                                    if (diferentes.get(k) == 0) {
                                                        if (conteo > 0) {
                                                            definitivaRepeticion.add(conteo);
                                                        }
                                                        definitivaNota.add(0);
                                                        definitivaRepeticion.add(repeticiones.get(k));
                                                        conteo = 0;
                                                    } else if (diferentes.get(k) < 0) {
                                                        conteo++;
                                                    } else {
                                                        if (conteo > 0) {
                                                            definitivaRepeticion.add(conteo);
                                                            conteo = 0;
                                                        }
                                                        definitivaNota.add(diferentes.get(k));
                                                        conteo++;
                                                    }
                                                }
                                            }
                                            definitivaRepeticion.add(conteo);
                                            for (int k = 0; k < definitivaNota.size(); k++) {
                                                retorno += "\n\t\t< nota duracion = " + (definitivaRepeticion.get(k) * 50) + " frecuencia" + definitivaNota.get(k) + " >";
                                            }
                                            retorno += " \n\t< canal />";
                                        }
                                    }
                                    retorno += " \n< pista />";
                                    System.out.println(retorno);
                                    break;
                                }
                            }
                            retorno = "< mensaje texto =\"La pista que solicitas no existe\" />";
                            System.out.println(retorno);
                        } else {
                            String retorno = "< mensaje texto = \"Aún no se han creado pistas\" />";
                            System.out.println(retorno);
                        }
                    } else {
                        ArrayList<Cancion> canstemp = obtener_canciones();
                        if (canstemp != null) {
                            String retorno = "<pistas>\n";
                            for (int i = 0; i < canstemp.size(); i++) {
                                long duracion = (canstemp.get(i).getReproductor() != null) ? canstemp.get(i).getReproductor().max() * 50 : 0;
                                retorno += "< pista nombre = \"" + canstemp.get(i).getId() + "\" duracion = " + duracion + " >\n";
                            }
                            retorno += "</pistas>";
                            System.out.println(retorno);
                        } else {
                            String retorno = "< mensaje texto = \"Aún no se han creado pistas\" />";
                            System.out.println(retorno);
                        }
                    }
                } else {
                    if (s.getR().getCanales() != null) {
                        int nuevo2 = s.getR().max() * 50;
                        int nuevo3 = nuevo2 / 1000;
                        int minutos = (int) (nuevo3 / 60);
                        int sec = (int) (nuevo3 % 60);
                        String tiempo_pasado = (sec > 9) ? minutos + ":" + sec : minutos + ":0" + sec;
                        Cancion nueva = new Cancion(s.getNombre(), tiempo_pasado, s.getR(), ">>Fue creada en el piano de la app movil");
                        ArrayList<Cancion> canstemp = obtener_canciones();
                        if (canstemp==null){
                            canstemp = new ArrayList<>();
                        }
                        canstemp.add(nueva);
                        File archivo = new File("src/main/resources/pistas.dat");
                        try {
                            FileOutputStream fos = new FileOutputStream(archivo);
                            ObjectOutputStream escribir = new ObjectOutputStream(fos);
                            escribir.writeObject(canstemp);
                            escribir.close();
                            fos.close();
                        } catch (Exception e) {
                            System.out.println("Error al escribir en el archivo. "
                                    + e.getMessage());
                        }
                        System.out.println("algo");
                    } else {
                        System.out.println("acá");
                    }
                }
            } else {
                System.out.println("Falta que agregues el tipo de solicitud");
            }
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("hola");
    }

    public static ArrayList<Lista> obtener() {
        File archivo = new File("src/main/resources/listas.dat");
        try {
            FileInputStream fis = new FileInputStream(archivo);
            ObjectInputStream leer;
            while (fis.available() > 0) {
                leer = new ObjectInputStream(fis);
                return (ArrayList) leer.readObject();
            }

        } catch (Exception e) {
            System.out.println("Error al leer el archivo. "
                    + e.getMessage());
        }
        return null;
    }

    public static ArrayList<Cancion> obtener_canciones() {
        File archivo = new File("src/main/resources/pistas.dat");
        try {
            FileInputStream fis = new FileInputStream(archivo);
            ObjectInputStream leer;
            while (fis.available() > 0) {
                leer = new ObjectInputStream(fis);
                return (ArrayList) leer.readObject();
            }

        } catch (Exception e) {
            System.out.println("Error al leer el archivo. "
                    + e.getMessage());
        }
        return null;
    }
}
