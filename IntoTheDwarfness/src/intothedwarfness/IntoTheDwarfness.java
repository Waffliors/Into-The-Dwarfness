/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intothedwarfness;

import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import javax.imageio.ImageIO;
import intothedwarfness.Classes.Map;
import java.awt.image.BufferedImage;
import intothedwarfness.Classes.Player;
import intothedwarfness.Classes.Window;
import intothedwarfness.Classes.TileMap;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                             *                   
 *                            @Studio: Waffliors                               *
 *                             @Team: Bugnatron                                *
 *            @Devs: Matheus Vicente, Nathan Andre and Raphael Melo            *
 *                             GDD available at:                               *
 *                                                                             *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

public class IntoTheDwarfness{
    public static void main (String[] args) throws IOException {
        
        //Loading the list of Sprites
        ArrayList<BufferedImage> spriteList = new ArrayList();
        loadanimations(spriteList);
        
        //Loading the list of Tiles
        ArrayList<TileMap> TilemapList = new ArrayList<>();
    	loadTile(TilemapList);
        
        //Creating the Map that receives a image and the tiles
        Map map = new Map(TilemapList, spriteList.get(18));
        
        //Initializing the Player
        Player player = new Player(spriteList.get(0));

        //Initializing game window:
        Window screen = new Window(player, map);
        
        screen.init();
        screen.run();
    }

    //Function that load the animations
    private static void loadanimations(ArrayList<BufferedImage> spriteList) throws IOException {
        for (int cont = 1; cont <= 19; cont++) {
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
         *  [17] = Worm Right
         *  [18] = TileSet
         */
    }

    //Function that initialize the Tiles for the TilemapList
    private static void loadTile(ArrayList<TileMap> TilemapList) {
        int x, y,srcX1,srcY1,
                srcX2 = 32,
                srcY2 = 32;

        for (y = 0; y < 20; y++) {
            srcX1 = 0;
            srcX2 = 0;
            srcY1 = 32 * y;
            srcY2 = srcY1 + 32;

            for (x = 0; x < 16; x++) {
                srcX1 = 32 * x;
                srcX2 = srcX1 + 32;

                TilemapList.add(new TileMap(srcX1, srcY1, srcX2, srcY2));
            }
        }
    }
    
    
}
