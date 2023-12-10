package com.example.souperaudioplayer;

import javafx.fxml.Initializable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreatePlaylistController implements Initializable {

    public void quit() throws IOException {
        MainApplication.change(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            File myObj = new File("filename.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
