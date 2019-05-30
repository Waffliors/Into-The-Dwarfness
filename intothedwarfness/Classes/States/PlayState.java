/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intothedwarfness.Classes.States;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author matheus
 */
public class PlayState extends GameState {

    @Override
    public void init() {System.out.println("Initialize play");}

    @Override
    public void tick() {
        
    }

    @Override
    public String getType() {
        return "PlayState";
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawString("Playing", 10, 10);
    }
    
}
