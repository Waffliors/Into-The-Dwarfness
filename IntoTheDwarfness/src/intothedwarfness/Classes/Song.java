/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intothedwarfness.Classes;

import java.io.File;
import javax.swing.*;
import java.net.*;

public class Song extends JApplet {
/* ***************************Class Variables******************************** */
    private URL songPath;
    private java.applet.AudioClip song;
    
/* **************************Class Constructor******************************* */   
    public Song(String path) throws MalformedURLException {
        songPath = new File(path).toURI().toURL();
        song = java.applet.Applet.newAudioClip(songPath);
        
    }

/* ****************************Class Methods********************************* */
    //Play the song in loop
    public void playSound() {
        song.loop();
    }
    //Stop the song
    public void stopSound() {
        song.stop();
    }
    //Play the song once
    public void playSoundOnce() {
        song.play();
    }
}