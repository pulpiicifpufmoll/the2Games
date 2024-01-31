package com.example.the2games;

import java.util.Timer;
import java.util.TimerTask;

public class ActivityTimer implements Runnable{

    private static final int TIEMPO_EN_MILISEGUNDOS = 20 * 60 * 1000; // 20 minutos en milisegundos
    private Timer timer;
    private long startTime;


    public ActivityTimer() {
        this.timer = new Timer();
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        timer.schedule(new TemporizadorTask(), TIEMPO_EN_MILISEGUNDOS);
    }

    private class TemporizadorTask extends TimerTask {
        //TODO llamar a método que muestre un mensaje de THE END
        @Override
        public void run() {
            System.out.println("¡Tiempo terminado! Realizar acciones aquí.");
            timer.cancel(); // Cancelar el temporizador después de ejecutar la tarea
        }
    }

    public String renturnTimeRemainingFormated() {
        long tiempoRestante = getLeftTime();
        String formattedTime = convertMillisecondsToTimeFormat(tiempoRestante);
        return formattedTime;
    }

    private String convertMillisecondsToTimeFormat(long milisegundos) {
        long segundos = milisegundos / 1000;
        long minutos = segundos / 60;
        segundos = segundos % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }

    private long getLeftTime(){
        long actualTime = System.currentTimeMillis();
        long timeElapsed = actualTime - startTime;
        long leftTime = TIEMPO_EN_MILISEGUNDOS - timeElapsed;

        return leftTime >= 0 ? leftTime : 0;  // Asegurar que el tiempo restante no sea negativo
    }

}
