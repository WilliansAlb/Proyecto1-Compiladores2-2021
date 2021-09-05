/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public Servidor(){
        
    }

    @Override
    public void run() {
        try{
            servidor = new ServerSocket(PUERTO);
            System.out.println("servidor iniciado");
            while(true){
                sc = servidor.accept();
                
                System.out.println("Cliente conectado");
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());
                
                String mensaje = in.readUTF();
                System.out.println(mensaje);
                out.writeUTF("¡Hola desde el servidor!");
                
                sc.close();
                System.out.println("Cliente desconectado");
            }
        } catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
