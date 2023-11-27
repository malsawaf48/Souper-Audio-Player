module com.example.souperaudioplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.souperaudioplayer to javafx.fxml;
    exports com.example.souperaudioplayer;
}