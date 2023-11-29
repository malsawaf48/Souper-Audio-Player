package com.example.souperaudioplayer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableListValue;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class AudioPlayerController implements Initializable{
    public Slider volumeSlider;
    public ChoiceBox<Double> speedBox;
    public Slider sliderScrubber;
    public Label lblLength;
    public Label lblCurrentTime;
    Double[] speedOptions = {0.25,0.5,0.75,1.0,1.25,1.50,1.75,2.0,3.0,4.0};

    String testAudio = "C:\\Users\\malsa\\OneDrive\\Documents\\Programming\\Games\\Souper-Audio-Player\\" +
            "Souper Audio Player\\audios\\DragonThemeMP3.mp3";
    Media audio = new Media(new File(testAudio).toURI().toString());
    MediaPlayer audioPlayer = new MediaPlayer(audio);
    boolean isPlaying = false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        speedBox.getItems().addAll(speedOptions);
        speedBox.setOnAction(this::getSpeed);
        sliderScrubber.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if(sliderScrubber.isPressed()){
                Duration changeTime = Duration.seconds(sliderScrubber.getValue());
                audioPlayer.seek(changeTime);}

            }
        });
        audioPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                sliderScrubber.setValue(audioPlayer.getCurrentTime().toSeconds());
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
    public void openFile(){

    }
    public void playAudio(){
        if(isPlaying == false) {
            audioPlayer.play();
            getAudioLength();
            sliderScrubber.setMax(((int) audioPlayer.getStopTime().toSeconds()));
            isPlaying = true;
        }
        else {
            audioPlayer.pause();
            isPlaying = false;
        }
    }
    public void skipAudio(){

    }
    public void backAudio(){
        System.out.println(audioPlayer.getCurrentTime());
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

}
