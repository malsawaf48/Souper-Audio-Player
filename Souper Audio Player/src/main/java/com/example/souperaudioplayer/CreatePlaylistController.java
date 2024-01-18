package com.example.souperaudioplayer;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreatePlaylistController implements Initializable {
    public TextField txtfPlaylistName;
    public ListView lstvFilesList;

    ArrayList<Audio> audioArray = new ArrayList<>();
    ArrayList<String> audioNames = new ArrayList<>();
    Playlist playlist = null;
    String name = "";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lstvFilesList.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != lstvFilesList
                        && event.getDragboard().hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });
        lstvFilesList.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    String filePaths = db.getFiles().toString();
                    filePaths = filePaths.substring(1, filePaths.length() - 1);
                    String[] draggedStrings = filePaths.split(",");
                    ArrayList<String> draggedPaths = new ArrayList<String>();
                    ArrayList<String> draggedNames = new ArrayList<String>();
                    int fileNameIndex = 0;
                    for (String onestring : draggedStrings) {
                        onestring = onestring.trim();
                        draggedPaths.add(onestring);
                        draggedNames.add(db.getFiles().get(fileNameIndex).getName());
                        Audio oneAudio = new Audio(draggedNames.get(fileNameIndex), onestring);
                        audioArray.add(oneAudio);
                        fileNameIndex++;
                    }
                    audioNames = draggedNames;
                    playlist = new Playlist("Queue", audioArray);
                    lstvFilesList.getItems().addAll(draggedNames);
                    success = true;
                }
                event.setDropCompleted(success);

                event.consume();
                }
            });
        }
    public void quit() throws IOException {
        MainApplication.change(0);
    }
    public void create(){
        if(!Objects.equals(txtfPlaylistName.getText().toString().replaceAll("\\s+",""), "")){
            name = txtfPlaylistName.getText();
            try {
                File myObj = new File("playlists\\"+name+".txt");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            try {
                FileWriter myWriter = new FileWriter("playlists\\"+name+".txt");
                for(Audio oneaudio: audioArray){
                    myWriter.write(oneaudio.getName()+","+oneaudio.getPath()+"\n");
                }
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            for(Audio oneaudio: audioArray){

            }
            txtfPlaylistName.clear();
            lstvFilesList.getItems().clear();
        }
    }
}