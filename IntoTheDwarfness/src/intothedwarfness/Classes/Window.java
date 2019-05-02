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

/*-----------------------------------------------------------------------------*
 *                              Class Variables                                *
 *-----------------------------------------------------------------------------*/
    private final Map map;
    private final Player player;
    private GameStateManager gsm;
    

/*-----------------------------------------------------------------------------*
 *                             Class Contructor                                *
 *-----------------------------------------------------------------------------*/
    public Window(Player player, Map map) {
        super("Ola Mundo Grafico");
        this.player = player;
        this.map = map;        
        this.setSize(1024, 768);
        this.setLocationRelativeTo(null);
        this.setUndecorated(false);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setFocusable(true);
        this.addKeyListener(this);
        this.setIgnoreRepaint(true);
        this.setBackground(new Color(47, 47, 46));
    }

/*-----------------------------------------------------------------------------*
 *                              Class Methods                                  *
 *-----------------------------------------------------------------------------*/
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
    @Override
    public void keyPressed(KeyEvent e) {
        if ("PlayState".equals(gsm.getType())) {
            map.move(e);
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void paint(Graphics g) {
        map.paintComponent(g);
        //player.paintComponent(g);
    }
    
    public void initialize() {
        gsm = new GameStateManager();
        gsm.init();
    }
    
    public void run() throws InterruptedException {
        int FPS = 25;

        boolean isRunning = true;
        while (!isRunning) {
                Thread.sleep(1000 / FPS);
                repaint();
            }
        }
    
    
    
    }

