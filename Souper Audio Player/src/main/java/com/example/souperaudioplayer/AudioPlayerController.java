package com.example.souperaudioplayer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableListValue;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
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

    public Label lblName;
    public ListView lstvQueue;
    public Label lblNowPlaying;
    public Label lblCurrentTime;
    public Label lblLength;
    public Slider sliderScrubber;
    public Slider sliderVolume;
    public ChoiceBox cboxSpeedBox;
    Double[] speedOptions = {0.25,0.5,0.75,1.0,1.25,1.50,1.75,2.0,3.0,4.0};
    Media audio;
    MediaPlayer audioPlayer;
    Playlist queue;
    boolean isPlaying = false;
    boolean shouldLoop = false;
    int queueNum;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cboxSpeedBox.getItems().addAll(speedOptions);
        cboxSpeedBox.setValue(1.0);
        cboxSpeedBox.setOnAction(this::setSpeed);
        loadQueue();
        audioPlayer = new MediaPlayer(queue.getAudios().get(0).getAudio());
        lblNowPlaying.setText("Playing: "+ queue.getAudios().get(0).getName());
        audioPlayer.setOnReady(this::setLengthLabel);
        setLengthLabel();
        sliderVolume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                audioPlayer.setVolume(sliderVolume.getValue());
            }
        });
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
        setCurrentLabelListener();

    }

    private void setSpeed(Event event) {
        audioPlayer.setRate((Double) cboxSpeedBox.getValue());
    }

    public void fileExplorer(){

    }
    public void loadQueue(){
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
        ArrayList<String> audioNames = new ArrayList<String>();
        for(Audio oneAudio: queue.getAudios()){
            audioNames.add(oneAudio.getName());
        }
        lstvQueue.getItems().addAll(audioNames);

    }
    public void setCurrentLabelListener(){
        audioPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration oldDuration, Duration newDuratiom) {
                sliderScrubber.setValue(newDuratiom.toSeconds());
                setCurrentTimeLabel();
            }
        });
    }
    public void setLengthLabel(){
        int min = (int) audioPlayer.getStopTime().toMinutes();
        int secs = ((int) audioPlayer.getStopTime().toSeconds())- (60 * min);
        double sliderSecs = audioPlayer.getStopTime().toSeconds();
        sliderScrubber.setMax(sliderSecs);
        String time = min +":"+secs;
        lblLength.setText(time);
    }
    public void setCurrentTimeLabel(){
        int min = (int) audioPlayer.getCurrentTime().toMinutes();
        int secs = ((int) audioPlayer.getCurrentTime().toSeconds())- (60 * min);
        String time = min +":"+secs;
        lblCurrentTime.setText(time);
    }
    public void backAudio(){
        if(queueNum > 0){
            queueNum --;
            audioPlayer.stop();
            audioPlayer = new MediaPlayer(queue.getAudios().get(queueNum).getAudio());
            play();
        }
        else{
            queueNum = (queue.getAudios().size()-1);
            audioPlayer.stop();
            audioPlayer = new MediaPlayer(queue.getAudios().get(queueNum).getAudio());
            play();
        }
    }
    public void playPause(){
        if(isPlaying == true){
            pause();
        }
        else{
            play();
        }
    }
    public void play(){
        audioPlayer.play();
        isPlaying = true;
        lblNowPlaying.setText("Playing: "+queue.getAudios().get(queueNum).getName());
        audioPlayer.setRate((Double) cboxSpeedBox.getValue());
        audioPlayer.setVolume(sliderVolume.getValue());
        audioPlayer.setOnReady(this::setLengthLabel);
        setCurrentLabelListener();
    }
    public void pause(){
        audioPlayer.pause();
        isPlaying = false;
    }
    public void skipAudio(){
        if(queueNum < queue.getAudios().size()-1){
            queueNum ++;
            audioPlayer.stop();
            audioPlayer = new MediaPlayer(queue.getAudios().get(queueNum).getAudio());
            play();
        }
        else{
            queueNum = 0;
            audioPlayer.stop();
            audioPlayer = new MediaPlayer(queue.getAudios().get(queueNum).getAudio());
            play();
        }
    }
    public void loopMode(){
        if(shouldLoop == true){
            shouldLoop = false;
            audioPlayer.setOnEndOfMedia(() -> audioPlayer.seek(Duration.ZERO));
        }
        else{
            shouldLoop = true;
            audioPlayer.setOnEndOfMedia(null);
        }
        System.out.println(shouldLoop);
    }


}
