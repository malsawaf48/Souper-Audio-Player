package com.example.souperaudioplayer;

import javafx.event.Event;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class AudioPlayerController implements Initializable {
    public Label lblName;
    public ListView<String> lstvQueue;
    public Label lblNowPlaying;
    public Label lblCurrentTime;
    public Label lblLength;
    public Slider sliderScrubber;
    public Slider sliderVolume;
    public ChoiceBox<Double> cboxSpeedBox;
    public Button btnPlayPause;
    Double[] speedOptions = {0.25, 0.5, 0.75, 1.0, 1.25, 1.50, 1.75, 2.0, 3.0, 4.0};
    MediaPlayer audioPlayer;
    Playlist queue;
    boolean isPlaying = false;
    int queueNum;
    ArrayList<Audio> draggedAudios = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cboxSpeedBox.getItems().addAll(speedOptions);
        cboxSpeedBox.setValue(1.0);
        cboxSpeedBox.setOnAction(this::setSpeed);
        lstvQueue.setOnDragOver(event -> {
            if (event.getGestureSource() != lstvQueue
                    && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
        lstvQueue.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                if (isPlaying) {
                    pause();
                }
                String filePaths = db.getFiles().toString();
                filePaths = filePaths.substring(1, filePaths.length() - 1);
                String[] draggedStrings = filePaths.split(",");
                ArrayList<String> draggedPaths = new ArrayList<>();
                ArrayList<String> draggedNames = new ArrayList<>();
                int fileNameIndex = 0;
                for (String onestring : draggedStrings) {
                    onestring = onestring.trim();
                    draggedPaths.add(onestring);
                    draggedNames.add(db.getFiles().get(fileNameIndex).getName());
                    Audio oneAudio = new Audio(draggedNames.get(fileNameIndex), onestring);
                    draggedAudios.add(oneAudio);
                    fileNameIndex++;
                }
                queue = new Playlist("Queue", draggedAudios);
                lstvQueue.getItems().addAll(draggedNames);
                success = true;
                start();
            }
            event.setDropCompleted(success);

            event.consume();
        });
    }
    public void start() {
        audioPlayer = new MediaPlayer(queue.getAudios().get(0).getAudio());
        lblNowPlaying.setText("Playing: " + queue.getAudios().get(0).getName());
        audioPlayer.setOnReady(this::onReadyPlayer);
        setLengthLabel();
        sliderVolume.valueProperty().addListener((observableValue, number, t1) -> audioPlayer.setVolume(sliderVolume.getValue()));
        sliderScrubber.valueProperty().addListener((observableValue, oldNumber, newNumber) -> {
            double currentTime = audioPlayer.getCurrentTime().toSeconds();
            if (Math.abs(currentTime - newNumber.doubleValue()) > 0.5) {
                audioPlayer.seek(Duration.seconds(newNumber.doubleValue()));
            }
        });
        sliderScrubber.valueChangingProperty().addListener((observableValue, wasChanging, isChanging) -> {
            if (!isChanging) {
                Duration changeTime = Duration.seconds(sliderScrubber.getValue());
                audioPlayer.seek(changeTime);
            }
        });
        lstvQueue.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldIndex, newIndex) -> changeAudioTo((int) newIndex));
        setCurrentLabelListener();
    }

    private void setSpeed(Event event) {
        audioPlayer.setRate(cboxSpeedBox.getValue());
    }

    public void setCurrentLabelListener() {
        audioPlayer.currentTimeProperty().addListener((observableValue, oldDuration, newDuratiom) -> {
            sliderScrubber.setValue(newDuratiom.toSeconds());
            setCurrentTimeLabel();
        });
    }

    public void onReadyPlayer() {
        setLengthLabel();
    }

    public void setLengthLabel() {
        int min = (int) audioPlayer.getStopTime().toMinutes();
        int secs = ((int) audioPlayer.getStopTime().toSeconds()) - (60 * min);
        double sliderSecs = audioPlayer.getStopTime().toSeconds();
        sliderScrubber.setMax(sliderSecs);
        String time = min + ":" + secs;
        lblLength.setText(time);
    }

    public void setCurrentTimeLabel() {
        int min = (int) audioPlayer.getCurrentTime().toMinutes();
        int secs = ((int) audioPlayer.getCurrentTime().toSeconds()) - (60 * min);
        String time = min + ":" + secs;
        lblCurrentTime.setText(time);
    }

    public void backAudio() {
        if(queueNum > 0) {
            queueNum--;
        } else {
            queueNum = (queue.getAudios().size() - 1);
        }
        audioPlayer.stop();
        audioPlayer = new MediaPlayer(queue.getAudios().get(queueNum).getAudio());
        play();
    }

    public void playPause() {
        if (isPlaying) {
            pause();
        } else {
            play();
        }
    }

    public void play() {
        audioPlayer.play();
        isPlaying = true;
        lblNowPlaying.setText("Playing: " + queue.getAudios().get(queueNum).getName());
        audioPlayer.setRate(cboxSpeedBox.getValue());
        audioPlayer.setVolume(sliderVolume.getValue());
        audioPlayer.setOnReady(this::setLengthLabel);
        audioPlayer.setOnEndOfMedia(this::skipAudio);
        btnPlayPause.setText("| |");
        setCurrentLabelListener();
    }

    public void pause() {
        audioPlayer.pause();
        btnPlayPause.setText(" > ");
        isPlaying = false;
    }

    public void skipAudio() {
        audioPlayer.stop();
        if (queueNum < queue.getAudios().size() - 1) {
            queueNum++;
        } else {
            queueNum = 0;
        }
        audioPlayer.stop();
        audioPlayer = new MediaPlayer(queue.getAudios().get(queueNum).getAudio());
        play();
    }

    public void changeAudioTo(int newQueueNum) {
        queueNum = newQueueNum;
        audioPlayer.stop();
        audioPlayer = new MediaPlayer(queue.getAudios().get(queueNum).getAudio());
        play();
    }
}
