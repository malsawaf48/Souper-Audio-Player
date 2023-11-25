package com.example.souperaudioplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlMainWindow = new FXMLLoader(MainApplication.class.getResource("MainWindow.fxml"));
        Scene Mainscene = new Scene(fxmlMainWindow.load());
        FXMLLoader fxmlAudioPlayer = new FXMLLoader(MainApplication.class.getResource("AudioPlayerWindow.fxml"));
        Scene AudioPlayerScene = new Scene(fxmlAudioPlayer.load());
        stage.setTitle("SAP");
        stage.setScene(Mainscene);
        stage.setScene(AudioPlayerScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}