package dickie.gui;

import java.io.IOException;

import dickie.Dickie;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Dickie using FXML.
 */
public class Main extends Application {

    private Dickie dickie = new Dickie();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            stage.setTitle("Dickie");
            stage.setMinHeight(500);
            stage.setMinWidth(417);

            MainWindow controller = fxmlLoader.<MainWindow>getController();
            controller.setDickie(dickie);  // inject the Dickie instance
            controller.showGreeting();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
