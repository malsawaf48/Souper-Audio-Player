package com.example.souperaudioplayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void menuPlayAudio(){
        System.out.println("Play Audio");
    }
    public void menuCreatePlaylist(){
        System.out.println("Create Playlist");
    }
    public void menuPlayPlaylist(){
        System.out.println("Play Playlist");
    }
    public void menuHelp(){
        System.out.println("Help");
    }
    public void menuQuit(){
        System.out.println("Close App");
    }
}