package com.example.souperaudioplayer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableListValue;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;

import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class AudioPlayerController implements Initializable{
    public Slider volumeSlider;
    public ChoiceBox<Double> speedBox;
    public Slider sliderScrubber;
    public Label lblLength;
    public Label lblCurrentTime;
    public ListView queueList;
    public Button PlayPause;
    Double[] speedOptions = {0.25,0.5,0.75,1.0,1.25,1.50,1.75,2.0,3.0,4.0};
    Timer audioLength = new Timer();

    String testAudio = "C:\\Users\\malsa\\OneDrive\\Documents\\Programming\\Games\\Souper-Audio-Player\\" +
            "Souper Audio Player\\audios\\DragonThemeMP3.mp3";
    Media audio = new Media(new File(testAudio).toURI().toString());
    MediaPlayer audioPlayer= new MediaPlayer(audio);
    Playlist queue;
    int queueNum = 0;
    boolean isPlaying = false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        speedBox.getItems().addAll(speedOptions);
        speedBox.setOnAction(this::getSpeed);
        sliderScrubber.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
                    double currentTime = audioPlayer.getCurrentTime().toSeconds();
                    if(Math.abs(currentTime - newNumber.doubleValue()) > 0.5){
                        audioPlayer.seek(Duration.seconds(newNumber.doubleValue()));
                    }
            }
        });
        sliderScrubber.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean wasChanging, Boolean isChanging) {
                if(isChanging == false){
                    Duration changeTime = Duration.seconds(sliderScrubber.getValue());
                    audioPlayer.seek(changeTime);
                }
            }
        });
        audioPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration oldDuration, Duration newDuratiom) {
                sliderScrubber.setValue(newDuratiom.toSeconds());
                getAudioCurrentTime();
            }
        });

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                audioPlayer.setVolume(volumeSlider.getValue());
            }
        });
    }
    public void playAudio(){
        if(isPlaying == false) {
            play();
        }
        else {
            pause();
        }
    }
    public void play(){
        audioPlayer.play();
        getAudioLength();
        sliderScrubber.setMax(((int) audioPlayer.getStopTime().toSeconds()));
        isPlaying = true;
        PlayPause.setText(">");
    }
    public void pause(){
        audioPlayer.pause();
        isPlaying = false;
        PlayPause.setText("| |");
    }
    public void skipAudio(){
        pause();
        //audioPlayer.stop();
        queueNum ++;
        audioPlayer = new MediaPlayer(queue.getAudios().get(queueNum).getAudio());
        play();
        sliderScrubber.setValue(0);
    }
    public void backAudio(){
        pause();
        //audioPlayer.stop();
        queueNum --;
        audioPlayer = new MediaPlayer(queue.getAudios().get(queueNum).getAudio());
        play();
        sliderScrubber.setValue(0);
    }
    public void getSpeed(ActionEvent event){
        audioPlayer.setRate(speedBox.getValue());
    }
    public void getAudioLength(){
        int mins = ((int) audioPlayer.getStopTime().toMinutes());
        int secs = ((int) audioPlayer.getStopTime().toSeconds()) - (60 * mins);
        String time = mins + ":"+secs;
        lblLength.setText(time);
    }
    public void getAudioCurrentTime(){
        int mins = ((int) audioPlayer.getCurrentTime().toMinutes());
        int secs = ((int) audioPlayer.getCurrentTime().toSeconds()) - (60 * mins);
        String time = mins + ":"+secs;
        lblCurrentTime.setText(time);

    }
    public void openFile(){
        Audio audio1 = new Audio("Dragon Theme", "C:\\Users\\malsa\\OneDrive\\Documents\\Programming" +
                "\\Games\\Souper-Audio-Player\\Souper Audio Player\\audios\\DragonThemeMP3.mp3");
        Audio audio2 = new Audio("Hyperbolic Time Chamber", "C:\\Users\\malsa\\OneDrive\\Documents\\" +
                "Programming\\Games\\Souper-Audio-Player\\Souper Audio Player\\audios\\Bruce Faulconer - Hyperbolic Time Chamber.mp3");
        Audio audio3 = new Audio("Vegeta Powers Up", "C:\\Users\\malsa\\OneDrive\\Documents\\Programming\\Games" +
                "\\Souper-Audio-Player\\Souper Audio Player\\audios\\Vegeta's Powers Up [Clean Cut].mp3");
        ArrayList<Audio> audioArrayList = new ArrayList<Audio>();
        audioArrayList.add(audio1);
        audioArrayList.add(audio2);
        audioArrayList.add(audio3);
        queue = new Playlist("Dragon Ball Z", audioArrayList);
        loadQueue(queue);
    }

    public void loadQueue(Playlist queue){
        ArrayList<String> audioNames = new ArrayList<String>();
        for(Audio oneAudio: queue.getAudios()){
            audioNames.add(oneAudio.getName());
        }
        queueList.getItems().addAll(audioNames);

    }
}
