/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelo.Jugador;

/**
 * FXML Controller class
 *
 * @author guill
 */
public class PantallaChantonController implements Initializable {

    @FXML
    private Label lblRondas;
    @FXML
    private Label lblTotalRondas;
    @FXML
    private TextField txtPuntosJugador;
    @FXML
    private TextField txtPuntosComputadora;
    @FXML
    private Label lblLetraEscogida;    
    @FXML
    private Button btnChanton;
    @FXML
    private Label lblTiempo;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtNombreJ;
    @FXML
    private TextField txtApellidoJ;
    @FXML
    private TextField txtPaisJ;
    @FXML
    private TextField txtAnimalJ;
    @FXML
    private TextField txtNombreC;
    @FXML
    private TextField txtApellidoC;
    @FXML
    private TextField txtPaisC;
    @FXML
    private TextField txtAnimalC;

    PantallaConfiguracionesController controladorJuego;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    public void recibeParametros(PantallaConfiguracionesController controlador, String rondas, String letra, List<String> campos, Jugador jugador){
        lblLetraEscogida.setText(letra);
        lblTotalRondas.setText(rondas);
        controladorJuego = controlador;
    }

    @FXML
    private void gritoChanton(ActionEvent event) {
    }

    @FXML
    private void regresar(ActionEvent event) {
    }
    
    //metodo de validacion de nombres del jugador
    /*public static int validarNombre(Character letra, String nombre){
        
    }*/

}
