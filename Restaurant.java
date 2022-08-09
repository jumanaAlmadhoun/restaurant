package restaurant;

import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author almadhoun
 */
public class Restaurant extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
   //     Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("sign in");
        stage.show();
    }

  
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        launch(args);

    }

}
