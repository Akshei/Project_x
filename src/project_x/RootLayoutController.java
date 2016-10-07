/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_x;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;



public class RootLayoutController implements Initializable {
    
    private ObservableList<File> directoryList = FXCollections.observableArrayList();
    private double savedMili = 0;
    private ChangeListener listener;
    @FXML
    private Slider song_time_slider_visible;
    @FXML
    private Slider song_time_slider_invisible;
    @FXML
    private Slider volume_slider;
    @FXML
    private ToggleButton play_pause_button;
    @FXML
    private ToggleButton mute_button;
    @FXML
    private ToggleButton randomize_button;
    @FXML
    private TableColumn<Song, String> current_playlist_column;
    @FXML
    private TableView<Song> current_playlist_table;
    private ObservableList<Song> allSongData = FXCollections.observableArrayList();
    private ObservableList<Song> currentSongData = FXCollections.observableArrayList();
    private MediaPlayer mediaPlayer;
    private Media media;
    @FXML
    private Label actual_song_time;
    @FXML
    private Label max_song_time;
    int selectedIndex = 0;
    private double volume = 1.0;
    //int semaphore = 1;
    boolean b = false;
    
 
    @FXML
    private void handleSongTimeSliderMouseReleased(){
        if (volume_slider.getValue() > 0.0){
            volume = mediaPlayer.getVolume();
        }
        
    }
    
    @FXML
    private void handleSongTimeSliderDrag(){
      // mediaPlayer.currentTimeProperty().removeListener(listener);
       //listener = null;
        //savedMili = System.currentTimeMillis();
         //actualTimeSliderRemoveListener();
        
        if(mediaPlayer != null){
            // mediaPlayer.pause();
          mediaPlayer.seek(Duration.seconds(mediaPlayer.getMedia().getDuration().toSeconds() * song_time_slider_invisible.getValue() * 0.01)); 
         // mediaPlayer.seek(Duration.seconds(mediaPlayer.getMedia().getDuration().toSeconds() * song_time_slider_visible.getValue() * 0.01)); 
         // mediaPlayer.play();
        }
        //savedMili = System.currentTimeMillis();
        //actualTimeSliderAddListener();
          //actual_song_time_slider.
    }
    
    @FXML
    private void handleSongVolumeSliderDrag()
    {
        changeVolumeBySliderDrag();
    }
    
    @FXML
    private void handlePlayPauseButton(ActionEvent event)
    {
        //if (mediaPlayer.)
        if (play_pause_button.isSelected())
        {
            playSong();
        }
        else
        {
            pauseSong();
        }
    }
    
    @FXML
    private void handleNextSongButton(ActionEvent event)
    {
       playNextSong();
    }
    
    @FXML
    private void handlePreviousSongButton(ActionEvent event)
    {
        playPreviousSong();
    }
    
     @FXML
    private void handleMuteButton (ActionEvent event)
    {
          if (mute_button.isSelected())
        {
            muteMusic();
        }
        else
        {
           unmuteMusic();
        }
    }
    
    private void changeVolumeBySliderDrag()
    {
        if(mediaPlayer != null){
          mediaPlayer.setVolume(volume_slider.getValue() / 100.0);
        }
        if (volume_slider.getValue() > 0.0){
           // volume = mediaPlayer.getVolume();
            if (mute_button.isSelected() == true){
                 mute_button.setSelected(false);
            }
        }
            if (volume_slider.getValue() == 0.0 && mute_button.isSelected() == false){
                 mute_button.setSelected(true);
            }
    }
    
    private void muteMusic()
    {
        if (mediaPlayer != null){
            volume = mediaPlayer.getVolume();
            mediaPlayer.setVolume(0.0);
        }
        volume_slider.setValue(0.0);

    }
    
    private void unmuteMusic()
    {
        if (volume == 0.0) { volume = 1.0; }
        if (mediaPlayer != null){
            mediaPlayer.setVolume(volume);
        }
        volume_slider.setValue(volume * 100.0);
    }
    
    private void playNextSong()
    {
        ifCurrentPlaylistEmptySetAllSong();
        deletePlayingSong();
        chooseNextSongNumber();
        //setSelectedIndex(getSelectedIndex() + 1);
        playSelectedSong();
    }
    
