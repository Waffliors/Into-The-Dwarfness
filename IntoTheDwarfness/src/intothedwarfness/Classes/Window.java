/******************************************************************************
 ** Class Window: This class is responsible for creating the game window,    **
 ** within this window will be created and drawn the objects of the game     **
 ** (Player, Enemy and Map), collected the events of the keyboard and where  **
 ** will be the game loop.                                                   **
 ******************************************************************************/
package intothedwarfness.Classes;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import intothedwarfness.Classes.States.PlayState;
import intothedwarfness.Classes.States.GameState;
import intothedwarfness.Classes.States.PauseState;
import intothedwarfness.Classes.States.GameStateManager;
import intothedwarfness.Interfaces.Drawable;

public class Window extends JFrame implements KeyListener {
/* ***************************Class Variables******************************** */
    private GameStateManager gsm;
    
    private final int WIDTH, HEIGHT;
    private final Map map;
    private final Player player;
    private final ArrayList<Character> enemies;
    private final ArrayList<BufferedImage> sprites;
    private ArrayList<Drawable> drawables;
    
/* **************************Class Constructor******************************* */
    public Window(ArrayList<BufferedImage> sprites) {
        super("Into The Dwarfness");
        
        this.sprites = sprites;
        this.map = new Map(sprites.get(18));
        this.player = new Player(sprites.get(0), map.getgUnblockedT());
        this.WIDTH = 1024;
        this.HEIGHT = 768;
        this.drawables = loadDrawables();
        this.enemies = null;
        
        this.setSize(this.WIDTH, this.HEIGHT);
        this.setLocationRelativeTo(null);
        this.setUndecorated(false);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setFocusable(true);
        this.addKeyListener(this);
        this.setIgnoreRepaint(true);
        this.setBackground(new Color(43, 43, 42));

    }

/* ********************Auxiliary methods of the Constructor****************** */
    private ArrayList<Drawable> loadDrawables() {
        ArrayList<Drawable> elements = new ArrayList();
        elements.add(this.map);
        elements.add(this.player);

        return elements;
    }
    
/* ****************************Class Methods********************************* */
    //Game Start
    public void initialize() {
        gsm = new GameStateManager();
        gsm.init();
    }
    
    //Game Loop
    public void run() throws InterruptedException {
        boolean isRunning = true;

        long excess = 0;
        long noDelays = 0;

        final long DESIRED_UPDATE_TIME = 80;
        final long NO_DELAYS_PER_YIELD = 16;

        // Cria double-buffering strategy genérico
        this.createBufferStrategy(2);
        //BufferStrategy strategy = this.getBufferStrategy();

        while (isRunning) {
            long beforeTime = System.currentTimeMillis();

            // Pula os quadros enquanto o tempo for em excesso.
            while (excess > DESIRED_UPDATE_TIME) {
                player.update();
                excess -= DESIRED_UPDATE_TIME;
            }
            player.update();
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
/* *************************Overridden Methods******************************* */
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
        if (e.getKeyChar() == '1'){
            map.stage1();
        }
        if (e.getKeyChar() == '2'){
            map.stage2();
        } 
        if (e.getKeyChar() == '3'){
            map.stage3();
        } 
        if (e.getKeyChar() == '4'){
            map.stage4();
        } 
        if (e.getKeyChar() == '5'){
            map.stage5();
        } 
        if (e.getKeyChar() == '6'){
            map.stage6();
        } 
        if (e.getKeyChar() == '7'){
            map.stage7();
        } 
        if (e.getKeyChar() == '8'){
            map.stage8();
        } 
        
        map.loadUnblockedGraph();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if ("PlayState".equals(gsm.getType())) {
            player.move(e, map);
            player.collision(e.getKeyCode());
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void paint(Graphics g) {
        BufferStrategy strategy = this.getBufferStrategy();
        do {
            do{
                Graphics graphics = strategy.getDrawGraphics();
                //Clear the previous frame
                graphics.clearRect(0, 0, this.WIDTH, this.HEIGHT);
                //For each drawable object in list, paint
                for(Drawable drawable: this.drawables){
                    drawable.paintComponent(graphics);
                }
                //Disposes of this graphics context, it's no longer referenced.
                graphics.dispose();
            } while (strategy.contentsRestored());       
            strategy.show();
        } while (strategy.contentsLost());
    }
       
    
}
