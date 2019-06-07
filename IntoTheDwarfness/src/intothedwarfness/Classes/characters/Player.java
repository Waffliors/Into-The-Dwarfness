/** ***************************************************************************
 **     Player class                                                         **
 **                                                                          **
 ** The player object is one that will be manipulated by the player, the     **
 ** class is responsible for manipulating the object through the keyboard    **
 ** events passed by the Window                                              **
 **                                                                          **
 *****************************************************************************/
package intothedwarfness.Classes.characters;

import java.awt.Graphics;
import java.util.ArrayList;
import intothedwarfness.IA.Node;
import java.awt.image.BufferedImage;
import intothedwarfness.Classes.Map;
import intothedwarfness.Classes.Song;
import intothedwarfness.Classes.Point;
import intothedwarfness.Interfaces.Drawable;
import intothedwarfness.Interfaces.Collidable;

public class Player extends Character implements Drawable, Collidable {
    // Constants
    private final Map MAP;
    private final BufferedImage SPRITE;
    private final int IMGSIZE, TILESIZE;
    private final ArrayList<Song> SONGS;
    // Player's configuration
    private char currentMove;
    private int xPos, yPos, actualStage, life;
    private int enemiesKilledCount, bossKilledCount;
    // Player's lists
    private ArrayList<Point> pivots;
    private ArrayList<Enemy> enemies;
    private ArrayList<Collidable> collidables;
    // Animation
    private int cont, atkCont, hitCont, deadCont;
    private int drawRef, startLine, animation, endLine;
    private boolean looking2Right, attacking, hitted, died;

    /* *********************** Class Constructor **************************** */
    public Player(BufferedImage spriteSheet, ArrayList<Song> songs, Map map) {
        // Set player's constants
        this.MAP = map;
        this.SPRITE = spriteSheet;
        this.IMGSIZE = 32;
        this.TILESIZE = 64;
        this.SONGS = songs;
        // Choose player settings
        this.life = 6;
        this.xPos = 512;
        this.yPos = 128;
        this.actualStage = 1;
        this.currentMove = '.';
        this.pivots = new ArrayList();
        this.collidables = new ArrayList();
        // Player's animation
        this.looking2Right = true;
        // Calls the methods that help to configure the player
        setPivot();
        initializeCollidables();
    }

    /* **************************Class Methods******************************* */

    /**
     * Method that takes the player's current position and assembles the four 
     * pivots used in collision
     */
    private void setPivot() {
        this.pivots.clear();
        this.pivots.add(new Point(this.xPos, this.yPos));
        this.pivots.add(new Point(this.xPos + TILESIZE, this.yPos));
        this.pivots.add(new Point(this.xPos, this.yPos + TILESIZE));
        this.pivots.add(new Point(this.xPos + TILESIZE, this.yPos + TILESIZE));
        // Pivots position in list:
        // at 0: Left Top
        // at 1: Right Top
        // at 2: Left Down
        // at 3: Right Down
    }

    /**
     * Method that initialize the player list of collidables
     */
    private void initializeCollidables() {
        // Clear the previous map tiles
        this.collidables.clear();
        for (Node[] nodeMap : MAP.getNodeMap()) {
            for (int j = 0; j < MAP.getNodeMap()[0].length; j++) {
                if (nodeMap[j].isBlocked()) {
                    collidables.add(nodeMap[j]);
                }
            }
        }
    }

    /**
     * Method that moves the player
     * 
     * @param key: get a key char that will tell to the method the direction of 
     * the move
     */
    private void move(char key) {
        // Save the current position
        int antXPos = this.xPos;
        int antYPos = this.yPos;

        switch (key) {
        case 'a':
            this.xPos = this.xPos - 8;
            break;
        case 'w':
            this.yPos = this.yPos - 8;
            break;
        case 's':
            this.yPos = this.yPos + 8;
            break;
        case 'd':
            this.xPos = this.xPos + 8;
            break;
        }
        // Set the new pivots
        setPivot();
        // If had collision, return to the old position
        if (collision()) {
            this.xPos = antXPos;
            this.yPos = antYPos;
        }
        // Check if the player need to switch the current stage
        checkStage(this.currentMove);
    }

