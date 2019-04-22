/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intothedwarfness;

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

public class IntoTheDwarfness {
    public static void main(String[] args) {
        //Creating Player
        Player player = new Player();
        //Initializing game window:
        Window screen = new Window("Into The Dwarfness", player);
        //Starting GameState and the game loop:
        screen.init();
        screen.run();
    }
}