     private void playPreviousSong()
    {
         ifCurrentPlaylistEmptySetAllSong();
        deletePlayingSong();
        choosePreviousSongNumber();
        //setSelectedIndex(getSelectedIndex() - 1);
        playSelectedSong();
    }
     
    private void chooseNextSongNumber()
    {
        if (randomize_button.isSelected() == true)
        {
            int kromka = 0;
            Random rand = new Random();
            do{
                kromka = rand.nextInt(currentSongData.size());
            }while (kromka == selectedIndex);
            setSelectedIndex(kromka);
        }
        else
        {
            setSelectedIndex(getSelectedIndex() + 1);
        }
    }
    
    private void choosePreviousSongNumber()
     {
        if (randomize_button.isSelected() == true)
        {
            int kromka = 0;
            Random rand = new Random();
            do{
                kromka = rand.nextInt(currentSongData.size());
            }while (kromka == selectedIndex);
            setSelectedIndex(kromka);
        }
        else
        {
            setSelectedIndex(getSelectedIndex() - 1);
        }
    }
    
    private void playSong()
    {
        if (mediaPlayer != null)
        {
            mediaPlayer.play();
        }
        else
        {
            ifCurrentPlaylistEmptySetAllSong();
           // handleButtonAction();
           readSelectedIndex();
           playSelectedSong();
        }
    }

    private void pauseSong()
    {
        if (mediaPlayer != null)
        {
            mediaPlayer.pause();
        }
    }
    
    @FXML
    private void handleButtonAction(/*ActionEvent event*/) {
        
        ifCurrentPlaylistEmptySetAllSong();
       
        deletePlayingSong();
       
        System.out.println("You clicked me!");
        playSelectedSong();
       
    }
    
    private void deletePlayingSong()
    {
         if (mediaPlayer != null){
            media = null;
            mediaPlayer.dispose();
           mediaPlayer = null;
        }
    }
    
    private void ifCurrentPlaylistEmptySetAllSong()
    {
         if (true == currentSongData.isEmpty() )
        {
            currentSongData = allSongData;
            current_playlist_table.setItems(currentSongData);
        }
    }
    
    private void readSelectedIndex()
    {
        selectedIndex = current_playlist_table.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {selectedIndex = 0;}
       
    }
    