    /**
     * Method that check if the player will change the stage
     *
     * @param key: get a key char that will tell to the method the direction of
     * the move
     * @return
     */
    private void checkStage(char key) {
        // Check the entries in stage 1
        if (actualStage == 1) {
            if (key == 's' && yPos >= 704 && (xPos >= 448 && xPos <= 512)) {
                this.yPos = 0;
                this.actualStage = 2;
            }
        }
        // Check the entries in stage 2
        if (actualStage == 2) {
            if (key == 'w' && yPos <= 0 && (xPos >= 448 && xPos <= 512)) {
                this.yPos = 704;
                this.actualStage = 1;
            }
            if (key == 's' && yPos >= 696 && (xPos >= 448 && xPos <= 512)) {
                this.yPos = 0;
                this.actualStage = 8;
            }
            if (key == 'd' && xPos >= 960 && (yPos >= 256 && yPos <= 384)) {
                this.xPos = 0;
                this.actualStage = 3;
            }
        }
        // Check the entries in stage 3
        if (actualStage == 3) {
            if (key == 'a' && xPos <= 8 && (yPos >= 256 && yPos <= 384)) {
                this.xPos = 960;
                this.actualStage = 2;
            }
            if (key == 's' && yPos >= 704 && (xPos >= 128 && xPos <= 832)) {
                this.yPos = 0;
                this.actualStage = 5;
            }
            if (key == 'w' && yPos <= 0 && (xPos >= 128 && xPos <= 832)) {
                this.yPos = 704;
                this.actualStage = 4;
            }
        }
        // Check the entries in stage 4
        if (actualStage == 4) {
            if (key == 's' && yPos >= 704 && (xPos >= 128 && xPos <= 832)) {
                this.yPos = 0;
                this.actualStage = 3;
            }
            if (key == 'w' && yPos <= 64 && (xPos >= 472 && xPos <= 488)) {
                this.yPos = 704;
                this.actualStage = 7;
            }
        }
        // Check the entries in stage 5
        if (actualStage == 5) {
            if (key == 'w' && yPos <= 0 && (xPos >= 128 && xPos <= 832)) {
                this.yPos = 704;
                this.actualStage = 3;
            }
            if (key == 'd' && xPos >= 960 && (yPos >= 256 && yPos <= 384)) {
                this.xPos = 0;
                this.actualStage = 6;
            }
        }
        // Check the entries in stage 6
        if (actualStage == 6) {
            if (key == 'a' && xPos <= 0 && (yPos >= 256 && yPos <= 384)) {
                this.xPos = 960;
                this.actualStage = 5;
            }
        }
        // Check the entries in stage 7
        if (actualStage == 7) {
            if (key == 's' && yPos >= 704 && (xPos >= 448 && xPos <= 512)) {
                this.yPos = 64;
                this.actualStage = 4;
            }
            if (key == 'w' && yPos <= 272 && (xPos >= 400 && xPos <= 550) && 
                    MAP.showPortal()) {
                this.yPos = 384;
                this.xPos = 768;
                this.actualStage = 8;
            }
        }
        // Check the entries in stage 8
        if (actualStage == 8) {
            if (key == 'w' && yPos <= 0 && (xPos >= 448 && xPos <= 512)) {
                this.yPos = 704;
                this.actualStage = 2;
            }
        }
        // After check, create the stage and the collide list
        MAP.stageCreator(actualStage);
        initializeCollidables();
    }
    
