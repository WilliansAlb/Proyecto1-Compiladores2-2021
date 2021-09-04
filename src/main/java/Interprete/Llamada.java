/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import static Interprete.Item.TIPO;
import Interprete.Metodos_Nativos.Esperar;
import Interprete.Metodos_Nativos.Mensaje;
import Interprete.Metodos_Nativos.Reproducir;
import Interprete.Metodos_Nativos.Sumarizar;
import Tablas.Simbolo;
import Tablas.Simbolos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.control.Label;

/**
 *
 * @author willi
 */
public class Llamada extends Instruccion {

    private String id;
    private String tipo;
    private List<ParametroEnviar> parametros;
    private int linea;
    private int columna;
    public Object retorno;
    public String retorno_tipo;

    public Llamada(String id, List<ParametroEnviar> parametros, int linea, int columna) {
        this.id = id;
        this.parametros = parametros;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public void interpretar(Simbolos tabla) {
        switch (id.toLowerCase()) {
            case "mensaje":
                interpretar_mensaje(tabla);
                break;
            case "reproducir":
                interpretar_reproducir(tabla);
                break;
            case "esperar":
                interpretar_esperar(tabla);
                break;
            case "sumarizar":
                interpretar_sumarizar(tabla);
                break;
            case "longitud":
                interpretar_longitud(tabla);
                break;
            case "ordenar":
                interpretar_ordenar(tabla);
                break;
            default:
                interpretar_llamada(tabla);
                break;
        }
    }

    public void interpretar_llamada(Simbolos tabla) {
        Metodo aux = tabla.obtener_metodo(id, parametros);
        if (aux != null) {
            aux.interpretar(tabla);
            if (aux.objeto_retorno != null) {
                retorno = aux.objeto_retorno;
                retorno_tipo = aux.tipo_retorno;
            }
        } else {
            System.out.println("No existe tal metodo");
        }
    }

    /**
     * Método que interpreta métodos del tipo mensaje(texto) de lo contrario,
     * llama al método interpretar_método
     *
     * @param tabla recibe la tabla de simbolos que se está utilizando
     */
    public void interpretar_mensaje(Simbolos tabla) {
        if (parametros.size() == 1) {
            if (parametros.get(0).getTipo() == ParametroEnviar.Tipo.EXPRESION) {
                Mensaje n = new Mensaje(parametros.get(0).getExpresion(), linea, columna);
                n.interpretar(tabla);
            } else {
                interpretar_llamada(tabla);
            }
        } else {
            interpretar_llamada(tabla);
        }
    }

    /**
     * Método que interpreta métodos del tipo
     * reproducir(nota,octava,tiempo,canal) de lo contrario, llama al método
     * intepretar_método
     *
     * @param tabla recibe la tabla de simbolos que se está utilizando
     */
    public void interpretar_reproducir(Simbolos tabla) {
        if (parametros.size() == 4) {
            if (parametros.get(0).getTipo() == ParametroEnviar.Tipo.NOTA) {
                if (parametros.get(1).getTipo() == ParametroEnviar.Tipo.EXPRESION
                        && parametros.get(2).getTipo() == ParametroEnviar.Tipo.EXPRESION
                        && parametros.get(3).getTipo() == ParametroEnviar.Tipo.EXPRESION) {
                    int octava = numero(parametros.get(1).getExpresion(), tabla);
                    if (octava >= 0) {
                        int tiempo = numero(parametros.get(2).getExpresion(), tabla);
                        if (tiempo > 0) {
                            int canal = numero(parametros.get(3).getExpresion(), tabla);
                            if (canal > 0) {
                                Notas ns = new Notas();
                                int note = ns.obtener_nota(parametros.get(0).getNota_orden(), octava);
                                Simbolo s = tabla.obtener("$reproducir");
                                String respuesta = (((Reproduccion) s.getDatos().get(0)).agregar(note, tiempo, canal)) ? "Agregada correctamente" : "No se pudo agregar";
                                tabla.cambiar("$reproducir", s.getDatos());
                                if (respuesta.equalsIgnoreCase("Agregada correctamente")) {
                                    retorno = tiempo;
                                    retorno_tipo = "numero";
                                } else {
                                    tabla.agregar_error("Semantico", "Ocurrió un error al momento de asignar la reproduccion.", linea, columna);
                                }
                            }
                        }
                    }
                } else {
                    interpretar_llamada(tabla);
                }
            } else {
                interpretar_llamada(tabla);
            }
        }
    }

    /**
     * Método que interpreta métodos del tipo sumarizar(arreglo) de lo
     * contrario, llama al método interpretar_método
     *
     * @param tabla recibe la tabla de simbolos que se está utilizando
     */
    public void interpretar_sumarizar(Simbolos tabla) {
        if (parametros.size() == 1) {
            List<Object> elementos = new LinkedList<>();
            List<Object> propiedades = new LinkedList<>();
            propiedades.add("");
            propiedades.add(0);
            propiedades.add(false);
            if (parametros.get(0).getTipo() == ParametroEnviar.Tipo.ARREGLO) {
                Item arr = parametros.get(0).getArreglo();
                arr.obtener_arreglo(elementos, tabla, propiedades, linea);
                retorno = sumarizar(propiedades.get(0).toString(), elementos);
                retorno_tipo = "string";
            } else if (parametros.get(0).getTipo() == ParametroEnviar.Tipo.EXPRESION) {
                Expresion arr = parametros.get(0).getExpresion();
                Termino t = arr.getValor();
                if (t instanceof Identificador) {
                    Identificador te = (Identificador) t;
                    Simbolo encontrado = tabla.obtener(te.getId());
                    if (encontrado != null) {
                        if (encontrado.getDimensiones() != null) {
                            retorno = sumarizar(encontrado.getTipo(), encontrado.getDatos());
                            retorno_tipo = "string";
                        } else {
                            tabla.agregar_error("Semantico", "El identificador " + te.getId() + " no le pertenece a un arreglo", linea, columna);
                        }
                    } else {
                        tabla.agregar_error("Semantico", "No se ha definido el arreglo " + te.getId(), linea, columna);
                    }
                } else {
                    tabla.agregar_error("Semantico", "El metodo sumarizar solo acepta como parametro un arreglo o un identificador de un arreglo", linea, columna);
                }
            } else {
                interpretar_llamada(tabla);
            }
        } else {
            interpretar_llamada(tabla);
        }
    }

    /**
     * Método que interpreta métodos del tipo ordenar(arreglo,tipo_de_orden) de
     * lo contrario, llama al método intepretar_método
     *
     * @param tabla recibe la tabla de simbolos que se está utilizando
     */
    public void interpretar_ordenar(Simbolos tabla) {
        if (parametros.size() == 2) {
            List<Object> elementos = new LinkedList<>();
            List<Object> propiedades = new LinkedList<>();
            propiedades.add("");
            propiedades.add(0);
            propiedades.add(false);
            if (parametros.get(1).getTipo() == ParametroEnviar.Tipo.ORDEN) {
                if (parametros.get(0).getTipo() == ParametroEnviar.Tipo.ARREGLO) {
                    Item arr = parametros.get(0).getArreglo();
                    arr.obtener_arreglo(elementos, tabla, propiedades, 0);
                    if ((int) propiedades.get(1) > 1) {
                        tabla.agregar_error("Semantico", "El arreglo es de mas de una dimension", linea, columna);
                    } else {
                        retorno = ordenar(elementos, parametros.get(1).getNota_orden(), propiedades.get(0).toString()) ? 1 : 0;
                        retorno_tipo = "numero";
                    }
                } else if (parametros.get(0).getTipo() == ParametroEnviar.Tipo.EXPRESION) {
                    Expresion arr = parametros.get(0).getExpresion();
                    Termino t = arr.getValor();
                    if (t instanceof Identificador) {
                        Identificador te = (Identificador) t;
                        Simbolo encontrado = tabla.obtener(te.getId());
                        if (encontrado != null) {
                            if (encontrado.getDimensiones() != null) {
                                if (encontrado.getDimensiones().size() > 1) {
                                    tabla.agregar_error("Semantico", "El identificador " + te.getId() + " le pertenece a un arreglo de mas de una dimension", linea, columna);
                                } else {
                                    if (encontrado.getDatos() != null) {
                                        if (encontrado.getDatos().size() > 1) {
                                            retorno = ordenar(encontrado.getDatos(), parametros.get(1).getNota_orden(), encontrado.getTipo()) ? 1 : 0;
                                            retorno_tipo = "numero";
                                        }
                                    }
                                }
                                System.out.println(parametros.get(1).getNota_orden());
                            } else {
                                tabla.agregar_error("Semantico", "El identificador " + te.getId() + " no le pertenece a un arreglo", linea, columna);
                            }
                        } else {
                            tabla.agregar_error("Semantico", "No se ha definido el arreglo " + te.getId(), linea, columna);
                        }
                    } else {
                        tabla.agregar_error("Semantico", "El metodo sumarizar solo acepta como parametro un arreglo o un identificador de un arreglo", linea, columna);
                    }
                }
            } else {
                interpretar_llamada(tabla);
            }
        } else {
            interpretar_llamada(tabla);
        }
    }

    public boolean ordenar(List elementos, String orden, String tipo_arreglo) {
        switch (tipo_arreglo) {
            case "string":
                ArrayList<String> arr = new ArrayList<>();
                for (int i = 0; i < elementos.size(); i++) {
                    if (elementos.get(i) != null) {
                        arr.add(elementos.get(i).toString());
                    }
                }
                return ordenar_strings(arr, orden, elementos);
            case "numero":
                ArrayList<Integer> arr1 = new ArrayList<>();
                for (int i = 0; i < elementos.size(); i++) {
                    if (elementos.get(i) != null) {
                        arr1.add((int) elementos.get(i));
                    }
                }
                return ordenar_numeros(arr1, orden, elementos);
            case "decimal":
                ArrayList<Double> arr3 = new ArrayList<>();
                for (int i = 0; i < elementos.size(); i++) {
                    if (elementos.get(i) != null) {
                        arr3.add((double) elementos.get(i));
                    }
                }
                return ordenar_decimales(arr3, orden, elementos);
            case "boolean":
                ArrayList<Boolean> arr2 = new ArrayList<>();
                for (int i = 0; i < elementos.size(); i++) {
                    if (elementos.get(i) != null) {
                        arr2.add((boolean) elementos.get(i));
                    }
                }
                return ordenar_booleanos(arr2, orden, elementos);
            case "caracter":
                ArrayList<String> arr4 = new ArrayList<>();
                for (int i = 0; i < elementos.size(); i++) {
                    if (elementos.get(i) != null) {
                        arr4.add((char) elementos.get(i) + "");
                    }
                }
                return ordenar_caracteres(arr4, orden, elementos);
            default:
                return false;
        }
    }

    public boolean ordenar_decimales(ArrayList<Double> listado, String orden, List elementos) {
        switch (orden) {
            case "ascendente":
                listado.sort((o1, o2) -> o1.compareTo(o2));
                if ((elementos.size() - listado.size()) != 0) {
                    int cuantos = elementos.size() - listado.size();
                    for (int i = 0; i < cuantos; i++) {
                        listado.add(null);
                    }
                }
                return true;
            case "descendente":
                listado.sort((o1, o2) -> o2.compareTo(o1));
                if ((elementos.size() - listado.size()) != 0) {
                    int cuantos = elementos.size() - listado.size();
                    for (int i = 0; i < cuantos; i++) {
                        listado.add(null);
                    }
                }
                return true;
            case "primos":
                ArrayList<Double> primos = new ArrayList<>();
                ArrayList<Double> demas = new ArrayList<>();
                for (int i = 0; i < listado.size(); i++) {
                    if (esPrimo(listado.get(i).intValue())) {
                        primos.add(listado.get(i));
                    } else {
                        demas.add(listado.get(i));
                    }
                }
                primos.sort((o1, o2) -> o1.compareTo(o2));
                demas.sort((o1, o2) -> o1.compareTo(o2));
                primos.addAll(demas);
                if ((elementos.size() - primos.size()) != 0) {
                    int cuantos = elementos.size() - primos.size();
                    for (int i = 0; i < cuantos; i++) {
                        primos.add(null);
                    }
                }
                return true;
            case "pares":
                ArrayList<Double> nuevo2 = new ArrayList<>();
                ArrayList<Double> pasados2 = new ArrayList<>();
                for (int i = 0; i < listado.size(); i++) {
                    if (listado.get(i).intValue() % 2 == 0) {
                        nuevo2.add(listado.get(i));
                    } else {
                        pasados2.add(listado.get(i));
                    }
                }
                nuevo2.sort((o1, o2) -> o1.compareTo(o2));
                pasados2.sort((o1, o2) -> o1.compareTo(o2));
                nuevo2.addAll(pasados2);
                if ((elementos.size() - nuevo2.size()) != 0) {
                    int cuantos = elementos.size() - nuevo2.size();
                    for (int i = 0; i < cuantos; i++) {
                        nuevo2.add(null);
                    }
                }
                return true;
            case "impares":
                ArrayList<Double> nuevo3 = new ArrayList<>();
                ArrayList<Double> pasados3 = new ArrayList<>();
                for (int i = 0; i < listado.size(); i++) {
                    if (listado.get(i).intValue() % 2 == 0) {
                        nuevo3.add(listado.get(i));
                    } else {
                        pasados3.add(listado.get(i));
                    }
                }
                nuevo3.sort((o1, o2) -> o1.compareTo(o2));
                pasados3.sort((o1, o2) -> o1.compareTo(o2));
                pasados3.addAll(nuevo3);
                if ((elementos.size() - pasados3.size()) != 0) {
                    int cuantos = elementos.size() - pasados3.size();
                    for (int i = 0; i < cuantos; i++) {
                        pasados3.add(null);
                    }
                }
                return true;
            default:
                return false;
        }
    }

    public boolean ordenar_strings(ArrayList<String> listado, String orden, List elementos) {
        System.out.println(Arrays.toString(listado.toArray()));
        switch (orden) {
            case "ascendente":
                listado.sort((String a, String b) -> a.compareTo(b));
                System.out.println(Arrays.toString(listado.toArray()));
                return true;
            case "descendente":
                listado.sort((String a, String b) -> b.compareTo(a));
                System.out.println(Arrays.toString(listado.toArray()));
                return true;
            case "primos":
                return false;
            case "pares":
                return false;
            case "impares":
                return false;
            default:
                return false;
        }
    }

    public boolean ordenar_booleanos(ArrayList<Boolean> listado, String orden, List elementos) {
        System.out.println(Arrays.toString(listado.toArray()));
        switch (orden) {
            case "ascendente":
                listado.sort((Boolean a, Boolean b) -> a.compareTo(b));
                System.out.println(Arrays.toString(listado.toArray()));
                return true;
            case "descendente":
                listado.sort((Boolean a, Boolean b) -> b.compareTo(a));
                System.out.println(Arrays.toString(listado.toArray()));
                return true;
            case "primos":
                return false;
            case "pares":
                return false;
            case "impares":
                return false;
            default:
                return false;
        }
    }

    public boolean ordenar_caracteres(ArrayList<String> listado, String orden, List elementos) {
        switch (orden) {
            case "ascendente":
                listado.sort((String a, String b) -> a.compareTo(b));
                return true;
            case "descendente":
                listado.sort((String a, String b) -> b.compareTo(a));
                return true;
            case "primos":
                ArrayList<String> primos = new ArrayList<>();
                ArrayList<String> demas = new ArrayList<>();
                for (int i = 0; i < listado.size(); i++) {
                    if (esPrimo((int) listado.get(i).charAt(0))) {
                        primos.add(listado.get(i));
                    } else {
                        demas.add(listado.get(i));
                    }
                }
                primos.sort((o1, o2) -> o1.compareTo(o2));
                demas.sort((o1, o2) -> o1.compareTo(o2));
                primos.addAll(demas);
                if ((elementos.size() - primos.size()) != 0) {
                    int cuantos = elementos.size() - primos.size();
                    for (int i = 0; i < cuantos; i++) {
                        primos.add(null);
                    }
                }
                return true;
            case "pares":
                ArrayList<String> pares = new ArrayList<>();
                ArrayList<String> no_pares = new ArrayList<>();
                for (int i = 0; i < listado.size(); i++) {
                    if ((int) listado.get(i).charAt(0) % 2 == 0) {
                        pares.add(listado.get(i));
                    } else {
                        no_pares.add(listado.get(i));
                    }
                }
                pares.sort((o1, o2) -> o1.compareTo(o2));
                no_pares.sort((o1, o2) -> o1.compareTo(o2));
                pares.addAll(no_pares);
                if ((elementos.size() - pares.size()) != 0) {
                    int cuantos = elementos.size() - pares.size();
                    for (int i = 0; i < cuantos; i++) {
                        pares.add(null);
                    }
                }
                return true;
            case "impares":
                ArrayList<String> impares = new ArrayList<>();
                ArrayList<String> normales = new ArrayList<>();
                for (int i = 0; i < listado.size(); i++) {
                    if ((int) listado.get(i).charAt(0) % 2 == 0) {
                        normales.add(listado.get(i));
                    } else {
                        impares.add(listado.get(i));
                    }
                }
                normales.sort((o1, o2) -> o1.compareTo(o2));
                impares.sort((o1, o2) -> o1.compareTo(o2));
                impares.addAll(normales);
                if ((elementos.size() - impares.size()) != 0) {
                    int cuantos = elementos.size() - impares.size();
                    for (int i = 0; i < cuantos; i++) {
                        impares.add(null);
                    }
                }
                return false;
            default:
                return false;
        }
    }

    public boolean ordenar_numeros(ArrayList<Integer> listado, String orden, List elementos) {
        switch (orden) {
            case "ascendente":
                listado.sort((o1, o2) -> o1.compareTo(o2));
                if ((elementos.size() - listado.size()) != 0) {
                    int cuantos = elementos.size() - listado.size();
                    for (int i = 0; i < cuantos; i++) {
                        listado.add(null);
                    }
                }
                return true;
            case "descendente":
                listado.sort((o1, o2) -> o2.compareTo(o1));
                if ((elementos.size() - listado.size()) != 0) {
                    int cuantos = elementos.size() - listado.size();
                    for (int i = 0; i < cuantos; i++) {
                        listado.add(null);
                    }
                }
                return true;
            case "primos":
                ArrayList<Integer> primos = new ArrayList<>();
                ArrayList<Integer> demas = new ArrayList<>();
                for (int i = 0; i < listado.size(); i++) {
                    if (esPrimo(listado.get(i))) {
                        primos.add(listado.get(i));
                    } else {
                        demas.add(listado.get(i));
                    }
                }
                primos.sort((o1, o2) -> o1.compareTo(o2));
                demas.sort((o1, o2) -> o1.compareTo(o2));
                primos.addAll(demas);
                if ((elementos.size() - primos.size()) != 0) {
                    int cuantos = elementos.size() - primos.size();
                    for (int i = 0; i < cuantos; i++) {
                        primos.add(null);
                    }
                }
                return true;
            case "pares":
                ArrayList<Integer> nuevo2 = new ArrayList<>();
                ArrayList<Integer> pasados2 = new ArrayList<>();
                for (int i = 0; i < listado.size(); i++) {
                    if (listado.get(i) % 2 == 0) {
                        nuevo2.add(listado.get(i));
                    } else {
                        pasados2.add(listado.get(i));
                    }
                }
                nuevo2.sort((o1, o2) -> o1.compareTo(o2));
                pasados2.sort((o1, o2) -> o1.compareTo(o2));
                nuevo2.addAll(pasados2);
                if ((elementos.size() - nuevo2.size()) != 0) {
                    int cuantos = elementos.size() - nuevo2.size();
                    for (int i = 0; i < cuantos; i++) {
                        nuevo2.add(null);
                    }
                }
                return true;
            case "impares":
                ArrayList<Integer> nuevo3 = new ArrayList<>();
                ArrayList<Integer> pasados3 = new ArrayList<>();
                for (int i = 0; i < listado.size(); i++) {
                    if (listado.get(i) % 2 == 0) {
                        nuevo3.add(listado.get(i));
                    } else {
                        pasados3.add(listado.get(i));
                    }
                }
                nuevo3.sort((o1, o2) -> o1.compareTo(o2));
                pasados3.sort((o1, o2) -> o1.compareTo(o2));
                pasados3.addAll(nuevo3);
                if ((elementos.size() - pasados3.size()) != 0) {
                    int cuantos = elementos.size() - pasados3.size();
                    for (int i = 0; i < cuantos; i++) {
                        pasados3.add(null);
                    }
                }
                return true;
            default:
                return false;
        }
    }

    public boolean esPrimo(int n) {
        if (n == 1 || n == 2 || n == 0) {
            return true;
        }
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Método que interpreta métodos del tipo longitud(cadena/arreglo)
     *
     * @param tabla recibe la tabla de simbolos que se está utilizando
     */
    public void interpretar_longitud(Simbolos tabla) {
        if (parametros.size() == 1) {
            if (parametros.get(0).getTipo() == ParametroEnviar.Tipo.EXPRESION) {
                if (esIdentificador(parametros.get(0).getExpresion().getValor())) {
                    Identificador idt = (Identificador) parametros.get(0).getExpresion().getValor();
                    if (esArreglo(idt, tabla)) {
                        Simbolo s = tabla.obtener(idt.getId());
                        if (s.getDatos() != null) {
                            retorno = s.getDatos().size();
                            retorno_tipo = "numero";
                        } else {
                            tabla.agregar_error("Semantico", "La variable con id " + s.getId() + " no ha sido inicializada", linea, columna);
                        }
                    } else {
                        Simbolo s = tabla.obtener(idt.getId());
                        if (s.getTipo().equalsIgnoreCase("string")) {
                            if (s.getDatos() != null) {
                                retorno = s.getDatos().get(0).toString().length();
                                retorno_tipo = "numero";
                            }
                        } else {
                            tabla.agregar_error("Semantico", "La variable con id " + idt.getId() + " no es ni arreglo ni string", linea, columna);
                        }
                    }
                } else {
                    Termino s = parametros.get(0).getExpresion().ejecutar(tabla);
                    if (s instanceof Primitivo) {
                        Primitivo sa = (Primitivo) s;
                        if (sa.asignar("string")) {
                            retorno = sa.getValor().toString().length();
                            retorno_tipo = "numero";
                        }
                    } else {
                        tabla.agregar_error("Semantico", "El valor enviado no es una cada o un arreglo", linea, columna);
                    }
                }
            } else if (parametros.get(0).getTipo() == ParametroEnviar.Tipo.ARREGLO) {
                List<Object> elementos = new LinkedList<>();
                List<Object> propiedades = new LinkedList<>();
                propiedades.add("");
                propiedades.add(0);
                propiedades.add(false);
                Item it = parametros.get(0).getArreglo();
                it.obtener_arreglo(elementos, tabla, propiedades, linea);
                retorno = elementos.size();
                retorno_tipo = "numero";
            } else {
                interpretar_llamada(tabla);
            }
        } else {
            interpretar_llamada(tabla);
        }
    }

    /**
     * Método que interpreta métodos del tipo esperar(tiempo,canal) de lo
     * contrario, llama al método interpretar_método
     *
     * @param tabla recibe la tabla de simbolos que se está utilizando
     */
    public void interpretar_esperar(Simbolos tabla) {
        if (parametros.size() == 2) {
            if (parametros.get(0).getTipo() == ParametroEnviar.Tipo.EXPRESION
                    && parametros.get(1).getTipo() == ParametroEnviar.Tipo.EXPRESION) {
                int tiempo = numero(parametros.get(0).getExpresion(), tabla);
                if (tiempo > 0) {
                    int canal = numero(parametros.get(1).getExpresion(), tabla);
                    if (canal >= 0) {
                        Simbolo s = tabla.obtener("$reproducir");
                        String respuesta = (((Reproduccion) s.getDatos().get(0)).agregar(0, tiempo, canal)) ? "Agregada correctamente" : "No se pudo agregar";
                        tabla.cambiar("$reproducir", s.getDatos());
                        if (respuesta.equalsIgnoreCase("Agregada correctamente")) {
                            retorno = tiempo;
                            retorno_tipo = "numero";
                        } else {
                            tabla.agregar_error("Semantico", "Ocurrió un error al momento de asignar la reproduccion.", linea, columna);
                        }
                    } else {
                        System.out.println("El canal no puede ser un numero negativo");
                    }
                } else {
                    System.out.println("No se pudo agregar el tiempo de espera");
                }
            } else {
                interpretar_llamada(tabla);
            }
        } else {
            interpretar_llamada(tabla);
        }
    }

    /**
     * Retorna el numero resultante de la expresión, siendo el resultado -1111
     * si ha ocurrido algún problema
     *
     * @param s la expresión a ejecutar
     * @param tabla recibe la tabla de simbolos que se está utilizando
     * @return el numero resultante de la expresión
     */
    public int numero(Expresion s, Simbolos tabla) {
        Termino ti = s.getValor();
        if (esIdentificador(ti)) {
            if (esArreglo((Identificador) ti, tabla)) {
                tabla.agregar_error("Semantico", "El metodo esperar solo acepta como parametro un número y se le envio " + ((Identificador) ti).getId() + " que es arreglo.", linea, columna);
                return -1111;
            } else {
                Simbolo ss = tabla.obtener(((Identificador) ti).getId());
                if (ss.getTipo().equalsIgnoreCase("numero")) {
                    if (ss.getDatos() != null) {
                        return (int) ss.getDatos().get(0);
                    } else {
                        tabla.agregar_error("Semantico", "La variable con id " + ss.getId() + " no ha sido inicializada", linea, columna);
                        return -1111;
                    }
                }
            }
        } else {
            Primitivo p = (Primitivo) s.ejecutar(tabla);
            if (p.getTipo().equalsIgnoreCase("numero")) {
                return (int) p.getValor();
            }
        }
        return -1111;
    }

    /**
     * Método que comprueba que un identificador sea de una variable que sea un
     * arreglo
     *
     * @param t el identificador a comprobar
     * @param tabla recibe la tabla de simbolos que se está utilizando
     * @return true si el identificador corresponde a una variable arreglo
     */
    public boolean esArreglo(Identificador t, Simbolos tabla) {
        return (tabla.obtener(t.getId())).getDimensiones() != null;
    }

    /**
     * Método que comprueba que un Termino sea de tipo identificador
     *
     * @param t el termino a comprobar
     * @return true si el termino es un identificador
     */
    public boolean esIdentificador(Termino t) {
        return t instanceof Identificador;
    }

    /**
     * Método que suma todos los elementos del arreglo que se le envia
     *
     * @param tipo el tipo de elemento que debe contener el arreglo
     * @param elementos los elementos del arreglo a sumar
     * @return la suma de elementos
     */
    public String sumarizar(String tipo, List<Object> elementos) {
        String sumariza = "";
        switch (tipo) {
            case "string":
                for (Object elemento : elementos) {
                    if (elemento != null) {
                        sumariza += elemento.toString();
                    }
                }
                return sumariza;
            case "numero":
                int resultado = 0;
                for (Object elemento : elementos) {
                    if (elemento != null) {
                        resultado += (int) elemento;
                    }
                }
                return resultado + "";
            case "decimal":
                double resultado2 = 0.0;
                for (Object elemento : elementos) {
                    if (elemento != null) {
                        resultado2 += (double) elemento;
                    }
                }
                return resultado2 + "";
            case "boolean":
                int resultado3 = 0;
                for (Object elemento : elementos) {
                    if (elemento != null) {
                        resultado3 += ((boolean) elemento) ? 1 : 0;
                    }
                }
                return resultado3 + "";
            case "caracter":
                for (Object elemento : elementos) {
                    if (elemento != null) {
                        sumariza += elemento.toString();
                    }
                }
                return sumariza;
            default:
                return "No es de un tipo valido";
        }
    }

    //getters and setters
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

    public List<ParametroEnviar> getParametros() {
        return parametros;
    }

    public void setParametros(List<ParametroEnviar> parametros) {
        this.parametros = parametros;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
