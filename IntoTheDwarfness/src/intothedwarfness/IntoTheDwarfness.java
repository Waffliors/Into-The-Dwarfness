/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intothedwarfness;

import intothedwarfness.Classes.States.GameStateManager;
import intothedwarfness.Classes.Window;

/**
 * @Studio: Waffliors
 * @Devs: Matheus Vicente, Nathan Andre and Raphael Melo
 */
public class IntoTheDwarfness {

    /**
     * @param args the command line arguments
     */
    public static GameStateManager gsm;
    
    public static void main(String[] args) {
        Window screen = new Window(800, 600, "Into The Dwarfness");

        init();
        run();
    }
    
    public static void init() {
        gsm = new GameStateManager();
        gsm.init();
    }

    public static void run() {
        boolean done = true;

        while (!done) {
            try {
                tick();
                //repaint();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void tick() {
        gsm.tick();
    }
}
