/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import static controlador.Main.palabras;
import hilos.*;
import static hilos.CuentaRegresiva.esperar;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private Map<String,String> palabrasJugador;
    private Map<String,String> palabrasPC;
    private List<TextField> listTxtJugador;
    private List<TextField> listTxtPC;
    private boolean chanton;
    
    private int i;
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
        listTxtPC = new ArrayList<>();
        listTxtJugador = new ArrayList<>();
        palabrasJugador = new TreeMap<>();
        palabrasPC = new TreeMap<>();
        habilitar = true;
        chanton = false;
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
        campos.add(0, "Letra");
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
        listTxtPC.clear();
        listTxtJugador.clear();
        habilitar = true;
        List<VBox> botones = new ArrayList<>();
        HBox newLetra = new HBox(10);
        newLetra.setAlignment(Pos.CENTER_RIGHT);
        TextField txtLetra = new TextField(this.lblLetra.getText().trim().toUpperCase());
        txtLetra.setDisable(true);
        txtLetra.setAlignment(Pos.CENTER);
        newLetra.getChildren().addAll(txtLetra,new Line(0.0f, 0.0f, 0.0f, 30.0f));
        lista.get(0).getChildren().addAll(new Line(-100f, 00.0f,65f, 0.0f),newLetra);     
        for(VBox vb: lista.subList(1, lista.size())){
            HBox newRonda = new HBox(10);
            newRonda.setAlignment(Pos.CENTER_RIGHT);
            TextField txfJugador = new TextField();
            if ("PC".equals(tipo)){
                txfJugador.setStyle("-fx-background-color: BLACK;-fx-text-fill: black");
                listTxtPC.add(txfJugador);
            }else
                listTxtJugador.add(txfJugador);
            HBox hb = (HBox)vb.getChildren().get(0);
            VBox vbBotones = vbBotones(txfJugador, ((Text)hb.getChildren().get(0)).getText());
            botones.add(vbBotones);
            newRonda.getChildren().addAll(txfJugador,new Line(0.0f, 0.0f, 0.0f, 30.0f));
            newRonda.setFocusTraversable(true);
            vb.getChildren().addAll(new Line(-100f, 00.0f,65f, 0.0f),newRonda);     
        }       
        if ("PC".equals(tipo))
            iniciarJuegoPC(listTxtPC);
        threadBloquear(lista,botones);        
    }
    
    private void threadBloquear(List<VBox> lista,List<VBox> botones){        
        Thread tr = new Thread(()->{
            while(habilitar){
                System.out.println(!habilitar);
                if(!habilitar){
                    System.out.println("chanton");
                    Runnable updater = () -> {
                        int cont = 0;
                        for(VBox vb: lista.subList(1, lista.size())){
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
            }
        });
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
        chanton = true;
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
            this.habilitar = false;
        });
        tr.setDaemon(true);
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
        chanton = false;
        this.txtChanton.setText("Chanton");
        this.lblLetra.setText("");
        this.lblLetra.setEditable(true);
        this.lblLetra.requestFocus();
        int rondaSgte = Integer.parseInt(txtRondas.getText())+1;
        this.txtRondas.setText(String.valueOf(rondaSgte));       
        if(rondaSgte<=(Integer.parseInt(this.txtTotalRondas.getText()))){
            this.btnSgteRonda.setDisable(true);
            this.btnChanton.setDisable(false);
        }
        this.lblLetra.setOnKeyPressed((KeyEvent event1) -> {
            if (event1.getCode() == KeyCode.ENTER) {
                if (!this.lblLetra.getText().trim().matches("[a-zA-Z]")){
                    Alert a = new Alert(AlertType.ERROR,"DEBE INGRESAR UNA LETRA VALIDA [A-Z]");
                    a.show();
                }else{
                    nuevaLetra();
                    this.lblLetra.setEditable(false);
                }
            }
        });
    }
    
    private void nuevaLetra(){
        agregarCampo(listaCamposJugador,"Jugador");
        agregarCampo(listaCamposComputador,"PC");
    }
    
    private void iniciarJuegoPC(List<TextField> camposCompu){        
        Map<String,List<String>> mapPal = palabras.get(this.lblLetra.getText().trim().charAt(0));        
        Thread tr = new Thread(() -> {
            i = 0;
            for(String str: campos.subList(1, campos.size())){
                if(!habilitar)
                    break;
                esperarRandom();
                List<String> listaPalabras = mapPal.get(str.toLowerCase().trim());
                String palabra = listaPalabras.get(numeroAleatorioEnRango(0,listaPalabras.size()-1));
                System.out.println(palabra);
                camposCompu.get(i).setText(palabra);
                i++;
            }
            if(!chanton)
                btnChanton.fire();
        });
        tr.setDaemon(true);
        tr.start();
    }
    
    private void esperarRandom(){
        Random rd = new Random();
        int tiempo = rd.nextInt(6000)+500;
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static int numeroAleatorioEnRango(int minimo, int maximo){
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }
   
}
