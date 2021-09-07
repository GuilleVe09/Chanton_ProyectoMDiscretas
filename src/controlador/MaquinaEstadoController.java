/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author John
 */
public class MaquinaEstadoController implements Initializable {    
    @FXML
    private VBox vbEstados;    
    @FXML
    private Pane S4;    
    @FXML
    private Pane lnB01;
    @FXML
    private Pane S0;
    @FXML
    private Pane SA1;
    @FXML
    private Pane SB2;
    @FXML
    private Pane SA3;
    @FXML
    private Pane SB1;
    @FXML
    private Pane SA2;
    @FXML
    private Pane SB3;
    @FXML
    private Pane lnA01;
    @FXML
    private Pane lnA12;
    @FXML
    private Pane lnA23;
    @FXML
    private Pane lnA34;
    @FXML
    private Pane lnB1;
    @FXML
    private Pane lnB2;
    @FXML
    private Pane lnB3;
    @FXML
    private Pane lnA3;
    @FXML
    private Pane lnA2;
    @FXML
    private Pane lnA1;
    @FXML
    private Pane lnB12;
    @FXML
    private Pane lnB23;
    @FXML
    private Pane lnB34;
    @FXML
    private Pane lnA40;
    @FXML
    private Pane lnB40;
    
    private int numEstadoI;
    private int numEstadoF;
    private MaquinaEstadoController instacia;   
    private char letra;
    //valor efecto
    private double i;
    private List<Pane> paneles;
    private List<String> indices;
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instacia = this;
        paneles = Arrays.asList(S0,lnA01,SA1,lnA1,lnA12,SA2,lnA2,lnA23,SA3,lnA3,lnA34,lnA40,lnB01,SB1,lnB1,lnB12,SB2,lnB2,lnB23,SB3,lnB3,lnB34,lnB40,S4);        
        indices = Arrays.asList("S0","lnA01","SA1","lnA1","lnA12","SA2","lnA2","lnA23","SA3","lnA3","lnA34","lnA40","lnB01","SB1","lnB1","lnB12","SB2","lnB2","lnB23","SB3","lnB3","lnB34","lnB40","S4");
        ocultar();
    }    
    
    private void ocultar(){           
        paneles.forEach(e->{
            e.setVisible(false);
        });
    }
    
    public void recibirParametros(int numEstadoI, int numEstadoF ,char letra){
        this.numEstadoI = numEstadoI;
        this.numEstadoF = numEstadoF;
        this.letra = letra;
        if(numEstadoI == 0 && numEstadoF ==0){
            iniciarEstado(0);
        }else{
            analizarAvance();
        }     
    }
    /*avanzar(Pane inicial, Pane linea, Pane fin, int num)*/
    private void analizarAvance(){
        int indPanelI = indices.indexOf("S"+letra+numEstadoI);
        int indLinea;
        if(numEstadoI == numEstadoF)
            indLinea = indices.indexOf("ln"+numEstadoI);
        else
            indLinea = indices.indexOf("ln"+letra+numEstadoI+""+numEstadoF);
        
        int indPanelF = indices.indexOf("S"+letra+numEstadoI+""+numEstadoF);
        
        avanzar(paneles.get(indPanelI),paneles.get(indLinea),paneles.get(indPanelF),numEstadoI);
    }
    
    private void iniciarEstado(int estado){
        paneles.get(0).setVisible(true);
        Label lbl = new Label("S0: LETRA ESCOGIDA");
        lbl.setWrapText(true);
        this.vbEstados.getChildren().add(lbl);
        Glow glow = new Glow();
        Thread tr = new Thread(()->{
            while (numEstadoI==estado){                
                for(i=0.1; i<=1;i=i+0.2){
                    glow.setLevel(i);
                    esperar(3);
                    Platform.runLater(()->{
                        paneles.get(0).setEffect(glow);                        
                    });
                }
            }
            eliminarEfectos();            
        });
        tr.setDaemon(true);
        tr.start(); 
    }
    
    private void eliminarEfectos(){
        Platform.runLater(()->{
            paneles.forEach(e->{
                e.setEffect(null);
                e.setOpacity(1);
            });
        });
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
    
    private void avanzar(Pane inicial, Pane linea, Pane fin, int num){      
        inicial.setVisible(true);
        linea.setVisible(true);
        fin.setVisible(true);
        Glow glow = new Glow();
        Thread tr = new Thread(()->{
            while (numEstadoI==num){                
                for(i=0.1; i<=1;i=i+0.2){
                    glow.setLevel(i);
                    esperar(3);
                    Platform.runLater(()->{
                        inicial.setEffect(glow);
                        linea.setEffect(glow);
                        if(inicial!=fin)
                            fin.setEffect(glow);
                        linea.setOpacity(i);
                    });
                }
            }
            eliminarEfectos();
        });
        tr.setDaemon(true);
        tr.start();        
    }

   
    public void cerrarMaquina(){
        Stage myStage = (Stage) this.S0.getScene().getWindow();
        myStage.close();
    }
}
