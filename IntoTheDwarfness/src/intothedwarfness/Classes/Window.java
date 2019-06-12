/*******************************************************************************
 **     Window Class                                                          **
 **                                                                           **
 ** This class is responsible for creating the game window,                   **
 ** within this window will be created and drawn the objects of the game      **
 ** (Player, Enemy and Map), collected the events of the keyboard and where   **
 ** will be the game loop.                                                    **
 **                                                                           **
 ** For the game clock, the algorithm was used as the base available in:      **
 **                                                                           **
 ** http://www.pontov.com.br/site/index.php/java/48-java2d/121-o-loop-de-animacao
 ******************************************************************************/
package intothedwarfness.Classes;

import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import intothedwarfness.IA.Node;
import intothedwarfness.IA.AStar;
import java.awt.event.KeyListener;
import java.awt.FontFormatException;
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

public class Window extends JFrame implements KeyListener {
    // Constants
    private final Map MAP;
    private final ArrayList<Song> SONGS;
    private final int SCREEN_WIDTH, SCREEN_HEIGHT;
    private final ArrayList<BufferedImage> SPRITES;
    // Variables of the game
    private HUD HUD;
    private Player player;
    private List<Node> path;
    private GameStateManager gsm;
    private ArrayList<Enemy> enemies;
    private ArrayList<Drawable> drawables;

