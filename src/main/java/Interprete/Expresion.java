/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import Tablas.Simbolo;
import Tablas.Simbolos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author willi
 */
public class Expresion implements Serializable {

    public static enum Operacion {
        SUMA,
        RESTA,
        POR,
        ENTRE,
        MOD,
        POT,
        NEGATIVO,
        NUMERO,
        ARREGLO,
        CADENA,
        MAYOR,
        MENOR,
        MAYOR_I,
        MENOR_I,
        DIFF,
        IGUAL,
        NOT,
        AND,
        OR,
        XOR,
        NOR,
        NAND,
        NULO,
        BOOLEAN,
        PRIMITIVO,
        METODO_RETORNO,
        LLAMADA,
        IDENTIFICADOR
    }
    private final Operacion tipo;
    /**
     * Operador izquierdo de la operación.
     */
    private Expresion izquierda;
    /**
     * Operador derecho de la operación.
     */
    private Expresion derecha;
    /**
     * Valor específico si se tratara de una literal, es decir un número o una
     * cadena.
     */
    private Termino valor;

    /**
     * Constructor de la clase para operaciones binarias (con dos operadores),
     * estas operaciones son: SUMA, RESTA, MULTIPLICACION, DIVISION,
     * CONCATENACION, MAYOR_QUE, MENOR_QUE
     *
     * @param izquierda Operador izquierdo de la operación
     * @param derecha Opeardor derecho de la operación
     * @param tipo Tipo de la operación
     */
    public Expresion(Expresion izquierda, Expresion derecha, Operacion tipo) {
        this.tipo = tipo;
        this.izquierda = izquierda;
        this.derecha = derecha;
    }

    /**
     * Constructor para operaciones unarias (un operador), estas operaciones
     * son: NEGATIVO NOT
     *
     * @param izquierda Único operador de la operación
     * @param tipo Tipo de operación
     */
    public Expresion(Expresion izquierda, Operacion tipo) {
        this.tipo = tipo;
        this.izquierda = izquierda;
    }

    /**
     * Constructor para operaciones unarias (un operador), cuyo operador es
     * específicamente una cadena, estas operaciones son: IDENTIFICADOR, CADENA
     *
     * @param a Cadena que representa la operación a realizar
     * @param tipo Tipo de operación
     */
    public Expresion(Termino a, Operacion tipo) {
        this.valor = a;
        this.tipo = tipo;
    }

    /**
     * Constructor para operaciones unarias (un operador), cuyo operador es
     * específicamente una NUMERO, estas operaciones son: NUMERO_NUMERO,
     * NUMERO_DECIMAL
     *
     * @param a Valor de tipo Double que representa la operación a realizar.
     */
    public Expresion(Termino a) {
        this.valor = a;
        this.tipo = Operacion.NUMERO;
    }

    public Termino ejecutar(Simbolos tabla) {
        Termino a = (izquierda == null) ? null : izquierda.ejecutar(tabla);
        Termino b = (derecha == null) ? null : derecha.ejecutar(tabla);
        if (tipo == Operacion.ENTRE) {
            return dividir(a, b);
        } else if (tipo == Operacion.SUMA) {
            return sumar(a, b);
        } else if (tipo == Operacion.POR) {
            return multiplicar(a, b);
        } else if (tipo == Operacion.PRIMITIVO) {
            return (Primitivo) valor;
        } else if (tipo == Operacion.MOD) {
            return modulo(a, b);
        } else if (tipo == Operacion.POT) {
            return potencia(a, b);
        } else if (tipo == Operacion.NOT) {
            return not(a);
        } else if (tipo == Operacion.NEGATIVO) {
            return negativo(a);
        } else if (tipo == Operacion.RESTA) {
            return resta(a, b);
        } else if (tipo == Operacion.AND) {
            return and(a, b);
        } else if (tipo == Operacion.NAND) {
            return nand(a, b);
        } else if (tipo == Operacion.OR) {
            return or(a, b);
        } else if (tipo == Operacion.NOR) {
            return nor(a, b);
        } else if (tipo == Operacion.XOR) {
            return xor(a, b);
        } else if (tipo == Operacion.IGUAL) {
            return igual(a, b);
        } else if (tipo == Operacion.DIFF) {
            return diff(a, b);
        } else if (tipo == Operacion.MAYOR) {
            return mayor(a, b);
        } else if (tipo == Operacion.MENOR) {
            return menor(a, b);
        } else if (tipo == Operacion.MAYOR_I) {
            return mayor_igual(a, b);
        } else if (tipo == Operacion.MENOR_I) {
            return menor_igual(a, b);
        } else if (tipo == Operacion.IDENTIFICADOR) {
            Identificador id = (Identificador) valor;
            if (!id.isIsArreglo()) {
                if (tabla.existe(id.getId())) {
                    Object valor2 = tabla.getValor(id.getId());
                    if (!valor2.toString().equalsIgnoreCase("Desconocido")) {
                        if (valor2 instanceof String) {
                            return new Primitivo("string", id.getLinea(), id.getColumna(),valor2);
                        } else if (valor2 instanceof Integer) {
                            return new Primitivo("numero", id.getLinea(), id.getColumna(),valor2);
                        } else if (valor2 instanceof Double) {
                            return new Primitivo("decimal", id.getLinea(), id.getColumna(),valor2);
                        } else if (valor2 instanceof Boolean) {
                            return new Primitivo("boolean", id.getLinea(), id.getColumna(),valor2);
                        } else {
                            return new Primitivo("caracter", id.getLinea(), id.getColumna(),valor2);
                        }
                    } else {
                        return new Primitivo("excepcion", id.getLinea(),id.getColumna(),"No has inicializado la variable " + id.getId());
                    }
                } else {
                    return new Primitivo("excepcion", id.getLinea(), id.getColumna(),"No has declarado la variable " + id.getId());
                }
            } else {
                if (tabla.existe(id.getId())) {
                    Simbolo s = tabla.obtener(id.getId());
                    List<Integer> dim = new LinkedList<>();
                    boolean solo_numeros = true;
                    for (Expresion dimensione : id.getDimensiones()) {
                        Termino ab = dimensione.ejecutar(tabla);
                        if (ab instanceof Primitivo) {
                            Primitivo abc = (Primitivo) dimensione.ejecutar(tabla);
                            if (!abc.getTipo().equalsIgnoreCase("excepcion")) {
                                if (abc.getTipo().equalsIgnoreCase("numero")) {
                                    dim.add((int) abc.getValor());
                                } else {
                                    solo_numeros = false;
                                    break;
                                }
                            } else {
                                solo_numeros = false;
                                break;
                            }
                        } else {
                            solo_numeros = false;
                            break;
                        }
                    }
                    if (solo_numeros) {
                        List<Integer> di = s.getDimensiones();
                        if (di.size() == dim.size()) {
                            boolean correcto = true;
                            for (int i = 0; i < di.size(); i++) {
                                if (!(di.get(i) > dim.get(i))) {
                                    correcto = false;
                                    break;
                                }
                            }
                            if (correcto) {
                                int posicion = tabla.obtener(id.getId()).insertarEn(dim, di);
                                Object objecto = tabla.obtener(id.getId()).getDatos().get(posicion);
                                return new Primitivo(s.getTipo(), id.getLinea(), id.getColumna(),objecto);
                            } else {
                                return new Primitivo("excepcion", id.getLinea(), id.getColumna(),"Excedido el limite del arreglo " + id.getId());
                            }
                        } else {
                            return new Primitivo("excepcion", id.getLinea(), id.getColumna(),"Las dimensiones del arreglo requerido no coinciden con el verdadero " + id.getId());
                        }
                    } else {
                        return new Primitivo("excepcion", id.getLinea(), id.getColumna(),"El arreglo " + id.getId() + " tiene como dimension un valor no entero");
                    }
                } else {
                    return new Primitivo("excepcion", id.getLinea(), id.getColumna(),"No has declarado el arreglo " + id.getId());
                }
            }
        } else if (tipo == Operacion.NULO) {
            Identificador ide = (Identificador) valor;
            if (tabla.existe(ide.getId())) {
                return new Primitivo("boolean", ide.getLinea(), ide.getColumna(),tabla.esNull(ide.getId()));
            } else {
                return new Primitivo("excepcion", ide.getLinea(), ide.getColumna(),"La variable a la que se le intento comprobar su previa inicializacion no existe");
            }
        } else if (tipo == Operacion.LLAMADA) {
            return ((Metodo_Retorno) valor).interpretar(tabla);
        } else if (tipo == Operacion.ARREGLO) {
            Identificador id = (Identificador) valor;
            if (id.isIsArreglo()) {
                Simbolo s = tabla.obtener(id.getId());
                if (s.getDimensiones() != null) {
                    ArrayList<Integer> array = new ArrayList<>();
                    for (Expresion dimension : id.getDimensiones()) {
                        Termino num = dimension.ejecutar(tabla);
                        if (num instanceof Primitivo) {
                            Primitivo ns = (Primitivo) num;
                            if (ns.asignar("numero")) {
                                array.add((int) ns.getValor());
                            } else {
                                return new Primitivo("excepcion", id.getLinea(), id.getColumna(),"La variable a la que se le intento comprobar su previa inicializacion no existe");
                            }
                        }
                    }
                    if (s.getDimensiones().size() == array.size()) {
                        for (int i = 0; i < s.getDimensiones().size(); i++) {
                            if (s.getDimensiones().get(i)<=array.get(i)){
                                return new Primitivo("excepcion", id.getLinea(), id.getColumna(),"Excedido el limite del arreglo");
                            }
                        }
                        int posicion = s.insertarEn(array, s.getDimensiones());
                        return new Primitivo(s.getTipo(), id.getLinea(), id.getColumna(),s.getDatos().get(posicion));
                    } else {
                        return new Primitivo("excepcion", id.getLinea(), id.getColumna(),"La variable a la que se le intento comprobar su previa inicializacion no existe");
                    }
                } else {
                    return new Primitivo("excepcion", id.getLinea(), id.getColumna(),"La variable a la que se le intento comprobar su previa inicializacion no existe");
                }
            } else {
                return new Primitivo("excepcion", id.getLinea(), id.getColumna(),"La variable a la que se le intento comprobar su previa inicializacion no existe");
            }
        } else {
            return new Primitivo("excepcion",-1, -1,"Ojala que no llegue acá");
        }
    }

