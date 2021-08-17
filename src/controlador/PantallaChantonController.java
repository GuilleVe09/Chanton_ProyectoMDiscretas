/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import hilos.CuentaRegresiva;
import hilos.ManejoBotones;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.layout.Background;
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
    
    PantallaConfiguracionesController controladorJuego;
   
    @FXML
    private Label lblLetra;
    @FXML
    private Label _txtJugador;
    @FXML
    private HBox hbJugador;
    @FXML
    private HBox hbComputadora;
    
    private List<String> campos;
    private String letraEscogida;
    private boolean ocultarTiempo;
    private List<VBox> listaCamposJugador;
    private List<VBox> listaCamposComputador;
    
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
            hbEncabezadoJugador.setAlignment(Pos.TOP_RIGHT);
            Text txt = new Text(s);
            txt.setWrappingWidth(105);
            hbEncabezadoJugador.getChildren().addAll(txt,new Line(0.0f, 10.0f, 0.0f, 30.0f));
            newCampo.getChildren().add(hbEncabezadoJugador);
            lista.add(newCampo);
            encabezado.getChildren().add(newCampo);
        }        
        agregarCampo(lista,tipo);
    }

    
    private void agregarCampo(List<VBox> lista, String tipo){
        for(VBox vb: lista){
            VBox vbBotones = vbBotones();
            HBox newRonda = new HBox(10);
            newRonda.setAlignment(Pos.TOP_RIGHT);
            TextField txfJugador = new TextField();
            if ("PC".equals(tipo))
                txfJugador.setStyle("-fx-background-color: BLACK");
            newRonda.getChildren().addAll(txfJugador,vbBotones,new Line(0.0f, 10.0f, 0.0f, 30.0f));
            vb.getChildren().addAll(newRonda);
        }       
    }

    private VBox vbBotones(){
        VBox vbBotones = new VBox(5);
        ImageView imgVisto = new ImageView(new Image("recursos/imagenes/visto.png"));
        imgVisto.setFitHeight(8.0);
        imgVisto.setFitWidth(8.0);
        ImageView imgX = new ImageView(new Image("recursos/imagenes/x.png"));
        imgX.setFitHeight(8.0);
        imgX.setFitWidth(8.0);
        Button visto = new Button("",imgVisto);
        visto.setMinHeight(10);
        Button x = new Button("",imgX);
        x.setMinHeight(10);
        vbBotones.getChildren().addAll(visto,x);
        return vbBotones;
    }
    
    private void bloquearTextField(List<TextField> lista, boolean activar){
        lista.forEach(e->{
            e.setDisable(activar);
        });
    }
    @FXML
    private void pararMano(ActionEvent event) {
        this.txtChanton.setVisible(true);
        this.txtTiempo.setVisible(true);
        this.btnChanton.setDisable(true);
        CuentaRegresiva cr = new CuentaRegresiva(10,this.txtTiempo,this.txtChanton);
        cr.setDaemon(true);
        cr.start();
        int value = 1;
        if (Integer.parseInt(this.txtTotalRondas.getText())==Integer.parseInt(this.txtRondas.getText()))
            value = 0;
        ManejoBotones mb = new ManejoBotones(this.btnChanton,btnSgteRonda,this.txtTiempo,value);
        mb.setDaemon(true);
        mb.start();
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
