package com.schedulingapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 480);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
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



        launch();
    }
}