    private void playSelectedSong()
    {
        media = new Media(new File(current_playlist_table.getItems().get(selectedIndex).getPath()).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
               playNextSong();
            }
        });
       actualTimeSliderAddListener();
       if (play_pause_button.isSelected() == false) {play_pause_button.setSelected(true);}
      
       
       // max_song_time.setText(mediaPlayer.getMedia().getDuration().toSeconds().toString()); 
       // media.durationProperty().toString()
      // actual_song_time_slider.
    }
                
    private void actualTimeSliderAddListener()
    {
       // max_song_time.setText(changeTimeInSecondsToMinutes(mediaPlayer.getMedia().getDuration().toSeconds())); 
         mediaPlayer.currentTimeProperty().addListener(listener = new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
               // if (System.currentTimeMillis() > savedMili + 50 )
               // if (mediaPlayer.currentTimeProperty().getValue().toMillis() > (savedMili + 100))
               //if (semaphore == 1)
                // {
                      song_time_slider_visible.setValue((mediaPlayer.currentTimeProperty().getValue().toSeconds() / mediaPlayer.getMedia().getDuration().toSeconds())* 100.0);
                      actual_song_time.setText(changeTimeInSecondsToMinutes(mediaPlayer.currentTimeProperty().getValue().toSeconds()));
                      max_song_time.setText(changeTimeInSecondsToMinutes(mediaPlayer.getMedia().getDuration().toSeconds()));
                      //actual_song_time.setText(String.valueOf(mediaPlayer.currentTimeProperty().getValue().toSeconds()));
                     // savedMili = mediaPlayer.currentTimeProperty().getValue().toMillis();
               //  }
               
            }
        });
    }
    
    private String changeTimeInSecondsToMinutes(double actual_seconds)
    {
        int seconds = (int) actual_seconds ;
        int min = 0;
        String czas;
        if (seconds > 60)
        {
            min = seconds/60 ;
            seconds = seconds%60;
        }
        if (seconds >= 10)
        {
             czas = Integer.toString(min) +":" + Integer.toString(seconds);
        }
        else
        {
            czas = Integer.toString(min) +":0" + Integer.toString(seconds);
        }
        
        return czas;
    }
    
    private void actualTimeSliderRemoveListener()
    {
        mediaPlayer.currentTimeProperty().removeListener(listener);
    }
    
    private void addMusicFiles(ActionEvent event){
        System.out.println("add music files!");
         FileChooser chooser = new FileChooser();
           chooser.setTitle("Open File");
           File file = chooser.showOpenDialog(new Stage());

           if (file != null){
               allSongData.add(new Song(file.getName(),file.getAbsolutePath()));
           }
    }
    private void openSave()
    {
        
    }
    
     private List<File> getNewTextFiles(File dir) {
         List<File> song_list = new ArrayList<File>();
         //File dir = new File("E:\\muzyka\\" /*EricTaylorRoyaltyFreeMusic02"*/);
         File[] file_list = dir.listFiles();
         for (File x : file_list)
         {
             if (x.isDirectory())
             {
                 for (File y : getNewTextFiles(x))
                 {
                     song_list.add(y);
                 }
             }
             else if (x.getName().toLowerCase().endsWith(".mp3"))
             {
                 song_list.add(x);
             }
         }
    /*return dir.listFiles(new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(".mp3");
        }
    });*/
    return song_list;
     }
    
     private void openWindowForSongLocalizations()
     {
//            FXMLLoader loader = new FXMLLoader();
//            Parent root =  loader.load(getClass().getResource("SongLocalization.fxml"));
//
//            // Show the scene containing the root layout.
//            Scene scene = new Scene(root);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.show(); 
         try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SongLocalization.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Lokalizacja piosnek");
        //dialogStage.initModality(Modality.WINDOW_MODAL);
        //dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Set the person into the controller.
        SongLocalizationController controller = loader.getController();
        controller.setRootLayoutController(this);
        controller.setWindowStage(dialogStage);
        controller.setDirectoryList(directoryList);
       // controller.setDialogStage(dialogStage);
        //controller.setPerson(person);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        //return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        //return false;
    }
     }
     
     private void initializePlaylistTable()
     {
        current_playlist_table.setItems(currentSongData);
        current_playlist_column.setCellValueFactory(cellData -> cellData.getValue().propertyName());
        current_playlist_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent click) {
                 if (click.getClickCount() == 2) {
                     deletePlayingSong();
                    readSelectedIndex();
                    playSelectedSong();
                 }
             }
         });
     }
     
     private void dodajNowaPiosenke (File plik){
         for (Song x : allSongData)
         {
             //System.out.println(plik.getAbsolutePath());
             if (plik.getName().equals(x.getName()) /*|| plik.getAbsolutePath() == x.getPath()*/)
             {
                 return;
             }
         }
          allSongData.add(new Song(plik.getName(),plik.getAbsolutePath()));
     }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //openSave();
       openWindowForSongLocalizations();
       for(File y: directoryList){
           for (File x: getNewTextFiles(new File(y.getAbsolutePath())))
            {
                dodajNowaPiosenke(x);
               // allSongData.add(new Song(x.getName(),x.getAbsolutePath()));
            }
       }
       initializePlaylistTable();
      
        volume_slider.setValue(100.0);
        
    }    
    
    private int getSelectedIndex()
    {
        return selectedIndex;
    }
    
    public ObservableList<File> getDirectoryList()
    {
        return directoryList;
    }
    
    private void setSelectedIndex(int selectedIndex_)
    {
        if (selectedIndex_ < 0) { selectedIndex_ = currentSongData.size() - 1; }
        selectedIndex = selectedIndex_ % currentSongData.size();
    }
    
    public void setDirectoriesList(ObservableList<File> directoryList_)
    {
        directoryList = directoryList_;
    }
    
    public void setTrue()
    {
        b = true;
    }
}
