/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author John
 */
public class MaquinaEstadoController implements Initializable {    
    @FXML
    private Pane S1;
    @FXML
    private Pane S2;
    @FXML
    private Pane img12;
    @FXML
    private Pane img21;
    @FXML
    private VBox vbEstados;
    @FXML
    private Pane S3;
    @FXML
    private Pane S4;
    @FXML
    private Pane S5;
    @FXML
    private Pane img23;
    @FXML
    private Text txt21;
    
    private int numEstado;
    private MaquinaEstadoController instacia;   
    //valor efecto
    private double i;
    @FXML
    private Pane img2;
    @FXML
    private Pane img31;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instacia = this;
        ocultar();
    }    
    
    private void ocultar(){           
        S1.setVisible(false);
        S2.setVisible(false);
        S3.setVisible(false);
        img12.setVisible(false);
        img21.setVisible(false);
        img23.setVisible(false);
        img2.setVisible(false);
        img31.setVisible(false);
    }
    
    public void recibirParametros(int numEstado){
        this.numEstado = numEstado;
        analizarAvance();
    }
    
    private void analizarAvance(){
        switch (numEstado) {
            case 1:
                avanzar1();
                break;
            case 12:
                avanzar12();
                break;
            case 21:
                retroceder21();
                break;
            case 23:
                avanzar23();
                break;
            case 2:
                avanzar2(2);
                break;
            case 31:
                retroceder31(31);
                break;
            default:
                break;
        }
    }
    
    public void closeWindows(){        
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/maquinaEstado.fxml"));
            Parent root = loader.load();
            PantallaInicialController controlador = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }          
    }

    private void esperar(int i) {
        try {
            Thread.sleep(i*100);
        } catch (InterruptedException ex) {
            Logger.getLogger(MaquinaEstadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void avanzar1(){
        if(!S1.isVisible()){
            Label lbl = new Label("1. Menu prinicipal");
            lbl.setWrapText(true);
            lbl.setId("estados");
            this.vbEstados.getChildren().add(lbl);
        }
        S1.setVisible(true);
        Glow glow = new Glow();
        Thread tr = new Thread(()->{
            while (numEstado==1){                
                for(i=0.1; i<=1;i=i+0.2){
                    glow.setLevel(i);
                    esperar(3);
                    Platform.runLater(()->{
                        S1.setEffect(glow);
                    });
                }
            }
            Platform.runLater(()-> S1.setEffect(null));
        });
        tr.setDaemon(true);
        tr.start();
    }
    
    private void avanzar12() {
        if(!S2.isVisible()){
            Label lbl = new Label("2. Ajustar configuraciones");
            lbl.setId("estados");
            lbl.setWrapText(true);
            this.vbEstados.getChildren().add(lbl);
        }
        S2.setVisible(true);
        img12.setVisible(true);
        Glow glow = new Glow();
        Thread tr = new Thread(()->{
            while (numEstado==12){                
                for(i=0.1; i<=1;i=i+0.2){
                    glow.setLevel(i);
                    esperar(3);
                    Platform.runLater(()->{
                        S1.setEffect(glow);
                        S2.setEffect(glow);
                        img12.setEffect(glow);
                        //img12.setOpacity(i);
                    });
                }
            }
            Platform.runLater(()->{
                S1.setEffect(null);
                S2.setEffect(null);
                img12.setEffect(null);
                img12.setOpacity(1);
            });
        });
        tr.setDaemon(true);
        tr.start();
    }
    
    private void retroceder21(){
        img21.setVisible(true);
        Glow glow = new Glow();
        Thread tr = new Thread(()->{
            while (numEstado==21){                
                for(i=0.1; i<=1;i=i+0.2){
                    glow.setLevel(i);
                    esperar(3);
                    Platform.runLater(()->{
                        S1.setEffect(glow);
                        S2.setEffect(glow);
                        img21.setEffect(glow);
                        img21.setOpacity(i);
                    });
                }
            }
            Platform.runLater(()->{
                S1.setEffect(null);
                S2.setEffect(null);
                img21.setEffect(null);
                img21.setOpacity(1);
            });
        });
        tr.setDaemon(true);
        tr.start();        
    }

    private void avanzar23() {        
        if(!S3.isVisible()){
            Label lbl = new Label("3. Jugar");
            lbl.setId("estados");
            lbl.setWrapText(true);
            this.vbEstados.getChildren().add(lbl);
        }
        S3.setVisible(true);
        img23.setVisible(true);
        Glow glow = new Glow();
        Thread tr = new Thread(()->{
            while (numEstado==23){                
                for(i=0.1; i<=1;i=i+0.2){
                    glow.setLevel(i);
                    esperar(3);
                    Platform.runLater(()->{
                        S2.setEffect(glow);
                        S3.setEffect(glow);
                        img23.setEffect(glow);
                        img23.setOpacity(i);
                    });
                }
            }
            Platform.runLater(()->{
                S3.setEffect(null);
                S2.setEffect(null);
                img23.setEffect(null);
                img23.setOpacity(1);
            });
        });
        tr.setDaemon(true);
        tr.start();
    }

    private void avanzar2(int num) {
        img2.setVisible(true);
        Glow glow = new Glow();
        Thread tr = new Thread(()->{
            while (numEstado==num){                
                for(i=0.1; i<=1;i=i+0.2){
                    glow.setLevel(i);
                    esperar(3);
                    Platform.runLater(()->{
                        S2.setEffect(glow);
                        img2.setEffect(glow);
                        img2.setOpacity(i);
                    });
                }
            }
            Platform.runLater(()->{
                S2.setEffect(null);
                img2.setEffect(null);
                img2.setOpacity(1);
            });
        });
        tr.setDaemon(true);
        tr.start();    
    }

    private void retroceder31(int num) {
        img31.setVisible(true);
        Glow glow = new Glow();
        Thread tr = new Thread(()->{
            while (numEstado==num){                
                for(i=0.1; i<=1;i=i+0.2){
                    glow.setLevel(i);
                    esperar(3);
                    Platform.runLater(()->{
                        S3.setEffect(glow);
                        S1.setEffect(glow);
                        img31.setEffect(glow);
                        //img31.setOpacity(i);
                    });
                }
            }
            Platform.runLater(()->{
                S3.setEffect(null);
                S1.setEffect(null);
                img31.setEffect(null);
                img31.setOpacity(1);
            });
        });
        tr.setDaemon(true);
        tr.start();   
    }
}
