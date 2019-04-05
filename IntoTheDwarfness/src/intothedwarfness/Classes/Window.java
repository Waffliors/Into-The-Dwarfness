/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intothedwarfness.Classes;

import javax.swing.JFrame;
/**
 *
 * @authors: Matheus Vicente 
 */
public class Window {
    JFrame frame;
    
    public int width, height;
    public String title;
    
    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        
        createFrame();
    }
    
    public void createFrame() {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
