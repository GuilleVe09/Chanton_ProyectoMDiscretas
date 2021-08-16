/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
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
    
    private String letraEscogida;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    public void recibeParametros(PantallaConfiguracionesController controlador, String rondas, String letra, List<String> campos, Jugador jugador){
        letraEscogida = letra;
        txtTotalRondas.setText(rondas);
        txtRondas.setText("1");
        txtJugador.setText(jugador.getNickname());
        controladorJuego = controlador;
    }
}
