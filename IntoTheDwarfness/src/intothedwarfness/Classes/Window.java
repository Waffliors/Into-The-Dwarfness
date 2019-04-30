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

public class Window extends JFrame implements KeyListener {

    /*------------------------------------------------------------------------*
     *------------------------ Class Variables -------------------------------*
     *------------------------------------------------------------------------*/
    private final Map map;
    private final Player player;
    private GameStateManager gsm;

    /*------------------------------------------------------------------------*
     *----------------------- Class Constructor ------------------------------*
     *------------------------------------------------------------------------*/
    public Window(Player player, Map map) {
        this.map = map;
        this.player = player;
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
        //Ignore repaint parameters
        this.setIgnoreRepaint(true);
    }

    /*------------------------------------------------------------------------*
     *------------------------- Class Methods --------------------------------*
     *------------------------------------------------------------------------*/
    //Method init: Initialize GameState
    public void init() {
        gsm = new GameStateManager();
        gsm.init();
    }

    //Method run: Execute the GameLoop
    public void run() {
        boolean done = true;
        while (done) {
            repaint();
        }
    }

    //Method tick: It's the Game Clock
    public void tick() {
        gsm.tick();
    }

    //Method paint: It's the method that paint the Window 
    @Override
    public void paint(Graphics g) {
        //Clear the previous 
        //g.clearRect(0, 0, super.getContentPane().getSize().width, super.getContentPane().getSize().height);
        //Paint
        //g.setColor(new Color(47, 47, 46));
        //g.fillRect(0, 0, super.getContentPane().getSize().width, super.getContentPane().getSize().height);
        map.paintComponent(g);
        player.paintComponent(g);
    }

    //Method keyTyped: listen when the key has been typed   
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'p') {
            if ("PlayState".equals(gsm.getType())) {
                GameState pause = new PauseState();
                gsm.switchState(pause);
            } else {
                GameState play = new PlayState();
                gsm.switchState(play);
            }
        }
    }

    //Method keyPressed: listen when the key is pressed
    @Override
    public void keyPressed(KeyEvent e) {
        if ("PlayState".equals(gsm.getType())) {
            Map.move(e);
        }
    }

    ////Method keyTyped: listen when the key has been released
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
