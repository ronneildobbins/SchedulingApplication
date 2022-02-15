module com.schedulingapp.schedulingappronneildobbins {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.schedulingapp to javafx.fxml;
    exports com.schedulingapp;
    exports controller;
    opens controller to javafx.fxml;
}