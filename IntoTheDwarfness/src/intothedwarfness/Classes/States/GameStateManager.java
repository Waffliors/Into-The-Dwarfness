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
public class GameStateManager {
    
    public GameState currGameState;
    
    public GameStateManager() {
        currGameState = new PauseState();
    }
    
    public void init() {
        currGameState.init();
    }
    
    public void tick() {
        currGameState.tick();
    }
    
    public String getType() {
        return currGameState.getType();
    }
    
    public void render(Graphics g) {
        currGameState.render(g);
    }
    
    public void switchState (GameState newState) {
        currGameState = newState;
        currGameState.init();
    }
}
