package controller;

import com.mysql.cj.protocol.Resultset;
import com.schedulingapp.Main;
import dao.JDBC;
import dao.userQuery;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Button exitButton;
    @FXML
    private Button loginButton;

    @FXML
    private Label zoneID;
    @FXML
    private Label title;

    private String ErrorTitle;
    private String emptyErrorHeader;
    private String emptyErrorText;

    private String failedHeader;
    private String failedText;

    @FXML
    private void login() throws IOException {

        String name = usernameField.getText();
        String pass = passwordField.getText();

        if(name.isEmpty() || pass.isEmpty()) {
            displayAlert(1);
        }
        else {

            boolean login = userQuery.login(name, pass);

            if (login){

                System.out.println("successful!");
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainMenu.fxml"));
                Parent root = fxmlLoader.load();


                Stage mainMenu = new Stage();
                mainMenu.setTitle("Main Menu");
                mainMenu.setScene(new Scene(root));
                mainMenu.initModality(Modality.APPLICATION_MODAL);
                mainMenu.show();

                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();
            }
            else{
                displayAlert(2);
            }



        }

    }

    @FXML
    private void exitProgram() {

        JDBC.closeConnection();
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }

    private void displayAlert(int alertType){

        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alertError.setTitle(ErrorTitle);
                alertError.setHeaderText(emptyErrorHeader);
                alertError.setContentText(emptyErrorText);
                alertError.showAndWait();

                break;
            case 2:
                alertError.setTitle(ErrorTitle);
                alertError.setHeaderText(failedHeader);
                alertError.setContentText(failedText);
                alertError.showAndWait();
                break;
        }

        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Locale locale =  Locale.getDefault();
        String language =  locale.getLanguage();

        ResourceBundle res;

        if(language.equals("fr")) {
            res = ResourceBundle.getBundle("properties.login_fr");
        }
        else{
            res = ResourceBundle.getBundle("properties.login_en");
        }
        
        title.setText(res.getString("title"));
        usernameLabel.setText(res.getString("username"));
        passwordLabel.setText(res.getString("password"));
        exitButton.setText(res.getString("exitButton"));
        loginButton.setText(res.getString("advance"));

        zoneID.setText(ZoneId.systemDefault().getId());

        ErrorTitle = res.getString("ErrorTitle");
        emptyErrorHeader = res.getString("emptyErrorHeader");
        emptyErrorText = res.getString("emptyErrorText");

        failedHeader = res.getString("failedHeader");
        failedText = res.getString("failedText");

    }
}
