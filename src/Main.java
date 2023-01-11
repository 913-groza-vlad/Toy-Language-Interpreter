import View.Ui;
import View.GUI.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader listLoader = new FXMLLoader();
            listLoader.setLocation(getClass().getResource("View/GUI/ListView.fxml"));
            GridPane root = (GridPane) listLoader.load();
            Scene scene = new Scene(root, 600, 500);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("View/GUI/listview.css")).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Toy Language Interpreter");
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        /* String example1 = "int v; v=2; Print(v)";
        String example2 = "int a; int b; a=2+3*5; b=a+1; Print(b)";
        String example3 = "bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)"; */


        // Ui ui = new Ui();
        // // ui.start();
        // ui.mainStart();

        launch(args);
    }
}