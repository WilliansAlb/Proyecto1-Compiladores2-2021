/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tablas;

import Interprete.Metodo;
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
            System.out.println("Declarada "+simbolo.getId());
            this.add(simbolo);
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
            System.out.println("La variable con identificador " + id + " fue cambiada");
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
    
    public boolean esNull(String id){
        for (Simbolo s : this) {
            if (s.getId().equals(id)) {
                return s.getDatos().get(0)==null;
            }
        }
        return false;
    }
    
    public void agregar_sistema(String id, String tipo, Object nuevo){
        List<Object> temp = new LinkedList<>();
        temp.add(nuevo);
        agregar(new Simbolo(id,tipo,null,temp,ambitos));
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
                    if (this.get(j).getAmbito()==ambitos){
                        this.remove(j);
                        break;
                    }
                }
                conteo--;
            }

        }
        ambitos--;
    }
}
