package com.example.souperaudioplayer;

import javafx.scene.media.Media;

import java.io.File;
import java.nio.file.Path;

public class Audio {
    private String name;
    private Media audio;
    private String path;

    public Audio(String name, String path){
        this.name = name;
        this.path = path;
        audio = new Media(new File(path).toURI().toString());
    }
    public String getName(){return name;}

    public Media getAudio() {
        return audio;
    }

    public String getPath() {
        return path;
    }
}
