package EntertainmentBox;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Imageviewer extends Application {
    public Button Select_btn, Rotate_btn, go_main, Zoom_in, Zoom_out;
    public Image image;ImageView imageview;FileChooser filechooser;
    public Scene scene_img;
    public Pane pain, container;Pane image_cont;Rectangle2D viewpoint;
    public Stage primarystage;public double value1, value2=0.05, setX_in, setY_in, setY_out, setX_out;public Scale newscale_in, newscale_out;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primarystage = primaryStage;
        filechooser  = new FileChooser();
        newscale_in = new Scale();newscale_out = new Scale();
        
        // Button Operation
        go_main = new Button("<- Go Main");
        go_main.setPrefSize(80, 25);
        go_main.setLayoutX(25);go_main.setLayoutY(23);
        
        Zoom_in = new Button("Zoom_in");
        Zoom_in.setLayoutX(400);Zoom_in.setLayoutY(25);
        Zoom_in.setPrefSize(70, 25);
        
        Zoom_out = new Button("Zoom_out");
        Zoom_out.setLayoutX(480);Zoom_out.setLayoutY(25);
        Zoom_out.setPrefSize(70, 25);
        
        Select_btn = new Button("Select");
        Select_btn.setPrefSize(60, 25);
        Select_btn.setLayoutX(241);Select_btn.setLayoutY(25);
        
        Rotate_btn = new Button("Rotate");
        Rotate_btn.setPrefSize(60, 25);
        Rotate_btn.setLayoutX(305);Rotate_btn.setLayoutY(25);
        
        
        image_cont = new Pane();
        image_cont.setPrefSize(560, 560);
        image_cont.setStyle("-fx-background-color: #40B4D6;");
        image_cont.setLayoutX(20);image_cont.setLayoutY(20);
        
        container = new Pane();        pain = new Pane();
        
        //ActionListener
        EventHandler<MouseEvent>handler = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(event.getSource()==Select_btn){
               
                    FileChooser filechooser = new FileChooser();
                  filechooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
                 
                File file = filechooser.showOpenDialog(primarystage);
                String path = file.getAbsolutePath();
                image = new Image(new File(path).toURI().toString());
                imageview = new ImageView(image);
                imageview.setFitHeight(400);
                imageview.setFitWidth(400);
                imageview.setLayoutX(20);
                imageview.setLayoutY(20);
                
                image_cont.getChildren().add(imageview);
                setX_in= imageview.getScaleX()+value2;
                setY_in= imageview.getScaleY()+value2;
                setX_out= imageview.getScaleX()-value2;
                setY_out= imageview.getScaleY()-value2;
                
                newscale_in.setX(setX_in);
                newscale_in.setY(setY_in);
                newscale_out.setX(setX_out);
                newscale_out.setY(setY_out);
                }
                else if(event.getSource() == Zoom_in){
                newscale_in.setPivotX(setX_in);
                newscale_in.setPivotY(setY_in);
//                System.out.println("setX_in"+setX_in+" setY_in"+setY_in);
                imageview.getTransforms().add(newscale_in);
                }
                else if(event.getSource()==Zoom_out){
                newscale_out.setPivotX(setX_out);
                newscale_out.setPivotY(setY_out);
//                System.out.println(" setX_out"+setX_out+" setY_out"+setY_out);
                imageview.getTransforms().add(newscale_out);
                }
                else if(event.getSource()==Rotate_btn){
                    value1=imageview.getRotate();
                    imageview.setRotate(value1+90);
                }
                else if(event.getSource()==go_main){
                    Main main = new Main();
                    try {
                        main.start(primarystage);
                    } catch (Exception ex) {
                        Logger.getLogger(Imageviewer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        
        Select_btn.setOnMouseClicked(handler);
        Rotate_btn.setOnMouseClicked(handler);
        go_main.setOnMouseClicked(handler);
        Zoom_in.setOnMouseClicked(handler);
        Zoom_out.setOnMouseClicked(handler);

        container.setPrefSize(580, 70);
        container.setLayoutX(10);container.setLayoutY(620);
        container.setStyle("-fx-background-color: #0099FF;");
        container.getChildren().addAll(Zoom_in,Zoom_out,Select_btn,Rotate_btn,go_main);
        

        pain.setStyle("-fx-background-color: #5E5E5E;");
        pain.getChildren().addAll(container,image_cont);
        
        scene_img = new Scene(pain, 600, 700);
        primarystage.setScene(scene_img);
        primarystage.setTitle("ImageViewer");
        primarystage.show();
        
    }
    public static void main(String[] args) {
        launch(args);
    }
}
    
