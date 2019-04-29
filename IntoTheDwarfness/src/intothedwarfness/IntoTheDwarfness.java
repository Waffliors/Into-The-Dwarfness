/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.



test
 */
package intothedwarfness;

<<<<<<< HEAD
import java.io.File;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
=======
import java.io.IOException;

>>>>>>> 404e51cb210e02684a5eb38d1e238683dc0f989f
import intothedwarfness.Classes.Player;
import intothedwarfness.Classes.Window;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                             *                   
 *                            @Studio: Waffliors                               *
 *                             @Team: Bugnatron                                *
<<<<<<< HEAD
 *            @Devs: Matheus Vicente, Nathan Andre and Raphael Melo            *
=======
 *            @Devs: Matheus Vicente, Nathan Andr� and Raphael Melo            *
>>>>>>> 404e51cb210e02684a5eb38d1e238683dc0f989f
 *                             GDD available at:                               *
 *                                                                             *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

<<<<<<< HEAD
public class IntoTheDwarfness extends JFrame {
    public static void main (String[] args) throws IOException {
        ArrayList<BufferedImage> spriteList = new ArrayList();
        loadanimations(spriteList);
         
        Player player = new Player(spriteList.get(0));
=======
public class IntoTheDwarfness {
    public static void main(String[] args) throws IOException {
        //Creating Player
        Player player = new Player();
        new Tilemap(0,0,0,0,0,0,0,0);
        //Initializing game window:
>>>>>>> 404e51cb210e02684a5eb38d1e238683dc0f989f
        Window screen = new Window("Into The Dwarfness", player);
        
        screen.init();
        screen.run();
    }
<<<<<<< HEAD

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
=======
>>>>>>> 404e51cb210e02684a5eb38d1e238683dc0f989f
}
