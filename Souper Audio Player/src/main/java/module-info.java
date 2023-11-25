module com.example.souperaudioplayer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens com.example.souperaudioplayer to javafx.fxml;
    exports com.example.souperaudioplayer;
}