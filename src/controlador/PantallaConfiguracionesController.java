/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import interfaz.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import modelo.Jugador;

/**
 * FXML Controller class
 *
 * @author guill
 */
public class PantallaConfiguracionesController implements Initializable {
    @FXML
    private Button btnIniciarJuego;        
    @FXML
    private Label lblNumeroRondas;
    @FXML
    private Label lblLetraEscogida;
    @FXML
    private Label lblRonda;
    @FXML
    private TextField txtletraEscogida;
    @FXML
    private Label lblLetraEscogidaEs;
    @FXML
    private Button btnEscogerLetraAzar;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField txtnickname;
    @FXML
    private VBox vbCamposJuego;    
    @FXML
    private Label _lblNickname;
    @FXML
    private Label _lblRondas;
    @FXML
    private Label _lblLetra;
    @FXML
    private Label _lblCampos;
    @FXML
    private Spinner<Integer> spnNumRondas;
    
    //Instancia del controlador
    private PantallaConfiguracionesController controladorJuego;
    public static MaquinaEstadoController maquina;
    private int nRondas;
    private Character letra;
    private final char[] listaLetras = {'A','B'};
    private final List<String> campos = Arrays.asList("Nombre","Apellido","Ciudad/pais","Fruta","Animal");
    private boolean continuar;    
    private List<String> camposSeleccionados;        
    private Jugador jugador;
    private int primeraVez;
    
    /**➛
     * →
     * ☞
     * Initializes the controller class. 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        primeraVez = 0;
        controladorJuego = this;
        continuar = false;
        camposSeleccionados = new ArrayList<>();
        this.spnNumRondas.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1));
        mostrarCamposDisponibles();
    }    

    
    public void closeWindows(){        
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/pantallaInicial.fxml"));
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
        
    private void guardarRondas() {
        try{
            nRondas = this.spnNumRondas.getValue();
            lblNumeroRondas.setText(String.valueOf(nRondas));
        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR, "No se ha podido guardar el numero de rondas");
            a.show();
            this.continuar = false;
        }   
    }

    // Poner validacion de que no se inserte un numero
    private void guardarLetraE(){
        if (this.txtletraEscogida.getText().trim().matches("[a-bA-B]")){
            continuar = true;
            letra = this.txtletraEscogida.getText().toUpperCase().charAt(0);
            this.lblLetraEscogida.setText(Character.toString(letra));
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR, "Ingrese unicamente una letra [A-"+listaLetras[listaLetras.length-1]+"]");
            a.show();
            this.lblLetraEscogida.setText("-");
            this.continuar = false;
        }
    }
    
    //Metodo para pasar la informacion del total de rondas y la letra escogida 
    @FXML
    private void iniciarJuego(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/pantallaChanton.fxml"));
            Parent root = loader.load();
            PantallaChantonController controladorChanton = loader.getController();
            controladorChanton.recibeParametros(controladorJuego, String.valueOf(nRondas), Character.toString(letra),this.camposSeleccionados,this.jugador);
            Scene scene = new Scene(root);
            Stage stage3 = new Stage();
            stage3.setScene(scene);            
            stage3.setOnCloseRequest(e->{
                controladorChanton.regresar(new ActionEvent());
                maquina.cerrarVentana();
            });
            stage3.show();
            scene.getWindow().setX(0);
            Stage myStage = (Stage) this.btnIniciarJuego.getScene().getWindow();
            myStage.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }      
    }

    @FXML
    private void escogerLetraAlAzar() {
        int indiceAleatorio = numeroAleatorioEnRango(0, listaLetras.length - 1);
        Character letraAleatoria = listaLetras[indiceAleatorio];
        txtletraEscogida.setText(Character.toString(letraAleatoria));
    }
    
    public static int numeroAleatorioEnRango(int minimo, int maximo){
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }
    
    @FXML
    public void guardarConfiguraciones(){
        if (validarTextField()){
            this.continuar = true;
            guardarJugador();
            guardarRondas();
            guardarLetraE();
            this.btnIniciarJuego.setDisable(!continuar);
            if(primeraVez == 0){
                iniciarMaquina();
                primeraVez++;
            }                
            else
                maquina.recibirParametros(letra);
        }            
    }
    //Valida que los textField no esten vacios 
    public boolean validarTextField(){
        if(this.txtnickname.getText().isEmpty() || this.txtletraEscogida.getText().isEmpty() || this.camposSeleccionados.isEmpty()){
            Alert a = new Alert(Alert.AlertType.ERROR, "Todos los campos son obligatorios");
            a.setHeaderText("INGRESE TODOS LOS DATOS");
            a.show();
            return false;
        }
        return true;
    }
    
    public void guardarJugador(){
        this.jugador = new Jugador(this.txtnickname.getText().trim());
    }    
    
    private void mostrarCamposDisponibles(){
        campos.subList(0, 3).stream().map((s) -> {
            CheckBox ch = new CheckBox(s);
            this.vbCamposJuego.getChildren().add(ch);
            ch.setOnAction(e->{
                if(ch.isSelected())
                    agregarCampo(s);
                else
                    eliminarCampo(s);
            });
            return ch;
        }).forEachOrdered((ch) -> {
            ch.fire();
        });
    }
    
    private void agregarCampo(String str){
        this.camposSeleccionados.add(str);
        this.btnIniciarJuego.setDisable(true);            
        this.continuar = true;
    }
    
    private void eliminarCampo(String str){
        this.camposSeleccionados.remove(str);
        this.btnIniciarJuego.setDisable(true);      
        if (this.camposSeleccionados.isEmpty()){
            this.btnIniciarJuego.setDisable(true);            
            Alert a = new Alert(Alert.AlertType.ERROR, "Debe seleccionar al menos un campo");
            a.setHeaderText("Seleccione uno o mas campos para el juego");
            a.show();
            this.continuar = false;
        }
    }

    private void iniciarMaquina(){
            FXMLLoader loaderMaquina = new FXMLLoader();       
            loaderMaquina.setLocation(Main.class.getResource("/vistas/maquinaEstado.fxml"));
            Pane ventanaMaquina;
        try {
            ventanaMaquina = (Pane) loaderMaquina.load();
            Stage stageMaquina = new Stage();                        
            maquina = loaderMaquina.getController();
            maquina.recibirParametros(letra);
            Scene sceneMaquina = new Scene(ventanaMaquina);            
            sceneMaquina.setRoot(ventanaMaquina);
            stageMaquina.setScene(sceneMaquina);
            
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            
            //set Stage boundaries to the lower right corner of the visible bounds of the main screen
            stageMaquina.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 750);
            stageMaquina.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 550);
            stageMaquina.setWidth(740);
            stageMaquina.setHeight(540);
            stageMaquina.show();        
        } catch (IOException ex) {
            Logger.getLogger(PantallaConfiguracionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }      
}
