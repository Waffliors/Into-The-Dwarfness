/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Class Window, here's created the game window, where will occur the Game  * 
 *  Loop, the events will be captured and where the objects of the game will   *
 *  be drawn and animated.                                                     *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package intothedwarfness.Classes;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import intothedwarfness.Interfaces.Drawable;
import intothedwarfness.Classes.States.GameStateManager;
import java.awt.Image;
import java.awt.Toolkit;


public class Window  extends JFrame  implements  KeyListener, Drawable {
    //Creating GameStateManeger
    private static GameStateManager gsm;
    
    /*-------------------------- Constructor ---------------------------------*/
    public Window(String title) {
        super (title);
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
        Image img1 = Toolkit.getDefaultToolkit().getImage("images\\Bat_Sprite_Sheet.png");
        g.drawImage(img1, 10, 10, 500, 500, this);
    }


    //Overlapped methods of KeyListener:    
    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("Key "+e.getKeyChar()+" typed!");
    }
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key"+e.getKeyChar()+" pressed!");
    }
    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key "+e.getKeyChar()+" released!");
    }
}
