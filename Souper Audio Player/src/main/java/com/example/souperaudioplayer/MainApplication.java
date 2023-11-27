package com.example.souperaudioplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    static Stage stage;
    static Scene mainScene;
    static Scene audioPlayerScene;
    static Scene createPlaylistScene;
    static Scene playPlaylistScene;

    @Override
    public void start(Stage stage1) throws IOException {
        FXMLLoader fxmlMainWindow = new
                FXMLLoader(MainApplication.class.getResource("MainWindow.fxml"));
        FXMLLoader fxmlAudioPlayer = new
                FXMLLoader(MainApplication.class.getResource("AudioPlayerWindow.fxml"));
        FXMLLoader fxmlCreatePlaylist =
                new FXMLLoader(MainApplication.class.getResource("CreatePlaylistWindow.fxml"));
        FXMLLoader fxmlPlayPlaylist =
                new FXMLLoader(MainApplication.class.getResource("PlayPlaylistWindow.fxml"));

        mainScene = new Scene(fxmlMainWindow.load());
        audioPlayerScene = new Scene(fxmlAudioPlayer.load());
        createPlaylistScene = new Scene(fxmlCreatePlaylist.load());
        playPlaylistScene = new Scene(fxmlPlayPlaylist.load());

        stage = stage1;
        MainApplication.stage.setTitle("Souper Audio Player");
        change(0);
    }

    public static void main(String[] args) {
        launch();
    }
    public static void change(int Scene) throws IOException {
        switch (Scene){
            case 0:
                stage.setScene(mainScene);
                stage.setTitle("Souper Audio Player");
                stage.show();
                break;
            case 1:
                stage.setScene(audioPlayerScene);
                break;
            case 2:
                stage.setScene(createPlaylistScene);
                break;
            case 3:
                stage.setScene(playPlaylistScene);
                break;
            case 4:
                break;
        }
    }
    public static void closeApp(){
        stage.close();
    }
}