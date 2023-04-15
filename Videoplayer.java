
package EntertainmentBox;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Videoplayer extends Application
{ 
        String paths_info;
        FileChooser filechooser;File file;
        Media media;MediaPlayer mediaplayer;
        MediaView mediaView;
        public Status currentStatus;
        public float currentRate = 1;
        public int vol_qn = 25;
        public double unit, currentdur;
        
        
        
        BorderPane pane = new BorderPane();
        
        Scene scene = new Scene(pane, 800, 700);
        //button
        Button play_pause, Mute, go_main;
        Slider Svolume, duration_sli;//Slider volume duration
        Label Lvol;
        HBox Hplay_pause, Hvolume, HMute, Hbottom;//layout
        VBox container;
       
        //ChangeListener
        ChangeListener listener;
        
        
        MenuBar menubar = new MenuBar();
        Menu FileMenu = new Menu("Media");
        MenuItem filemenu1 = new MenuItem("Open File");
        MenuItem filemenu2 = new MenuItem("Quit");
        Menu playbacMenu = new Menu("Playback");
        Menu playbacMenu1 = new Menu("Speed");
        MenuItem playbacMenu1_1 = new MenuItem("Faster");
        MenuItem playbacMenu1_2 = new MenuItem("Normal");
        MenuItem playbacMenu1_3  = new MenuItem("Slower");
        MenuItem playbacMenu2 = new MenuItem("Jump Forward");
        MenuItem playbacMenu3 = new MenuItem("Jump Backward");
        MenuItem playbacMenu4 = new MenuItem("Pause");
        MenuItem playbacMenu5 = new MenuItem("Play");
        Menu AudioMenu = new Menu("Audio");
        MenuItem AudioMenu1 = new MenuItem("Subtitle");
        MenuItem AudioMenu2 = new MenuItem("Mute");
        
        public Stage primarystage;
        
    @Override
    public void start(Stage primaryStage) throws Exception {
        primarystage = primaryStage;
        
        pane.setPrefHeight(40);

         //play_pause  button
        go_main = new Button("<- Go Main");
        go_main.setPrefSize(80, 30);
        go_main.setLayoutX(25);go_main.setLayoutY(25);
        
        play_pause = new Button("||");
        play_pause.setPrefSize(40,30);
        //Mute-UnMute button
        Mute = new Button("Mute");
        Mute.setPrefSize(100, 30);
        //Volume slider
        Svolume = new Slider(1,100,20);
        Svolume.setPrefSize(150, 30);
        //Duration slider
        duration_sli = new Slider(1,100,1);
        duration_sli.setPrefWidth(780);
        duration_sli.setPrefHeight(60);
        //vol label
        Lvol = new Label(vol_qn+"%");
        Lvol.setPrefSize(30,30);
        
        //MenuBar ActionListener
        filemenu1.setOnAction(event -> ActionSwitch(event, pane, primaryStage));
        filemenu2.setOnAction(event -> ActionSwitch(event, pane, primaryStage));
        playbacMenu1_1.setOnAction(event -> ActionSwitch(event, pane, primaryStage));
        playbacMenu1_2.setOnAction(event -> ActionSwitch(event, pane, primaryStage));
        playbacMenu1_3.setOnAction(event -> ActionSwitch(event, pane, primaryStage));
        playbacMenu2.setOnAction(event -> ActionSwitch(event, pane, primaryStage));
        playbacMenu3.setOnAction(event -> ActionSwitch(event, pane, primaryStage));
        playbacMenu4.setOnAction(event -> ActionSwitch(event, pane, primaryStage));
        playbacMenu5.setOnAction(event -> ActionSwitch(event, pane, primaryStage));
 
        AudioMenu1.setOnAction(event -> ActionSwitch(event, pane, primaryStage));
        AudioMenu2.setOnAction(event -> ActionSwitch(event, pane, primaryStage));
       
        
        //Layout
        Hplay_pause = new HBox(play_pause);
        Hvolume = new HBox(Svolume,Lvol);
        HMute = new HBox(go_main,Mute);
        //alignment
        Hplay_pause.setAlignment(Pos.CENTER);
        HMute.setAlignment(Pos.CENTER_LEFT);
        Hvolume.setAlignment(Pos.CENTER_RIGHT);
        //growing
        HBox.setHgrow(Hplay_pause, Priority.ALWAYS);
        HBox.setHgrow(HMute, Priority.ALWAYS);
        HBox.setHgrow(Hvolume, Priority.ALWAYS);
        
        
        
        EventHandler<MouseEvent>handler = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                 if(event.getSource()==play_pause){
                     currentStatus = mediaplayer.getStatus();
                 if(currentStatus==Status.PLAYING){
                     mediaplayer.pause(); 
                     play_pause.setText(" > ");
                 }
                 else if(currentStatus==Status.PAUSED || currentStatus==Status.STOPPED){
                     mediaplayer.play();
                     play_pause.setText(" || ");
                 }
                 }
                 else if(event.getSource() == Mute){
                    if(mediaplayer.isMute()){
                        mediaplayer.setMute(false);
                        Mute.setText("Mute");
                    }
                    else{
                        mediaplayer.setMute(true);
                        Mute.setText("Unmute");
                    }
                }
                  else if(event.getSource() == Svolume){
                     vol_qn = (int) Svolume.getValue();
                     mediaplayer.setVolume((double) vol_qn/100);
                     Lvol.setText(vol_qn+"%");
                }
                else if(event.getSource()==duration_sli){
                     currentdur = (double) duration_sli.getValue();
                     mediaplayer.seek(Duration.seconds((unit*currentdur)/100));
                 }
                 else if(event.getSource()==go_main){
                    Main main = new Main();
                     try {
                         main.start(primarystage);
                     } catch (Exception ex) {
                         Logger.getLogger(Videoplayer.class.getName()).log(Level.SEVERE, null, ex);
                     }
                }
                  
            }
           
        };
        //End of video
        
        go_main.setOnMouseClicked(handler);
        play_pause.setOnMouseClicked(handler);
        Mute.setOnMouseClicked(handler);
        Svolume.setOnMouseClicked(handler);
        Svolume.setOnMouseDragged(handler);
        duration_sli.setOnMouseClicked(handler);
        duration_sli.setOnMouseDragged(handler);
        
        
        //All HBox
        Hbottom = new HBox(HMute,Hplay_pause,Hvolume);
        Hbottom.setPadding(new Insets(10));
        container = new VBox(duration_sli,Hbottom);
        
        
        
        //ChangeListener
        listener = new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                unit = mediaplayer.getTotalDuration().toSeconds();
                currentdur = mediaplayer.getCurrentTime().toSeconds();
                duration_sli.setValue((currentdur/unit)*100);
                currentStatus = mediaplayer.getStatus();
                if(unit<=currentdur) 
                {mediaplayer.stop();duration_sli.setValue(1);}
                
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        pane.setTop(menubar);
        pane.setBottom(container);
        
        playbacMenu1.getItems().addAll(playbacMenu1_1, playbacMenu1_2, playbacMenu1_3);
        playbacMenu.getItems().addAll(playbacMenu1, playbacMenu2, playbacMenu3, playbacMenu4, playbacMenu5);
        AudioMenu.getItems().addAll(AudioMenu1, AudioMenu2);
        FileMenu.getItems().addAll(filemenu1, filemenu2);
        menubar.getMenus().addAll(FileMenu, playbacMenu, AudioMenu);
        
        primarystage.setScene(scene);
        primarystage.setTitle("VideoPlayer");
        primarystage.show();
    }
    public static void main(String[] args) 
    {
        launch(args);
    }
    private void ActionSwitch(ActionEvent event, BorderPane pane, Stage primaryStage) {
        if(event.getSource() == filemenu1){
        filechooser  = new FileChooser();
        filechooser.setTitle("Open File");
        filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("mp3 file", "*"),new FileChooser.ExtensionFilter("mp4 file", "*.mp4"),new FileChooser.ExtensionFilter("MKV file", "*.mkv"));// extFilter = new FileChooser.ExtensionFilter("mp3 file","*.mp4");
        
        file = filechooser.showOpenDialog(primaryStage);
        paths_info = file.getAbsolutePath();
       
        media = new Media(new File(paths_info).toURI().toString());
        mediaplayer = new javafx.scene.media.MediaPlayer(media);
        mediaView = new MediaView(mediaplayer);
        mediaplayer.setAutoPlay(true);
        mediaplayer.setCycleCount(1);
        mediaplayer.setVolume(0.25);
        
        DoubleProperty mvw = mediaView.fitWidthProperty();
        DoubleProperty mvh = mediaView.fitHeightProperty();
        mvw.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
        mediaView.setPreserveRatio(true);
         
        currentStatus = mediaplayer.getStatus();
        if(currentStatus == javafx.scene.media.MediaPlayer.Status.READY)
            mediaplayer.totalDurationProperty().addListener(listener);
            mediaplayer.currentTimeProperty().addListener(listener);
        pane.setCenter(mediaView);
        } 
        else if(event.getSource()==filemenu2)
        {
            currentStatus = mediaplayer.getStatus();
            if(currentStatus == Status.PLAYING)
                mediaplayer.stop();
                duration_sli.setValue(1.0);
                play_pause.setText(">");
        }
        else if(event.getSource()==playbacMenu4 )
        {
            currentStatus = mediaplayer.getStatus();
            if(currentStatus == Status.PLAYING)
                mediaplayer.pause();
        }
        else if(event.getSource()==playbacMenu1_1 )
        {
            currentRate = (float) 1.5;
            mediaplayer.setRate(currentRate);
        }
        else if(event.getSource()==playbacMenu1_2 )
        {
            currentRate = 1;
            mediaplayer.setRate(currentRate);
        }
        else if(event.getSource()==playbacMenu1_3 )
        {
            currentRate = (float) 0.5;
            mediaplayer.setRate(currentRate);
        }
       else if(event.getSource() == AudioMenu1){
           //Subtitle 
           filechooser.getExtensionFilters().addAll(
                   new FileChooser.ExtensionFilter("mp3 file", "*"),
                   new FileChooser.ExtensionFilter("mp4 file", "*.mp4"),
                   new FileChooser.ExtensionFilter("* file", "*.*"));// extFilter = new FileChooser.ExtensionFilter("mp3 file","*.mp4");
                   file = filechooser.showOpenDialog(primaryStage);
                   paths_info = file.getAbsolutePath();
                   
       }
       else if(event.getSource() == AudioMenu2){
           if(mediaplayer.isMute()){
               mediaplayer.setMute(false);
               AudioMenu2.setText("Mute");
           }
           else{
               mediaplayer.setMute(true);
               AudioMenu2.setText("Unmute");
           }
            
       }
    }

}