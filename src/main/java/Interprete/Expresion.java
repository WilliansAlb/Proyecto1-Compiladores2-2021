/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author willi
 */
public class Expresion {

    public static enum Tipo_operacion {
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
        VARIABLE
    }
    private final Tipo_operacion tipo;
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
    public Expresion(Expresion izquierda, Expresion derecha, Tipo_operacion tipo) {
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
    public Expresion(Expresion izquierda, Tipo_operacion tipo) {
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
    public Expresion(Termino a, Tipo_operacion tipo) {
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
        this.tipo = Tipo_operacion.NUMERO;
    }

    public Termino ejecutar() {
        Termino a = (izquierda == null) ? null : izquierda.ejecutar();
        Termino b = (derecha == null) ? null : derecha.ejecutar();

        if (tipo == Tipo_operacion.ENTRE) {
            return dividir(a, b);
        } else if (tipo == Tipo_operacion.SUMA) {
            return sumar(a, b);
        } else if (tipo == Tipo_operacion.POR) {
            return multiplicar(a, b);
        } else if (tipo == Tipo_operacion.PRIMITIVO) {
            return (Primitivo) valor;
        } else if (tipo == Tipo_operacion.MOD) {
            return modulo(a, b);
        } else if (tipo == Tipo_operacion.POT) {
            return potencia(a, b);
        } else if (tipo == Tipo_operacion.NOT) {
            return not(a);
        } else if (tipo == Tipo_operacion.NEGATIVO) {
            return negativo(a);
        } else if (tipo == Tipo_operacion.RESTA) {
            return resta(a, b);
        } else if (tipo == Tipo_operacion.AND) {
            return and(a, b);
        } else if (tipo == Tipo_operacion.NAND) {
            return nand(a, b);
        } else if (tipo == Tipo_operacion.OR) {
            return or(a, b);
        } else if (tipo == Tipo_operacion.NOR) {
            return nor(a, b);
        } else if (tipo == Tipo_operacion.XOR) {
            return xor(a, b);
        } else if (tipo == Tipo_operacion.IGUAL) {
            return igual(a, b);
        } else if (tipo == Tipo_operacion.DIFF) {
            return diff(a, b);
        } else if (tipo == Tipo_operacion.MAYOR) {
            return mayor(a, b);
        } else if (tipo == Tipo_operacion.MENOR) {
            return menor(a, b);
        } else if (tipo == Tipo_operacion.MAYOR_I) {
            return mayor_igual(a, b);
        } else if (tipo == Tipo_operacion.MENOR_I) {
            return menor_igual(a, b);
        } else {
            return new Primitivo("excepcion", null, "Ojala que no llegue acá");
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
                return new Primitivo("boolean", null, n1 < n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (int) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", null, n1 < n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (int) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", null, n1 < n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (int) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", null, n1 < n2);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("decimal")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                double n1 = (double) a1.getValor();
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n1 < n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                double n1 = (double) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", null, n1 < n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                double n1 = (double) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", null, n1 < n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                double n1 = (double) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", null, n1 < n2);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("boolean")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n1 < n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", null, n1 < n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", null, n1 < n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", null, n1 < n2);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("caracter")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                char s1 = (char) a1.getValor();
                char s2 = (char) b1.getValor();
                int s11 = (int) s1;
                int s22 = (int) s2;
                return new Primitivo("boolean", null, s11 < s22);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                char c1 = (char) a1.getValor();
                int n1 = (int) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n2 < n1);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                char c1 = (char) a1.getValor();
                double n1 = (double) b1.getValor();
                double n2 = (int) c1;
                return new Primitivo("boolean", null, n2 < n1);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                char c1 = (char) a1.getValor();
                int n1 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n2 < n1);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else {
            if (b1.getTipo().equalsIgnoreCase("string")) {
                String s1 = a1.getValor().toString();
                int s11 = s1.length();
                String s2 = b1.getValor().toString();
                int s22 = s2.length();
                return new Primitivo("boolean", null, s11 < s22);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
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
                return new Primitivo("boolean", null, n1 > n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (int) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", null, n1 > n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (int) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", null, n1 > n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (int) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", null, n1 > n2);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("decimal")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                double n1 = (double) a1.getValor();
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n1 > n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                double n1 = (double) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", null, n1 > n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                double n1 = (double) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", null, n1 > n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                double n1 = (double) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", null, n1 > n2);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("boolean")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n1 > n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", null, n1 > n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", null, n1 > n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", null, n1 > n2);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("caracter")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                char s1 = (char) a1.getValor();
                char s2 = (char) b1.getValor();
                int s11 = (int) s1;
                int s22 = (int) s2;
                return new Primitivo("boolean", null, s11 > s22);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                char c1 = (char) a1.getValor();
                int n1 = (int) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n2 > n1);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                char c1 = (char) a1.getValor();
                double n1 = (double) b1.getValor();
                double n2 = (int) c1;
                return new Primitivo("boolean", null, n2 > n1);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                char c1 = (char) a1.getValor();
                int n1 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n2 > n1);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else {
            if (b1.getTipo().equalsIgnoreCase("string")) {
                String s1 = a1.getValor().toString();
                int s11 = s1.length();
                String s2 = b1.getValor().toString();
                int s22 = s2.length();
                return new Primitivo("boolean", null, s11 > s22);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
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
                return new Primitivo("boolean", null, n1 >= n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (int) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", null, n1 >= n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (int) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", null, n1 >= n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (int) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", null, n1 >= n2);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("decimal")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                double n1 = (double) a1.getValor();
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n1 >= n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                double n1 = (double) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", null, n1 >= n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                double n1 = (double) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", null, n1 >= n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                double n1 = (double) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", null, n1 >= n2);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("boolean")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n1 >= n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", null, n1 >= n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", null, n1 >= n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", null, n1 >= n2);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("caracter")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                char s1 = (char) a1.getValor();
                char s2 = (char) b1.getValor();
                int s11 = (int) s1;
                int s22 = (int) s2;
                return new Primitivo("boolean", null, s11 >= s22);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                char c1 = (char) a1.getValor();
                int n1 = (int) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n2 >= n1);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                char c1 = (char) a1.getValor();
                double n1 = (double) b1.getValor();
                double n2 = (int) c1;
                return new Primitivo("boolean", null, n2 >= n1);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                char c1 = (char) a1.getValor();
                int n1 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n2 >= n1);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else {
            if (b1.getTipo().equalsIgnoreCase("string")) {
                String s1 = a1.getValor().toString();
                int s11 = s1.length();
                String s2 = b1.getValor().toString();
                int s22 = s2.length();
                return new Primitivo("boolean", null, s11 >= s22);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
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
                return new Primitivo("boolean", null, n1 <= n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (int) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", null, n1 <= n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (int) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", null, n1 <= n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (int) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", null, n1 <= n2);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("decimal")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                double n1 = (double) a1.getValor();
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n1 <= n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                double n1 = (double) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", null, n1 <= n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                double n1 = (double) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", null, n1 <= n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                double n1 = (double) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", null, n1 <= n2);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("boolean")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n1 <= n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", null, n1 <= n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", null, n1 <= n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", null, n1 <= n2);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("caracter")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                char s1 = (char) a1.getValor();
                char s2 = (char) b1.getValor();
                int s11 = (int) s1;
                int s22 = (int) s2;
                return new Primitivo("boolean", null, s11 <= s22);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                char c1 = (char) a1.getValor();
                int n1 = (int) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n2 <= n1);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                char c1 = (char) a1.getValor();
                double n1 = (double) b1.getValor();
                double n2 = (int) c1;
                return new Primitivo("boolean", null, n2 <= n1);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                char c1 = (char) a1.getValor();
                int n1 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n2 <= n1);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else {
            if (b1.getTipo().equalsIgnoreCase("string")) {
                String s1 = a1.getValor().toString();
                int s11 = s1.length();
                String s2 = b1.getValor().toString();
                int s22 = s2.length();
                return new Primitivo("boolean", null, s11 <= s22);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
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
                return new Primitivo("boolean", null, n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (int) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", null, n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (int) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", null, n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (int) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", null, n1 != n2);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("decimal")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                double n1 = (double) a1.getValor();
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                double n1 = (double) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", null, n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                double n1 = (double) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", null, n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                double n1 = (double) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", null, n1 != n2);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("boolean")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", null, n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", null, n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                int n2 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", null, n1 != n2);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("caracter")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                String s1 = a1.getValor().toString();
                String s2 = b1.getValor().toString();
                return new Primitivo("boolean", null, !s1.equals(s2));
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                char c1 = (char) a1.getValor();
                int n1 = (int) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                char c1 = (char) a1.getValor();
                double n1 = (double) b1.getValor();
                double n2 = (int) c1;
                return new Primitivo("boolean", null, n1 != n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                char c1 = (char) a1.getValor();
                int n1 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n1 != n2);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else {
            if (b1.getTipo().equalsIgnoreCase("string")) {
                String s1 = a1.getValor().toString();
                String s2 = b1.getValor().toString();
                return new Primitivo("boolean", null, !s1.equals(s2));
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
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
                return new Primitivo("boolean", null, n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (int) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", null, n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (int) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", null, n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (int) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", null, n1 == n2);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("decimal")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                double n1 = (double) a1.getValor();
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                double n1 = (double) a1.getValor();
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", null, n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                double n1 = (double) a1.getValor();
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", null, n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                double n1 = (double) a1.getValor();
                int n2 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", null, n1 == n2);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("boolean")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                char c1 = (char) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                int n2 = (int) b1.getValor();
                return new Primitivo("boolean", null, n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                int n1 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                double n2 = (double) b1.getValor();
                return new Primitivo("boolean", null, n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                int n1 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                int n2 = (valor_boolean(a1.getValor().toString()) ? 1 : 0);
                return new Primitivo("boolean", null, n1 == n2);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else if (a1.getTipo().equalsIgnoreCase("caracter")) {
            if (b1.getTipo().equalsIgnoreCase("caracter")) {
                String s1 = a1.getValor().toString();
                String s2 = b1.getValor().toString();
                return new Primitivo("boolean", null, s1.equals(s2));
            } else if (b1.getTipo().equalsIgnoreCase("numero")) {
                char c1 = (char) a1.getValor();
                int n1 = (int) b1.getValor();
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("decimal")) {
                char c1 = (char) a1.getValor();
                double n1 = (double) b1.getValor();
                double n2 = (int) c1;
                return new Primitivo("boolean", null, n1 == n2);
            } else if (b1.getTipo().equalsIgnoreCase("boolean")) {
                char c1 = (char) a1.getValor();
                int n1 = (valor_boolean(b1.getValor().toString()) ? 1 : 0);
                int n2 = (int) c1;
                return new Primitivo("boolean", null, n1 == n2);
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        } else {
            if (b1.getTipo().equalsIgnoreCase("string")) {
                String s1 = a1.getValor().toString();
                String s2 = b1.getValor().toString();
                return new Primitivo("boolean", null, s1.equals(s2));
            } else {
                return new Primitivo("excepcion", null, "No puedes comparar un string con otro tipo de dato");
            }
        }
    }

    public Termino nand(Termino a, Termino b) {
        if (a.getTipo().equalsIgnoreCase("boolean") && b.getTipo().equalsIgnoreCase("boolean")) {
            Primitivo a1 = (Primitivo) a;
            Primitivo b1 = (Primitivo) b;
            boolean boo1 = valor_boolean(a1.getValor().toString());
            boolean boo2 = valor_boolean(b1.getValor().toString());
            return new Primitivo("boolean", null, !(boo1 && boo2));
        } else {
            return new Primitivo("excepcion", null, "El operador and funciona solo con valores booleanos");
        }
    }

    public Termino or(Termino a, Termino b) {
        if (a.getTipo().equalsIgnoreCase("boolean") && b.getTipo().equalsIgnoreCase("boolean")) {
            Primitivo a1 = (Primitivo) a;
            Primitivo b1 = (Primitivo) b;
            boolean boo1 = valor_boolean(a1.getValor().toString());
            boolean boo2 = valor_boolean(b1.getValor().toString());
            return new Primitivo("boolean", null, boo1 || boo2);
        } else {
            return new Primitivo("excepcion", null, "El operador and funciona solo con valores booleanos");
        }
    }

    public Termino nor(Termino a, Termino b) {
        if (a.getTipo().equalsIgnoreCase("boolean") && b.getTipo().equalsIgnoreCase("boolean")) {
            Primitivo a1 = (Primitivo) a;
            Primitivo b1 = (Primitivo) b;
            boolean boo1 = valor_boolean(a1.getValor().toString());
            boolean boo2 = valor_boolean(b1.getValor().toString());
            return new Primitivo("boolean", null, !(boo1 || boo2));
        } else {
            return new Primitivo("excepcion", null, "El operador and funciona solo con valores booleanos");
        }
    }

    public Termino xor(Termino a, Termino b) {
        if (a.getTipo().equalsIgnoreCase("boolean") && b.getTipo().equalsIgnoreCase("boolean")) {
            Primitivo a1 = (Primitivo) a;
            Primitivo b1 = (Primitivo) b;
            boolean boo1 = valor_boolean(a1.getValor().toString());
            boolean boo2 = valor_boolean(b1.getValor().toString());
            return new Primitivo("boolean", null, ((boo1 && !boo2) || (!boo1 && boo2)));
        } else {
            return new Primitivo("excepcion", null, "El operador and funciona solo con valores booleanos");
        }
    }

    public Termino and(Termino a, Termino b) {
        if (a.getTipo().equalsIgnoreCase("boolean") && b.getTipo().equalsIgnoreCase("boolean")) {
            Primitivo a1 = (Primitivo) a;
            Primitivo b1 = (Primitivo) b;
            boolean boo1 = valor_boolean(a1.getValor().toString());
            boolean boo2 = valor_boolean(b1.getValor().toString());
            return new Primitivo("boolean", null, boo1 && boo2);
        } else {
            return new Primitivo("excepcion", null, "El operador and funciona solo con valores booleanos");
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
                        return new Primitivo("numero", null, (int) (a_int(a1.getValor()) - a_int(a2.getValor())));
                    } else {
                        String d1 = a1.getValor().toString();
                        String d2 = a2.getValor().toString();
                        double pr = Double.parseDouble(d1);
                        double pr2 = Double.parseDouble(d2);
                        return new Primitivo("decimal", null, (double) (pr - pr2));
                    }
                } else {
                    // IF todos los STRING
                    if ((obt == Tipos.STRING || obt2 == Tipos.STRING)) {
                        return new Primitivo("excepcion", null, "La resta solo acepta operandos enteramente númericos");
                    } // IF para CARACTER-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.CARACTER) {
                        char c = (char) a1.getValor();
                        char t = (char) a2.getValor();
                        int concatenando = (int) c - (int) t;
                        if (concatenando >= 0) {
                            return new Primitivo("caracter", null, (char) concatenando);
                        } else {
                            return new Primitivo("excepcion", null, "Resta de caracteres excedio el limite de no tener negativos");
                        }
                    } // IF para CARACTER-NUMERO, NUMERO-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.NUMERO
                            || obt == Tipos.NUMERO && obt2 == Tipos.CARACTER) {
                        char caracter = (obt == Tipos.CARACTER) ? (char) (a1.getValor()) : (char) (a2.getValor());
                        int entero = (obt == Tipos.NUMERO) ? (int) (a1.getValor()) : (int) (a2.getValor());
                        if (obt == Tipos.CARACTER) {
                            return new Primitivo("numero", null, (int) caracter - entero);
                        } else {
                            return new Primitivo("numero", null, entero - (int) caracter);
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
                                return new Primitivo("excepcion", null, "No existen caracteres con indice negativo");
                            } else {
                                char c = (char) (s1 - ca);
                                return new Primitivo("caracter", null, c);
                            }
                        } else {
                            if ((ca - s1) < 0) {
                                return new Primitivo("excepcion", null, "No existen caracteres con indice negativo");
                            } else {
                                char c = (char) (ca - s1);
                                return new Primitivo("caracter", null, c);
                            }
                        }
                    } // IF para NUMERO-BOOLEAN, BOOLEAN-NUMERO
                    else if (obt == Tipos.NUMERO && obt2 == Tipos.BOOLEAN
                            || obt == Tipos.BOOLEAN && obt2 == Tipos.NUMERO) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor().toString()) : valor_boolean(a2.getValor().toString());
                        int bo = (boo) ? 1 : 0;
                        int nu = (obt == Tipos.NUMERO) ? (int) a1.getValor() : (int) a2.getValor();
                        if (obt == Tipos.NUMERO) {
                            return new Primitivo("numero", null, nu - bo);
                        } else {
                            return new Primitivo("numero", null, bo - nu);
                        }
                    } // IF para DECIMAL-BOOLEAN, BOOLEAN-DECIMAL
                    else if (obt == Tipos.DECIMAL && obt2 == Tipos.BOOLEAN
                            || obt == Tipos.BOOLEAN && obt2 == Tipos.DECIMAL) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor().toString()) : valor_boolean(a2.getValor().toString());
                        double bo = (boo) ? 1.0 : 0.0;
                        double nu = (obt == Tipos.DECIMAL) ? (double) a1.getValor() : (double) a2.getValor();
                        if (obt == Tipos.DECIMAL) {
                            return new Primitivo("decimal", null, nu - bo);
                        } else {
                            return new Primitivo("decimal", null, bo - nu);
                        }
                    }// IF para BOOLEAN-BOOLEAN
                    else if (obt == Tipos.BOOLEAN && obt2 == Tipos.BOOLEAN) {
                        boolean boo = (boolean) valor_boolean(a1.getValor().toString());
                        boolean bo2 = (boolean) valor_boolean(a2.getValor().toString());
                        int b1 = (boo) ? 1 : 0;
                        int b2 = (bo2) ? 1 : 0;
                        return new Primitivo("numero", null, b1 - b2);
                    }// IF para CARACTER-DECIMAL, DECIMAL-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.DECIMAL
                            || obt == Tipos.DECIMAL && obt2 == Tipos.CARACTER) {
                        char carac = (obt == Tipos.CARACTER) ? (char) a1.getValor() : (char) a2.getValor();
                        double dec = (obt == Tipos.DECIMAL) ? (double) a1.getValor() : (double) a2.getValor();
                        if (obt == Tipos.DECIMAL) {
                            return new Primitivo("decimal", null, dec - (int) carac);
                        } else {
                            return new Primitivo("decimal", null, (int) carac - dec);
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
                return new Primitivo("numero", null, -bo);
            } else if (a1.getValor() instanceof Double) {
                double nega = -(double) a1.getValor();
                return new Primitivo("decimal", null, nega);
            } else if (a1.getValor() instanceof Integer) {
                int nega = -(int) a1.getValor();
                return new Primitivo("numero", null, nega);
            } else {
                char nega = (char) a1.getValor();
                int ne = -(int) nega;
                return new Primitivo("numero", null, ne);
            }
        } else {
            return new Primitivo("excepcion", null, "El operador negativo funciona solo con valores numericos");
        }
    }

    public Termino not(Termino a) {
        if (a.getTipo().equalsIgnoreCase("boolean")) {
            Primitivo a1 = (Primitivo) a;
            if (a1.getValor() instanceof Boolean) {
                boolean boo = (boolean) a1.getValor();
                return new Primitivo("boolean", null, !boo);
            } else {
                return new Primitivo("boolean", null, !valor_boolean(a1.getValor()));
            }
        } else {
            return new Primitivo("excepcion", null, "El operador not funciona solo con valores booleanos");
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
                        return new Primitivo("excepcion", null, "Base de potencia es un tipo de dato no numerico");
                    } else if (obt == Tipos.NUMERO) {
                        int n1 = (int) a1.getValor();
                        int pot = (int) Math.pow(n1, n2);
                        return new Primitivo("numero", null, pot);
                    } else if (obt == Tipos.BOOLEAN) {
                        boolean n1 = (boolean) a1.getValor();
                        int numero1 = (n1) ? 1 : 0;
                        int pot = (int) Math.pow(numero1, n2);
                        return new Primitivo("numero", null, pot);
                    } else if (obt == Tipos.CARACTER) {
                        char n1 = (char) a1.getValor();
                        int numero1 = (int) n1;
                        int pot = (int) Math.pow(numero1, n2);
                        return new Primitivo("numero", null, pot);
                    } else {
                        double n1 = (double) a1.getValor();
                        double pot = (double) Math.pow(n1, n2);
                        return new Primitivo("decimal", null, pot);
                    }
                } else {
                    return new Primitivo("excepcion", null, "Potencia solo acepta exponentes de tipo entero");
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
                    return new Primitivo("numero", null, mod);
                } else {
                    return new Primitivo("excepcion", null, "Modulo solo acepta operandos de tipo entero");
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
                        return new Primitivo("numero", null, (int) (a_int(a1.getValor()) * a_int(a2.getValor())));
                    } else {
                        String d1 = a1.getValor().toString();
                        String d2 = a2.getValor().toString();
                        double pr = Double.parseDouble(d1);
                        double pr2 = Double.parseDouble(d2);
                        return new Primitivo("decimal", null, (double) (pr * pr2));
                    }
                } else {
                    // IF para STRING-STRING, CARACTER-STRING, STRING-CARACTER
                    if ((obt == Tipos.STRING && obt2 == Tipos.STRING)
                            || (obt == Tipos.CARACTER && obt2 == Tipos.STRING)
                            || (obt == Tipos.STRING && obt2 == Tipos.CARACTER)) {
                        return new Primitivo("excepcion", null, "Multiplicacion entre valores no numericos (" + a1.getTipo() + "*" + a2.getTipo() + ")");
                    } // IF para CARACTER-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.CARACTER) {
                        char c = (char) a1.getValor();
                        char t = (char) a2.getValor();
                        int c1 = (int) c;
                        int t1 = (int) t;
                        if ((c1 * t1) < 255) {
                            int mul = c1 * t1;
                            return new Primitivo("caracter", null, (char) mul);
                        } else {
                            return new Primitivo("excepcion", null, "Multiplicacion de caracteres excedio el limite de 255");
                        }
                    } // IF para CARACTER-NUMERO, NUMERO-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.NUMERO
                            || obt == Tipos.NUMERO && obt2 == Tipos.CARACTER) {
                        char caracter = (obt == Tipos.CARACTER) ? (char) (a1.getValor()) : (char) (a2.getValor());
                        int entero = (obt == Tipos.NUMERO) ? (int) (a1.getValor()) : (int) (a2.getValor());
                        int n = (int) caracter;
                        return new Primitivo("numero", null, n * entero);
                    } // IF para BOOLEAN-CARACTER, CARACTER-BOOLEAN
                    else if (obt == Tipos.BOOLEAN && obt2 == Tipos.CARACTER
                            || obt == Tipos.CARACTER && obt2 == Tipos.BOOLEAN) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor()) : valor_boolean(a2.getValor());
                        char carac = (obt == Tipos.CARACTER) ? (char) a1.getValor() : (char) a2.getValor();
                        int s1 = (boo) ? 1 : 0;
                        int c1 = (int) carac;
                        int mult = s1 * c1;
                        return new Primitivo("numero", null, (char) mult);
                    } // IF para BOOLEAN-STRING
                    else if (obt == Tipos.BOOLEAN && obt2 == Tipos.STRING) {
                        return new Primitivo("excepcion", null, "Multiplicacion entre valores no numericos (" + a1.getTipo() + "*" + a2.getTipo() + ")");
                    } // IF para STRING-BOOLEAN
                    else if (obt == Tipos.STRING && obt2 == Tipos.BOOLEAN) {
                        return new Primitivo("excepcion", null, "Multiplicacion entre valores no numericos (" + a1.getTipo() + "*" + a2.getTipo() + ")");
                    } // IF para STRING-NUMERO, NUMERO-STRING, DECIMAL-STRING, STRING-DECIMAL
                    else if (obt == Tipos.STRING && obt2 == Tipos.NUMERO
                            || obt == Tipos.NUMERO && obt2 == Tipos.STRING
                            || obt == Tipos.DECIMAL && obt2 == Tipos.STRING
                            || obt == Tipos.STRING && obt2 == Tipos.DECIMAL) {
                        return new Primitivo("excepcion", null, "Multiplicacion entre valores no numericos (" + a1.getTipo() + "*" + a2.getTipo() + ")");
                    } // IF para NUMERO-BOOLEAN, BOOLEAN-NUMERO
                    else if (obt == Tipos.NUMERO && obt2 == Tipos.BOOLEAN
                            || obt == Tipos.BOOLEAN && obt2 == Tipos.NUMERO) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor().toString()) : valor_boolean(a2.getValor().toString());
                        int bo = (boo) ? 1 : 0;
                        int nu = (obt == Tipos.NUMERO) ? (int) a1.getValor() : (int) a2.getValor();
                        return new Primitivo("numero", null, bo * nu);
                    } // IF para DECIMAL-BOOLEAN, BOOLEAN-DECIMAL
                    else if (obt == Tipos.DECIMAL && obt2 == Tipos.BOOLEAN
                            || obt == Tipos.BOOLEAN && obt2 == Tipos.DECIMAL) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor().toString()) : valor_boolean(a2.getValor().toString());
                        double bo = (boo) ? 1.0 : 0.0;
                        double nu = (obt == Tipos.DECIMAL) ? (double) a1.getValor() : (double) a2.getValor();
                        return new Primitivo("decimal", null, bo * nu);
                    }// IF para BOOLEAN-BOOLEAN
                    else if (obt == Tipos.BOOLEAN && obt2 == Tipos.BOOLEAN) {
                        boolean boo = (boolean) valor_boolean(a1.getValor().toString());
                        boolean bo2 = (boolean) valor_boolean(a2.getValor().toString());
                        return new Primitivo("boolean", null, boo && bo2);
                    }// IF para CARACTER-DECIMAL, DECIMAL-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.DECIMAL
                            || obt == Tipos.DECIMAL && obt2 == Tipos.CARACTER) {
                        char carac = (obt == Tipos.CARACTER) ? (char) a1.getValor() : (char) a2.getValor();
                        double dec = (obt == Tipos.DECIMAL) ? (double) a1.getValor() : (double) a2.getValor();
                        int car = (int) carac;
                        return new Primitivo("decimal", null, (double) (dec * car));
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
                        System.out.println("entra a suma enteros");
                        return new Primitivo("numero", null, (int) (a_int(a1.getValor()) + a_int(a2.getValor())));
                    } else {
                        String d1 = a1.getValor().toString();
                        String d2 = a2.getValor().toString();
                        double pr = Double.parseDouble(d1);
                        double pr2 = Double.parseDouble(d2);
                        return new Primitivo("decimal", null, (double) (pr + pr2));
                    }
                } else {
                    // IF para STRING-STRING, CARACTER-STRING, STRING-CARACTER
                    if ((obt == Tipos.STRING && obt2 == Tipos.STRING)
                            || (obt == Tipos.CARACTER && obt2 == Tipos.STRING)
                            || (obt == Tipos.STRING && obt2 == Tipos.CARACTER)) {
                        String concatenando = (String) a1.getValor() + (String) a2.getValor();
                        return new Primitivo("string", null, concatenando);
                    } // IF para CARACTER-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.CARACTER) {
                        char c = (char) a1.getValor();
                        char t = (char) a2.getValor();
                        int concatenando = (int) c + (int) t;
                        if (concatenando < 255) {
                            return new Primitivo("caracter", null, (char) concatenando);
                        } else {
                            return new Primitivo("excepcion", null, "Suma de caracteres excedio el limite de 255");
                        }
                    } // IF para CARACTER-NUMERO, NUMERO-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.NUMERO
                            || obt == Tipos.NUMERO && obt2 == Tipos.CARACTER) {
                        char caracter = (obt == Tipos.CARACTER) ? (char) (a1.getValor()) : (char) (a2.getValor());
                        int entero = (obt == Tipos.NUMERO) ? (int) (a1.getValor()) : (int) (a2.getValor());
                        return new Primitivo("numero", null, entero + (int) caracter);
                    } // IF para BOOLEAN-CARACTER, CARACTER-BOOLEAN
                    else if (obt == Tipos.BOOLEAN && obt2 == Tipos.CARACTER
                            || obt == Tipos.CARACTER && obt2 == Tipos.BOOLEAN) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor()) : valor_boolean(a2.getValor());
                        char carac = (obt == Tipos.CARACTER) ? (char) a1.getValor() : (char) a2.getValor();
                        int s1 = (boo) ? 1 : 0;
                        s1 += (int) carac;
                        if (s1 < 255) {
                            return new Primitivo("caracter", null, (char) s1);
                        } else {
                            return new Primitivo("excepcion", null, "Suma de caracteres excedio el limite de 255");
                        }
                    } // IF para BOOLEAN-STRING
                    else if (obt == Tipos.BOOLEAN && obt2 == Tipos.STRING) {
                        String concatenar = (obt == Tipos.BOOLEAN) ? a1.getValor().toString() : a2.getValor().toString();
                        return new Primitivo("string", null, concatenar + (String) a2.getValor());
                    } // IF para STRING-BOOLEAN
                    else if (obt == Tipos.STRING && obt2 == Tipos.BOOLEAN) {
                        String concatenar = (obt == Tipos.BOOLEAN) ? a1.getValor().toString() : a2.getValor().toString();
                        return new Primitivo("string", null, (String) a1.getValor() + concatenar);
                    } // IF para STRING-NUMERO, NUMERO-STRING, DECIMAL-STRING, STRING-DECIMAL
                    else if (obt == Tipos.STRING && obt2 == Tipos.NUMERO
                            || obt == Tipos.NUMERO && obt2 == Tipos.STRING
                            || obt == Tipos.DECIMAL && obt2 == Tipos.STRING
                            || obt == Tipos.STRING && obt2 == Tipos.DECIMAL) {
                        String concatenando = a1.getValor().toString() + a2.getValor().toString();
                        return new Primitivo("string", null, concatenando);
                    } // IF para NUMERO-BOOLEAN, BOOLEAN-NUMERO
                    else if (obt == Tipos.NUMERO && obt2 == Tipos.BOOLEAN
                            || obt == Tipos.BOOLEAN && obt2 == Tipos.NUMERO) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor().toString()) : valor_boolean(a2.getValor().toString());
                        int bo = (boo) ? 1 : 0;
                        int nu = (obt == Tipos.NUMERO) ? (int) a1.getValor() : (int) a2.getValor();
                        return new Primitivo("numero", null, bo + nu);
                    } // IF para DECIMAL-BOOLEAN, BOOLEAN-DECIMAL
                    else if (obt == Tipos.DECIMAL && obt2 == Tipos.BOOLEAN
                            || obt == Tipos.BOOLEAN && obt2 == Tipos.DECIMAL) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor().toString()) : valor_boolean(a2.getValor().toString());
                        double bo = (boo) ? 1.0 : 0.0;
                        double nu = (obt == Tipos.DECIMAL) ? (double) a1.getValor() : (double) a2.getValor();
                        return new Primitivo("decimal", null, bo + nu);
                    }// IF para BOOLEAN-BOOLEAN
                    else if (obt == Tipos.BOOLEAN && obt2 == Tipos.BOOLEAN) {
                        boolean boo = (boolean) valor_boolean(a1.getValor().toString());
                        boolean bo2 = (boolean) valor_boolean(a2.getValor().toString());
                        boolean respuesta = boo || bo2;
                        return new Primitivo("boolean", null, respuesta);
                    }// IF para CARACTER-DECIMAL, DECIMAL-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.DECIMAL
                            || obt == Tipos.DECIMAL && obt2 == Tipos.CARACTER) {
                        char carac = (obt == Tipos.CARACTER) ? (char) a1.getValor() : (char) a2.getValor();
                        double dec = (obt == Tipos.DECIMAL) ? (double) a1.getValor() : (double) a2.getValor();
                        double respuesta = carac + dec;
                        return new Primitivo("decimal", null, respuesta);
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
                        return new Primitivo("excepcion", null, "División entre cero");
                    } else {
                        if (obt == Tipos.NUMERO && obt2 == Tipos.NUMERO) {
                            return new Primitivo("numero", null, (int) (a_int(a1.getValor()) / a_int(a2.getValor())));
                        } else {
                            String d1 = a1.getValor().toString();
                            String d2 = a2.getValor().toString();
                            double pr = Double.parseDouble(d1);
                            double pr2 = Double.parseDouble(d2);
                            return new Primitivo("decimal", null, (double) (pr / pr2));
                        }
                    }
                } else {
                    // IF para STRING-STRING, CARACTER-STRING, STRING-CARACTER
                    if ((obt == Tipos.STRING && obt2 == Tipos.STRING)
                            || (obt == Tipos.CARACTER && obt2 == Tipos.STRING)
                            || (obt == Tipos.STRING && obt2 == Tipos.CARACTER)) {
                        return new Primitivo("excepcion", null, "División entre valores no numericos (" + a1.getTipo() + "/" + a2.getTipo() + ")");
                    } // IF para CARACTER-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.CARACTER) {
                        char c = (char) a1.getValor();
                        char t = (char) a2.getValor();
                        int c1 = (int) c;
                        int t1 = (int) t;
                        if (t1 == 0) {
                            return new Primitivo("excepcion", null, "División entre cero");
                        } else {
                            int res = (int) (c1 / t1);
                            return new Primitivo("caracter", null, (char) res);
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
                                return new Primitivo("numero", null, retorno);
                            } else {
                                return new Primitivo("excepcion", null, "División entre cero");
                            }
                        } else {
                            if (n != 0) {
                                int retorno = (int) (entero / n);
                                return new Primitivo("numero", null, retorno);
                            } else {
                                return new Primitivo("excepcion", null, "División entre cero");
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
                                return new Primitivo("excepcion", null, "División entre cero");
                            } else {
                                int retorno = (int) (s1 / c1);
                                return new Primitivo("caracter", null, (char) retorno);
                            }
                        } else {
                            if (s1 == 0) {
                                return new Primitivo("excepcion", null, "División entre cero");
                            } else {
                                int retorno = (int) (c1 / s1);
                                return new Primitivo("caracter", null, (char) retorno);
                            }
                        }
                    } // IF para BOOLEAN-STRING
                    else if (obt == Tipos.BOOLEAN && obt2 == Tipos.STRING) {
                        return new Primitivo("excepcion", null, "División entre valores no numericos (" + a1.getTipo() + "/" + a2.getTipo() + ")");
                    } // IF para STRING-BOOLEAN
                    else if (obt == Tipos.STRING && obt2 == Tipos.BOOLEAN) {
                        return new Primitivo("excepcion", null, "División entre valores no numericos (" + a1.getTipo() + "/" + a2.getTipo() + ")");
                    } // IF para STRING-NUMERO, NUMERO-STRING, DECIMAL-STRING, STRING-DECIMAL
                    else if (obt == Tipos.STRING && obt2 == Tipos.NUMERO
                            || obt == Tipos.NUMERO && obt2 == Tipos.STRING
                            || obt == Tipos.DECIMAL && obt2 == Tipos.STRING
                            || obt == Tipos.STRING && obt2 == Tipos.DECIMAL) {
                        return new Primitivo("excepcion", null, "División entre valores no numericos (" + a1.getTipo() + "/" + a2.getTipo() + ")");
                    } // IF para NUMERO-BOOLEAN, BOOLEAN-NUMERO
                    else if (obt == Tipos.NUMERO && obt2 == Tipos.BOOLEAN
                            || obt == Tipos.BOOLEAN && obt2 == Tipos.NUMERO) {
                        boolean boo = (obt == Tipos.BOOLEAN) ? valor_boolean(a1.getValor().toString()) : valor_boolean(a2.getValor().toString());
                        int bo = (boo) ? 1 : 0;
                        int nu = (obt == Tipos.NUMERO) ? (int) a1.getValor() : (int) a2.getValor();
                        if (obt == Tipos.NUMERO) {
                            if (bo == 0) {
                                return new Primitivo("excepcion", null, "División entre cero");
                            } else {
                                int retorno = (int) (nu / bo);
                                return new Primitivo("numero", null, retorno);
                            }
                        } else {
                            if (nu == 0) {
                                return new Primitivo("excepcion", null, "División entre cero");
                            } else {
                                int retorno = (int) (bo / nu);
                                return new Primitivo("numero", null, retorno);
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
                                return new Primitivo("excepcion", null, "División entre cero");
                            } else {
                                double retorno = (double) (nu / bo);
                                return new Primitivo("decimal", null, retorno);
                            }
                        } else {
                            if (nu == 0) {
                                return new Primitivo("excepcion", null, "División entre cero");
                            } else {
                                double retorno = (double) (bo / nu);
                                return new Primitivo("decimal", null, retorno);
                            }
                        }
                    }// IF para BOOLEAN-BOOLEAN
                    else if (obt == Tipos.BOOLEAN && obt2 == Tipos.BOOLEAN) {
                        boolean boo = (boolean) valor_boolean(a1.getValor().toString());
                        boolean bo2 = (boolean) valor_boolean(a2.getValor().toString());
                        if (!bo2) {
                            return new Primitivo("excepcion", null, "División entre cero");
                        } else {
                            boolean respuesta = boo && bo2;
                            return new Primitivo("boolean", null, respuesta);
                        }
                    }// IF para CARACTER-DECIMAL, DECIMAL-CARACTER
                    else if (obt == Tipos.CARACTER && obt2 == Tipos.DECIMAL
                            || obt == Tipos.DECIMAL && obt2 == Tipos.CARACTER) {
                        char carac = (obt == Tipos.CARACTER) ? (char) a1.getValor() : (char) a2.getValor();
                        double dec = (obt == Tipos.DECIMAL) ? (double) a1.getValor() : (double) a2.getValor();
                        int car = (int) carac;
                        if (obt == Tipos.CARACTER) {
                            if (dec == 0) {
                                return new Primitivo("excepcion", null, "División entre cero");
                            } else {
                                double retorno = (double) (car / dec);
                                return new Primitivo("decimal", null, retorno);
                            }
                        } else {
                            if (car == 0) {
                                return new Primitivo("excepcion", null, "División entre cero");
                            } else {
                                double retorno = (double) (dec / car);
                                return new Primitivo("decimal", null, retorno);
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
}
