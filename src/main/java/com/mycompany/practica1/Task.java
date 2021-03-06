/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica1;

import Interprete.Reproduccion;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import jm.music.data.Note;
import jm.util.Play;

/**
 *
 * @author willi
 */
public class Task extends Thread {

    boolean isActive = true;
    boolean suspender;
    boolean pausar;
    private LineChart<String, Number> chart;
    private Series<String, Number> series = new Series<>();
    List<Series<String, Number>> seriees = new LinkedList<>();
    ProgressBar progreso;
    Label pasado;
    Reproduccion reproduce;

    Synth n = new Synth();
    int conteo = 0;
    int segundos = 0;

    public Task(LineChart<String, Number> chart, ProgressBar progreso, Label pasado) {
        this.chart = chart;
        this.chart.setLayoutY(100.00);
        this.chart.setLayoutX(100.00);
        //this.chart.getData().add(series);
        this.progreso = progreso;
        this.pasado = pasado;
        suspender = false;
        pausar = false;
    }

    public Task(LineChart<String, Number> chart, ProgressBar progreso, Label pasado, Reproduccion reproduce) {
        this.chart = chart;
        this.chart.setLayoutY(100.00);
        this.chart.setLayoutX(100.00);
        //this.chart.getData().add(series);
        this.progreso = progreso;
        this.pasado = pasado;
        suspender = false;
        pausar = false;
        this.reproduce = reproduce;
    }

    @Override
    public void run() {
        n.setInstrument(0);
        if (reproduce != null) {
            while (isActive && segundos < reproduce.max() - 1) {
                try {
                    // Simulate heavy processing stuff
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    if (segundos == 0) {
                        progreso.setProgress(0);
                        for (int i = 0; i < reproduce.getCanales().size(); i++) {
                            Series<String, Number> series2 = new Series<>();
                            series2.setName("Canal "+reproduce.getCanales().get(i).getId());
                            seriees.add(series2);
                            this.chart.getData().add(seriees.get(i));
                            this.seriees.get(i).getData().add(new Data<>("" + 0, 0));
                        }
                    }
                    for (int i = 0; i < reproduce.getCanales().size(); i++) {
                        int re = reproduce.getCanales().get(i).nota(segundos);
                        double res = (Math.abs(re) > 0) ? ((double)(((double)Math.pow(2, ((double)(Math.abs(re) - 69) / 12))) * 440)) : 0;
                        double re2 = (Math.abs(re) > 0) ? Math.round(res*100.0)/100.0 : 0.00;
                        this.seriees.get(i).getData().add(new Data<>("" + segundos + 1, re2));
                        if (re > 0) {
                            n.noteOn(re);
                        }
                    }

                    segundos++;
                    if ((segundos * 50) % 50 == 0) {
                        int nue = segundos * 50;
                        int sec = (nue) / 1000;
                        double pro = (double) segundos / reproduce.max();
                        progreso.setProgress(pro);
                        int minutos = (int) (sec / 60);
                        int sec2 = (int) (sec % 60);
                        String tiempo_pasado = (sec2 > 9) ? minutos + ":" + sec2 : minutos + ":0" + sec2;
                        pasado.setText(tiempo_pasado);
                    }
                });
                synchronized (this) {
                    while (suspender) {
                        try {
                            wait();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (pausar) {
                        break;
                    }
                }
            }
            try {
                // Simulate heavy processing stuff
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                for (int i = 0; i < reproduce.getCanales().size(); i++) {
                    this.seriees.get(i).getData().add(new Data<>("final", 0));
                }
            });
            kill();
            isActive = true;
            segundos = 0;
        }
    }

    //Pausar el hilo
    synchronized void pausarhilo() {
        pausar = true;
        //lo siguiente garantiza que un hilo suspendido puede detenerse.
        suspender = false;
        notify();
        kill();
    }

    //Suspender un hilo
    synchronized void suspenderhilo() {
        n.allNotesOff();
        suspender = true;
    }

    //Renaudar un hilo
    synchronized void renaudarhilo() {
        suspender = false;
        notify();
    }

    public void kill() {
        n.allNotesOff();
        isActive = false;
    }

}
