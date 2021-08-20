/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

import javafx.application.Platform;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author John
 */
public class CuentaRegresiva extends Thread{
    private int tiempo;
    private Text regresiva;
    private Text titulo;

    public CuentaRegresiva(int tiempo, Text regresiva, Text titulo) {
        this.tiempo = tiempo;
        this.regresiva = regresiva;
        this.titulo = titulo;
    }
    
    @Override
    public void run() {
        while(tiempo>=0){
            Runnable updater = () -> {
                regresiva.setText(String.valueOf(tiempo));
            };
            Platform.runLater(updater);
            esperar(1);
            tiempo--;
        }
        this.regresiva.setVisible(false);   
        titulo.setTextAlignment(TextAlignment.CENTER);
        titulo.setText("¡¡Parenme la mano!!");
    }
    
    public static void esperar(int segundos){
        try {
            Thread.sleep(segundos * 1000);
         } catch (InterruptedException e) {
            System.out.println(e);
         }
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public Text getRegresiva() {
        return regresiva;
    }

    public void setRegresiva(Text regresiva) {
        this.regresiva = regresiva;
    }
    
    
}
