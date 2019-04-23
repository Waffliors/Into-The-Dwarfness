/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Class Window, here's created the game window, where will occur the Game    * 
 *  Loop, the events will be captured and where the objects of the game will   *
 *  be drawn and animated.                                                     *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package intothedwarfness.Classes;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import intothedwarfness.Classes.States.PlayState;
import intothedwarfness.Classes.States.GameState;
import intothedwarfness.Classes.States.PauseState;
import intothedwarfness.Classes.States.GameStateManager;
import intothedwarfness.IntoTheDwarfness;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Window  extends IntoTheDwarfness  implements  KeyListener {
    
    /*------------------------------------------------------------------------*
     *------------------------ Class Variables -------------------------------*
     *------------------------------------------------------------------------*/
    
    private Player player;
    private Tilemap tilemap;
    private static GameStateManager gsm;
    private boolean test;
    private Timer time;
    
    /*------------------------------------------------------------------------*
     *----------------------- Class Constructor ------------------------------*
     *------------------------------------------------------------------------*/
    
    public Window(String title, Player player, Tilemap tilemap) {
        this.player = player;
        this.tilemap = tilemap;
        this.test = true;
        //Maximize the window to fill the screen
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //Disabling Windows borders
        this.setUndecorated(true);
        //Configuring program to close when prompted
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Shows or hides this component depending on the value of parameter 
        this.setVisible(true);
        //Indicates whether this Component is focusable
        this.setFocusable(true);
        //Records this in the list of events to be passed
        this.addKeyListener(this);
        //
        
        
        //
    }
    
    /*------------------------------------------------------------------------*
     *------------------------- Class Methods --------------------------------*
     *------------------------------------------------------------------------*/
    
    //Method init: Initialize GameState
    public void init() {
        gsm = new GameStateManager();
        gsm.init();
    }

    //Initialize GameLoop
    public void run() {
        boolean done = true;
        while (done) {
            try {
                tick();
                Thread.sleep(145);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }

    //Game Clock
    public void tick() {
        gsm.tick();
    }
    
    //Paint the screen
    @Override
    public void paint(Graphics g) {
        //Fill the background
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, super.getContentPane().getSize().width, super.getContentPane().getSize().height);
        //Drawing test image
        try {
            g.drawImage(ImageIO.read(new File("images/dwarf/idle_r_1.png")), player.getXPosition(), player.getyPosition(), 64, 64, null);
            //g.drawImage(player.draw(), player.getXPosition(), player.getyPosition(), player.getXPosition() + 64, player.getyPosition() + 64, 0, 0, 220, 233, Color.getHSBColor(135, 57, 36), null);
            
            
            
            //gsm.render(g);
        } catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Overlapped methods of KeyListener:    
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'p') {
            if (gsm.getType() == "PlayState") {
                GameState pause = new PauseState();
                gsm.switchState(pause);
            } else {
                GameState play = new PlayState();
                gsm.switchState(play);
            }
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (gsm.getType() == "PlayState") {
            player.move(e);
            repaint();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}
}
