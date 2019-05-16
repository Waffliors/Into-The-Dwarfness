/** ****************************************************************************
 ** Class Window: This class is responsible for creating the game window,    **
 ** within this window will be created and drawn the objects of the game     **
 ** (Player, Enemy and Map), collected the events of the keyboard and where  **
 ** will be the game loop.                                                   **
 ***************************************************************************** */
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
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Window extends JFrame implements KeyListener {
    /* ***************************Class Variables******************************** */
    private GameStateManager gsm;
    
    private final int width, height;
    private final Map map;
    private final Player player;
    private final ArrayList<Enemy> enemies = new ArrayList();
    private final ArrayList<BufferedImage> sprites;
    private Song song;
    private ArrayList<Drawable> drawables;

    /* **************************Class Constructor******************************* */
    public Window(ArrayList<BufferedImage> sprites) {
        super("Into The Dwarfness");

        this.sprites = sprites;
        this.map = new Map(sprites.get(8));
        this.player = new Player(sprites.get(0), map);
        this.width = 1024;
        this.height = 768;
        this.drawables = loadDrawables();
        Enemy spider = new Enemy(512, 128, 2, sprites.get(3), map.getgUnblockedT());
        this.enemies.add(spider);
        this.setSize(this.width, this.height);
        
        this.drawables = loadDrawables();

        this.setSize(this.width, this.height);
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
        for (Enemy enemy : this.enemies) {
            elements.add(enemy);
        }

        return elements;
    }

    /* ****************************Class Methods********************************* */
    //Game Start
    public void initialize() {
        try {
            song = new Song("songs/music/DungeonRun80bpm.wav");
        } catch (MalformedURLException ex) {
        }
        gsm = new GameStateManager();
        gsm.init();
    }
    
    //Game Loop
    public void run() throws InterruptedException {
        song.playSound();
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
                for (Enemy enemy : this.enemies) {
                    enemy.update();
                }
                excess -= DESIRED_UPDATE_TIME;
            }
            if ("PlayState".equals(gsm.getType())) {
                player.update();
                for (Enemy enemy : this.enemies) {
                    if (enemy.isStage(this.map)) {
                        enemy.update();                        
                    }
                }
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
            player.setCurrentMove(e.getKeyChar());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.setCurrentMove('.');
        player.setLook(e.getKeyChar());
    }

    @Override
    public void paint(Graphics g) {
        BufferStrategy strategy = this.getBufferStrategy();
        do {
            do {
                Graphics graphics = strategy.getDrawGraphics();
                //Clear the previous frame
                graphics.clearRect(0, 0, this.width, this.height);
                //For each drawable object in list, paint
                for (Drawable drawable : this.drawables) {
                    if (drawable.getClass() == this.enemies.get(0).getClass()) {
                        if (drawable.isStage(this.map)) {
                            drawable.paintComponent(graphics);
                        }
                    } else {
                        drawable.paintComponent(graphics);
                    }
                }
                //Disposes of this graphics context, it's no longer referenced.
                graphics.dispose();
            } while (strategy.contentsRestored());
            strategy.show();
        } while (strategy.contentsLost());
    }
}
