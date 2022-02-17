package controller;

import com.schedulingapp.Main;
import dao.appointmentQuery;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class appointmentViewController implements Initializable {

    @FXML
    private Tab allTab;

    @FXML
    private TableView allApptTable;

    @FXML
    private TableColumn allApptId;

    @FXML
    private TableColumn allApptTitle;

    @FXML
    private TableColumn allApptDesc;

    @FXML
    private TableColumn allApptLocation;

    @FXML
    private TableColumn allApptContact;

    @FXML
    private TableColumn allApptType;

    @FXML
    private TableColumn allApptStart;

    @FXML
    private TableColumn allApptEnd;

    @FXML
    private TableColumn allApptCID;

    @FXML
    private TableColumn allApptUId;


    @FXML
    private Tab monthlyTab;


    @FXML
    private Tab weeklyTab;

    @FXML
    private Button backButton;

    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label currentTabLabel;



    @FXML
    private void selectAll() {
        if(this.currentTabLabel == null){

        }
        else {
            currentTabLabel.setText("Current Tab: All");
        }
    }

    @FXML
    private void selectMonthly() {
        currentTabLabel.setText("Current Tab: Monthly");
    }

    @FXML
    private void selectWeekly() {
        currentTabLabel.setText("Current Tab: Weekly");
    }

    @FXML
    private void addAction(){

    }
    @FXML
    private void backAction() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainMenu.fxml"));
        Parent root = fxmlLoader.load();


        Stage mainMenu = new Stage();
        mainMenu.setTitle("Main Menu");
        mainMenu.setScene(new Scene(root));
        mainMenu.initModality(Modality.APPLICATION_MODAL);
        mainMenu.show();

        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


          allApptId.setCellValueFactory(new PropertyValueFactory<>("apptId"));
          allApptTitle.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
          allApptDesc.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
          allApptLocation.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
          allApptContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
          allApptType.setCellValueFactory(new PropertyValueFactory<>("apptType"));
          allApptStart.setCellValueFactory(new PropertyValueFactory<>("formatEnd"));
          allApptEnd.setCellValueFactory(new PropertyValueFactory<>("formatStart"));
          allApptCID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
          allApptUId.setCellValueFactory(new PropertyValueFactory<>("userID"));
          allApptTable.setItems(appointmentQuery.getAllAppointments());

    }
}
