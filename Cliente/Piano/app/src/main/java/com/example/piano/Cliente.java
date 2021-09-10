package com.example.piano;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
public class Cliente {

    public Cliente(){
    }
    public String mandar(String texto){
        final String HOST = "192.168.1.2";
        final int PUERTO = 5000;
        DataInputStream in;
        DataOutputStream out;
        String mensaje ="error";
        try {
            Socket sc = new Socket(HOST, PUERTO);
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            out.writeUTF(texto);
            mensaje =  in.readUTF();
            sc.close();
        } catch (IOException ex) {
            System.out.println("error");
        }
        return mensaje;
    }
}
