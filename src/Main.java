

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Button btn = new Button("TEXT BTN");
        
        Scene sc = new Scene(btn,400,400);
        primaryStage.setScene(sc);
        
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}