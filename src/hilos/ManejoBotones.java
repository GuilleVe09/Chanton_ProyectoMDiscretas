/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 *
 * @author John
 */
public class ManejoBotones extends Thread {
    private Button btnChanton;
    private Button btnSgteRonda;
    private Text tiempo;
    private int cantTiempo;
    private int nRondas;
    
    public ManejoBotones(Button chanton, Button sgteRonda, Text tiempo,int nRondas) {
        this.btnChanton = chanton;
        this.btnSgteRonda = sgteRonda;
        this.tiempo = tiempo;
        this.nRondas = nRondas;
    }
    
    @Override
    public void run() {
        while(cantTiempo!=0){
            Runnable updater = () -> {
                this.cantTiempo = Integer.parseInt(tiempo.getText());
            };
            Platform.runLater(updater);            
        }                
        this.btnChanton.setDisable(true);
        if(nRondas==1){
            this.btnSgteRonda.setDisable(false);
        }
    }
    
}
