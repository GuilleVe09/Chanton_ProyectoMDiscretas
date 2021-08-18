/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import hilos.*;
import static hilos.CuentaRegresiva.esperar;
import hilos.ManejoBotonesLaterales;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelo.Jugador;

/**
 * FXML Controller class
 *
 * @author guill
 */
public class PantallaChantonController implements Initializable {
    @FXML
    private Text txtJugador;
    @FXML
    private Text txtPuntajeJugador;
    @FXML
    private Text txtPuntajeCompu;       
    @FXML
    private Text txtRondas;
    @FXML
    private Text txtTotalRondas;
    @FXML
    private Text txtChanton;
    @FXML
    private Text txtTiempo;
    @FXML
    private Button btnChanton;
    @FXML
    private Button btnSgteRonda;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField lblLetra;
    @FXML
    private Label _txtJugador;
    @FXML
    private HBox hbJugador;
    @FXML
    private HBox hbComputadora;
    
    PantallaConfiguracionesController controladorJuego;
      
    private List<String> campos;
    private String letraEscogida;
    private boolean habilitar;
    private List<VBox> listaCamposJugador;
    private List<VBox> listaCamposComputador;
    private hiloJuegoComputadora hiloJuegoC;
    private ManejoBotones hiloManejoBotones;
    private Map<String,String> palabrasJugador;
    private Map<String,String> palabrasPC;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ImageView imgChanton = new ImageView(new Image("recursos/imagenes/chanton.png"));
        imgChanton.setFitHeight(70.0);
        imgChanton.setFitWidth(82.0);
        this.btnChanton.setGraphic(imgChanton);
        listaCamposJugador = new ArrayList<>();
        listaCamposComputador = new ArrayList<>();        
        palabrasJugador = new TreeMap<>();
        palabrasPC = new TreeMap<>();
        habilitar = true;
    }    
    
    
    public void recibeParametros(PantallaConfiguracionesController controlador, String rondas, String letra, List<String> campos, Jugador jugador){
        letraEscogida = letra;
        lblLetra.setText(letra);
        txtTotalRondas.setText(rondas);
        txtRondas.setText("1");
        txtJugador.setText(jugador.getNickname());
        _txtJugador.setText(jugador.getNickname());
        controladorJuego = controlador;
        this.campos = campos;
        mostrarCamposJugador(hbJugador,listaCamposJugador,"Jugador");
        mostrarCamposJugador(this.hbComputadora,listaCamposComputador,"PC");
    }
    
    public void mostrarCamposJugador(HBox encabezado, List<VBox> lista, String tipo){           
        for(String s: campos){
            VBox newCampo = new VBox(10);
            newCampo.setAlignment(Pos.TOP_RIGHT);
            HBox hbEncabezadoJugador = new HBox(10);
            hbEncabezadoJugador.setAlignment(Pos.CENTER_RIGHT);
            Text txt = new Text(s);
            txt.setWrappingWidth(130);
            hbEncabezadoJugador.getChildren().addAll(txt,new Line(0.0f, 0.0f, 0.0f, 30.0f));
            newCampo.getChildren().add(hbEncabezadoJugador);
            lista.add(newCampo);
            encabezado.getChildren().add(newCampo);
        }        
        agregarCampo(lista,tipo);
    }

    
    private void agregarCampo(List<VBox> lista, String tipo){
        habilitar = true;
        List<VBox> botones = new ArrayList<>();
        for(VBox vb: lista){
            HBox newRonda = new HBox(10);
            newRonda.setAlignment(Pos.CENTER_RIGHT);
            TextField txfJugador = new TextField();
            if ("PC".equals(tipo))
                txfJugador.setStyle("-fx-background-color: BLACK");
            HBox hb = (HBox)vb.getChildren().get(0);
            VBox vbBotones = vbBotones(txfJugador, ((Text)hb.getChildren().get(0)).getText());
            botones.add(vbBotones);
            newRonda.getChildren().addAll(txfJugador,new Line(0.0f, 0.0f, 0.0f, 30.0f));
            newRonda.setFocusTraversable(true);
            vb.getChildren().addAll(new Line(-100f, 00.0f,65f, 0.0f),newRonda);     
        }       
        threadBloquear(lista,botones);
        
    }
    
    private void threadBloquear(List<VBox> lista,List<VBox> botones){        
        Thread tr = new Thread(()->{
            do{
                System.out.println(!habilitar);
                if(!habilitar){
                    System.out.println("chanton");
                    Runnable updater = () -> {
                        int cont = 0;
                        for(VBox vb: lista){
                            String campo = ((Text)(((HBox)vb.getChildren().get(0)).getChildren().get(0))).getText();
                            System.out.println(campo.toLowerCase().trim());
                            HBox ultimo = ((HBox)vb.getChildren().get(vb.getChildren().size()-1));                            
                            ultimo.getChildren().remove(1);
                            bloquearTextField((TextField)ultimo.getChildren().get(0),habilitar);
                            ultimo.getChildren().addAll(botones.get(cont),new Line(0.0f, 0.0f, 0.0f, 30.0f));
                            cont++;
                        }
                    };
                    Platform.runLater(updater);
                }
            }while(habilitar);});
        tr.setDaemon(true);
        tr.start();         
    }
    private void bloquearTextField(TextField e, boolean habilitar){
        e.setDisable(habilitar);
        e.setEditable(habilitar);
        e.setStyle("-fx-background-color: white");
    }

    private VBox vbBotones(TextField txfJugador, String campo){
        VBox vbBotones = new VBox(5);
        ImageView imgVisto = new ImageView(new Image("recursos/imagenes/visto.png"));
        imgVisto.setFitHeight(8.0);
        imgVisto.setFitWidth(8.0);
        ImageView imgX = new ImageView(new Image("recursos/imagenes/x.png"));
        imgX.setFitHeight(8.0);
        imgX.setFitWidth(8.0);
        Button visto = new Button("",imgVisto);
        visto.setMinHeight(8);
        Button x = new Button("",imgX);
        x.setMinHeight(8);
        vbBotones.getChildren().addAll(visto,x);
        return vbBotones;
    }
    
    private void sumarPuntos(){
        
    }
    
    @FXML
    private void pararMano(ActionEvent event) {
        this.txtChanton.setVisible(true);
        this.txtTiempo.setVisible(true);
        this.btnChanton.setDisable(true);
        CuentaRegresiva cr = new CuentaRegresiva(10,this.txtTiempo,this.txtChanton);
        cr.setDaemon(true);
        cr.start();
        Thread tr = new Thread(()->{
            esperar(10);                
            int value = 1;
            if (Integer.parseInt(this.txtTotalRondas.getText())==Integer.parseInt(this.txtRondas.getText()))
                value = 0;
            this.btnChanton.setDisable(true);
            if(value==1)
                this.btnSgteRonda.setDisable(false);
            habilitar = false;
        });
        tr.start();
    }

    @FXML
    private void regresar(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/pantallaInicial.fxml"));
            Parent root = loader.load();
            PantallaInicialController controlador = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage myStage = (Stage) this.btnChanton.getScene().getWindow();
            myStage.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }    
    }

    @FXML
    private void sgteRonda(ActionEvent event) {
        int rondaSgte = Integer.parseInt(txtRondas.getText())+1;
        this.txtRondas.setText(String.valueOf(rondaSgte));       
        if(rondaSgte<=(Integer.parseInt(this.txtTotalRondas.getText()))){
            this.btnSgteRonda.setDisable(true);
            this.btnChanton.setDisable(false);
        }
        agregarCampo(listaCamposJugador,"Jugador");
        agregarCampo(listaCamposComputador,"PC");
    }
   
}
