package com.example.souperaudioplayer;

import javafx.scene.media.Media;

import java.nio.file.Path;

public class Audio {
    private String name;
    private Media audio;

    public Audio(String name, Media audio){
        this.name = name;
        this.audio = audio;
    }
}
