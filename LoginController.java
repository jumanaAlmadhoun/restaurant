/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
;
import javafx.stage.Stage;
import javax.swing.*;

/**
 * FXML Controller class
 *
 * @author almadhoun
 */


public class LoginController implements Initializable {

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    public void login(Event e) throws IOException {
        if (username.getText().trim().matches("jomana") && password.getText().equals("12345")) {
            //to upload the main menueController
            Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Main Menu");
            /*  Rectangle2D rd = Screen.getPrimary().getVisualBounds();
            stage.setX((rd.getWidth() - stage.getWidth() / 2));
            stage.setY((rd.getHeight() - stage.getHeight() / 2));*/

        } else {
            //error msg
            JOptionPane.showMessageDialog(null, "Check username or password");
        }

    }

    public void exit() {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
