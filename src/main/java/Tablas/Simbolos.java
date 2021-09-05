/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tablas;

import Interprete.Errores;
import Interprete.Item;
import Interprete.Llamada;
import Interprete.Metodo;
import Interprete.ParametroEnviar;
import Interprete.Primitivo;
import Interprete.Termino;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author willi
 */
public class Simbolos extends LinkedList<Simbolo> {

    public int ambitos = 0;

    /**
     * Constructor de la clase que lo único que hace es llamar al constructor de
     * la clase padre, es decir, el constructor de LinkedList.
     */
    public Simbolos() {
        super();
    }

    /**
     * Método que busca una variable en la tabla de símbolos y devuelve su
     * valor.
     *
     * @param id Identificador de la variable que quiere buscarse
     * @return Valor de la variable que se buscaba, si no existe se devuelve
     * nulo
     */
    public Object getValor(String id) {
        for (Simbolo s : this) {
            if (s.getId().equals(id)) {
                return s.getDatos().get(0);
            }
        }
        System.out.println("La variable " + id + " no existe en este ámbito.");
        return "Desconocido";
    }

    /**
     * Método que asigna un valor a una variable específica, si no la encuentra
     * no realiza la asignación y despliega un mensaje de error.
     *
     * @param id Identificador de la variable que quiere buscarse
     * @param valor Valor que quiere asignársele a la variable buscada
     */
    void setValor(String id, Object valor) {
        for (Simbolo s : this) {
            if (s.getId().equals(id)) {
                s.getDatos().set(0, valor);
                s.setAmbito(0);
                s.setDimensiones(null);
                s.setTipo(id);
                return;
            }
        }
        System.out.println("La variable " + id + " no existe en este ámbito, por lo "
                + "que no puede asignársele un valor.");
    }

    public Simbolo obtener(String id_buscar) {
        for (Simbolo s : this) {
            if (s.getId().equals(id_buscar)) {
                return s;
            }
        }
        return null;
    }

    public Simbolo obtener_retorno_actual() {
        for (Simbolo s : this) {
            if (s.getId().equals("$retorno")) {
                if (s.getAmbito() == ambitos) {
                    return s;
                }
            }
        }
        return null;
    }

    public boolean tieneRetorno() {
        if (ultimo_retorno() != null) {
            return ultimo_retorno().getDatos().get(0) != null;
        } else {
            return false;
        }
    }

    public boolean tieneBreak() {
        return this.stream().filter(s -> (s.getId().equals("$break"))).anyMatch(s -> (s.getAmbito() == ambitos));
    }

    public void agregar(Simbolo simbolo) {
        boolean existe = false;
        for (Simbolo s : this) {
            if (s.getId().equals(simbolo.getId())) {
                existe = true;
            }
        }
        if (existe) {
            System.out.println("La variable con identificador " + simbolo.getId() + " ya está declarada");
        } else {
            System.out.println("Declarada " + simbolo.getId());
            this.add(simbolo);
        }
    }

    public void subir_break() {
        for (Simbolo este : this) {
            if (este.getId().equalsIgnoreCase("$break")) {
                System.out.println("sube el break");
                este.setAmbito(este.getAmbito() - 1);
            }
        }
    }

