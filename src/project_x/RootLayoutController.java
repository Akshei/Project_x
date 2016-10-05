/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_x;

import java.io.File;
import java.io.FilenameFilter;
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
import javafx.fxml.Initializable;
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
    
    private double savedMili = 0;
    @FXML
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
    @FXML
    private ObservableList<Song> allSongData = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Song> currentSongData = FXCollections.observableArrayList();
    @FXML
    private MediaPlayer mediaPlayer;
    @FXML
    private Media media;
    @FXML
    private Label actual_song_time;
    @FXML
    private Label max_song_time;
    int selectedIndex;
    private double volume = 1.0;
    //int semaphore = 1;
    
 
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
            stopSong();
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
    
    @FXML
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
    
    @FXML
    private void muteMusic()
    {
        if (mediaPlayer != null){
            volume = mediaPlayer.getVolume();
            mediaPlayer.setVolume(0.0);
        }
        volume_slider.setValue(0.0);

    }
    
    @FXML
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
        deletePlayingSong();
        chooseNextSongNumber();
        //setSelectedIndex(getSelectedIndex() + 1);
        playSelectedSong();
    }
    
     private void playPreviousSong()
    {
        deletePlayingSong();
        choosePreviousSongNumber();
        //setSelectedIndex(getSelectedIndex() - 1);
        playSelectedSong();
    }
     
    @FXML 
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
    
    @FXML 
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
    
    @FXML
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

    @FXML
    private void stopSong()
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
    
    @FXML
    private void deletePlayingSong()
    {
         if (mediaPlayer != null){
            media = null;
            mediaPlayer.dispose();
           mediaPlayer = null;
        }
    }
    
    @FXML
    private void ifCurrentPlaylistEmptySetAllSong()
    {
         if (true == currentSongData.isEmpty() )
        {
            currentSongData = allSongData;
            current_playlist_table.setItems(currentSongData);
        }
    }
    
    @FXML
    private void readSelectedIndex()
    {
        selectedIndex = current_playlist_table.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {selectedIndex = 0;}
       
    }
    
    @FXML
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
       // max_song_time.setText(mediaPlayer.getMedia().getDuration().toSeconds().toString()); 
       // media.durationProperty().toString()
      // actual_song_time_slider.
    }
                
    @FXML
    private void actualTimeSliderAddListener()
    {
         mediaPlayer.currentTimeProperty().addListener(listener = new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
               // if (System.currentTimeMillis() > savedMili + 50 )
               // if (mediaPlayer.currentTimeProperty().getValue().toMillis() > (savedMili + 100))
               //if (semaphore == 1)
                // {
                      song_time_slider_visible.setValue((mediaPlayer.currentTimeProperty().getValue().toSeconds() / mediaPlayer.getMedia().getDuration().toSeconds())* 100.0);
                     // savedMili = mediaPlayer.currentTimeProperty().getValue().toMillis();
               //  }
               
            }
        });
    }
    
    @FXML
    private void actualTimeSliderRemoveListener()
    {
        mediaPlayer.currentTimeProperty().removeListener(listener);
    }
    
    @FXML
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //openSave();
       
        // allSongData.add(new Song("Braid - downstream", "F:\\Rzeczy karolka\\muzykama\\braid ost\\Braid OST - 02 - Downstream.mp3"));
       // allSongData.add(new Song("Lindsey - electric", "F:\\Rzeczy karolka\\muzykama\\Lindsey Stirling\\9. Lindsey Stirling - Electric Daisy Violin.mp3"));
         //allSongData.add(new Song("forest", "F:\\Rzeczy karolka\\muzykama\\rozne\\forest.mp3"));
        // allSongData.add(new Song("frozen heart", "F:\\Rzeczy karolka\\muzykama\\frozen - ost\\CD1\\01. Frozen Heart.mp3"));
        // allSongData.add(new Song("Funeral of provincional vampire", "F:\\Rzeczy karolka\\muzykama\\jelonek\\jelonek\\06. Funeral of Provincial Vampire.BoT.mp3"));
        
         allSongData.add(new Song("kalimba", "C:\\Users\\Public\\Music\\Sample Music\\Kalimba.mp3"));
        allSongData.add(new Song("maid - flaxen hair", "C:\\Users\\Public\\Music\\Sample Music\\Maid with the Flaxen Hair.mp3"));
        
       for (File x: getNewTextFiles(new File("E:")))
       {
            allSongData.add(new Song(x.getName(),x.getAbsolutePath()));
       }

        //currentSongData = allSongData ;
        current_playlist_table.setItems(currentSongData);
       current_playlist_column.setCellValueFactory(cellData -> cellData.getValue().propertyName());
        volume_slider.setValue(100.0);
        
    }    
    
    int getSelectedIndex()
    {
        return selectedIndex;
    }
    
    void setSelectedIndex(int selectedIndex_)
    {
        if (selectedIndex_ < 0) { selectedIndex_ = currentSongData.size() - 1; }
        selectedIndex = selectedIndex_ % currentSongData.size();
    }
    
}
