package com.example.souperaudioplayer;

import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioPlayerController {
    String testAudio = "C:\\Users\\malsa\\OneDrive\\Documents\\Programming\\Games\\Souper-Audio-Player\\" +
            "Souper Audio Player\\audios\\DragonThemeMP3.mp3";
    Media audio = new Media(new File(testAudio).toURI().toString());
    MediaPlayer audioPlayer = new MediaPlayer(audio);
    public void openFile(){
        System.out.println("Open File Explorer");
    }
    public void playAudio(){
        audioPlayer.play();
    }
}
