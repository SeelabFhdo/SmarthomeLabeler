import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by jonas on 14.08.17.
 */
public class ViewMain extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/MainView.fxml"));

        Scene scene = new Scene(root, 680, 450);
        stage.setTitle("Smarthome-Labeler");
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

}
