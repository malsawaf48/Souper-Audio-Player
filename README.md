This is my audio player program developed for my Java final project.

This project incorporates the following:

Classes: Audio, Playlist, AudioPlayerController, CreatePlaylistController, PlayPlaylistController, MainController, MainApplication.

Files: Saves playlists into files that can be created in the program. Can read those files and load the audio player with those files.

GUI: There are 4 windows that interact with each other.

Composition: The Playlist class contains an array of Audios.

Error/Exception handling/Data validation: Multiple try catch in code and doesnt allow the user to create a playlist without a name.


Other features that are used that we did not go over in class are:

Listeners: used for changing volume and for scrubbing through the audio.

MediaPlayer: This is used for playing the audio files.

Dragboard: This is used to allow the user to drag and drop files into the program.

SceneBuilder: Used for creating the GUI layout.
