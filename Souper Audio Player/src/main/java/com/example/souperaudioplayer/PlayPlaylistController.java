package com.example.souperaudioplayer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class PlayPlaylistController implements Initializable {
    public ListView lstvPlaylistList;
    ArrayList<Playlist> playlists = new ArrayList<>();
    ArrayList<Audio> audioArray = new ArrayList<>();
    ArrayList<String> playlistsNames = new ArrayList<String>();
    ArrayList<String> audioNames = new ArrayList<>();
    int selectedIndex;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
        lstvPlaylistList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNum, Number newNum) {
                selectedIndex = (int) newNum;
            }
        });
    }
    public void refreshFiles(){
        refresh();
    }
    public void refresh(){
        File playlistsFolder = new File("playlists");
        String[] paths = playlistsFolder.list();
        playlistsNames.clear();
        lstvPlaylistList.getItems().clear();
        playlistsNames.addAll(List.of(paths));
        lstvPlaylistList.getItems().addAll(playlistsNames);
        for(String oneName: playlistsNames){
            audioArray.clear();
            try (BufferedReader br = new BufferedReader(new FileReader(new File("playlists\\"+oneName)))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] info = line.split(",");
                    Audio audio = new Audio(info[0], info[1]);
                    audioNames.add(info[0]);
                    audioArray.add(audio);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Playlist playlist = new Playlist(oneName, audioArray);
            playlists.add(playlist);
        }


    }
    public void play() throws IOException {
        for(Audio oneaudio: audioArray){
            System.out.println(oneaudio.getName());
        }
        MainApplication.change(1);
    }
    public void quit() throws IOException {
        MainApplication.change(0);
    }
}
