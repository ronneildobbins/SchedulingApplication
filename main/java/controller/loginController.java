package controller;

import com.schedulingapp.Main;
import dao.JDBC;
import dao.appointmentQuery;
import dao.userQuery;
import javafx.collections.ObservableList;
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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Login Controller that controls the login screen
 *
 * @author Ronneil Dobbins
 */
public class loginController implements Initializable {


    /**
     * The username text field
     */
    @FXML
    private TextField usernameField;

    /**
     * The password text field
     */
    @FXML
    private TextField passwordField;

    /**
     * The username label. changes based on language
     */
    @FXML
    private Label usernameLabel;

    /**
     * The password label. changes based on language
     */
    @FXML
    private Label passwordLabel;

    /**
     * the exit button
     */
    @FXML
    private Button exitButton;

    /**
     * the login button
     */
    @FXML
    private Button loginButton;

    /**
     * Displays user zoneid
     */
    @FXML
    private Label zoneID;

    /**
     * The title of the program. Changes based on user language
     */
    @FXML
    private Label title;

    /**
     * The error alert title. Changes based on user language
     */
    private String ErrorTitle;
    /**
     * the error alert header if fields are empty. Changes based on user language
     */
    private String emptyErrorHeader;

    /**
     * the error alert text if fields are empty. Changes based on user language
     */
    private String emptyErrorText;

    /**
     * Failed Alert Header. Changes based on user language
     */
    private String failedHeader;
    /**
     * Failed Alert Text. Changes based on user language
     */
    private String failedText;

    /**
     * Log user in and out of the scheduling application. When the login button is press,
     * it ensures the text boxes are not empty and pass it onto the userQuery.login() boolean. If it
     * returns true then the user is login and sent to the main menu. If not the user is given an error message.
     *
     * Also, it checks if the user has any appointments and notifies them if it is within 15 minutes.
     * Includes lambda expression which is used to add appointments to the reminder
     *
     * @throws IOException if it fails to load the main menu screen
     */
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

                loginActivity(1, name);
                ObservableList<String> reminder = appointmentQuery.getAppointReminders();
                if(reminder == null || reminder.isEmpty()){
                    Alert reminderList = new Alert(Alert.AlertType.INFORMATION);
                    reminderList.setTitle("Appointment Reminder");
                    reminderList.setHeaderText("You Have No Upcoming Appointments");
                    reminderList.setContentText("You have no upcoming appointments within 15 minutes");
                    reminderList.showAndWait();
                }
                else{

                    /**
                     * Lambda Expression used to add appointments to the reminder
                     */
                    AtomicReference<String> content = new AtomicReference<>("");
                    reminder.forEach(i -> {
                        content.set(content + i);
                    });

                    Alert reminderList = new Alert(Alert.AlertType.INFORMATION);
                    reminderList.setTitle("Appointment Reminder");
                    reminderList.setHeaderText("You Have some Upcoming Appointments");
                    reminderList.setContentText(content.get());
                    reminderList.showAndWait();
                }

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
                loginActivity(2, name);
                displayAlert(2);
            }



        }

    }

    /**
     * Exit the scheduling application and closes connection to database
     */
    @FXML
    private void exitProgram() {

        JDBC.closeConnection();
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }

    /**
     * Records and stores login activity information in the login_activity.txt.
     * @param loginType loginType is used to determine if the user was successful or unsuccessful in login attempt
     * @param name Records the username of the user which the login attempt was made on
     * @throws IOException if the named file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason
     */
    private void loginActivity(int loginType, String name) throws IOException {

        BufferedWriter pw = new BufferedWriter(new FileWriter("login_activity.txt", true));

        switch(loginType){
            case 1:
                pw.write("New Login Attempt By User: " + name + " on Date: " + Timestamp.valueOf(LocalDateTime.now()) + "-UTC Login Successful\n" );
                pw.close();
                break;
            case 2:
                pw.write("New Login Attempt By User: " + name + " on Date: " + Timestamp.valueOf(LocalDateTime.now()) + "-UTC Login Unsuccessful\n" );
                pw.close();

                break;
        }

    }

    /**
     * Display main error messages on login screen in the user default language. Contains Lambda expression to
     * switch alert types
     * @param alertType alertType is used to determine which error to display
     */
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

    /**
     * Get the locale and language to determine what error messages and language to display
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
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
