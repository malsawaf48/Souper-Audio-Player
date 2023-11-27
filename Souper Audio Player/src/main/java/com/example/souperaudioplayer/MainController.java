package com.example.souperaudioplayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController {

    public void playAudio() throws IOException {
        MainApplication.change(1);
    }
    public void createPlaylist() throws IOException {
        MainApplication.change(2);
    }
    public void playPlaylist() throws IOException {
        MainApplication.change(3);
    }
    public void help(){
        System.out.println("Help");
    }
    public void quit(){
        MainApplication.closeApp();
    }
}