/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intothedwarfness;

import java.io.File;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import intothedwarfness.Classes.Player;
import intothedwarfness.Classes.Window;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                             *                   
 *                            @Studio: Waffliors                               *
 *                             @Team: Bugnatron                                *
 *            @Devs: Matheus Vicente, Nathan Andre and Raphael Melo            *
 *                             GDD available at:                               *
 *                                                                             *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

public class IntoTheDwarfness extends JFrame {
    public static void main (String[] args) throws IOException {
        ArrayList<BufferedImage> spriteList = new ArrayList();
        loadanimations(spriteList);
         
        Player player = new Player(spriteList.get(0));
        Window screen = new Window("Into The Dwarfness", player);
        
        screen.init();
        screen.run();
    }

    private static void loadanimations(ArrayList<BufferedImage> spriteList) 
            throws IOException {
        for (int cont = 1; cont <= 17; cont++) {
            spriteList.add(ImageIO.read(new File("images/" + cont + ".png")));
        }
        /*  [0] = Dwarf
         *  [1] = Adventurer
         *  [2] = Bat
         *  [3] = Purple Totem
         *  [4] = Cyclops
         *  [5] = Destructible objects
         *  [6] = Map TileSet
         *  [7] = Red Totem
         *  [8] = Evil Warrior Right
         *  [9] = Evil Warrior(Left)
         *  [10] = Green Portal
         *  [11] = Green Totem
         *  [12] = Leaf Creature
         *  [13] = Minotaur
         *  [14] = Purple Portal
         *  [15] = Spider
         *  [16] = Worm Left
         *  [17] = Worm Right */
    }
}