    /* *********************** Class Constructor **************************** */
    public Window(ArrayList<BufferedImage> SPRITES, ArrayList<Song> SONGS, 
            ArrayList<BufferedImage> health_bar) {
        // Call the super to set the screen name
        super("Into The Dwarfness");
        // Set the constants
        this.SONGS = SONGS;
        this.SPRITES = SPRITES;
        this.SCREEN_WIDTH = 1024;
        this.SCREEN_HEIGHT = 768;
        this.MAP = new Map(SPRITES.get(6), 12, 16, SPRITES.get(5), SONGS);
        this.enemies = new ArrayList();
        this.drawables = new ArrayList();
        try {
            buildGame(health_bar);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null,ex);
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

    /**
     * Load all drawable elements list
     *
     * @return : ArrayList that contains all drawable elements
     */
    private ArrayList<Drawable> loadDrawables() {
        ArrayList<Drawable> elements = new ArrayList();
        // Add all game's elements
        elements.add(this.MAP);
        for (Enemy enemy : this.enemies) {
            elements.add(enemy);
        }
        elements.add(this.player);
        elements.add(this.HUD);
        return elements;
    }

    /**
     * Initialize the Game state manager
     */
    public void initialize() {
        gsm = new GameStateManager();
        gsm.init();
    }

    /**
     * Initialize all the enemies of the game
     *
     * @return : ArrayList that contains all game's enemies
     */
    private ArrayList<Enemy> enemiesFactory() {
        // All the enemies will collide with the player
        ArrayList<Collidable> enemyCollidables = new ArrayList();
        enemyCollidables.add(player);

        // Create the spiders
        Enemy spider_1 = new Enemy(
        64 * 4, 64 * 9, 1, SPRITES.get(2), SONGS, MAP, enemyCollidables, 0);
        Enemy spider_2 = new Enemy(
        64 * 2, 64 * 1, 2, SPRITES.get(2), SONGS, MAP, enemyCollidables, 0);
        Enemy spider_3 = new Enemy(
        64 * 6, 64 * 8, 2, SPRITES.get(2), SONGS, MAP, enemyCollidables, 0);

        // Create the fire elementals
        Enemy fire_elemental_1 = new Enemy(
        64 * 3, 64 * 3, 3, SPRITES.get(7), SONGS, MAP, enemyCollidables, 1);
        Enemy fire_elemental_2 = new Enemy(
        64 * 9, 64 * 4, 3, SPRITES.get(7), SONGS, MAP, enemyCollidables, 1);
        Enemy fire_elemental_3 = new Enemy(
        64 * 9, 64 * 2, 3, SPRITES.get(7), SONGS, MAP, enemyCollidables, 1);
        Enemy fire_elemental_4 = new Enemy(
        64 * 12, 64 * 8, 3, SPRITES.get(7), SONGS, MAP, enemyCollidables, 1);

        // Create the gladiators
        Enemy gladiator_1 = new Enemy(
        64 * 3, 64 * 4, 4, SPRITES.get(3), SONGS, MAP, enemyCollidables, 2);
        Enemy gladiator_2 = new Enemy(
        64 * 12, 64 * 4, 4, SPRITES.get(3), SONGS, MAP,enemyCollidables, 2);
        Enemy gladiator_3 = new Enemy(
        64 * 8, 64 * 5, 5, SPRITES.get(3), SONGS, MAP, enemyCollidables, 2);
        Enemy gladiator_4 = new Enemy(
        64 * 7, 64 * 8, 5, SPRITES.get(3), SONGS, MAP, enemyCollidables, 2);

        // Create the minotaurs
        Enemy minotaur_1 = new Enemy(
        64 * 12, 64 * 4, 6, SPRITES.get(4), SONGS, MAP,enemyCollidables, 3);
        Enemy minotaur_2 = new Enemy(
        64 * 4, 64 * 9, 8, SPRITES.get(4), SONGS, MAP, enemyCollidables, 3);
        Enemy minotaur_3 = new Enemy(
        64 * 8, 64 * 9, 8, SPRITES.get(4), SONGS, MAP, enemyCollidables, 3);

        // Add all enemies in the enemies list
        enemies.add(spider_1);
        enemies.add(spider_2);
        enemies.add(spider_3);
        enemies.add(minotaur_1);
        enemies.add(minotaur_2);
        enemies.add(minotaur_3);
        enemies.add(gladiator_1);
        enemies.add(gladiator_2);
        enemies.add(gladiator_3);
        enemies.add(gladiator_4);
        enemies.add(fire_elemental_1);
        enemies.add(fire_elemental_2);
        enemies.add(fire_elemental_3);
        enemies.add(fire_elemental_4);
        
        return enemies;
    }

    /**
     * Method that reload the game after de player die
     * 
     * @param health_bar
     * @throws FontFormatException
     * @throws IOException 
     */
    private void buildGame(ArrayList<BufferedImage> health_bar) 
            throws FontFormatException, IOException {
        if (this.enemies.size() > 0) {
            this.enemies.clear();
        }
        if (this.drawables.size() > 0) {
            this.drawables.clear();
        }
        this.player = new Player(SPRITES.get(0), SONGS, MAP);
        this.enemies = this.enemiesFactory();
        this.HUD = new HUD(health_bar, this.player);
        this.player.receiveCollidables(enemies);
        this.drawables = loadDrawables();
        this.MAP.stageCreator(1);
        this.MAP.setPortal(false);
        initialize();
    }
    
    /**
     * Set a random path in the map for the AStar of an enemy
     * 
     * @param enemy 
     */
    private void setRandomPath(Enemy enemy) {
        Random random = new Random();
        boolean find = false;
        Node node = null;

        while (!find) {
            node = MAP.getNode(
                    random.nextInt(MAP.getLINES()), 
                    random.nextInt(MAP.getCOLUMNS()));
            if (!node.isBlocked()) {
                find = true;
            }
        }
        
        this.path = AStar.aStar(enemy.getNodePos(), node, MAP);
        enemy.setPath(path);
    }

    /**
     * Set a path in the map for the AStar between the player and the enemy
     * 
     * @param enemy 
     */
    private void pathToPlayer(Enemy enemy) {
        this.path = AStar.aStar(enemy.getNodePos(), player.getNodePos(), MAP);
        enemy.setPath(path);
        //If can't find a path to the player, set a random path
        if (path == null) {
            setRandomPath(enemy);
        }
    }
    
    /**
     * Get a key typed event
     * 
     * @param e the key typed 
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'p' || e.getKeyChar() == 'P') {
            if ("PlayState".equals(gsm.getType())) {
                GameState pause = new PauseState();
                gsm.switchState(pause);
            } else {
                GameState play = new PlayState();
                gsm.switchState(play);
            }
        }
    }
    
    /**
     * Get a key pressed event
     * 
     * @param e the key pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if ("PlayState".equals(gsm.getType())) {
            player.setCurrentMove(e.getKeyChar());
            if (player.getLife() == 0 && e.getKeyChar() == ' ') {
                try {
                    buildGame(HUD.getHealthBar());
                } catch (FontFormatException | IOException ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    /**
     * Get a key released event
     * 
     * @param e the key released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        player.setCurrentMove('.');
        player.setLook(e.getKeyChar());
    }

    /**
     * The game loop
     * 
     * @throws InterruptedException 
     */
    public void run() throws InterruptedException {
        long excess = 0;
        long noDelays = 0;
        boolean isRunning = true;
        final long NO_DELAYS_PER_YIELD = 16;
        final long DESIRED_UPDATE_TIME = 80;

        // Creates generic double buffer strategy
        this.createBufferStrategy(2);

        // Set a random path for all the enemies
        for (Enemy enemy : this.enemies) {
            setRandomPath(enemy);
        }

        while (isRunning) {
            long beforeTime = System.currentTimeMillis();
            // Skip the frames while the time is in excess.
            while (excess > DESIRED_UPDATE_TIME) {
                player.update();
                player.receiveCollidables(enemies);
                for (Enemy enemy : this.enemies) {
                    enemy.update();
                }
                excess -= DESIRED_UPDATE_TIME;
            }
            
            if ("PlayState".equals(gsm.getType())) {
                int temp = 0;
                int temp2 = 0;
                player.update();
                player.receiveCollidables(enemies);

                for (Enemy enemy : this.enemies) {
                    if (enemy.isStage(this.MAP)) {
                        if (enemy.getXPosition() % 64 == 0 && 
                                enemy.getYPosition() % 64 == 0) {
                            if (enemy.inRange(player)) {
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
                    player.setEnemiesKilledCount(temp);
                    player.setBossKilledCount(temp2);
                    if (player.getBossKilledCount() >= 1) {
                        MAP.setPortal(true);
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

    /**
     * Paint all the elements of the game
     * 
     * @param g the graphic context
     */
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
                for (Drawable drawable : this.drawables) {
                    if (drawable.getClass() == this.enemies.get(0).getClass()) {
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
