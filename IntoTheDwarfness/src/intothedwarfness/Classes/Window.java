/** ****************************************************************************
 ** Class Window: This class is responsible for creating the game window,     **
 ** within this window will be created and drawn the objects of the game      **
 ** (Player, Enemy and Map), collected the events of the keyboard and where   **
 ** will be the game loop.                                                    **
 ***************************************************************************** */
package intothedwarfness.Classes;

import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import intothedwarfness.IA.Node;
import intothedwarfness.IA.AStar;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import intothedwarfness.Interfaces.Drawable;
import intothedwarfness.Interfaces.Collidable;
import intothedwarfness.Classes.States.PlayState;
import intothedwarfness.Classes.States.GameState;
import intothedwarfness.Classes.characters.Enemy;
import intothedwarfness.Classes.characters.Player;
import intothedwarfness.Classes.States.PauseState;
import intothedwarfness.Classes.States.GameStateManager;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Window extends JFrame implements KeyListener {

    // Constants
    private final Map MAP;
    private final ArrayList<Song> SONGS;
    private final int SCREEN_WIDTH, SCREEN_HEIGHT;
    private final ArrayList<BufferedImage> SPRITES;
    private boolean pause;

    // Variables of the class
    private Player PLAYER;
    private ArrayList<Enemy> ENEMIES;
    private HUD HUD;
    private List<Node> path;
    private GameStateManager gsm;
    private ArrayList<Drawable> DRAWABLES;

    /**
     * *
     * Game Screen Constructor
     *
     * @param SPRITES: List of sprites that will be used in the game
     * @param SONGS: List of songs that will be used in the game
     */
    public Window(ArrayList<BufferedImage> SPRITES, ArrayList<Song> SONGS, ArrayList<BufferedImage> health_bar) {

        // Call the super to set the screen name
        super("Into The Dwarfness");
        // Set the constants
        this.SONGS = SONGS;
        this.SPRITES = SPRITES;
        this.SCREEN_WIDTH = 1024;
        this.SCREEN_HEIGHT = 768;
        this.MAP = new Map(SPRITES.get(6), 12, 16, SPRITES.get(5), SONGS);
        this.ENEMIES = new ArrayList();
        this.DRAWABLES = new ArrayList();
        try {
            buildGame(health_bar);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Set configuration of the screen
        this.setSize(this.SCREEN_WIDTH, this.SCREEN_HEIGHT);
        this.setSize(this.SCREEN_WIDTH, this.SCREEN_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setUndecorated(false);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setFocusable(true);
        this.addKeyListener(this);
        this.setBackground(new Color(39, 39, 37));
    }

    public boolean isPause() {
        return pause;
    }

    /**
     * *
     * Load all drawable elements list
     *
     * @return : ArrayList that contains all drawable elements
     */
    private ArrayList<Drawable> loadDrawables() {
        ArrayList<Drawable> elements = new ArrayList();
        // Add all game's elements
        elements.add(this.MAP);
        for (Enemy enemy : this.ENEMIES) {
            elements.add(enemy);
        }
        elements.add(this.PLAYER);
        elements.add(this.HUD);
        return elements;
    }

    /**
     * *
     * Initialize the Game state manager
     */
    public void initialize() {
        gsm = new GameStateManager();
        gsm.init();
    }

    /**
     * *
     * Initialize all the enemies of the game
     *
     * @return : ArrayList that contains all game's enemies
     */
    private ArrayList<Enemy> enemiesFactory() {
        ArrayList<Enemy> enemies = new ArrayList();
        // All the enemies will collide with the player
        ArrayList<Collidable> enemyCollidables = new ArrayList();
        enemyCollidables.add(PLAYER);

        Enemy spider_1 = new Enemy(64 * 4, 64 * 9, 1, SPRITES.get(2), SONGS, MAP,
                enemyCollidables, 0);
        Enemy spider_2 = new Enemy(64 * 2, 64 * 1, 2, SPRITES.get(2), SONGS, MAP,
                enemyCollidables, 0);
        Enemy spider_3 = new Enemy(64 * 6, 64 * 8, 2, SPRITES.get(2), SONGS, MAP,
                enemyCollidables, 0);

        Enemy fire_elemental_1 = new Enemy(64 * 3, 64 * 3, 3, SPRITES.get(7), SONGS, MAP,
                enemyCollidables, 1);
        Enemy fire_elemental_2 = new Enemy(64 * 9, 64 * 4, 3, SPRITES.get(7), SONGS, MAP,
                enemyCollidables, 1);
        Enemy fire_elemental_3 = new Enemy(64 * 9, 64 * 2, 3, SPRITES.get(7), SONGS, MAP,
                enemyCollidables, 1);
        Enemy fire_elemental_4 = new Enemy(64 * 12, 64 * 8, 3, SPRITES.get(7), SONGS, MAP,
                enemyCollidables, 1);

        Enemy gladiator_1 = new Enemy(64 * 3, 64 * 4, 4, SPRITES.get(3), SONGS, MAP,
                enemyCollidables, 2);
        Enemy gladiator_2 = new Enemy(64 * 12, 64 * 4, 4, SPRITES.get(3), SONGS, MAP,
                enemyCollidables, 2);
        Enemy gladiator_3 = new Enemy(64 * 8, 64 * 5, 5, SPRITES.get(3), SONGS, MAP,
                enemyCollidables, 2);
        Enemy gladiator_4 = new Enemy(64 * 7, 64 * 8, 5, SPRITES.get(3), SONGS, MAP,
                enemyCollidables, 2);

        Enemy minotaur_1 = new Enemy(64 * 12, 64 * 4, 6, SPRITES.get(4), SONGS, MAP,
                enemyCollidables, 3);
        Enemy minotaur_2 = new Enemy(64 * 4, 64 * 9, 8, SPRITES.get(4), SONGS, MAP,
                enemyCollidables, 3);
        Enemy minotaur_3 = new Enemy(64 * 8, 64 * 9, 8, SPRITES.get(4), SONGS, MAP,
                enemyCollidables, 3);

        enemies.add(spider_1);
        enemies.add(spider_2);
        enemies.add(spider_3);
        enemies.add(fire_elemental_1);
        enemies.add(fire_elemental_2);
        enemies.add(fire_elemental_3);
        enemies.add(fire_elemental_4);

        enemies.add(gladiator_1);
        enemies.add(gladiator_2);
        enemies.add(gladiator_3);
        enemies.add(gladiator_4);

        enemies.add(minotaur_1);
        enemies.add(minotaur_2);
        enemies.add(minotaur_3);

        return enemies;
    }

    private void buildGame(ArrayList<BufferedImage> health_bar) throws FontFormatException, IOException {
        if (this.ENEMIES.size() > 0) {
            this.ENEMIES.clear();
        }
        if (this.DRAWABLES.size() > 0) {
            this.DRAWABLES.clear();
        }
        this.PLAYER = new Player(SPRITES.get(0), SONGS, MAP);
        this.ENEMIES = this.enemiesFactory();
        this.HUD = new HUD(health_bar, this.PLAYER);
        this.PLAYER.receiveCollidables(ENEMIES);
        this.DRAWABLES = loadDrawables();
        this.MAP.stageCreator(1);
        initialize();
    }

    // Game Loop
    public void run() throws InterruptedException {
        boolean isRunning = true;
        SONGS.get(15).playSound();

        long excess = 0;
        long noDelays = 0;

        final long DESIRED_UPDATE_TIME = 80;
        final long NO_DELAYS_PER_YIELD = 16;

        // Cria double-buffering strategy genérico
        this.createBufferStrategy(2);
        // BufferStrategy strategy = this.getBufferStrategy();

        // Set path and pass it to enemy
        for (Enemy enemy : this.ENEMIES) {
            setRandomPath(enemy);
        }

        while (isRunning) {
            long beforeTime = System.currentTimeMillis();
            // Pula os quadros enquanto o tempo for em excesso.
            while (excess > DESIRED_UPDATE_TIME) {
                PLAYER.update();
                PLAYER.receiveCollidables(ENEMIES);
                for (Enemy enemy : this.ENEMIES) {
                    enemy.update();
                }
                excess -= DESIRED_UPDATE_TIME;
            }
            if ("PlayState".equals(gsm.getType())) {

                int temp = 0;
                int temp2 = 0;
                PLAYER.update();
                PLAYER.receiveCollidables(ENEMIES);

                for (Enemy enemy : this.ENEMIES) {
                    if (enemy.isStage(this.MAP)) {
                        if (enemy.getXPosition() % 64 == 0 && enemy.getYPosition() % 64 == 0) {
                            if (enemy.inRange(PLAYER)) {
                                pathToPlayer(enemy);
                            }
                            if (enemy.endedPath()) {
                                setRandomPath(enemy);
                            }
                        }
                        enemy.update();
                    }
                    if (enemy.died()) {
                        temp = temp + 1;
                    }
                    if (enemy.died() && "BossType".equals(enemy.getType())) {
                        temp2 = temp2 + 1;
                        temp = temp - 1;
                    }
                    PLAYER.setEnemiesKilledCount(temp);
                    PLAYER.setBossKilledCount(temp2);

                    if (PLAYER.getBossKilledCount() >= 1) {
                        MAP.setPortal(true);
                    }
                }
            }

            // map.getNode(screenWidth, screenWidth);
            repaint();
            if (PLAYER.getLife() == 0) {
                try {
                    buildGame(HUD.getHealthBar());
                } catch (FontFormatException | IOException ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
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

    private void setRandomPath(Enemy enemy) {
        Random random = new Random();
        boolean find = false;
        Node node = null;

        while (!find) {
            node = MAP.getNode(random.nextInt(11), random.nextInt(15));
            if (!node.isBlocked()) {
                find = true;
            }
        }

        this.path = AStar.aStar(enemy.getNodePos(), node, MAP);
        enemy.setPath(path);

    }

    private void pathToPlayer(Enemy enemy) {
        Node end = PLAYER.getNodePos();
////        if (!PLAYER.getNodePos().getNeighbors().get(0).isBlocked()) {
////            end = PLAYER.getNodePos().getNeighbors().get(0);
////        } else if (!PLAYER.getNodePos().getNeighbors().get(1).isBlocked()) {
////            end = PLAYER.getNodePos().getNeighbors().get(1);
////        }
        this.path = AStar.aStar(enemy.getNodePos(), end, MAP);
        enemy.setPath(path);
        if (path == null) {
            setRandomPath(enemy);
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
            PLAYER.setCurrentMove(e.getKeyChar());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        PLAYER.setCurrentMove('.');
        PLAYER.setLook(e.getKeyChar());
    }

    @Override
    public void paint(Graphics g) {

        BufferStrategy strategy = this.getBufferStrategy();
        if (strategy == null) {
            return;
        }
        do {
            do {
                Graphics graphics = strategy.getDrawGraphics();
                // Clear the previous frame
                graphics.clearRect(0, 0, this.SCREEN_WIDTH, this.SCREEN_HEIGHT);
                // For each drawable object in list, paint
                for (Drawable drawable : this.DRAWABLES) {
                    if (drawable.getClass() == this.ENEMIES.get(0).getClass()) {
                        if (drawable.isStage(this.MAP)) {
                            drawable.paintComponent(graphics);
                        }
                    } else {
                        drawable.paintComponent(graphics);
                    }
                }
                if ("PauseState".equals(gsm.getType())) {
                    HUD.paintPauseMenu(graphics);
                }
                // Disposes of this graphics context, it's no longer referenced.
                graphics.dispose();
            } while (strategy.contentsRestored());
            strategy.show();
        } while (strategy.contentsLost());
    }
}
