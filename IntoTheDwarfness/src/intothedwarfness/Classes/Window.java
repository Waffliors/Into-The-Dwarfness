/******************************************************************************/
/**Class Window: This class is responsible for creating the game window,     **/
/**within this window will be created and drawn the objects of the game      **/
/**(Player, Enemy and Map), collected the events of the keyboard and where   **/
/**will be the game loop.                                                    **/
/******************************************************************************/
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
import intothedwarfness.Interfaces.Drawable;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Window extends JFrame implements KeyListener {
/*****************************Class Variables**********************************/
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
        this.player = new Player(sprites.get(0));
        this.map = new Map(loadTile(), sprites.get(18));
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
        this.setBackground(new Color(47, 47, 46));

    }

/* ********************Auxiliary methods of the Constructor****************** */
    private ArrayList<TileMap> loadTile() {
        ArrayList<TileMap> mapTiles = new ArrayList();
        int x, y, srcX1, srcY1, srcX2, srcY2, id = -1;
        for (y = 0; y < 20; y++) {
            srcY1 = 32 * y;
            srcY2 = srcY1 + 32;
            for (x = 0; x < 16; x++) {
                srcX1 = 32 * x;
                srcX2 = srcX1 + 32;
                mapTiles.add(new TileMap(srcX1, srcY1, srcX2, srcY2, id += 1));
            }
        }
        return mapTiles;
    }
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
        
        //Variable used to check the excess of delayed frames
        long excess = 0;
        
        long noDelays = 0;

        final long DESIRED_UPDATE_TIME = 30;
        final long NO_DELAYS_PER_YIELD = 16;

        // Cria double-buffering strategy genérico
        this.createBufferStrategy(2);
        BufferStrategy strategy = this.getBufferStrategy();

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
                
                graphics.clearRect(0, 0, this.WIDTH, this.HEIGHT);
                for(Drawable drawable: this.drawables){
                    drawable.paintComponent(graphics);
                }
                
                graphics.dispose();
            } while (strategy.contentsRestored());
            
            strategy.show();
            
        } while (strategy.contentsLost());
    }
        
}
