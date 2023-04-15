
package EntertainmentBox;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Audioplayer extends Application{
    //Button, Slider, Layout Media Object 
    Button play,left,right,Select, go_main; Slider volume, duration_sli;RadioButton mute_unmute;
    Media Smedia,Dmedia;MediaPlayer Smediaplayer, Dmediaplayer;MediaView mediaview;
    
    ChangeListener listener;
    double unit, currentdur;
    Status ScurrentStatus,DcurrentStatus;
    
    FileChooser filechooser;File file;
    String paths_infoA;
    Pane paincenter,painbottom;
    Pane bpane = new Pane();
    Scene scene;
    public Stage primarystage;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primarystage = primaryStage;
        //Button Select, play, left, right, slider
        //select 
        go_main = new Button("<- Go Main");
        go_main.setPrefSize(80, 25);
        go_main.setLayoutX(25);go_main.setLayoutY(19);
        
        Select = new Button("Select");
        Select.setLayoutX(18);Select.setLayoutY(19);
        Select.setPrefSize(50, 25);
        
        play = new Button("play");
        play.setLayoutX(391);play.setLayoutY(19);
        play.setPrefSize(50, 25);
        
        //left Button
        left = new Button("<");
        left.setLayoutX(323);left.setLayoutY(19);
        left.setPrefSize(50, 25);
        //right Button
        right = new Button(">");
        right.setLayoutX(459);right.setLayoutY(19);
        right.setPrefSize(50, 25);
        //volume Slider
        volume = new Slider(0,100,20);
        volume.setLayoutX(636);volume.setLayoutY(25);
        //duration Slider
        duration_sli = new Slider(0,100,0);
        duration_sli.setLayoutX(5.0);duration_sli.setLayoutY(583.0);
        duration_sli.setPrefWidth(784);
        //mute and unmute
        mute_unmute = new RadioButton("Mute");
        mute_unmute.setLayoutX(150);mute_unmute.setLayoutY(26.0);
        //centerLayout
        paincenter =  new Pane();
        paincenter.setPrefSize(787, 450);
        paincenter.setLayoutX(5.0);
        paincenter.setLayoutY(100.0);
        paincenter.setStyle("-fx-background-color: #28A0E5;");
        
        //bottomLayout
        painbottom = new Pane();
        painbottom.setPrefSize(793, 68);
        painbottom.setLayoutY(608.0);
        painbottom.setStyle("-fx-background-color: #25C566;");
        painbottom.getChildren().addAll(mute_unmute,left,play,right,volume, go_main);
        //fullLayout
        bpane.setPrefSize(690, 800);
        bpane.setStyle("-fx-background-color: #5E5E5E;");
        bpane.getChildren().addAll(Select,painbottom,duration_sli,paincenter);
        //Add EventHandler
        EventHandler<MouseEvent>handler = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(event.getSource()==Select) {
                    paths_infoA = "F:\\Others\\Java_Program\\EntertainmentBox\\src\\MainAudioPlayer\\animation.mp4";
                    Dmedia = new Media(new File(paths_infoA).toURI().toString());
                    Dmediaplayer = new javafx.scene.media.MediaPlayer(Dmedia);
                    
                    
                    mediaview = new MediaView(Dmediaplayer);
                    DoubleProperty mvw = mediaview.fitWidthProperty();
                    DoubleProperty mvh = mediaview.fitHeightProperty();
                    mvw.bind(Bindings.selectDouble(mediaview.sceneProperty(), "width"));
                    mvh.bind(Bindings.selectDouble(mediaview.sceneProperty(), "height"));
                    mediaview.setPreserveRatio(true);
                    paincenter.getChildren().add(mediaview);
                    //Selected Fil
                    filechooser  = new FileChooser();
                    filechooser.setTitle("Open File");
                    filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("mp3 file", "*"),new FileChooser.ExtensionFilter("mp4 file", "*.mp4"),new FileChooser.ExtensionFilter("WAV file", "*.wav"));// extFilter = new FileChooser.ExtensionFilter("mp3 file","*.mp4");
                    
                    file = filechooser.showOpenDialog(primaryStage);
                    paths_infoA = file.getAbsolutePath();
                    
                    Smedia = new Media(new File(paths_infoA).toURI().toString());
                    Smediaplayer = new javafx.scene.media.MediaPlayer(Smedia);
                    Smediaplayer.setAutoPlay(true);
                    Smediaplayer.setCycleCount(1);
                    Dmediaplayer.setAutoPlay(true);
                    ScurrentStatus = Smediaplayer.getStatus();
                    if(ScurrentStatus==MediaPlayer.Status.READY)
                    Smediaplayer.totalDurationProperty().addListener(listener);
                    Smediaplayer.currentTimeProperty().addListener(listener);
               }
                else if(event.getSource()==left){
                    
                }
                else if(event.getSource()==play){
                    Status currentStatus = Smediaplayer.getStatus();
                    if(currentStatus == MediaPlayer.Status.PLAYING)
                    {Smediaplayer.pause();Dmediaplayer.pause();play.setText("Pause");}
                    else if(currentStatus == MediaPlayer.Status.PAUSED)
                    {Smediaplayer.play();Dmediaplayer.play();play.setText("Play");}
                }
                else if(event.getSource()==right){
                        
                }
                else if(event.getSource()==volume){
                    int vol_qn = (int) volume.getValue();
                    Smediaplayer.setVolume((double) vol_qn/100);
                    
                }
                else if(event.getSource()==duration_sli){
                     currentdur = (double) duration_sli.getValue();
                     Smediaplayer.seek(Duration.seconds((unit*currentdur)/100));
                 
                }
                else if(event.getSource()==mute_unmute){
                    if(Smediaplayer.isMute()){
                        Smediaplayer.setMute(false);
                        mute_unmute.setText("Mute");
                    }
                    else{
                        Smediaplayer.setMute(true);
                        mute_unmute.setText("Unmute");
                    }
                }
                else if(event.getSource()==go_main){
                    Main main = new Main();
                    try {
                        main.start(primarystage);
                    } catch (Exception ex) {
                        Logger.getLogger(Audioplayer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        };
        go_main.setOnMouseClicked(handler);
        Select.setOnMouseClicked(handler);
        play.setOnMouseClicked(handler);
        left.setOnMouseClicked(handler);
        right.setOnMouseClicked(handler);
        volume.setOnMouseClicked(handler);
        volume.setOnMouseDragged(handler);
        mute_unmute.setOnMouseClicked(handler);
        
        //For Duration Slider
        listener = new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                unit = Smediaplayer.getTotalDuration().toSeconds();
                currentdur = Smediaplayer.getCurrentTime().toSeconds();
                duration_sli.setValue((currentdur/unit)*100);
                ScurrentStatus = Smediaplayer.getStatus();
                DcurrentStatus = Dmediaplayer.getStatus();
                if(unit<=currentdur) { Smediaplayer.stop();Smediaplayer.getOnReady();}
            }
        };
        
        scene = new Scene(bpane, 800, 680);
//        primarystage.getIcons().add(1, new Image("F:\\Others\\Java_Program\\EntertainmentBox\\src\\MainAudioPlayer\\icon.jpg"));
        primarystage.setScene(scene);
        primarystage.setTitle("AudioPlayer");
        primarystage.show();
        
    }
    public static void main(String[] args) {
        launch(args);
    }
}
