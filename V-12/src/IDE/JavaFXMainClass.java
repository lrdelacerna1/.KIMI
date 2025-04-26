package IDE;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;



public class JavaFXMainClass extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            
            //load the splash screen FXML 
            Parent splashRoot = FXMLLoader.load(getClass().getResource("SplashScreen.fxml")); 
            Scene splashScene = new Scene(splashRoot);
            
            //show the splash screen
            primaryStage.setScene(splashScene);
            primaryStage.show();
            
            //Transition 
            // process: pause the app -> show the next event -> continue the app   
            
            // pause the app
            PauseTransition delay = new PauseTransition(Duration.seconds(6));
            
            delay.setOnFinished(event -> {
                try{
                    Parent root = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));
                    Scene scene = new Scene(root);

                    primaryStage.setTitle("KIMI.IDE!");
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } catch(IOException ex){
                    Logger.getLogger(JavaFXMainClass.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });
            delay.play();
           
        } catch (IOException ex) {
            Logger.getLogger(JavaFXMainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
