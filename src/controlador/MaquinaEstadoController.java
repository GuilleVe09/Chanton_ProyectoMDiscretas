/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private Pane lnA40;
    @FXML
    private Pane lnB40;
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
                
    //valor efecto
    private double i;
    private int estadoI;
    private int estadoF;
    private int num;
    private MaquinaEstadoController instacia;   
    private List<Pane> estados;
    private List<String> indices;
    private char letra;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        num = 0;
        instacia = this;
        estados = Arrays.asList(lnB01,S0,SA1,SB2,SA3,SB1,SA2,SB3,lnA40,lnB40,S4,lnA01,lnA12,lnA23,lnA34,lnB1,lnB2,lnB3,lnA3,lnA2,lnA1,lnB12,lnB23,lnB34);
        indices = Arrays.asList("lnB01","S0","SA1","SB2","SA3","SB1","SA2","SB3","lnA40","lnB40","S4","lnA01","lnA12","lnA23","lnA34","lnB1","lnB2","lnB3","lnA3","lnA2","lnA1","lnB12","lnB23","lnB34");
        ocultar();
    }    
    
    private void ocultar(){           
        estados.forEach(e->e.setVisible(false));
    }
    
    public void cambiarLetra(char letra){
        this.letra = letra;
    }
    
    public void recibirParametros(char letra){
        this.letra = letra;    
        num = 0;
        empezarMaquina();
    }
    
    public void cambiarEstado(int inicio, int fin, char letraLineaRecursiva){
        analizarAvance(inicio, fin,letraLineaRecursiva);
    }
    
    private void analizarAvance(int inicio, int fin, char letraLineaRecursiva){        
        int indPaneI=0;
        int indLine=0;
        int indPaneF=-1;
        String strEstado;
        if(inicio == fin){
            indPaneI = indices.indexOf("S"+letra+""+inicio);
            indLine = indices.indexOf("ln"+letra+""+inicio);            
            System.out.println("S"+letra+"inicio");
            System.out.println("ln"+letra+""+inicio);
        }
        else if(inicio==0){
            indPaneI = indices.indexOf("S0");
            indLine = indices.indexOf("ln"+letraLineaRecursiva+"0"+fin);
            indPaneF = indices.indexOf("S"+letra+""+fin);
        }        
        else if(inicio<4 && fin<4){
            indPaneI = indices.indexOf("S"+letra+""+inicio);
            indLine = indices.indexOf("ln"+letra+""+inicio+""+fin);
            indPaneF = indices.indexOf("S"+letra+""+fin);
        }else if(inicio<4 && fin == 4){
            indPaneI = indices.indexOf("S4");
            indLine = indices.indexOf("ln"+letra+""+inicio+""+fin);
            indPaneF = indices.indexOf("S"+letra+""+fin);
        }else{
            indPaneI = indices.indexOf("S4");
            indLine = indices.indexOf("ln"+letra+"40");
            indPaneF = indices.indexOf("S0");                    
        }
        
        Pane pInicial = estados.get(indPaneI);
        Pane linea = estados.get(indLine);
        Pane pFinal = null;
        if(indPaneF!=-1){
            pFinal = estados.get(indPaneF);             
            agregarEstado(pFinal,indices.get(indPaneF));
        }else{
            if(inicio!=fin)
                agregarEstado(S4,"S4"); 
        }           
        avanzar(pInicial,linea, pFinal);
    }
    
    private void agregarEstado(Pane pane, String estado){
        if(!pane.isVisible()){
            if(null != estado)
                switch (estado) {
                case "SA1":
                    vbEstados.getChildren().add(new Label("S4: Nombre con A"));
                    break;
                case "SA2":
                    vbEstados.getChildren().add(new Label("S5: Apellido con A"));
                    break;
                case "SA3":
                    vbEstados.getChildren().add(new Label("S6: País con A"));
                    break;
                case "S4":
                    vbEstados.getChildren().add(new Label("S7: Ronda terminada"));
                    break;
                case "SB1":
                    vbEstados.getChildren().add(new Label("S1: Nombre con B"));
                    break;
                case "SB2":
                    vbEstados.getChildren().add(new Label("S2: Apellido con A"));
                    break;
                case "SB3":
                    vbEstados.getChildren().add(new Label("S3: País con B"));
                    break;
                default:
                    break;
            }
        }        
    }
    
    private void avanzar(Pane paneI, Pane linea, Pane paneF){
        paneI.setVisible(true);
        linea.setVisible(true);
        if(paneF!=null){
            paneF.setVisible(true);            
        }
            
            
            
    }
    
    private void empezarMaquina(){
        num = 1;
        if(!S0.isVisible())
            vbEstados.getChildren().add(new Label("S0: Letra escogida"));
        S0.setVisible(true);
        Glow glow = new Glow();
        Thread tr = new Thread(()->{
            while(num == 1){
                for (i = 0.1; i <= 1; i+=0.2) {
                    glow.setLevel(i);
                    esperar(3);
                    Platform.runLater(()->{
                        S0.setEffect(glow);
                    });
                }
            }
            quitarEfectos();
        });
        tr.setDaemon(true);
        tr.start();        
    }    
    
    private void quitarEfectos(){
        this.estados.forEach(e->{
            e.setEffect(null);
        });
    }

    private void esperar(int i) {
        try {
            Thread.sleep(i*100);
        } catch (InterruptedException ex) {
            Logger.getLogger(MaquinaEstadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cerrarVentana(){
        Stage myStage = (Stage) this.S4.getScene().getWindow();
        myStage.close();
    }
    
    
}
