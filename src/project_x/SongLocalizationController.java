package project_x;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class SongLocalizationController implements Initializable {
    
    RootLayoutController rootController;
    private ObservableList<File> directoryList = FXCollections.observableArrayList();
    @FXML
    ListView directoryListView;
    Stage window_stage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      directoryListView.setItems(directoryList);
    }    
    
    @FXML
    private void handleDodajLokalizacje(ActionEvent event){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("JavaFX Projects");
        File defaultDirectory = new File("c:");
        chooser.setInitialDirectory(defaultDirectory);
        Stage stage = new Stage();
        File selectedDirectory = chooser.showDialog(stage);
        directoryList.add(selectedDirectory);
         
    }
    
    @FXML
    private void handleAcceptButton(ActionEvent event)
    {
        //System.out.println(directoryList.isEmpty());
        rootController.setDirectoriesList(directoryList);
        rootController.setTrue();
        window_stage.close();
    }
        
    public void setWindowStage(Stage stage)
    {
        window_stage = stage;
    }
    
    public void setRootLayoutController(RootLayoutController controller)
    {
        rootController = controller;
    }
}
