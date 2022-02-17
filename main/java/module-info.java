module com.schedulingapp.schedulingappronneildobbins {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.java;
    requires java.sql;


    opens com.schedulingapp to javafx.fxml;
    exports com.schedulingapp;
    exports controller;
    opens controller to javafx.fxml;
    exports  dao;
    opens dao to javafx.base;
    exports  model;
    opens model to javafx.base;
}