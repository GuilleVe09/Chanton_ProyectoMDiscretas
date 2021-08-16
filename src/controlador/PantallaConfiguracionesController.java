/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
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
    PantallaConfiguracionesController controladorJuego;
    
    private int nRondas;
    private Character letra;
    private final char[] listaLetras = {'A','B','C','D','E'};
    private final List<String> campos = Arrays.asList("Nombre","Apellido","Ciudad/pais");
    private boolean continuar;    
    private List<String> camposSeleccionados;        
    private Jugador jugador;
    
        
    /**➛
     * →
     * ☞
     * Initializes the controller class. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
    
    public void exitApplication(ActionEvent event) {
        Platform.exit();
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
        if (this.txtletraEscogida.getText().trim().matches("[a-zA-Z]")){
            continuar = true;
            letra = this.txtletraEscogida.getText().toUpperCase().charAt(0);
            this.lblLetraEscogida.setText(Character.toString(letra));
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR, "Ingrese unicamente una letra");
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
            stage3.show();
            
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
        for (String s: campos){
            CheckBox ch = new CheckBox(s);
            this.vbCamposJuego.getChildren().add(ch);
            ch.setOnAction(e->{
                if(ch.isSelected())
                    agregarCampo(s);
                else
                    eliminarCampo(s);
            });
        }
    }
    
    private void agregarCampo(String str){
        this.camposSeleccionados.add(str);
        this.continuar = true;
    }
    
    private void eliminarCampo(String str){
        this.camposSeleccionados.remove(str);
        if (this.camposSeleccionados.isEmpty()){
            Alert a = new Alert(Alert.AlertType.ERROR, "Debe seleccionar al menos un campo");
            a.setHeaderText("Seleccione uno o mas campos para el juego");
            a.show();
            this.continuar = false;
        }
    }

}
