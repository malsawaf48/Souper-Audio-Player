package com.example.souperaudioplayer;

import java.util.ArrayList;

public class Playlist {
    private String name;
    private ArrayList<Audio> audios;

    public Playlist(String name, ArrayList<Audio> audios){
        this.name = name;
        this.audios = audios;
    }

    public ArrayList<Audio> getAudios() {
        return audios;
    }
}
