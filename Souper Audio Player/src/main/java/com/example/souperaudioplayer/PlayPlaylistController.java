package com.example.souperaudioplayer;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class PlayPlaylistController implements Initializable {
    public ListView lstvPlaylistList;
    ArrayList<String> playlists = new ArrayList<String>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void refreshFiles(){
        try {
            File myObj = new File("filename.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                playlists.add(data);
                    lstvPlaylistList.getItems().addAll(playlists);
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public void quit() throws IOException {
        MainApplication.change(0);
    }
}
