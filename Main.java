
package EntertainmentBox;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class Main extends Application{
    Button Imageviewer, Audioplayer, videoplayer, New_game;
    Pane pane;
    Scene scene;
    public Stage primarystage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primarystage = primaryStage;
        
        Imageviewer = new Button("Imageviewer");
        Imageviewer.setPrefSize(120, 25);
        Imageviewer.setLayoutX(243);Imageviewer.setLayoutY(206);
        
        Audioplayer = new Button("Audioplayer");
        Audioplayer.setPrefSize(120, 25);
        Audioplayer.setLayoutX(243);Audioplayer.setLayoutY(266);
        
        videoplayer = new Button("videoplayer");
        videoplayer.setPrefSize(120, 25);
        videoplayer.setLayoutX(243);videoplayer.setLayoutY(326);
        
        New_game = new Button("New Game");
        New_game.setPrefSize(120, 25);
        New_game.setLayoutX(243);New_game.setLayoutY(386);
        
        EventHandler<MouseEvent>handler  = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(event.getSource()==Imageviewer){
                    Imageviewer obj_im = new Imageviewer();
                
                    try {
                        obj_im.start(primarystage);
                    } catch (Exception ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                }
                else if(event.getSource()==Audioplayer){
                    Audioplayer obj_au = new Audioplayer();
                    try {
                        obj_au.start(primarystage);
                    } catch (Exception ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(event.getSource()==videoplayer){
                    Videoplayer obj_vi = new Videoplayer();
                    try {
                        obj_vi.start(primarystage);
                    } catch (Exception ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(event.getSource()==New_game)
                {
                    NewGame  newgame= new NewGame();
                    try {
                        newgame.start(primarystage);
                    } catch (Exception ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        };
        
        Imageviewer.setOnMouseClicked(handler);
        Audioplayer.setOnMouseClicked(handler);
        videoplayer.setOnMouseClicked(handler);
        New_game.setOnMouseClicked(handler);
        
        
        pane = new Pane();
        pane.setPrefSize(600, 600);
        pane.setStyle("-fx-background-color: #5E5E5E;");
        pane.getChildren().addAll(Imageviewer,Audioplayer,videoplayer, New_game);
//        pane.fitWidthProperty().bind(scene.widthProperty());
//        pane.fitHeightProperty().bind(scene.heightProperty());
        scene = new Scene(pane,600,600);
        primarystage.setScene(scene);
        primarystage.show();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static void main(String[] args) {
        launch(args);
    }
}
