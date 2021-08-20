/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

    public static Map<Character,HashMap<String,List<String>>> palabras;
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
            
            Stage stage = new Stage();
            FXMLLoader loader2 = new FXMLLoader();
            loader2.setLocation(Main.class.getResource("/vistas/maquinaEstado.fxml"));
            Pane ventana2 = (Pane) loader2.load();
            
            Scene scene2 = new Scene(ventana2);
            scene2.setRoot(ventana2);
            stage.setScene(scene2);
            stage.show();
            
            
        }catch(IOException e){
            System.out.println(e.getMessage()); 
       }
    }

    
    public static HashMap<Character,HashMap<String,List<String>>> crearMapa(){
        HashMap<Character,HashMap<String,List<String>>> mapaFinal = new HashMap<>();           
        List<String> archivos = new ArrayList<>();
        File folder = new File("src/recursos/archivos");
        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) 
                archivos.add(file.getName());                
        }
        try{
            for(String arch: archivos){
                HashMap<String,List<String>> mapa = new HashMap<>();    
                Scanner input = new Scanner(new File("src/recursos/archivos/"+arch));            
                while(input.hasNextLine()){
                    String line = input.nextLine();
                    String[] partes = line.split(",");
                    List palabras = mapa.getOrDefault(partes[1], new LinkedList());
                    palabras.add(partes[0]);
                    mapa.put(partes[1], palabras);
                }
                mapaFinal.put(Character.toUpperCase(arch.toUpperCase().charAt(0)), mapa);
                input.close();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return mapaFinal;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        palabras = crearMapa();
        System.out.println(palabras);
        launch(args);
    }
    
}
