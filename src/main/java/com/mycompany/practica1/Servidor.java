/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica1;

import Analizador.LexerSolicitud;
import Analizador.parserSolicitud;
import Interprete.Solicitud;
import static com.mycompany.practica1.Test.TEXTO;
import static com.mycompany.practica1.Test.obtener;
import static com.mycompany.practica1.Test.obtener_canciones;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

/**
 *
 * @author willi
 */
public class Servidor extends Thread {

    ServerSocket servidor = null;
    Socket sc = null;
    DataInputStream in;
    DataOutputStream out;
    final int PUERTO = 5000;
    public ObservableList<Cancion> data_cancion;
    int m = 0;

    public Servidor() {

    }

    public Servidor(ObservableList<Cancion> data_cancion) {
        this.data_cancion = data_cancion;
    }

    @Override
    public void run() {
        try {
            servidor = new ServerSocket(PUERTO);
            System.out.println("servidor iniciado");
            while (true) {
                sc = servidor.accept();

                System.out.println("Cliente conectado");
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());

                String mensaje = in.readUTF();
                System.out.println(mensaje);
                String retornando = retorno(mensaje);
                out.writeUTF(retornando);

                sc.close();
                System.out.println("Cliente desconectado");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String retorno(String texto) {
        parserSolicitud par = new parserSolicitud(new LexerSolicitud(new StringReader(texto)));
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
                                    return retorno;
                                }
                            }
                            retorno = "< mensaje texto =\"La lista que solicitas no existe\" />";
                            return retorno;
                        } else {
                            String retorno = "< mensaje texto = \"Aún no se han creado listas\" />";
                            return retorno;
                        }
                    } else {
                        ArrayList<Lista> listemp = obtener();
                        if (listemp != null) {
                            if (!listemp.isEmpty()) {
                                String retorno = "<listas>\n";
                                for (int i = 0; i < listemp.size(); i++) {
                                    int tem = (listemp.get(i).getCanciones() != null) ? listemp.get(i).getCanciones().size() : 0;
                                    retorno += "< lista nombre = \"" + listemp.get(i).getId() + "\" pistas = " + tem + " >\n";
                                }
                                retorno += "</listas>";
                                return retorno;
                            } else {
                                return "< mensaje texto = \"Aún no se han creado listas\" />";
                            }
                        } else {
                            String retorno = "< mensaje texto = \"Aún no se han creado listas\" />";
                            return retorno;
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
                                                double res = (Math.abs(definitivaNota.get(k)) > 0) ? ((double) (((double) Math.pow(2, ((double) (Math.abs(definitivaNota.get(k)) - 69) / 12))) * 440)) : 0;
                                                double re2 = Math.round(res * 100.0) / 100.0;
                                                retorno += "\n\t\t< nota duracion = " + (definitivaRepeticion.get(k) * 50) + " frecuencia " + re2 + " >";
                                            }
                                            retorno += " \n\t< canal />";
                                        }
                                    }
                                    retorno += " \n< pista />";
                                    return retorno;
                                }
                            }
                            retorno = "< mensaje texto =\"La pista que solicitas no existe\" />";
                            return retorno;
                        } else {
                            String retorno = "< mensaje texto = \"Aún no se han creado pistas\" />";
                            return retorno;
                        }
                    } else {
                        ArrayList<Cancion> canstemp = obtener_canciones();
                        if (canstemp != null) {
                            if (!canstemp.isEmpty()) {
                                String retorno = "<pistas>\n";
                                for (int i = 0; i < canstemp.size(); i++) {
                                    long duracion = (canstemp.get(i).getReproductor() != null) ? canstemp.get(i).getReproductor().max() * 50 : 0;
                                    retorno += "< pista nombre = \"" + canstemp.get(i).getId() + "\" duracion = " + duracion + " >\n";
                                }
                                retorno += "</pistas>";
                                return retorno;
                            } else {
                                return "< mensaje texto = \"Aún no se han creado pistas\" />";
                            }
                        } else {
                            String retorno = "< mensaje texto = \"Aún no se han creado pistas\" />";
                            return retorno;
                        }
                    }
                } else {
                    if (s.getR().getCanales() != null) {
                        int nuevo2 = s.getR().max() * 50;
                        int nuevo3 = nuevo2 / 1000;
                        int minutos = (int) (nuevo3 / 60);
                        int sec = (int) (nuevo3 % 60);
                        String tiempo_pasado = (sec > 9) ? minutos + ":" + sec : minutos + ":0" + sec;
                        s.getR().setId(s.getNombre());
                        Cancion nueva = new Cancion(s.getNombre(), tiempo_pasado, s.getR(), ">>Fue creada en el piano de la app movil");
                        ArrayList<Cancion> canstemp = obtener_canciones();
                        String retorno = "< respuesta texto = \"No se creo bien el archivo\" />";
                        if (canstemp == null) {
                            canstemp = new ArrayList<>();
                            canstemp.add(nueva);
                            Platform.runLater(() -> {
                                data_cancion.add(nueva);
                            });
                            retorno = "< respuesta texto = \"Canción creada correctamente\" />";
                        } else {
                            m = -1;
                            for (int i = 0; i < canstemp.size(); i++) {
                                if (canstemp.get(i).getId().equals(s.getNombre())) {
                                    m = i;
                                    break;
                                }
                            }
                            if (m != -1) {
                                canstemp.set(m, nueva);
                                Platform.runLater(() -> {
                                    data_cancion.set(m, nueva);
                                });
                                retorno = "< respuesta texto = \"Dado que la canción ya existia, fue modificada correctamente\" />";
                            } else {
                                canstemp.add(nueva);
                                Platform.runLater(() -> {
                                    data_cancion.add(nueva);
                                });
                                retorno = "< respuesta texto = \"Canción creada correctamente\" />";
                            }
                        }
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
                            retorno = "< respuesta texto = \"Error al escribir el archivo binario\" />";
                        }
                        return retorno;
                    } else {
                        return "< respuesta texto = \"No se creo bien el archivo\" />";
                    }
                }
            } else {
                return "< respuesta texto = \"Falta que agregues el tipo de solicitud\" />";
            }
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "< respuesta texto = \"Error inesperado\" />";
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