    public void eliminar_break() {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getId().equalsIgnoreCase("$break")) {
                this.remove(i);
                break;
            }
        }
    }

    public void cambiar(String id, List<Object> nuevo) {
        boolean cambiada = false;
        for (Simbolo s : this) {
            if (s.getId().equals(id)) {
                cambiada = true;
                s.setDatos(nuevo);
                break;
            }
        }
        if (cambiada) {
            //System.out.println("La variable con identificador " + id + " fue cambiada");
        } else {
            System.out.println("La variable con identificador " + id + " no fue declarada antes");
        }
    }

    public boolean existe(String id) {
        for (Simbolo s : this) {
            if (s.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean esNull(String id) {
        for (Simbolo s : this) {
            if (s.getId().equals(id)) {
                return s.getDatos().get(0) == null;
            }
        }
        return false;
    }

    public void agregar_sistema(String id, String tipo, Object nuevo) {
        List<Object> temp = new LinkedList<>();
        temp.add(nuevo);
        agregar(new Simbolo(id, tipo, null, temp, ambitos));
    }

    public Simbolo ultimo_retorno() {
        for (int i = this.size() - 1; i >= 0; i--) {
            if (this.get(i).getId().contains("$retorno")) {
                return this.get(i);
            }
        }
        return null;
    }

    public void eliminar_ambito() {
        int i = (ambitos == 0) ? -1 : ambitos;
        if (i != -1) {
            int conteo = 0;
            for (int j = 0; j < this.size(); j++) {
                if (this.get(j).getAmbito() == ambitos) {
                    conteo++;
                }
            }
            while (conteo != 0) {
                for (int j = 0; j < this.size(); j++) {
                    if (this.get(j).getAmbito() == ambitos) {
                        this.remove(j);
                        break;
                    }
                }
                conteo--;
            }

        }
        ambitos--;
    }

    /**
     * Método que inserta en la posición que se le manda el nuevo objeto
     *
     * @param id el identificador del arreglo a cambiar
     * @param nuevo el objeto nuevo a insertar
     * @param posicion la posicion donde se insertará
     * @return true si logró hacer el cambio
     */
    public boolean cambiar_arreglo(String id, Object nuevo, int posicion) {
        for (Simbolo ss : this) {
            if (ss.getId().equalsIgnoreCase(id)) {
                ss.getDatos().set(posicion, nuevo);
                return true;
            }
        }
        return false;
    }

    public boolean agregar_errores(Errores agregar) {
        for (Simbolo ss : this) {
            if (ss.getId().equalsIgnoreCase("$errores")) {
                ss.getDatos().add(agregar);
                return true;
            }
        }
        return false;
    }

    public boolean agregar_error(String tipo_error, String razon, int linea, int columna) {
        for (Simbolo ss : this) {
            if (ss.getId().equalsIgnoreCase("$errores")) {
                ss.getDatos().add(new Errores(tipo_error, razon, linea, columna));
                return true;
            }
        }
        return false;
    }

    public Metodo obtener_metodo(String id, List<ParametroEnviar> parametros_enviados) {
        ArrayList<Metodo> metodos = (ArrayList) this.obtener("$metodos").getDatos().get(0);
        System.out.println("acá en loop");
        for (Metodo metodo : metodos) {
            System.out.println(metodo.getId());
            if (id.equalsIgnoreCase(metodo.getId())) {
                System.out.println(parametros_enviados!=null);
                System.out.println(metodo.getParametros()!=null);
                if (parametros_enviados != null && metodo.getParametros() != null) {
                    if (parametros_enviados.size() == metodo.getParametros().size()) {
                        ArrayList<Simbolo> array = new ArrayList<>();
                        for (int i = 0; i < parametros_enviados.size(); i++) {
                            if (parametros_enviados.get(i).getTipo() == ParametroEnviar.Tipo.ARREGLO && metodo.getParametros().get(i).isArreglo()) {
                                Item arr = parametros_enviados.get(i).getArreglo();
                                List<Object> elementos = new LinkedList<>();
                                List<Object> propiedades = new LinkedList<>();
                                propiedades.add("");
                                propiedades.add(0);
                                propiedades.add(false);
                                List<Integer> dimen = new LinkedList<>();
                                propiedades.add(dimen);
                                arr.obtener_arreglo(elementos, this, propiedades, -1);
                                if (propiedades.get(0).toString().equalsIgnoreCase(metodo.getParametros().get(i).getTipo())) {
                                    array.add(
                                            new Simbolo(metodo.getParametros().get(i).getId(), metodo.getParametros().get(i).getTipo(),
                                                    dimen, elementos, ambitos + 1));
                                }
                            } else if (parametros_enviados.get(i).getTipo() == ParametroEnviar.Tipo.EXPRESION && !metodo.getParametros().get(i).isArreglo()) {
                                Termino av = parametros_enviados.get(i).getExpresion().ejecutar(this);
                                if (av instanceof Primitivo) {
                                    Primitivo avv = (Primitivo) av;
                                    System.out.println(((Primitivo) av).getValor());
                                    if (avv.asignar(metodo.getParametros().get(i).getTipo())) {
                                        List<Object> ob = new LinkedList<>();
                                        ob.add(avv.getValor());
                                        array.add(
                                                new Simbolo(metodo.getParametros().get(i).getId(), metodo.getParametros().get(i).getTipo(),
                                                        null, ob, ambitos + 1));
                                    }
                                }
                            } else if (parametros_enviados.get(i).getTipo() == ParametroEnviar.Tipo.LLAMADA) {
                                Llamada ll = parametros_enviados.get(i).getLlama();
                                ll.interpretar(this);
                                if (ll.retorno != null) {
                                    if (ll.retorno_tipo.equalsIgnoreCase(metodo.getParametros().get(i).getTipo())) {
                                        List<Object> ob = new LinkedList<>();
                                        ob.add(ll.retorno);
                                        array.add(
                                                new Simbolo(metodo.getParametros().get(i).getId(), metodo.getParametros().get(i).getTipo(),
                                                        null, ob, ambitos + 1));
                                    }
                                }
                            }
                        }
                        if (array.size() == metodo.getParametros().size()) {
                            array.forEach(a -> {
                                this.agregar(a);
                            });
                            return metodo;
                        }
                    }
                } else if (parametros_enviados == null && metodo.getParametros() == null) {
                    System.out.println("acá en loop");
                    return metodo;
                }
            }
        }
        return null;
    }
}
