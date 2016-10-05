/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_x;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;

/**
 *
 * @author Maciek
 */
public class Project_X extends Application {
    
      private Stage stage;
      RootLayoutController rootLayoutController;
      FXMLLoader loader;
     
    @Override
    public void start(Stage stage) throws Exception {
        
          loader = new FXMLLoader();
            Parent root =  loader.load(getClass().getResource("RootLayout.fxml"));

            // Show the scene containing the root layout.
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show(); 
        
        /*Parent root = FXMLLoader.load(getClass().getResource("RootLayout.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show(); */
        
        
       // rootLayoutController = loader.getController();
       // rootLayoutController.setMainApp(this);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                 System.out.println("Stage is closing");
            }
        }); 
    }
    
    public Project_X(){};
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
    }
    
}
