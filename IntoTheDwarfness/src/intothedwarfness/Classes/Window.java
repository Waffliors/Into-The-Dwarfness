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
import java.awt.image.BufferStrategy;

public class Window extends JFrame implements KeyListener {

/*-----------------------------------------------------------------------------*
 *                              Class Variables                                *
 *-----------------------------------------------------------------------------*/
    private final Map map;
    private final int width;
    private final int height;
    private final Player player;
    private GameStateManager gsm;
    

/*-----------------------------------------------------------------------------*
 *                             Class Contructor                                *
 *-----------------------------------------------------------------------------*/
    public Window(Player player, Map map) {
        super("Into The Dwarfness");
        this.player = player;
        this.map = map;
        this.width = 1024;
        this.height = 768;
        this.setSize(this.width, this.height);
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
            player.move(e);
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void paint(Graphics g) {
        BufferStrategy strategy = this.getBufferStrategy();
        do {
            do {
                Graphics graphics = strategy.getDrawGraphics();
                
                graphics.clearRect(0, 0, this.width, this.height);
                map.paintComponent(graphics);
                player.paintComponent(graphics);
                graphics.dispose();
            } while (strategy.contentsRestored());
            
            strategy.show();
            
        } while (strategy.contentsLost());
    }
    
    public void initialize() {
        gsm = new GameStateManager();
        gsm.init();
    }
    
    public void run() throws InterruptedException {
         boolean isRunning = true;
        long excess = 0;
        long noDelays = 0;

        final long DESIRED_UPDATE_TIME = 60;
        final long NO_DELAYS_PER_YIELD = 16;
        
        // Cria double-buffering strategy genérico
        this.createBufferStrategy(2);
        // BufferStrategy strategy = this.getBufferStrategy();

        while (isRunning) {
            long beforeTime = System.currentTimeMillis();

            // Pula os quadros enquanto o tempo for em excesso.
            while (excess > DESIRED_UPDATE_TIME) {
                //game.processLogics();
                excess -= DESIRED_UPDATE_TIME;
            }

            repaint();

            long afterTime = System.currentTimeMillis();
            long sleepTime = afterTime - beforeTime;

            if (sleepTime < DESIRED_UPDATE_TIME) {
                Thread.sleep(DESIRED_UPDATE_TIME - sleepTime);
                noDelays = 0;
            } else {
                excess += sleepTime - DESIRED_UPDATE_TIME;

                if (++noDelays == NO_DELAYS_PER_YIELD) {
                    Thread.yield();
                }
            }
        }
    }
}