    /**
     * Method that control the animation of the player
     */
    private void animate() {
        // Counters of animations
        // There are two standard animations that are continuously being
        // incremented: Idle and Running, they use the same counters
        this.cont += 1;
        
        // The others animations need another's counters because of when called,
        // they have to start from 0. At the start of animation, play de sfx
        if (attacking) {
            if(atkCont == 0){
                playSong(0);
            }
            atkCont += 1;
        }
        if (hitted) {
            if(hitCont == 0){
                playSong(2);
            }
            hitCont += 1;
        }
        if (died) {
            if(deadCont == 0){
                playSong(1);
            }
            deadCont += 1;
        }

        // The animations begin to divide by categories, starting in:
        // alive player or dead player
        // Alive player
        if (!died) {
            // Then it is divided by:
            // is looking to the right, movement and action
            if (!attacking && !hitted) {
                // Idle and Running while looking to the Right
                if (looking2Right) {
                    if (currentMove == 'a' || currentMove == 'w' || 
                        currentMove == 's' || currentMove == 'd') {
                        startAnimation(1, 0, 8);
                    }
                    if (currentMove == '.') {
                        startAnimation(0, 0, 5);
                    }
                }
                // Idle and Running while looking to the Left
                if (!looking2Right) {
                    if (currentMove == 'a' || currentMove == 'w' || 
                        currentMove == 's' || currentMove == 'd') {
                        startAnimation(6, 0, 8);
                    }
                    if (currentMove == '.') {
                        startAnimation(5, 0, 5);
                    }
                }
                // Stop condition of animations of the type "movement"
                if (cont == this.endLine) {
                    cont = this.startLine;
                }
                // All the animations of moving types use the same counter
                this.drawRef = cont;
            }
            // Attack animations
            if (attacking) {
                if (looking2Right) {
                    startAnimation(2, 0, 7);
                }
                if (!looking2Right) {
                    startAnimation(7, 0, 7);
                }
                // Play the song of this animation
                if (atkCont == 1) {
                }
                // Stop condition of animations of the type "atatck"
                if (atkCont == this.endLine) {
                    atkCont = 0;
                    cont = 0;
                    attacking = false;
                }
                this.drawRef = atkCont;
            }
            // Animations to get hit
            if (hitted) {
                if (looking2Right) {
                    startAnimation(3, 0, 4);
                }
                if (!looking2Right) {
                    startAnimation(8, 0, 4);
                }
                // Play song of this animation
                if (hitCont == 1) {
                }
                // Stop condition of animations of the type "get hit", also
                // decrease the player's life
                if (hitCont == endLine) {
                    cont = 0;
                    hitCont = 0;
                    hitted = false;
                    life -= 1;
                    if (life == 0) {
                        died = true;
                    }
                }
                this.drawRef = hitCont;
            }
        }
        // When the player died
        if (died) {
            if (looking2Right) {
                startAnimation(4, 0, 7);
            }
            if (!looking2Right) {
                startAnimation(9, 0, 7);
            }
            // Play song of this animation
            if (deadCont == 1) {
            }
            // Stop condition of animations of the type "died"
            if (deadCont == this.endLine) {
                deadCont = endLine - 1;
            }
            this.drawRef = deadCont;
        }
    }
    
    /**
     * Method that set the current animation
     *
     * @param animation : set the line of animation in the sprite
     * @param startLine : set the start line of animation
     * @param endLine : set the final collumn of the animation
     */
    private void startAnimation(int animation, int startLine, int endLine) {
        this.startLine = startLine;
        this.animation = animation;
        this.endLine = endLine;
    }

    /**
     * Method that play the player's SFX
     *
     * @param ref : the position of the sound selected in the list of SFX
     */
    private void playSong(int ref) {
        SONGS.get(ref).playSoundOnce();
    }
    
    /**
     * Method that receives the char of the window's KeyEvent and set the 
     * player's state
     * 
     * @param currentMove : the pressed key in window
     */
    public void setCurrentMove(char currentMove) {
        // Caps Lock filter
        switch (currentMove) {
            case 'A':
                currentMove = 'a';
                break;
            case 'W':
                currentMove = 'w';
                break;
            case 'D':
                currentMove = 'd';
                break;
            case 'S':
                currentMove = 's';
                break;
        }
        // Set the current move
        if (!died) {
            if (currentMove == 'd') {
                looking2Right = true;
            }
            if (currentMove == 'a') {
                looking2Right = false;
            }
            if (currentMove == ' ') {
                if (this.atkCont == 0) {
                    this.attacking = true;
                }
            }
            this.currentMove = currentMove;
        }
    }
    
    /**
     * Recieve the list of the enemies created in Window
     * 
     * @param enemies : ArrayList with the enemies
     */
    public void recieveCollidables(ArrayList<Enemy> enemies) {
        // Set the enemy list
        this.enemies = enemies;
        // Add the enemies in the collidables list
        for (Enemy enemy : this.enemies) {
            if (enemy.isStage(this.MAP)) {
                collidables.add(enemy);
            }
        }
    }

