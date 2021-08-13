/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author guill
 */
public class PantallaJuegoController implements Initializable {

    @FXML
    private Button btnRetroceder;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void retro(ActionEvent event) {
    }
    

    
    
    public void closeWindows(){
        
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/pantallaInicial.fxml"));
            Parent root = loader.load();
            EjemploControlador controlador = loader.getController();
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

    
}
