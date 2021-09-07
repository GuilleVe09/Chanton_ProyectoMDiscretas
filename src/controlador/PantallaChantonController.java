/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import static controlador.PantallaConfiguracionesController.maquina;
import static interfaz.Main.palabras;
import hilos.*;
import static hilos.CuentaRegresiva.esperar;
import interfaz.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Screen;
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
    @FXML
    private Button btnFinalizar;
    
    PantallaConfiguracionesController controladorJuego;
      
    private List<String> campos;
    private String letraEscogida;
    private boolean habilitar;
    private List<VBox> listaCamposJugador;
    private List<VBox> listaCamposComputador;
    private Map<String,String> palabrasJugador;
    private Map<String,String> palabrasPC;
    private boolean chanton;
    private Jugador jugador;
    private Jugador PC;
    private Map<String, TextField> mapPC_;
    private Map<String,TextField> mapJugador_;
    private int i;
    private TextField txtTotalRondaPC;
    private List<HBox> listaResultado;
    private int indice;
    private int estado;
    private int tiempoChanton;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
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
        chanton = false;
        this.PC = new Jugador("PC");
        mapPC_ = new HashMap<>();
        mapJugador_ = new HashMap<>();
        listaResultado = new ArrayList<>();
        indice = 0;      
        estado = 1;
        tiempoChanton = 10;        
    }        
    
    public void recibeParametros(PantallaConfiguracionesController controlador, String rondas, String letra, List<String> campos, Jugador jugador){
        letraEscogida = letra;
        lblLetra.setText(letra);
        txtTotalRondas.setText(rondas);
        txtRondas.setText("1");
        txtJugador.setText(jugador.getNickname());
        _txtJugador.setText(jugador.getNickname());
        this.jugador = jugador;
        controladorJuego = controlador;
        this.campos = campos;
        campos.add(0, "Letra");
        campos.add("Total");
        mostrarEncabezado(hbJugador,listaCamposJugador,jugador);
        mostrarEncabezado(this.hbComputadora,listaCamposComputador,PC);        
        maquina.cambiarEstado(0,1, this.lblLetra.getText().charAt(0));
    }
    
    private void mostrarEncabezado(HBox encabezado, List<VBox> lista, Jugador tipo){       
        campos.stream().map((s) -> {
            VBox newCampo = new VBox(10);
            newCampo.setAlignment(Pos.TOP_RIGHT);
            HBox hbEncabezadoJugador = new HBox(10);
            hbEncabezadoJugador.setAlignment(Pos.CENTER_RIGHT);
            Text txt = new Text(s);
            txt.setWrappingWidth(130);
            hbEncabezadoJugador.getChildren().addAll(txt,new Line(0.0f, 0.0f, 0.0f, 30.0f));
            newCampo.getChildren().add(hbEncabezadoJugador);
            return newCampo;
        }).map((newCampo) -> {
            lista.add(newCampo);
            return newCampo;
        }).forEachOrdered((newCampo) -> {
            encabezado.getChildren().add(newCampo);
        });        
        agregarCampo(lista,tipo);
    }
    
    private void agregarCampo(List<VBox> lista, Jugador tipo){        
        tipo.setPuntajeRonda(0);
        List<TextField> listTxtPC = new ArrayList<>();
        habilitar = true;
        List<VBox> botones = new ArrayList<>();
        HBox newLetra = new HBox(10);
        newLetra.setAlignment(Pos.CENTER_RIGHT);
        TextField txtLetra = new TextField(this.lblLetra.getText().trim().toUpperCase());
        txtLetra.setDisable(true);
        txtLetra.setAlignment(Pos.CENTER);
        newLetra.getChildren().addAll(txtLetra,new Line(0.0f, 0.0f, 0.0f, 30.0f));
        lista.get(0).getChildren().addAll(new Line(-100f, 00.0f,65f, 0.0f),newLetra);     
        
        HBox hbTotal = new HBox(10);
        hbTotal.setAlignment(Pos.CENTER_RIGHT);
        TextField txtTotal = new TextField("0");
        hbTotal.setDisable(true);
        hbTotal.setAlignment(Pos.CENTER);
        hbTotal.getChildren().addAll(txtTotal,new Line(0.0f, 0.0f, 0.0f, 30.0f));
        
        lista.subList(1, lista.size()-1).forEach((vb) -> {
            HBox newRonda = new HBox(10);
            newRonda.setAlignment(Pos.CENTER_RIGHT);
            TextField txfJugador = new TextField();
            if ("PC".equals(tipo.getNickname())){
                txfJugador.setStyle("-fx-background-color: BLACK;-fx-text-fill: black");
                listTxtPC.add(txfJugador);
            }
            if(!tipo.equals(PC)){
                txfJugador.setOnKeyPressed((KeyEvent event1) -> {
                    if (event1.getCode() == KeyCode.ENTER) {
                        if(!txfJugador.getText().trim().isEmpty()){
                            if (Character.toUpperCase(txfJugador.getText().trim().charAt(0)) == lblLetra.getText().trim().charAt(0)){                                
                                desbloquearSiguiente();
                                if(estado<=4){
                                    maquina.cambiarEstado((estado-1), estado, this.lblLetra.getText().charAt(0));                                
                                    System.out.println((estado-1)+" siguiente correcto: "+(estado));
                                }else{
                                    maquina.cambiarEstado(estado, 0, this.lblLetra.getText().charAt(0));                                
                                    System.out.println(estado+" siguiente correcto: 0");
                                }                                                                
                            }                                                    
                            else{
                                char l;
                                if(lblLetra.getText().charAt(0)=='A')
                                    l = 'B';
                                else
                                    l = 'A';
                                maquina.cambiarEstado(estado, estado, l);
                                System.out.println(estado+" siguiente incorrecto: "+estado);
                            }                            
                        }
                        
                    }
                });
            }
            HBox hb = (HBox)vb.getChildren().get(0);
            VBox vbBotones = vbBotones(txfJugador, ((Text)hb.getChildren().get(0)).getText(), tipo,txtTotal);
            botones.add(vbBotones);
            
            newRonda.getChildren().addAll(txfJugador,new Line(0.0f, 0.0f, 0.0f, 30.0f));
            newRonda.setFocusTraversable(true);
            vb.getChildren().addAll(new Line(-100f, 00.0f,65f, 0.0f),newRonda);
            if(!tipo.equals(PC)){
                bloquearCampo(newRonda, tipo);
            }
                
        });   
        lista.get(lista.size()-1).getChildren().addAll(new Line(-100f, 00.0f,65f, 0.0f),hbTotal);
        if ("PC".equals(tipo.getNickname())){
            this.txtTotalRondaPC = txtTotal;
            iniciarJuegoPC(listTxtPC);
        }
        listaResultado.get(0).setDisable(false);
        threadBloquear(lista,botones,tipo);        
    }
    
    private void threadBloquear(List<VBox> lista,List<VBox> botones, Jugador jugador){
        Thread tr = new Thread(()->{
            while(habilitar){
                System.out.print("");
            }
            if(!habilitar){
                Runnable updater = () -> {
                    int cont = 0;
                    for(VBox vb: lista.subList(1, lista.size()-1)){
                        String campo = ((Text)(((HBox)vb.getChildren().get(0)).getChildren().get(0))).getText();
                        HBox ultimo = ((HBox)vb.getChildren().get(vb.getChildren().size()-1));                            
                        ultimo.getChildren().remove(1);                        
                        bloquearTextField((TextField)ultimo.getChildren().get(0),habilitar);
                        ultimo.getChildren().addAll(botones.get(cont),new Line(0.0f, 0.0f, 0.0f, 30.0f));
                        cont++;
                    }                   
                };
                Platform.runLater(updater); 
            }            
            esperar(2);
            this.txtChanton.setVisible(false);
        });
        tr.setDaemon(true);
        tr.start();         
    }
    
    private void bloquearCampo(HBox campoJugado, Jugador jugador){
        indice = 0;
        if(!jugador.getNickname().equalsIgnoreCase("PC"))
            this.listaResultado.add(campoJugado);
        campoJugado.setDisable(true);
    }
    
    private void desbloquearSiguiente(){
        indice++;
        System.out.println("INDICE "+indice);
        if (indice<listaResultado.size()){
            listaResultado.get(indice).setDisable(false);        
                Platform.runLater(()->{
                    ((TextField)(listaResultado.get(indice).getChildren().get(0))).requestFocus();
                });                        
        }
        if(estado<=4)
            estado++;
            
    }
    
    private void bloquearTextField(TextField e, boolean habilitar){
        e.setDisable(habilitar);
        e.setEditable(habilitar);
        e.setStyle("-fx-background-color: white");
    }

    private VBox vbBotones(TextField txfJugador, String campo, Jugador user, TextField total){
        VBox vbBotones = new VBox(5);
        ImageView imgVisto = new ImageView(new Image("recursos/imagenes/visto.png"));
        imgVisto.setFitHeight(8.0);
        imgVisto.setFitWidth(8.0);        
        Button visto = new Button("",imgVisto);
        visto.setMinHeight(8);
        ImageView imgX = new ImageView(new Image("recursos/imagenes/x.png"));
        imgX.setFitHeight(8.0);
        imgX.setFitWidth(8.0);        
        Button x = new Button("",imgX);
        x.setMinHeight(8);
        vbBotones.getChildren().addAll(visto,x);
        if(user.getNickname().equals("PC"))
            this.mapPC_.put(campo,txfJugador);
        else
            this.mapJugador_.put(campo, txfJugador);       
       
        visto.setOnAction(e->{
            sumarPuntos(mapJugador_.get(campo),mapPC_.get(campo),true);
            visto.setDisable(true);
            x.setDisable(true);
            total.setText(String.valueOf(user.getPuntajeRonda()));
            this.txtTotalRondaPC.setText(String.valueOf(PC.getPuntajeRonda()));             
        });
        x.setOnAction(e->{
            sumarPuntos(mapJugador_.get(campo),mapPC_.get(campo),false);
            visto.setDisable(true);
            x.setDisable(true);
            total.setText(String.valueOf(user.getPuntajeRonda()));
            this.txtTotalRondaPC.setText(String.valueOf(PC.getPuntajeRonda()));
        });
        return vbBotones;
    }
    
    private void sumarPuntos(TextField tfJugador, TextField tfpC, boolean palabraValida){
        boolean letraCorrecta = false;
        if(!tfJugador.getText().trim().isEmpty())
            letraCorrecta = tfJugador.getText().trim().toUpperCase().charAt(0)==this.lblLetra.getText().trim().toUpperCase().charAt(0);
        
        if((tfJugador.getText().trim().isEmpty() || !letraCorrecta) && !tfpC.getText().trim().isEmpty()){
            tfJugador.setStyle("-fx-background-color: #FF6973");                 
            tfpC.setStyle("-fx-background-color: #5BF0B6");
            this.PC.aumentarPuntaje(100);
        }
        else if(tfpC.getText().trim().isEmpty() && !tfJugador.getText().trim().isEmpty() && letraCorrecta && palabraValida){
            tfpC.setStyle("-fx-background-color: #FF6973");                    
            tfJugador.setStyle("-fx-background-color: #5BF0B6");
            this.jugador.aumentarPuntaje(100);
        }else if(!tfJugador.getText().trim().equalsIgnoreCase(tfpC.getText().trim()) && !tfpC.getText().isEmpty() && !palabraValida){
            tfJugador.setStyle("-fx-background-color: #FF6973");                 
            tfpC.setStyle("-fx-background-color: #5BF0B6");
            this.PC.aumentarPuntaje(100);
        }
        else if(tfJugador.getText().trim().equalsIgnoreCase(tfpC.getText().trim()) && !tfJugador.getText().isEmpty() && !tfpC.getText().isEmpty() && palabraValida){
            this.PC.aumentarPuntaje(50);
            this.jugador.aumentarPuntaje(50);
            tfJugador.setStyle("-fx-background-color: #FAF49A");
            tfpC.setStyle("-fx-background-color: #FAF49A");
        }else if(!tfJugador.getText().trim().equalsIgnoreCase(tfpC.getText().trim()) && !tfJugador.getText().isEmpty() && !tfpC.getText().isEmpty() && palabraValida){
            this.PC.aumentarPuntaje(100);
            this.jugador.aumentarPuntaje(100);
            tfJugador.setStyle("-fx-background-color: #5BF0B6");
            tfpC.setStyle("-fx-background-color: #5BF0B6");
        }
        txtPuntajeJugador.setText(String.valueOf(this.jugador.getPuntaje()));
        txtPuntajeCompu.setText(String.valueOf(this.PC.getPuntaje()));
    }
    
    @FXML
    private void pararMano(ActionEvent event) {
        chanton = true;
        this.txtChanton.setVisible(true);
        this.txtTiempo.setVisible(true);
        this.btnChanton.setDisable(true);
        CuentaRegresiva cr = new CuentaRegresiva(tiempoChanton,this.txtTiempo,this.txtChanton);
        cr.setDaemon(true);
        cr.start();
        Thread tr = new Thread(()->{
            esperar(tiempoChanton);                
            int value = 1;
            if (Integer.parseInt(this.txtTotalRondas.getText())==Integer.parseInt(this.txtRondas.getText()))
                value = 0;
            this.btnChanton.setDisable(true);
            if(value==1)
                this.btnSgteRonda.setDisable(false);
            this.habilitar = false;
            if(this.txtTotalRondas.getText().trim().equals(this.txtRondas.getText().trim()))
                this.btnFinalizar.setDisable(false);
        });
        tr.setDaemon(true);
        tr.start();
    }

    @FXML
    public void regresar(ActionEvent event) {
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
            maquina.cerrarVentana();
            Platform.runLater(()->habilitar = false);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }    
    }

    @FXML
    private void sgteRonda(ActionEvent event) {
        indice = 0;           
        listaResultado.clear();
        chanton = false;
        this.txtChanton.setText("Chanton");       
        int rondaSgte = Integer.parseInt(txtRondas.getText())+1;
        this.txtRondas.setText(String.valueOf(rondaSgte));       
        if(rondaSgte<=(Integer.parseInt(this.txtTotalRondas.getText()))){
            this.btnSgteRonda.setDisable(true);
            this.btnChanton.setDisable(false);
        }
        estado = 1;     
        if(lblLetra.getText().charAt(0)=='A')
            lblLetra.setText("B");           
        else
            lblLetra.setText("A");        
        nuevaLetra();
        maquina.cambiarLetra(lblLetra.getText().trim().charAt(0));
        maquina.cambiarEstado(0, 1, lblLetra.getText().trim().charAt(0));            
        this.lblLetra.setEditable(false);
    }
    
    private void nuevaLetra(){
        agregarCampo(listaCamposJugador,jugador);
        agregarCampo(listaCamposComputador,PC);
    }
    
    private void iniciarJuegoPC(List<TextField> camposCompu){     
        Map<String,List<String>> mapPal = palabras.get(this.lblLetra.getText().trim().toUpperCase().charAt(0));        
        System.out.println(mapPal);
        esperarRandom(5000);
        Thread tr = new Thread(() -> {
            i = 0;
            for(String str: campos.subList(1, campos.size()-1)){
                System.out.print("");
                esperarRandom(10000);
                if(habilitar){
                    List<String> listaPalabras = mapPal.get(str.toLowerCase().trim());
                    String palabra = listaPalabras.get(numeroAleatorioEnRango(0,listaPalabras.size()-1));
                    System.out.println(palabra);
                    camposCompu.get(i).setText(palabra);
                }
                i++;
            }
            if(!chanton)
                btnChanton.fire();
        });
        tr.setDaemon(true);
        tr.start();
    }
    
    private void esperarRandom(int n){
        Random rd = new Random();
        int tiempo = rd.nextInt(n)+500;
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static int numeroAleatorioEnRango(int minimo, int maximo){
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }

    @FXML
    private void finalizar(ActionEvent event) {
        this.txtChanton.setVisible(true);
        Alert a = new Alert(AlertType.INFORMATION,"JUGADOR GANADOR");
        if(jugador.getPuntaje()>PC.getPuntaje()){
            a.setContentText("¡"+jugador.getNickname()+" HAS GANADO!");
            this.txtChanton.setText("¡"+jugador.getNickname()+" HAS GANADO!");
        }
        else if(jugador.getPuntaje()==PC.getPuntaje()){
            this.txtChanton.setText("¡Ha habido un empate!");
            a.setContentText("¡Ha habido un empate!");
        }
        else{
            this.txtChanton.setText("¡"+PC.getNickname()+" HA GANADO!\n"+jugador.getNickname()+" has perdido :c");
            a.setContentText("¡"+PC.getNickname()+" HA GANADO!\n"+jugador.getNickname()+" has perdido :c");
        }
        a.show();
    }
    
      

}
