/** ***************************************************************************
 ** Centro Universitário SENAC                                               **
 ** Tecnologia em jogos digitais - 1º Semestre de 2019                       **
 ** Bruno Cavalcante de Souza Sanches                                        **
 **                                                                          **
 ** Projeto Integrador III - Projeto Final                                   **
 ** Arquivo: Into the Dwarfness                                              **
 **                                                                          **
 ** Matheus Vicente Rodrigues da Silva                                       **
 ** Nathan André da Silva                                                    **
 ** Raphael Oliveira Melo                                                    **
 **                                                                          **
 ** 13/06/2019                                                               **
 ******************************************************************************/
package intothedwarfness;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import intothedwarfness.Classes.Song;
import java.net.MalformedURLException;
import intothedwarfness.Classes.Window;

public class IntoTheDwarfness {
    
    public static void main(String[] args) throws IOException {
        // Loading the sprites of the game
        ArrayList<BufferedImage> sprites = new ArrayList();
        loadSprites(sprites);
        // Loading the HUD elements of the game
        ArrayList<BufferedImage> hud = new ArrayList();
        loadHUD(hud);
        // Loading the songs of the game
        ArrayList<Song> songs = new ArrayList();
        loadSongs(songs);
        //The order of the elements in the above lists can be seen in:
        //Into-The-Dwarfness\IntoTheDwarfness\documentation
        
        // Creating the window of the game
        Window screen = new Window(sprites, songs, hud);
        screen.initialize();
        try {
            screen.run();
        } catch (InterruptedException ex) {
        }
    }

    /**
     * Load the wav song files that will be used in the game
     * 
     * @param songList : The ArrayList where they will be added
     * @throws MalformedURLException
     */
    private static void loadSongs(ArrayList<Song> songList) 
            throws MalformedURLException {
        // First, load the dwarf's SFX
        songList.add(new Song(
                "songs/sfx\\Dwarf/Dwarf - Attack.wav"));
        songList.add(new Song(
                "songs/sfx\\Dwarf/Dwarf - Died.wav"));
        songList.add(new Song(
                "songs/sfx\\Dwarf/Dwarf - Hitted.wav"));
        // Then, the fire elemental's SFX
        songList.add(new Song(
                "songs/sfx/Fire Elemental/Fire Elemental - Attack.wav"));
        songList.add(new Song(
                "songs/sfx/Fire Elemental/Fire Elemental - Died.wav"));
        songList.add(new Song(
                "songs/sfx/Fire Elemental/Fire Elemental - Hurt.wav"));
        // Then, the gladiator's SFX
        songList.add(new Song(
                "songs/sfx/Gladiator/Gladiator - Attack.wav"));
        songList.add(new Song(
                "songs/sfx/Gladiator/Gladiator - Died.wav"));
        songList.add(new Song(
                "songs/sfx/Gladiator/Gladiator - Hurt.wav"));
        // Then, the minitaur's SFX
        songList.add(new Song(
                "songs/sfx/Minotaur/Minotaur - Attack.wav"));
        songList.add(new Song(
                "songs/sfx/Minotaur/Minotaur - Died.wav"));
        songList.add(new Song(
                "songs/sfx/Minotaur/Minotaur - Hurt.wav"));
        songList.add(new Song(
                "songs/sfx/Spider/Spider - Attack.wav"));
        // Then, the spider's SFX
        songList.add(new Song(
                "songs/sfx/Spider/Spider - Died.wav"));
        // And finally, the Musics
        songList.add(new Song(
                "songs/music/Boss Music.wav"));
        songList.add(new Song(
                "songs/music/Stage Song.wav"));
    }

    /**
     * Load all the sprites that will be used in the game
     * 
     * @param spriteList : The ArrayList where they will be added
     * @throws IOException
     */
    private static void loadSprites(ArrayList<BufferedImage> spriteList)
            throws IOException {
        // This loop will add all the images in the list
        for (int i = 1; i <= 8; i++) {
            spriteList.add(ImageIO.read(new File("images/" + i + ".png")));
        }
        
        for (int i = 1; i < 2; i++) {
            spriteList.add(ImageIO.read(new File("images/totem_"+i+".png")));
        }
        
        // Then, add the imagem that is used to see the tiles
        spriteList.add(ImageIO.read(new File("images/TILES REF.png")));
    }

    /**
     * Load the images used in the game HUD
     * 
     * @param hudList : The ArrayList where they will be added
     * @throws IOException
     */
    private static void loadHUD(ArrayList<BufferedImage> hudList) 
            throws IOException {
        // As in loadSprite, the loop load the images of player's life
        for (int i = 0; i <= 6; i++) {
            hudList.add(ImageIO.read(new File("images/HUD/Health_" + i + ".png")));
        }
        //After that, load the other images
        hudList.add(ImageIO.read(new File("images/HUD/Enemy_Count.png")));
        hudList.add(ImageIO.read(new File("images/HUD/Boss_Count.png")));
        hudList.add(ImageIO.read(new File("images/HUD/Key.png")));
        hudList.add(ImageIO.read(new File("images/HUD/Pause_Image.png")));
        hudList.add(ImageIO.read(new File("images/HUD/Pause_Background.png")));
        hudList.add(ImageIO.read(new File("images/HUD/Dead_Image.png")));
        hudList.add(ImageIO.read(new File("images/HUD/Dead_Background.png")));
    }
}
