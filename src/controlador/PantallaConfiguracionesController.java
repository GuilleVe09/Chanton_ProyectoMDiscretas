/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author guill
 */
public class PantallaConfiguracionesController implements Initializable {

    private Button btnRetroceder;
    @FXML
    private TextField txtnumRondas;
    @FXML
    private Button btnIniciarJuego;
    
    private int nRondas;
    private Character letra;
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
    
    //Instancia del controlador
    PantallaConfiguracionesController controladorJuego;
    @FXML
    private Button btnEscogerLetraAzar;
    
    private char[] listaLetras = {'A','B','C','D','E'};
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField txtnickname;
    @FXML
    private VBox vbCamposJuego;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        controladorJuego = this;
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
            
            
            Stage myStage = (Stage) this.btnRetroceder.getScene().getWindow();
            myStage.close();
            
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
          
    }
    
    
    public void exitApplication(ActionEvent event) {
        Platform.exit();
    }

    private void guardarRondas(MouseEvent event) {
        try{
            nRondas = Integer.parseInt(txtnumRondas.getText());
            lblNumeroRondas.setText(String.valueOf(nRondas));
        }catch(NumberFormatException e){
            Alert a = new Alert(Alert.AlertType.ERROR, "Inserte unicamente numeros");
            a.show();
        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR, "No se ha podido guardar el numero de rondas");
            a.show();
        }
        
    }

    // Poner validacion de que no se inserte un numero
    private void guardarLetraE(MouseEvent event) {
        String letraCadena;
        try{
            letraCadena = txtletraEscogida.getText();
            letra = letraCadena.charAt(0);
            lblLetraEscogida.setText(String.valueOf(letra));
            
        }catch(IllegalArgumentException e){
            Alert a = new Alert(Alert.AlertType.ERROR, "Ingrese unicamente una letra");
            a.show();
        }catch(Exception e2){
            Alert a = new Alert(Alert.AlertType.ERROR, "No se ha podido guardar el numero de rondas");
            a.show();
        }
    }

    //Metodo para pasar la informacion del total de rondas y la letra escogida 
    @FXML
    private void iniciarJuego(MouseEvent event) throws IOException {
        
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/pantallaChanton.fxml"));
            Parent root = loader.load();
            PantallaChantonController controladorChanton = loader.getController();

            controladorChanton.recibeParametros(controladorJuego, String.valueOf(nRondas), Character.toString(letra));
            Scene scene = new Scene(root);
            Stage stage3 = new Stage();
            stage3.setScene(scene);
            stage3.show();
            
            Stage myStage = (Stage) this.btnIniciarJuego.getScene().getWindow();
            myStage.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        
    }

    @FXML
    private void escogerLetraAlAzar(ActionEvent event) {
        int indiceAleatorio = numeroAleatorioEnRango(0, listaLetras.length - 1);
        Character letraAleatoria = listaLetras[indiceAleatorio];
        txtletraEscogida.setText(Character.toString(letraAleatoria));
    }
    
    public static int numeroAleatorioEnRango(int minimo, int maximo){
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }

    
}
