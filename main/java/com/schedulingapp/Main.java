package com.schedulingapp;

import dao.JDBC;
import dao.contactQuery;
import dao.customerQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 *
 * Main implements GUI-based scheduling desktop application which allows the user to log in, create, delete and modify customers & appointments.
 * Also, it allows the user to view information about customers and appointments.
 *
 * JavaDoc located in JavaDoc folder
 *
 * @author ronneildobbins
 */
public class Main extends Application {

    /**
     * Displays Login Screen
     * @param stage main stage of application
     * @throws IOException Unsuccessful loading scene
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 480);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * Launches application. Establishes a connection with the database.
     * Set the language for the login screen and determines user locale and Zone Id.
     *
     * @param args The command line parameters.
     * @throws SQLException if it's unsuccessful in establishing a connection to the database.
     *
     */
    public static void main(String[] args) throws SQLException {

        //Attempts to connect to the database
        JDBC.openConnection();

        Locale locale =  Locale.getDefault();
        String language =  locale.getLanguage();

        //prints language and ZoneId to the command terminal
        System.out.println(language);
        System.out.println(ZoneId.systemDefault().getId());

        //launches application
        launch();
    }
}