    /**
     * Method that check if the player collide
     * 
     * @return : true if collide, false if not;
     */
    private boolean collision() {
        // Get the sides of the player
        int rSide = (this.getPivotRT().getX() + this.getPivotRD().getX());
        int lSide = (this.getPivotLT().getX() + this.getPivotLD().getX());
        int topSide = (this.getPivotRT().getY() + this.getPivotLT().getY());
        int underSide = (this.getPivotRD().getY() + this.getPivotLD().getY());
        // For each colliding object to pick up the sides of the object and
        // compare with those of the player
        for (Collidable c : this.collidables) {
            int rSide_C = (c.getPivotRT().getX() + c.getPivotRD().getX());
            int lSide_C = (c.getPivotLT().getX() + c.getPivotLD().getX());
            int topSide_C = (c.getPivotRT().getY() + c.getPivotLT().getY());
            int underSide_C = (c.getPivotRD().getY() + c.getPivotLD().getY());
            // Is the right edge of the player to the right of the left edge of 
            //the object?
            if (rSide > lSide_C) {
                // Is the left edge of the player to the left of the right edge 
                //of the object?
                if (lSide < rSide_C) {
                    // The bottom edge of the player is below the top edge of 
                    //the object?
                    if (underSide > topSide_C) {
                        // Is the top edge of the player above the bottom edge 
                        //of the object?
                        if (topSide < underSide_C) {
                            if ("EnemyType".equals(c.getType()) 
                                || "BossType".equals(c.getType())) {
                                if (attacking && atkCont == 0) {
                                    c.gotHit();
                                }
                                return false;
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Method that checks if the player stopped any action
     * @param view 
     */
    public void setLook(char view) {
        if (view == 'a' || view == 'w' || view == 'd' || 
            view == 's' || view == ' ') {
            this.cont = 0;
        }
    }

    /**
     * Method that paint the player
     * @param g : the graphic context
     */
    @Override
    public void paintComponent(Graphics g) {
        // Get a piece of the Image based on animation counters and super matrix
        BufferedImage image = SPRITE.getSubimage(
                super.tile_32x32[drawRef][animation].getSrcX1(),
                super.tile_32x32[drawRef][animation].getSrcY1(), 
                IMGSIZE, IMGSIZE);
        // Draw in the player's position
        g.drawImage(image, xPos, yPos, 64, 64, null);
    }
    
    /**
     * Method called in window that upadate the player
     */
    @Override
    public void update() {
        // Moves the player to the saved position
        if (!died) {
            move(this.currentMove);
        }
        // Do the animations
        animate();
    }
    
    /**
     * @return the player's x position on the screen
     */
    public int getXPosition() {
        return this.xPos;
    }

    /**
     * @return the player's y position on the screen
     */
    public int getYPosition() {
        return this.yPos;
    }

    /**
     * @return the player's list of collidables 
     */
    public ArrayList<Collidable> getCollidables() {
        return collidables;
    }

    /**
     * @return the player's actual node in NodeMap 
     */
    public Node getNodePos() {
        return this.MAP.getNode(yPos / 64, xPos / 64);
    }

    /**
     * @return the list of enemies 
     */
    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }

    /**
     * @return the LeftTop pivot
     */
    @Override
    public Point getPivotLT() {
        return this.pivots.get(0);
    }
    
    /**
     * @return the RightTop pivot
     */
    @Override
    public Point getPivotRT() {
        return this.pivots.get(1);
    }

    /**
     * @return the LeftDown pivot
     */
    @Override
    public Point getPivotLD() {
        return this.pivots.get(2);
    }

    /**
     * @return the RightDown pivot
     */
    @Override
    public Point getPivotRD() {
        return this.pivots.get(3);
    }

    /**
     * @return the object type
     */
    @Override
    public String getType() {
        return "PlayerType";
    }

    /**
     * Method that tell to the object that he got hitted
     */
    @Override
    public void gotHit() {
        this.hitted = true;
    }

    /**
     * @return the actual stage of the game
     */
    public int getActualStage() {
        return actualStage;
    }

    /**
     * @return the player's life 
     */
    public int getLife() {
        return this.life;
    }

    /**
     * Method that increment the enemies killed counter
     * @param enemiesKilledCount 
     */
    public void setEnemiesKilledCount(int enemiesKilledCount) {
        this.enemiesKilledCount = enemiesKilledCount;
    }

    /**
     * Method that increment the boss killed counter
     * @param bossKilledCount
     */
    public void setBossKilledCount(int bossKilledCount) {
        this.bossKilledCount = bossKilledCount;
    }

    /**
     * @return : get the number of killed enemies 
     */
    public int getEnemiesKilledCount() {
        return enemiesKilledCount;
    }
    
    /**
     * @return : get the number of killed bosses 
     */
    public int getBossKilledCount() {
        return bossKilledCount;
    }

    @Override
    public Boolean isStage(Map map) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
