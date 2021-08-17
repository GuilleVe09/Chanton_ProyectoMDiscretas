/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 *
 * @author guill
 */
public class Main extends Application{

     @Override
    public void start(Stage primaryStage) {
        
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/vistas/pantallaInicial.fxml"));
            Pane ventana = (Pane) loader.load();
            
            Scene scene = new Scene(ventana);
            scene.setRoot(ventana);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch(IOException e){
            System.out.println(e.getMessage()); 
       }
    }

    
    public static HashMap<Character,HashMap<String,List<String>>> crearMapa(){
        HashMap<Character,HashMap<String,List<String>>> mapaFinal = new HashMap<>();
        HashMap<String,List<String>> mapa = new HashMap<>();
        
        
        try{
            Scanner input = new Scanner(new File("src/recursos.archivos/a.txt"));
            
            while(input.hasNextLine()){
                String line = input.nextLine();
                String[] partes = line.split(",");
                List palabras = mapa.getOrDefault(partes[1], new LinkedList());
                palabras.add(partes[0]);
                mapa.put(partes[1], palabras);
            }
            input.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        mapaFinal.put('a', mapa);
        return mapaFinal;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
