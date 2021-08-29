/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tablas;

import java.util.ArrayList;

/**
 *
 * @author willi
 */
public class TablaSimbolos extends ArrayList<Simbolo>{
    
    public TablaSimbolos(){
        super();
    }
    
    public Object getValor(String id) {
        for(Simbolo s:this){
            System.out.println(s.getId());
            if(s.getId().equals(id)){
                return s.getDatos().get(0);
            }
        }
        return null;
    }
}
