/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;

/**
 * FXML Controller class
 *
 * @author John
 */
public class MaquinaEstadoController implements Initializable {

    private BorderPane borderPane;
    
    private PantallaChantonController pc;
    @FXML
    private Line lineIn;
    @FXML
    private Circle circle1;
    @FXML
    private QuadCurve line12;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        pc = new PantallaChantonController();
        //setBorderPane(pc.getRoot());
        
    }    
    
    public void setBorderPane(BorderPane bp){
         borderPane = bp;
    }
    
}
