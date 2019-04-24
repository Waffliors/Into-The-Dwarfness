/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intothedwarfness;

import javax.swing.JFrame;
import java.io.IOException;
import intothedwarfness.Classes.Player;
import intothedwarfness.Classes.Tilemap;
import intothedwarfness.Classes.Window;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                             *                   
 *                            @Studio: Waffliors                               *
 *                             @Team: Bugnatron                                *
 *            @Devs: Matheus Vicente, Nathan Andr� and Raphael Melo            *
 *                             GDD available at:                               *
 *                                                                             *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

public class IntoTheDwarfness extends JFrame {
    public static void main (String[] args) throws IOException {
        
        //For the game will be used a list that will contain the spritesheets
        ArrayList<BufferedImage> spriteList = new ArrayList();
        loadanimations(spriteList);
         
        //Creating Player
        Player player = new Player(spriteList.get(0));
        Tilemap tilemap = new Tilemap(0, 0, 0, 0, 0, 0, 0, 0);
        
        //Initializing game window:
        Window screen = new Window("Into The Dwarfness", player);
        //Starting GameState and the game loop:
        screen.init();
        screen.run();
    }

    private static void loadanimations(ArrayList<BufferedImage> spriteList) 
            throws IOException {
        for (int cont = 1; cont <= 17; cont++) {
            spriteList.add(ImageIO.read(new File("images/" + cont + ".png")));
        }
        /*The organiaztion of spriteList:
         *[0] = Dwarf
         *[1] = Adventurer
         *[2] = Bat
         *[3] = Purple Totem
         *[4] = Cyclops
         *[5] = Destructible objects
         *[6] = Map TileSet
         *[7] = Red Totem
         *[8] = Evil Warrior Right
         *[9] = Evil Warrior(Left)
         *[10] = Green Portal
         *[11] = Green Totem
         *[12] = Leaf Creature
         *[13] = Minotaur
         *[14] = Purple Portal
         *[15] = Spider
         *[16] = Worm Left
         *[17] = Worm Right
         */
    }
}
