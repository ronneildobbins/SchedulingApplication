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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 480);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {

        JDBC.openConnection();


        Locale locale =  Locale.getDefault();
        String language =  locale.getLanguage();

        if (language.equals("en")){
            System.out.print(language);
            System.out.printf(ZoneId.systemDefault().getId());
        }
        else{
            System.out.print(language);
            System.out.printf(ZoneId.systemDefault().getId());
        }



        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        String s = dtf.format(LocalDateTime.now());
        System.out.println(s);

        launch();
    }
}