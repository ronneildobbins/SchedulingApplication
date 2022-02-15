package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class Login implements Initializable {
    public TextField usernameField;
    public TextField passwordField;
    public Label usernameLabel;
    public Label passwordLabel;
    public Button exitButton;
    public Button loginButton;

    public Label zoneID;
    public Label title;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Locale locale =  Locale.getDefault();
        String language =  locale.getLanguage();

        if(language.equals("en")){

        }

        zoneID.setText(ZoneId.systemDefault().getId());


    }
}
