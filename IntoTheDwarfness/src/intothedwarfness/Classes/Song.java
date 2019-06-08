/*******************************************************************************
 **      Song Class                                                           **
 **                                                                           **
 ** Create the objects that will be responsible by hold the songs of the game **
 ** and play when requested                                                   **
 ******************************************************************************/
package intothedwarfness.Classes;

import java.net.*;
import java.io.File;
import javax.swing.*;

public class Song extends JApplet {
    private URL songPath;
    private java.applet.AudioClip song;
    
/* **************************Class Constructor******************************* */   
    public Song(String path) throws MalformedURLException {
        songPath = new File(path).toURI().toURL();
        song = java.applet.Applet.newAudioClip(songPath);
    }

    /* ************************* Class Methods ****************************** */
    /**
     * Play the song in loop
     */
    public void playSound() {
        song.loop();
    }
    /**
     * Srop the song
     */
    public void stopSound() {
        song.stop();
    }
    /**
     * Play the song just one time
     */
    public void playSoundOnce() {
        song.play();
    }
}