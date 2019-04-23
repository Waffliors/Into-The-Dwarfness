/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intothedwarfness;

import java.io.IOException;
import intothedwarfness.Classes.Player;
import intothedwarfness.Classes.Tile;
import intothedwarfness.Classes.Tilemap;
import intothedwarfness.Classes.Window;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                             *                   
 *                            @Studio: Waffliors                               *
 *                             @Team: Bugnatron                                *
 *            @Devs: Matheus Vicente, Nathan Andre and Raphael Melo            *
 *                             GDD available at:                               *
 *                                                                             *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

public class IntoTheDwarfness extends JFrame {
    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<BufferedImage>> animationslist= new ArrayList();
        loadanimations(animationslist);
        
        
        Player player = new Player(animationslist);
        Tilemap tilemap = new Tilemap();
        Window screen = new Window("Into The Dwarfness", player, tilemap);
        
        
        ArrayList<Tile> tilelist = new ArrayList();
        
        screen.init();
        screen.run();
    }
    
    private static void loadanimations(ArrayList<ArrayList<BufferedImage>> animations) throws IOException{
        ArrayList<BufferedImage> dwarf = new ArrayList();
        dwarf.add(ImageIO.read(new File("images/dwarf/idle_r_1.png")));
        dwarf.add(ImageIO.read(new File("images/dwarf/idle_r_2.png")));
        dwarf.add(ImageIO.read(new File("images/dwarf/idle_r_3.png")));
        dwarf.add(ImageIO.read(new File("images/dwarf/idle_r_4.png")));
        dwarf.add(ImageIO.read(new File("images/dwarf/idle_r_5.png")));
        animations.add(dwarf);
       
    }
}
