/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intothedwarfness.Classes.States;

import java.awt.Graphics;

/**
 *
 * @author mathe
 */
public class PlayState extends GameState {

    @Override
    public void init() {}

    @Override
    public void tick() {}

    @Override
    public void render(Graphics g) {
        g.drawString("Playing", 0, 0);
    }
    
}
