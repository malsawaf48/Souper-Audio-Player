package com.example.souperaudioplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.setTitle("Souper Audio Player");
        change(0);
    }

    public static void main(String[] args) {
        launch();
    }
    public static void change(int Scene) throws IOException {
        FXMLLoader fxmlMainWindow = new FXMLLoader(MainApplication.class.getResource("MainWindow.fxml"));
        Scene Mainscene = new Scene(fxmlMainWindow.load());
        FXMLLoader fxmlAudioPlayer = new FXMLLoader(MainApplication.class.getResource("AudioPlayerWindow.fxml"));
        Scene AudioPlayerScene = new Scene(fxmlAudioPlayer.load());
        switch (Scene){
            case 0:
                primaryStage.setScene(Mainscene);
                primaryStage.setTitle("Souper Audio Player");
                primaryStage.show();
                break;
            case 1:
                primaryStage.setScene(AudioPlayerScene);
                break;
        }
    }
}