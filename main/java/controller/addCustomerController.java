package controller;

import com.schedulingapp.Main;
import dao.countryQuery;
import dao.customerQuery;
import dao.divisionQuery;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class addCustomerController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField postalField;

    @FXML
    private TextField phoneField;

    @FXML
    private ComboBox<String> countryCombo;

    @FXML
    private ComboBox divisionCombo;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;


    @FXML
    private void addCustomer() throws IOException {


        String name = nameField.getText();
        String address = addressField.getText();
        String postal = postalField.getText();
        String phone = phoneField.getText();

        if(divisionCombo.getValue() == null){
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Division Not Selected");
            alertError.setContentText("Please select a Division");
            alertError.showAndWait();

        }
        else {
            if (name.isEmpty() || address.isEmpty() || postal.isEmpty() || phone.isEmpty()) {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Error");
                alertError.setHeaderText("Empty or Blank Fields");
                alertError.setContentText("Please fill out all fields to create customer");
                alertError.showAndWait();

            } else {

                int divID = divisionQuery.getDivID((String) divisionCombo.getValue());

                boolean success = customerQuery.addCustomer(name, address, postal, phone, divID);

                if (success) {
                    Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
                    alertSuccess.setTitle("Success");
                    alertSuccess.setHeaderText("Create Customer Success");
                    alertSuccess.setContentText("The attempt to create a new customer has succeed. Please press the OK button to be redirected to customer view");
                    Optional<ButtonType> result = alertSuccess.showAndWait();
                    if(result.get() == ButtonType.OK){

                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("customerView.fxml"));
                        Parent root = fxmlLoader.load();


                        Stage customerView = new Stage();
                        customerView.setTitle("Customer View");
                        customerView.setScene(new Scene(root));
                        customerView.initModality(Modality.APPLICATION_MODAL);
                        customerView.show();

                        Stage stage = (Stage) saveButton.getScene().getWindow();
                        stage.close();
                    }

                } else {
                    Alert alertError = new Alert(Alert.AlertType.ERROR);
                    alertError.setTitle("Error");
                    alertError.setHeaderText("Create Customer Failed");
                    alertError.setContentText("The attempt to create a new customer has failed. Please Try Again !");
                    alertError.showAndWait();
                }
            }
        }
    }



    @FXML
    private void cancelAddCustomer() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("customerView.fxml"));
        Parent root = fxmlLoader.load();


        Stage customerView = new Stage();
        customerView.setTitle("Customer View");
        customerView.setScene(new Scene(root));
        customerView.initModality(Modality.APPLICATION_MODAL);
        customerView.show();

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void countrySelected(){

        String country = countryCombo.getValue();
        int countryid = 0;
        if (country.equals("U.S")){
            countryid = 1;
        }
        else if(country.equals("UK")){
            countryid = 2;
        }
        else if(country.equals("Canada")){
            countryid = 3;
        }
        else{
            countryid = 0;
        }

        divisionCombo.setItems(divisionQuery.getAllDivision(countryid));
        divisionCombo.setDisable(false);



    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryCombo.setItems(countryQuery.getAllCountries());
        }

    }