    public Termino menor(Termino a, Termino b) {
        Primitivo a1 = (Primitivo) a;
        Primitivo b1 = (Primitivo) b;
        if (a1.getTipo().equalsIgnoreCase("numero")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                int n1 = (int) a1.getValor();
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 < n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (int) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 < n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (int) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 < n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (int) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 < n2);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("decimal")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                double n1 = (double) a1.getValor();
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 < n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                double n1 = (double) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 < n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                double n1 = (double) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 < n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                double n1 = (double) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 < n2);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("boolean")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(), n1 < n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(), n1 < n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(), n1 < n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", a.getLinea(), a.getColumna(), n1 < n2);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("caracter")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                char s1 = (char) a1.getValor();
                char s2 = (char) b1.getValor();
                int s11 = (int) s1;
                int s22 = (int) s2;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),s11 < s22);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                char c1 = (char) a1.getValor();
                int n1 = (int) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n2 < n1);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                char c1 = (char) a1.getValor();
                double n1 = (double) b1.getValor();
                double n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n2 < n1);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                char c1 = (char) a1.getValor();
                int n1 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n2 < n1);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else {
            if (b1.getTipo().equalsIgnoreCase("string")) {
                String s1 = a1.getValor().toString();
                int s11 = s1.length();
                String s2 = b1.getValor().toString();
                int s22 = s2.length();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),s11 < s22);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        }
    }

    public Termino mayor(Termino a, Termino b) {
        Primitivo a1 = (Primitivo) a;
        Primitivo b1 = (Primitivo) b;
        if (a1.getTipo().equalsIgnoreCase("numero")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                int n1 = (int) a1.getValor();
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 > n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (int) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 > n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (int) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 > n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (int) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 > n2);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("decimal")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                double n1 = (double) a1.getValor();
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 > n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                double n1 = (double) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 > n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                double n1 = (double) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 > n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                double n1 = (double) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 > n2);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("boolean")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 > n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 > n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 > n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 > n2);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("caracter")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                char s1 = (char) a1.getValor();
                char s2 = (char) b1.getValor();
                int s11 = (int) s1;
                int s22 = (int) s2;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),s11 > s22);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                char c1 = (char) a1.getValor();
                int n1 = (int) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n2 > n1);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                char c1 = (char) a1.getValor();
                double n1 = (double) b1.getValor();
                double n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n2 > n1);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                char c1 = (char) a1.getValor();
                int n1 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n2 > n1);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else {
            if (b1.getTipo().equalsIgnoreCase("string")) {
                String s1 = a1.getValor().toString();
                int s11 = s1.length();
                String s2 = b1.getValor().toString();
                int s22 = s2.length();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),s11 > s22);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        }
    }

    public Termino mayor_igual(Termino a, Termino b) {
        Primitivo a1 = (Primitivo) a;
        Primitivo b1 = (Primitivo) b;
        if (a1.getTipo().equalsIgnoreCase("numero")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                int n1 = (int) a1.getValor();
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 >= n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (int) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 >= n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (int) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 >= n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (int) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 >= n2);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("decimal")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                double n1 = (double) a1.getValor();
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 >= n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                double n1 = (double) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 >= n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                double n1 = (double) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 >= n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                double n1 = (double) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 >= n2);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("boolean")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 >= n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 >= n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 >= n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 >= n2);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("caracter")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                char s1 = (char) a1.getValor();
                char s2 = (char) b1.getValor();
                int s11 = (int) s1;
                int s22 = (int) s2;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),s11 >= s22);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                char c1 = (char) a1.getValor();
                int n1 = (int) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n2 >= n1);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                char c1 = (char) a1.getValor();
                double n1 = (double) b1.getValor();
                double n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n2 >= n1);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                char c1 = (char) a1.getValor();
                int n1 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n2 >= n1);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else {
            if (b1.getTipo().equalsIgnoreCase("string")) {
                String s1 = a1.getValor().toString();
                int s11 = s1.length();
                String s2 = b1.getValor().toString();
                int s22 = s2.length();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),s11 >= s22);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        }
    }

    public Termino menor_igual(Termino a, Termino b) {
        Primitivo a1 = (Primitivo) a;
        Primitivo b1 = (Primitivo) b;
        if (a1.getTipo().equalsIgnoreCase("numero")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                int n1 = (int) a1.getValor();
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 <= n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (int) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 <= n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (int) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 <= n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (int) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 <= n2);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("decimal")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                double n1 = (double) a1.getValor();
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 <= n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                double n1 = (double) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 <= n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                double n1 = (double) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 <= n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                double n1 = (double) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 <= n2);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("boolean")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 <= n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 <= n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 <= n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 <= n2);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("caracter")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                char s1 = (char) a1.getValor();
                char s2 = (char) b1.getValor();
                int s11 = (int) s1;
                int s22 = (int) s2;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),s11 <= s22);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                char c1 = (char) a1.getValor();
                int n1 = (int) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n2 <= n1);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                char c1 = (char) a1.getValor();
                double n1 = (double) b1.getValor();
                double n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n2 <= n1);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                char c1 = (char) a1.getValor();
                int n1 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n2 <= n1);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else {
            if (b1.getTipo().equalsIgnoreCase("string")) {
                String s1 = a1.getValor().toString();
                int s11 = s1.length();
                String s2 = b1.getValor().toString();
                int s22 = s2.length();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),s11 <= s22);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        }
    }

    public Termino diff(Termino a, Termino b) {
        Primitivo a1 = (Primitivo) a;
        Primitivo b1 = (Primitivo) b;
        if (a1.getTipo().equalsIgnoreCase("numero")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                int n1 = (int) a1.getValor();
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (int) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (int) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (int) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 != n2);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("decimal")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                double n1 = (double) a1.getValor();
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                double n1 = (double) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                double n1 = (double) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                double n1 = (double) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 != n2);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("boolean")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                int n2 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 != n2);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("caracter")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                String s1 = a1.getValor().toString();
                String s2 = b1.getValor().toString();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),!s1.equals(s2));
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                char c1 = (char) a1.getValor();
                int n1 = (int) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                char c1 = (char) a1.getValor();
                double n1 = (double) b1.getValor();
                double n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                char c1 = (char) a1.getValor();
                int n1 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 != n2);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else {
            if (b1.getTipo().equalsIgnoreCase("string")) {
                String s1 = a1.getValor().toString();
                String s2 = b1.getValor().toString();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),!s1.equals(s2));
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        }
    }

    public Termino igual(Termino a, Termino b) {
        Primitivo a1 = (Primitivo) a;
        Primitivo b1 = (Primitivo) b;
        if (a1.getTipo().equalsIgnoreCase("numero")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                int n1 = (int) a1.getValor();
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (int) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (int) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (int) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 == n2);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("decimal")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                double n1 = (double) a1.getValor();
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                double n1 = (double) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                double n1 = (double) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                double n1 = (double) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 == n2);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("boolean")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                int n2 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 == n2);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("caracter")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                String s1 = a1.getValor().toString();
                String s2 = b1.getValor().toString();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),s1.equals(s2));
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                char c1 = (char) a1.getValor();
                int n1 = (int) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                char c1 = (char) a1.getValor();
                double n1 = (double) b1.getValor();
                double n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                char c1 = (char) a1.getValor();
                int n1 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                int n2 = (int) c1;
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),n1 == n2);
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        } else {
            if (b1.getTipo().equalsIgnoreCase("string")) {
                String s1 = a1.getValor().toString();
                String s2 = b1.getValor().toString();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(),s1.equals(s2));
            } else {
                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No puedes comparar un string con otro tipo de dato");
            }
        }
    }

    public Termino nand(Termino a, Termino b) {
        if (a.getTipo().equalsIgnoreCase("boolean") && b.getTipo().equalsIgnoreCase("boolean")) {
            Primitivo a1 = (Primitivo) a;
            Primitivo b1 = (Primitivo) b;
            boolean boo1 = valor_boolean(a1.getValor().toString());
            boolean boo2 = valor_boolean(b1.getValor().toString());
            return new Primitivo("boolean", a.getLinea(), a.getColumna(),!(boo1 && boo2));
        } else {
            return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"El operador and funciona solo con valores booleanos");
        }
    }

    public Termino or(Termino a, Termino b) {
        if (a.getTipo().equalsIgnoreCase("boolean") && b.getTipo().equalsIgnoreCase("boolean")) {
            Primitivo a1 = (Primitivo) a;
            Primitivo b1 = (Primitivo) b;
            boolean boo1 = valor_boolean(a1.getValor().toString());
            boolean boo2 = valor_boolean(b1.getValor().toString());
            return new Primitivo("boolean", a.getLinea(), a.getColumna(),boo1 || boo2);
        } else {
            return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"El operador and funciona solo con valores booleanos");
        }
    }

    public Termino nor(Termino a, Termino b) {
        if (a.getTipo().equalsIgnoreCase("boolean") && b.getTipo().equalsIgnoreCase("boolean")) {
            Primitivo a1 = (Primitivo) a;
            Primitivo b1 = (Primitivo) b;
            boolean boo1 = valor_boolean(a1.getValor().toString());
            boolean boo2 = valor_boolean(b1.getValor().toString());
            return new Primitivo("boolean", a.getLinea(), a.getColumna(),!(boo1 || boo2));
        } else {
            return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"El operador and funciona solo con valores booleanos");
        }
    }

    public Termino xor(Termino a, Termino b) {
        if (a.getTipo().equalsIgnoreCase("boolean") && b.getTipo().equalsIgnoreCase("boolean")) {
            Primitivo a1 = (Primitivo) a;
            Primitivo b1 = (Primitivo) b;
            boolean boo1 = valor_boolean(a1.getValor().toString());
            boolean boo2 = valor_boolean(b1.getValor().toString());
            return new Primitivo("boolean", a.getLinea(), a.getColumna(),((boo1 && !boo2) || (!boo1 && boo2)));
        } else {
            return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"El operador and funciona solo con valores booleanos");
        }
    }

    public Termino and(Termino a, Termino b) {
        if (a.getTipo().equalsIgnoreCase("boolean") && b.getTipo().equalsIgnoreCase("boolean")) {
            Primitivo a1 = (Primitivo) a;
            Primitivo b1 = (Primitivo) b;
            boolean boo1 = valor_boolean(a1.getValor().toString());
            boolean boo2 = valor_boolean(b1.getValor().toString());
            return new Primitivo("boolean", a.getLinea(), a.getColumna(),boo1 && boo2);
        } else {
            return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"El operador and funciona solo con valores booleanos");
        }
    }

    public Termino resta(Termino a, Termino b) {
        if (a instanceof Primitivo && b instanceof Primitivo) {
            Primitivo a1 = (Primitivo) a;
            Primitivo a2 = (Primitivo) b;
            int obt = obtener_tipo(a1);
            int obt2 = obtener_tipo(a2);
            if (obt == Tipos.EXCEPCION
                    || obt2 == Tipos.EXCEPCION) {
                if (obt == Tipos.EXCEPCION) {
                    return a1;
                } else {
                    return a2;
                }
            } else {
                if (numerico(obt, obt2)) {
                    if (obt == Tipos.NUMERO && obt2 == Tipos.NUMERO) {
                        return new Primitivo("numero", a.getLinea(), a.getColumna(),(int) (a_int(a1.getValor()) - a_int(a2.getValor())));
                    } else {
                        String d1 = a1.getValor().toString();
                        String d2 = a2.getValor().toString();
                        double pr = Double.parseDouble(d1);
                        double pr2 = Double.parseDouble(d2);
                        return new Primitivo("decimal", a.getLinea(), a.getColumna(),(double) (pr - pr2));
                    }
                } else {
                    // IF todos los STRING
                    if ((obt == Tipos.STRING || obt2 == Tipos.STRING)) {
                        return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"La resta solo acepta operandos enteramente númericos");
                    } // IF para CARACTER-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.CARACTER) {
                        char c = (char) a1.getValor();
                        char t = (char) a2.getValor();
                        int concatenando = (int) c - (int) t;
                        if (concatenando >= 0) {
                            return new Primitivo("caracter", a.getLinea(), a.getColumna(),(char) concatenando);
                        } else {
                            return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"Resta de caracteres excedio el limite de no tener negativos");
                        }
                    } // IF para CARACTER-NUMERO, NUMERO-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.NUMERO
                            || obt == Tipos.NUMERO && obt2 == Tipos.CARACTER) {
                        char caracter = (obt == Tipos.CARACTER) ? (char) (a1.getValor()) : (char) (a2.getValor());
                        int entero = (obt == Tipos.NUMERO) ? (int) (a1.getValor()) : (int) (a2.getValor());
                        if (obt == Tipos.CARACTER) {
                            return new Primitivo("numero", a.getLinea(), a.getColumna(),(int) caracter - entero);
                        } else {
                            return new Primitivo("numero", a.getLinea(), a.getColumna(),entero - (int) caracter);
                        }
                    } // IF para BOOLEAN-CARACTER, CARACTER-BOOLEAN
                    else if (obt == Tipos.BOOLEAN && obt2 == Tipos.CARACTER
                            || obt == Tipos.CARACTER && obt2 == Tipos.BOOLEAN) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor()) : valor_boolean(a2.getValor());
                        char carac = (obt == Tipos.CARACTER) ? (char) a1.getValor() : (char) a2.getValor();
                        int s1 = (boo) ? 1 : 0;
                        int ca = (int) carac;
                        if (obt == Tipos.BOOLEAN) {
                            if ((s1 - ca) < 0) {
                                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No existen caracteres con indice negativo");
                            } else {
                                char c = (char) (s1 - ca);
                                return new Primitivo("caracter", a.getLinea(), a.getColumna(),c);
                            }
                        } else {
                            if ((ca - s1) < 0) {
                                return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"No existen caracteres con indice negativo");
                            } else {
                                char c = (char) (ca - s1);
                                return new Primitivo("caracter", a.getLinea(), a.getColumna(),c);
                            }
                        }
                    } // IF para NUMERO-BOOLEAN, BOOLEAN-NUMERO
                    else if (obt == Tipos.NUMERO && obt2 == Tipos.BOOLEAN
                            || obt == Tipos.BOOLEAN && obt2 == Tipos.NUMERO) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor().toString()) : valor_boolean(a2.getValor().toString());
                        int bo = (boo) ? 1 : 0;
                        int nu = (obt == Tipos.NUMERO) ? (int) a1.getValor() : (int) a2.getValor();
                        if (obt == Tipos.NUMERO) {
                            return new Primitivo("numero", a.getLinea(), a.getColumna(),nu - bo);
                        } else {
                            return new Primitivo("numero", a.getLinea(), a.getColumna(),bo - nu);
                        }
                    } // IF para DECIMAL-BOOLEAN, BOOLEAN-DECIMAL
                    else if (obt == Tipos.DECIMAL && obt2 == Tipos.BOOLEAN
                            || obt == Tipos.BOOLEAN && obt2 == Tipos.DECIMAL) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor().toString()) : valor_boolean(a2.getValor().toString());
                        double bo = (boo) ? 1.0 : 0.0;
                        double nu = (obt == Tipos.DECIMAL) ? (double) a1.getValor() : (double) a2.getValor();
                        if (obt == Tipos.DECIMAL) {
                            return new Primitivo("decimal", a.getLinea(), a.getColumna(),nu - bo);
                        } else {
                            return new Primitivo("decimal", a.getLinea(), a.getColumna(),bo - nu);
                        }
                    }// IF para BOOLEAN-BOOLEAN
                    else if (obt == Tipos.BOOLEAN && obt2 == Tipos.BOOLEAN) {
                        boolean boo = (boolean) valor_boolean(a1.getValor().toString());
                        boolean bo2 = (boolean) valor_boolean(a2.getValor().toString());
                        int b1 = (boo) ? 1 : 0;
                        int b2 = (bo2) ? 1 : 0;
                        return new Primitivo("numero", a.getLinea(), a.getColumna(),b1 - b2);
                    }// IF para CARACTER-DECIMAL, DECIMAL-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.DECIMAL
                            || obt == Tipos.DECIMAL && obt2 == Tipos.CARACTER) {
                        char carac = (obt == Tipos.CARACTER) ? (char) a1.getValor() : (char) a2.getValor();
                        double dec = (obt == Tipos.DECIMAL) ? (double) a1.getValor() : (double) a2.getValor();
                        if (obt == Tipos.DECIMAL) {
                            return new Primitivo("decimal", a.getLinea(), a.getColumna(),dec - (int) carac);
                        } else {
                            return new Primitivo("decimal", a.getLinea(), a.getColumna(),(int) carac - dec);
                        }
                    } else {
                        return null;
                    }
                }
            }
        } else {
            return null;
        }
    }

    public Termino negativo(Termino a) {
        if (!a.getTipo().equalsIgnoreCase("string")) {
            Primitivo a1 = (Primitivo) a;
            if (a1.getValor() instanceof Boolean) {
                boolean boo = (boolean) a1.getValor();
                int bo = (boo) ? 1 : 0;
                return new Primitivo("numero", a.getLinea(), a.getColumna(),-bo);
            } else if (a1.getValor() instanceof Double) {
                double nega = -(double) a1.getValor();
                return new Primitivo("decimal", a.getLinea(), a.getColumna(),nega);
            } else if (a1.getValor() instanceof Integer) {
                int nega = -(int) a1.getValor();
                return new Primitivo("numero", a.getLinea(), a.getColumna(),nega);
            } else {
                char nega = (char) a1.getValor();
                int ne = -(int) nega;
                return new Primitivo("numero", a.getLinea(), a.getColumna(),ne);
            }
        } else {
            return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"El operador negativo funciona solo con valores numericos");
        }
    }

    public Termino not(Termino a) {
        if (a.getTipo().equalsIgnoreCase("boolean")) {
            Primitivo a1 = (Primitivo) a;
            if (a1.getValor() instanceof Boolean) {
                boolean boo = (boolean) a1.getValor();
                return new Primitivo("boolean", a.getLinea(), a.getColumna(), !boo);
            } else {
                return new Primitivo("boolean", a.getLinea(), a.getColumna(), !valor_boolean(a1.getValor()));
            }
        } else {
            return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "El operador not funciona solo con valores booleanos");
        }
    }

    public Termino potencia(Termino a, Termino b) {
        if (a instanceof Primitivo && b instanceof Primitivo) {
            Primitivo a1 = (Primitivo) a;
            Primitivo a2 = (Primitivo) b;
            int obt = obtener_tipo(a1);
            int obt2 = obtener_tipo(a2);
            if (obt == Tipos.EXCEPCION
                    || obt2 == Tipos.EXCEPCION) {
                if (obt == Tipos.EXCEPCION) {
                    return a1;
                } else {
                    return a2;
                }
            } else {
                if (obt2 == Tipos.NUMERO) {
                    int n2 = (int) a2.getValor();
                    if (obt == Tipos.STRING) {
                        return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "Base de potencia es un tipo de dato no numerico");
                    } else if (obt == Tipos.NUMERO) {
                        int n1 = (int) a1.getValor();
                        int pot = (int) Math.pow(n1, n2);
                        return new Primitivo("numero", a.getLinea(), a.getColumna(), pot);
                    } else if (obt == Tipos.BOOLEAN) {
                        boolean n1 = (boolean) a1.getValor();
                        int numero1 = (n1) ? 1 : 0;
                        int pot = (int) Math.pow(numero1, n2);
                        return new Primitivo("numero", a.getLinea(), a.getColumna(), pot);
                    } else if (obt == Tipos.CARACTER) {
                        char n1 = (char) a1.getValor();
                        int numero1 = (int) n1;
                        int pot = (int) Math.pow(numero1, n2);
                        return new Primitivo("numero", a.getLinea(), a.getColumna(), pot);
                    } else {
                        double n1 = (double) a1.getValor();
                        double pot = (double) Math.pow(n1, n2);
                        return new Primitivo("decimal", a.getLinea(), a.getColumna(), pot);
                    }
                } else {
                    return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "Potencia solo acepta exponentes de tipo entero");
                }
            }
        } else {
            System.err.println("Error de tipos, la división debe hacerse entre números.");
            return null;
        }
    }

    public Termino modulo(Termino a, Termino b) {
        if (a instanceof Primitivo && b instanceof Primitivo) {
            Primitivo a1 = (Primitivo) a;
            Primitivo a2 = (Primitivo) b;
            int obt = obtener_tipo(a1);
            int obt2 = obtener_tipo(a2);
            if (obt == Tipos.EXCEPCION
                    || obt2 == Tipos.EXCEPCION) {
                if (obt == Tipos.EXCEPCION) {
                    return a1;
                } else {
                    return a2;
                }
            } else {
                if (obt == Tipos.NUMERO && obt2 == Tipos.NUMERO) {
                    int n1 = (int) a1.getValor();
                    int n2 = (int) a2.getValor();
                    int mod = n1 % n2;
                    return new Primitivo("numero", a.getLinea(), a.getColumna(),mod);
                } else {
                    return new Primitivo("excepcion", a.getLinea(), a.getColumna(),"Modulo solo acepta operandos de tipo entero");
                }
            }
        } else {
            System.err.println("Error de tipos, la división debe hacerse entre números.");
            return null;
        }
    }

    public Termino multiplicar(Termino a, Termino b) {
        if (a instanceof Primitivo && b instanceof Primitivo) {
            Primitivo a1 = (Primitivo) a;
            Primitivo a2 = (Primitivo) b;
            int obt = obtener_tipo(a1);
            int obt2 = obtener_tipo(a2);
            if (obt == Tipos.EXCEPCION
                    || obt2 == Tipos.EXCEPCION) {
                if (obt == Tipos.EXCEPCION) {
                    return a1;
                } else {
                    return a2;
                }
            } else {
                if (numerico(obt, obt2)) {
                    if (obt == Tipos.NUMERO && obt2 == Tipos.NUMERO) {
                        return new Primitivo("numero", a.getLinea(), a.getColumna(), (int) (a_int(a1.getValor()) * a_int(a2.getValor())));
                    } else {
                        String d1 = a1.getValor().toString();
                        String d2 = a2.getValor().toString();
                        double pr = Double.parseDouble(d1);
                        double pr2 = Double.parseDouble(d2);
                        return new Primitivo("decimal", a.getLinea(), a.getColumna(), (double) (pr * pr2));
                    }
                } else {
                    // IF para STRING-STRING, CARACTER-STRING, STRING-CARACTER
                    if ((obt == Tipos.STRING && obt2 == Tipos.STRING)
                            || (obt == Tipos.CARACTER && obt2 == Tipos.STRING)
                            || (obt == Tipos.STRING && obt2 == Tipos.CARACTER)) {
                        return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "Multiplicacion entre valores no numericos (" + a1.getTipo() + "*" + a2.getTipo() + ")");
                    } // IF para CARACTER-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.CARACTER) {
                        char c = (char) a1.getValor();
                        char t = (char) a2.getValor();
                        int c1 = (int) c;
                        int t1 = (int) t;
                        if ((c1 * t1) < 255) {
                            int mul = c1 * t1;
                            return new Primitivo("caracter", a.getLinea(), a.getColumna(), (char) mul);
                        } else {
                            return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "Multiplicacion de caracteres excedio el limite de 255");
                        }
                    } // IF para CARACTER-NUMERO, NUMERO-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.NUMERO
                            || obt == Tipos.NUMERO && obt2 == Tipos.CARACTER) {
                        char caracter = (obt == Tipos.CARACTER) ? (char) (a1.getValor()) : (char) (a2.getValor());
                        int entero = (obt == Tipos.NUMERO) ? (int) (a1.getValor()) : (int) (a2.getValor());
                        int n = (int) caracter;
                        return new Primitivo("numero", a.getLinea(), a.getColumna(), n * entero);
                    } // IF para BOOLEAN-CARACTER, CARACTER-BOOLEAN
                    else if (obt == Tipos.BOOLEAN && obt2 == Tipos.CARACTER
                            || obt == Tipos.CARACTER && obt2 == Tipos.BOOLEAN) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor()) : valor_boolean(a2.getValor());
                        char carac = (obt == Tipos.CARACTER) ? (char) a1.getValor() : (char) a2.getValor();
                        int s1 = (boo) ? 1 : 0;
                        int c1 = (int) carac;
                        int mult = s1 * c1;
                        return new Primitivo("numero", a.getLinea(), a.getColumna(), (char) mult);
                    } // IF para BOOLEAN-STRING
                    else if (obt == Tipos.BOOLEAN && obt2 == Tipos.STRING) {
                        return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "Multiplicacion entre valores no numericos (" + a1.getTipo() + "*" + a2.getTipo() + ")");
                    } // IF para STRING-BOOLEAN
                    else if (obt == Tipos.STRING && obt2 == Tipos.BOOLEAN) {
                        return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "Multiplicacion entre valores no numericos (" + a1.getTipo() + "*" + a2.getTipo() + ")");
                    } // IF para STRING-NUMERO, NUMERO-STRING, DECIMAL-STRING, STRING-DECIMAL
                    else if (obt == Tipos.STRING && obt2 == Tipos.NUMERO
                            || obt == Tipos.NUMERO && obt2 == Tipos.STRING
                            || obt == Tipos.DECIMAL && obt2 == Tipos.STRING
                            || obt == Tipos.STRING && obt2 == Tipos.DECIMAL) {
                        return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "Multiplicacion entre valores no numericos (" + a1.getTipo() + "*" + a2.getTipo() + ")");
                    } // IF para NUMERO-BOOLEAN, BOOLEAN-NUMERO
                    else if (obt == Tipos.NUMERO && obt2 == Tipos.BOOLEAN
                            || obt == Tipos.BOOLEAN && obt2 == Tipos.NUMERO) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor().toString()) : valor_boolean(a2.getValor().toString());
                        int bo = (boo) ? 1 : 0;
                        int nu = (obt == Tipos.NUMERO) ? (int) a1.getValor() : (int) a2.getValor();
                        return new Primitivo("numero", a.getLinea(), a.getColumna(), bo * nu);
                    } // IF para DECIMAL-BOOLEAN, BOOLEAN-DECIMAL
                    else if (obt == Tipos.DECIMAL && obt2 == Tipos.BOOLEAN
                            || obt == Tipos.BOOLEAN && obt2 == Tipos.DECIMAL) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor().toString()) : valor_boolean(a2.getValor().toString());
                        double bo = (boo) ? 1.0 : 0.0;
                        double nu = (obt == Tipos.DECIMAL) ? (double) a1.getValor() : (double) a2.getValor();
                        return new Primitivo("decimal", a.getLinea(), a.getColumna(), bo * nu);
                    }// IF para BOOLEAN-BOOLEAN
                    else if (obt == Tipos.BOOLEAN && obt2 == Tipos.BOOLEAN) {
                        boolean boo = (boolean) valor_boolean(a1.getValor().toString());
                        boolean bo2 = (boolean) valor_boolean(a2.getValor().toString());
                        return new Primitivo("boolean", a.getLinea(), a.getColumna(), boo && bo2);
                    }// IF para CARACTER-DECIMAL, DECIMAL-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.DECIMAL
                            || obt == Tipos.DECIMAL && obt2 == Tipos.CARACTER) {
                        char carac = (obt == Tipos.CARACTER) ? (char) a1.getValor() : (char) a2.getValor();
                        double dec = (obt == Tipos.DECIMAL) ? (double) a1.getValor() : (double) a2.getValor();
                        int car = (int) carac;
                        return new Primitivo("decimal", a.getLinea(), a.getColumna(), (double) (dec * car));
                    } else {
                        return null;
                    }
                }
            }
        } else {
            System.err.println("Error de tipos, la división debe hacerse entre números.");
            return null;
        }
    }

    public Termino sumar(Termino a, Termino b) {
        if (a instanceof Primitivo && b instanceof Primitivo) {
            Primitivo a1 = (Primitivo) a;
            Primitivo a2 = (Primitivo) b;
            int obt = obtener_tipo(a1);
            int obt2 = obtener_tipo(a2);
            if (obt == Tipos.EXCEPCION
                    || obt2 == Tipos.EXCEPCION) {
                if (obt == Tipos.EXCEPCION) {
                    return a1;
                } else {
                    return a2;
                }
            } else {
                if (numerico(obt, obt2)) {
                    if (obt == Tipos.NUMERO && obt2 == Tipos.NUMERO) {
                        return new Primitivo("numero", a.getLinea(), a.getColumna(), (int) (a_int(a1.getValor()) + a_int(a2.getValor())));
                    } else {
                        String d1 = a1.getValor().toString();
                        String d2 = a2.getValor().toString();
                        double pr = Double.parseDouble(d1);
                        double pr2 = Double.parseDouble(d2);
                        return new Primitivo("decimal", a.getLinea(), a.getColumna(), (double) (pr + pr2));
                    }
                } else {
                    // IF para STRING-STRING, CARACTER-STRING, STRING-CARACTER
                    if ((obt == Tipos.STRING && obt2 == Tipos.STRING)
                            || (obt == Tipos.CARACTER && obt2 == Tipos.STRING)
                            || (obt == Tipos.STRING && obt2 == Tipos.CARACTER)) {
                        String concatenando = (String) a1.getValor() + (String) a2.getValor();
                        return new Primitivo("string", a.getLinea(), a.getColumna(), concatenando);
                    } // IF para CARACTER-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.CARACTER) {
                        char c = (char) a1.getValor();
                        char t = (char) a2.getValor();
                        int concatenando = (int) c + (int) t;
                        if (concatenando < 255) {
                            return new Primitivo("caracter", a.getLinea(), a.getColumna(), (char) concatenando);
                        } else {
                            return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "Suma de caracteres excedio el limite de 255");
                        }
                    } // IF para CARACTER-NUMERO, NUMERO-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.NUMERO
                            || obt == Tipos.NUMERO && obt2 == Tipos.CARACTER) {
                        char caracter = (obt == Tipos.CARACTER) ? (char) (a1.getValor()) : (char) (a2.getValor());
                        int entero = (obt == Tipos.NUMERO) ? (int) (a1.getValor()) : (int) (a2.getValor());
                        return new Primitivo("numero", a.getLinea(), a.getColumna(), entero + (int) caracter);
                    } // IF para BOOLEAN-CARACTER, CARACTER-BOOLEAN
                    else if (obt == Tipos.BOOLEAN && obt2 == Tipos.CARACTER
                            || obt == Tipos.CARACTER && obt2 == Tipos.BOOLEAN) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor()) : valor_boolean(a2.getValor());
                        char carac = (obt == Tipos.CARACTER) ? (char) a1.getValor() : (char) a2.getValor();
                        int s1 = (boo) ? 1 : 0;
                        s1 += (int) carac;
                        if (s1 < 255) {
                            return new Primitivo("caracter", a.getLinea(), a.getColumna(), (char) s1);
                        } else {
                            return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "Suma de caracteres excedio el limite de 255");
                        }
                    } // IF para BOOLEAN-STRING
                    else if (obt == Tipos.BOOLEAN && obt2 == Tipos.STRING) {
                        String concatenar = (obt == Tipos.BOOLEAN) ? a1.getValor().toString() : a2.getValor().toString();
                        return new Primitivo("string", a.getLinea(), a.getColumna(), concatenar + (String) a2.getValor());
                    } // IF para STRING-BOOLEAN
                    else if (obt == Tipos.STRING && obt2 == Tipos.BOOLEAN) {
                        String concatenar = (obt == Tipos.BOOLEAN) ? a1.getValor().toString() : a2.getValor().toString();
                        return new Primitivo("string", a.getLinea(), a.getColumna(), (String) a1.getValor() + concatenar);
                    } // IF para STRING-NUMERO, NUMERO-STRING, DECIMAL-STRING, STRING-DECIMAL
                    else if (obt == Tipos.STRING && obt2 == Tipos.NUMERO
                            || obt == Tipos.NUMERO && obt2 == Tipos.STRING
                            || obt == Tipos.DECIMAL && obt2 == Tipos.STRING
                            || obt == Tipos.STRING && obt2 == Tipos.DECIMAL) {
                        String concatenando = a1.getValor().toString() + a2.getValor().toString();
                        return new Primitivo("string", a.getLinea(), a.getColumna(), concatenando);
                    } // IF para NUMERO-BOOLEAN, BOOLEAN-NUMERO
                    else if (obt == Tipos.NUMERO && obt2 == Tipos.BOOLEAN
                            || obt == Tipos.BOOLEAN && obt2 == Tipos.NUMERO) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor().toString()) : valor_boolean(a2.getValor().toString());
                        int bo = (boo) ? 1 : 0;
                        int nu = (obt == Tipos.NUMERO) ? (int) a1.getValor() : (int) a2.getValor();
                        return new Primitivo("numero", a.getLinea(), a.getColumna(), bo + nu);
                    } // IF para DECIMAL-BOOLEAN, BOOLEAN-DECIMAL
                    else if (obt == Tipos.DECIMAL && obt2 == Tipos.BOOLEAN
                            || obt == Tipos.BOOLEAN && obt2 == Tipos.DECIMAL) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor().toString()) : valor_boolean(a2.getValor().toString());
                        double bo = (boo) ? 1.0 : 0.0;
                        double nu = (obt == Tipos.DECIMAL) ? (double) a1.getValor() : (double) a2.getValor();
                        return new Primitivo("decimal", a.getLinea(), a.getColumna(), bo + nu);
                    }// IF para BOOLEAN-BOOLEAN
                    else if (obt == Tipos.BOOLEAN && obt2 == Tipos.BOOLEAN) {
                        boolean boo = (boolean) valor_boolean(a1.getValor().toString());
                        boolean bo2 = (boolean) valor_boolean(a2.getValor().toString());
                        boolean respuesta = boo || bo2;
                        return new Primitivo("boolean", a.getLinea(), a.getColumna(), respuesta);
                    }// IF para CARACTER-DECIMAL, DECIMAL-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.DECIMAL
                            || obt == Tipos.DECIMAL && obt2 == Tipos.CARACTER) {
                        char carac = (obt == Tipos.CARACTER) ? (char) a1.getValor() : (char) a2.getValor();
                        double dec = (obt == Tipos.DECIMAL) ? (double) a1.getValor() : (double) a2.getValor();
                        double respuesta = carac + dec;
                        return new Primitivo("decimal", a.getLinea(), a.getColumna(), respuesta);
                    } else {
                        return null;
                    }
                }
            }
        } else {
            return null;
        }
    }

    public Termino dividir(Termino a, Termino b) {
        if (a instanceof Primitivo && b instanceof Primitivo) {
            Primitivo a1 = (Primitivo) a;
            Primitivo a2 = (Primitivo) b;
            int obt = obtener_tipo(a1);
            int obt2 = obtener_tipo(a2);
            if (obt == Tipos.EXCEPCION
                    || obt2 == Tipos.EXCEPCION) {
                if (obt == Tipos.EXCEPCION) {
                    return a1;
                } else {
                    return a2;
                }
            } else {
                if (numerico(obt, obt2)) {
                    if ((int) a2.getValor() == 0) {
                        return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "División entre cero");
                    } else {
                        if (obt == Tipos.NUMERO && obt2 == Tipos.NUMERO) {
                            return new Primitivo("numero", a.getLinea(), a.getColumna(), (int) (a_int(a1.getValor()) / a_int(a2.getValor())));
                        } else {
                            String d1 = a1.getValor().toString();
                            String d2 = a2.getValor().toString();
                            double pr = Double.parseDouble(d1);
                            double pr2 = Double.parseDouble(d2);
                            return new Primitivo("decimal", a.getLinea(), a.getColumna(), (double) (pr / pr2));
                        }
                    }
                } else {
                    // IF para STRING-STRING, CARACTER-STRING, STRING-CARACTER
                    if ((obt == Tipos.STRING && obt2 == Tipos.STRING)
                            || (obt == Tipos.CARACTER && obt2 == Tipos.STRING)
                            || (obt == Tipos.STRING && obt2 == Tipos.CARACTER)) {
                        return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "División entre valores no numericos (" + a1.getTipo() + "/" + a2.getTipo() + ")");
                    } // IF para CARACTER-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.CARACTER) {
                        char c = (char) a1.getValor();
                        char t = (char) a2.getValor();
                        int c1 = (int) c;
                        int t1 = (int) t;
                        if (t1 == 0) {
                            return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "División entre cero");
                        } else {
                            int res = (int) (c1 / t1);
                            return new Primitivo("caracter", a.getLinea(), a.getColumna(), (char) res);
                        }
                    } // IF para CARACTER-NUMERO, NUMERO-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.NUMERO
                            || obt == Tipos.NUMERO && obt2 == Tipos.CARACTER) {
                        char caracter = (obt == Tipos.CARACTER) ? (char) (a1.getValor()) : (char) (a2.getValor());
                        int entero = (obt == Tipos.NUMERO) ? (int) (a1.getValor()) : (int) (a2.getValor());
                        int n = (int) caracter;
                        if (obt == Tipos.CARACTER) {
                            if (entero != 0) {
                                int retorno = (int) (n / entero);
                                return new Primitivo("numero", a.getLinea(), a.getColumna(), retorno);
                            } else {
                                return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "División entre cero");
                            }
                        } else {
                            if (n != 0) {
                                int retorno = (int) (entero / n);
                                return new Primitivo("numero", a.getLinea(), a.getColumna(), retorno);
                            } else {
                                return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "División entre cero");
                            }
                        }
                    } // IF para BOOLEAN-CARACTER, CARACTER-BOOLEAN
                    else if (obt == Tipos.BOOLEAN && obt2 == Tipos.CARACTER
                            || obt == Tipos.CARACTER && obt2 == Tipos.BOOLEAN) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor()) : valor_boolean(a2.getValor());
                        char carac = (obt == Tipos.CARACTER) ? (char) a1.getValor() : (char) a2.getValor();
                        int s1 = (boo) ? 1 : 0;
                        int c1 = (int) carac;
                        if (obt == Tipos.BOOLEAN) {
                            if (c1 == 0) {
                                return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "División entre cero");
                            } else {
                                int retorno = (int) (s1 / c1);
                                return new Primitivo("caracter", a.getLinea(), a.getColumna(), (char) retorno);
                            }
                        } else {
                            if (s1 == 0) {
                                return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "División entre cero");
                            } else {
                                int retorno = (int) (c1 / s1);
                                return new Primitivo("caracter", a.getLinea(), a.getColumna(), (char) retorno);
                            }
                        }
                    } // IF para BOOLEAN-STRING
                    else if (obt == Tipos.BOOLEAN && obt2 == Tipos.STRING) {
                        return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "División entre valores no numericos (" + a1.getTipo() + "/" + a2.getTipo() + ")");
                    } // IF para STRING-BOOLEAN
                    else if (obt == Tipos.STRING && obt2 == Tipos.BOOLEAN) {
                        return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "División entre valores no numericos (" + a1.getTipo() + "/" + a2.getTipo() + ")");
                    } // IF para STRING-NUMERO, NUMERO-STRING, DECIMAL-STRING, STRING-DECIMAL
                    else if (obt == Tipos.STRING && obt2 == Tipos.NUMERO
                            || obt == Tipos.NUMERO && obt2 == Tipos.STRING
                            || obt == Tipos.DECIMAL && obt2 == Tipos.STRING
                            || obt == Tipos.STRING && obt2 == Tipos.DECIMAL) {
                        return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "División entre valores no numericos (" + a1.getTipo() + "/" + a2.getTipo() + ")");
                    } // IF para NUMERO-BOOLEAN, BOOLEAN-NUMERO
                    else if (obt == Tipos.NUMERO && obt2 == Tipos.BOOLEAN
                            || obt == Tipos.BOOLEAN && obt2 == Tipos.NUMERO) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor().toString()) : valor_boolean(a2.getValor().toString());
                        int bo = (boo) ? 1 : 0;
                        int nu = (obt == Tipos.NUMERO) ? (int) a1.getValor() : (int) a2.getValor();
                        if (obt == Tipos.NUMERO) {
                            if (bo == 0) {
                                return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "División entre cero");
                            } else {
                                int retorno = (int) (nu / bo);
                                return new Primitivo("numero", a.getLinea(), a.getColumna(), retorno);
                            }
                        } else {
                            if (nu == 0) {
                                return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "División entre cero");
                            } else {
                                int retorno = (int) (bo / nu);
                                return new Primitivo("numero", a.getLinea(), a.getColumna(), retorno);
                            }
                        }
                    } // IF para DECIMAL-BOOLEAN, BOOLEAN-DECIMAL
                    else if (obt == Tipos.DECIMAL && obt2 == Tipos.BOOLEAN
                            || obt == Tipos.BOOLEAN && obt2 == Tipos.DECIMAL) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor().toString()) : valor_boolean(a2.getValor().toString());
                        double bo = (boo) ? 1.0 : 0.0;
                        double nu = (obt == Tipos.DECIMAL) ? (double) a1.getValor() : (double) a2.getValor();
                        if (obt == Tipos.DECIMAL) {
                            if (bo == 0) {
                                return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "División entre cero");
                            } else {
                                double retorno = (double) (nu / bo);
                                return new Primitivo("decimal", a.getLinea(), a.getColumna(), retorno);
                            }
                        } else {
                            if (nu == 0) {
                                return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "División entre cero");
                            } else {
                                double retorno = (double) (bo / nu);
                                return new Primitivo("decimal", a.getLinea(), a.getColumna(), retorno);
                            }
                        }
                    }// IF para BOOLEAN-BOOLEAN
                    else if (obt == Tipos.BOOLEAN && obt2 == Tipos.BOOLEAN) {
                        boolean boo = (boolean) valor_boolean(a1.getValor().toString());
                        boolean bo2 = (boolean) valor_boolean(a2.getValor().toString());
                        if (!bo2) {
                            return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "División entre cero");
                        } else {
                            boolean respuesta = boo && bo2;
                            return new Primitivo("boolean", a.getLinea(), a.getColumna(), respuesta);
                        }
                    }// IF para CARACTER-DECIMAL, DECIMAL-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.DECIMAL
                            || obt == Tipos.DECIMAL && obt2 == Tipos.CARACTER) {
                        char carac = (obt == Tipos.CARACTER) ? (char) a1.getValor() : (char) a2.getValor();
                        double dec = (obt == Tipos.DECIMAL) ? (double) a1.getValor() : (double) a2.getValor();
                        int car = (int) carac;
                        if (obt == Tipos.CARACTER) {
                            if (dec == 0) {
                                return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "División entre cero");
                            } else {
                                double retorno = (double) (car / dec);
                                return new Primitivo("decimal", a.getLinea(), a.getColumna(),retorno);
                            }
                        } else {
                            if (car == 0) {
                                return new Primitivo("excepcion", a.getLinea(), a.getColumna(), "División entre cero");
                            } else {
                                double retorno = (double) (dec / car);
                                return new Primitivo("decimal", a.getLinea(), a.getColumna(), retorno);
                            }
                        }
                    } else {
                        return null;
                    }
                }
            }
        } else {
            System.err.println("Error de tipos, la división debe hacerse entre números.");
            return null;
        }
    }

    public boolean valor_boolean(Object boo) {
        return boo.toString().equalsIgnoreCase("verdadero")
                || boo.toString().equalsIgnoreCase("true")
                || boo.toString().equalsIgnoreCase("1");
    }

    public int obtener_tipo(Primitivo a) {
        switch (a.getTipo()) {
            case "numero":
                return Tipos.NUMERO;
            case "string":
                return Tipos.STRING;
            case "caracter":
                return Tipos.CARACTER;
            case "boolean":
                return Tipos.BOOLEAN;
            case "decimal":
                return Tipos.DECIMAL;
            default:
                return Tipos.EXCEPCION;
        }
    }

    public int a_int(Object o) {
        return (int) o;
    }

    public Double a_decimal(Object o) {
        int i = (int) o;
        return new Double(i);
    }

    public boolean numerico(int a1, int a2) {
        boolean b1 = a1 == Tipos.NUMERO || a1 == Tipos.DECIMAL;
        boolean b2 = a2 == Tipos.NUMERO || a2 == Tipos.DECIMAL;
        return b1 && b2;
    }

    public Expresion(Operacion tipo) {
        this.tipo = tipo;
    }

    public Expresion getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(Expresion izquierda) {
        this.izquierda = izquierda;
    }

    public Expresion getDerecha() {
        return derecha;
    }

    public void setDerecha(Expresion derecha) {
        this.derecha = derecha;
    }

    public Termino getValor() {
        return valor;
    }

    public void setValor(Termino valor) {
        this.valor = valor;
    }

}
