/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Class Window, here's created the game window, where will occur the Game    * 
 *  Loop, the events will be captured and where the objects of the game will   *
 *  be drawn and animated.                                                     *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package intothedwarfness.Classes;

import intothedwarfness.Classes.States.GameState;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import intothedwarfness.Classes.States.GameStateManager;
import intothedwarfness.Classes.States.PauseState;
import intothedwarfness.Classes.States.PlayState;
import java.awt.Color;

public class Window  extends JFrame  implements  KeyListener {

    private Player player;
    private static GameStateManager gsm;
    
    /*-------------------------- Constructor ---------------------------------*/
    public Window(String title, Player player) {
        super (title);
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
    }
    
    /*---------------------------- Methods -----------------------------------*/
    
    //Initialize GameState
    public void init() {
        gsm = new GameStateManager();
        gsm.init();
    }

    //Initialize GameLoop
    public void run() {
        boolean done = true;
        while (!done) {
            System.out.println("GameStateManager atual: " + gsm.getType());
            try {
                tick();
                repaint();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
        g.fillRect(0, 0, super.getContentPane().getSize().width, 
                         super.getContentPane().getSize().height);
        
        //Drawing test image
        g.drawImage(player.draw(), player.getXPosition(), player.getyPosition(),this);

        gsm.render(g);

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
        player.move(e);
        repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {}
}
