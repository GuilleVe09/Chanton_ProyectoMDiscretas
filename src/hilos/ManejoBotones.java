/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

import java.util.List;
import javafx.scene.control.TextField;

/**
 *
 * @author John
 */
public class ManejoBotones extends Thread{
    
    
    private void bloquearTextField(List<TextField> lista, boolean deshabilitar){
        lista.forEach(e->{
            e.setDisable(deshabilitar);
            e.setStyle("-fx-background-color: white");
        });       
    }
    
}
