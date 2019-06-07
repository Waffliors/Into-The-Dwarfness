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
 ** 30/05/2019                                                               **
 ******************************************************************************/
package intothedwarfness;

import intothedwarfness.Classes.Song;
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import intothedwarfness.Classes.Window;
import java.net.MalformedURLException;

public class IntoTheDwarfness {

    public static void main(String[] args) throws IOException, InterruptedException{

        //Loading the images of the game
        ArrayList<BufferedImage> sprites = new ArrayList();
        for (int i = 1; i <= 8; i++) {
            sprites.add(ImageIO.read(new File("images/" + i + ".png")));
        }
        sprites.add(ImageIO.read(new File("images/TILES REF.png")));
            
        
        //Loading the songs of the game
        ArrayList<Song> songs = loadSong();
        ArrayList<BufferedImage> HUD = new ArrayList();

	    for(int i = 0; i <= 6; i ++)
	    {
	    	HUD.add(ImageIO.read(new File("images/HUD/Health_" + i + ".png")));
	    }    
	    HUD.add(ImageIO.read(new File("images/HUD/Enemy_Count.png")));
	    HUD.add(ImageIO.read(new File("images/HUD/Boss_Count.png")));
            HUD.add(ImageIO.read(new File("images/HUD/key.png")));

        //Creating the window of the game
        Window screen = new Window(sprites, songs, HUD);
        screen.initialize();
        screen.run();
    }
    
    public static ArrayList<Song> loadSong() throws MalformedURLException{
        ArrayList<Song> songs = new ArrayList();
        songs.add(new Song("songs/music/DungeonRun80bpm.wav"));
        songs.add(new Song("songs/sfx/melee sounds/sword sound.wav"));
        songs.add(new Song("songs/sfx/hurt/pain2.wav"));
        songs.add(new Song("songs/sfx/hurt/die2.wav"));
        songs.add(new Song("songs/sfx/footsteps/gravel.wav"));
        songs.add(new Song("songs/sfx/Gladiator/fight01.wav"));
        return songs;
    }
}
