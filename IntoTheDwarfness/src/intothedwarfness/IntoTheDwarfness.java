/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intothedwarfness;

import java.io.IOException;

import intothedwarfness.Classes.Player;
import intothedwarfness.Classes.Tilemap;
import intothedwarfness.Classes.Window;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                             *                   
 *                            @Studio: Waffliors                               *
 *                             @Team: Bugnatron                                *
 *            @Devs: Matheus Vicente, Nathan André and Raphael Melo            *
 *                             GDD available at:                               *
 *                                                                             *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

public class IntoTheDwarfness {
    public static void main(String[] args) throws IOException {
        //Creating Player
        Player player = new Player();
        Tilemap tilemap = new Tilemap(0, 0, 0, 0, 0, 0, 0, 0);
        
        //Initializing game window:
        Window screen = new Window("Into The Dwarfness", player);
        //Starting GameState and the game loop:
        screen.init();
        screen.run();
    }
}
