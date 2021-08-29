/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica1;

import java.util.Random;
import javafx.application.Platform;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import jm.music.data.Note;
import jm.util.Play;

/**
 *
 * @author willi
 */
public class Task extends Thread {

    private boolean isActive = true;
    private LineChart<String, Number> chart;
    private Series<String, Number> series = new Series<>();
    int conteo = 0;
    int segundos = 0;

    public Task(LineChart<String, Number> chart) {
        this.chart = chart;
        this.chart.setLayoutY(100.00);
        this.chart.setLayoutX(100.00);
        this.chart.getData().add(series);
    }

    @Override
    public void run() {
        while (isActive) {
            try {
                // Simulate heavy processing stuff
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            segundos++;
            System.out.println("Ha pasado: " + segundos + " segundos de la cancion");
            Note nota = new Note();
            nota.setPitch(22);
            Play.midi(nota);
            Platform.runLater(() -> {
                Random n = new Random(System.currentTimeMillis());
                int n1 = n.nextInt(5);
                int[] nm = {32, 440, 3951, 138, 880};
                // Add a new number to the linechart
                this.series.getData().add(new Data<>("" + conteo++, nm[n1]));

                // Remove first number of linechart
                //this.series.getData().remove(0);
            });
        }
    }

    public void kill() {
        isActive = false;
    }

}
