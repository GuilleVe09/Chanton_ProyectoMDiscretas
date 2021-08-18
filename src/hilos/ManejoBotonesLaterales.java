/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

import static hilos.CuentaRegresiva.esperar;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 *
 * @author John
 */
public class ManejoBotonesLaterales extends Thread {
    private Button btnChanton;
    private Button btnSgteRonda;
    private int tiempo;
    private int nRondas;
    
    public ManejoBotonesLaterales(Button chanton, Button sgteRonda,int nRondas) {
        this.btnChanton = chanton;
        this.btnSgteRonda = sgteRonda;
        this.tiempo = 0;
        this.nRondas = nRondas;
    }
    
    @Override
    public void run() {
        while(tiempo<=10){ 
            esperar(1);
            tiempo++;
        }                
        this.btnChanton.setDisable(true);
        System.out.println(tiempo);
        if(nRondas==1 && tiempo==11)
            this.btnSgteRonda.setDisable(false);
    }
    
}
