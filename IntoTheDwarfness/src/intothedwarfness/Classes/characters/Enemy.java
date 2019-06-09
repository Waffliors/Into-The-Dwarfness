/*******************************************************************************
 **     Enemy Class                                                           **
 **                                                                           **
 ** Here the enemies will be drawn, according to what will be                 **
 ** received, a different type of enemy will be drawn with their own          **
 ** characteristics and movements                                             **
 ******************************************************************************/
package intothedwarfness.Classes.characters;

import java.util.List;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import intothedwarfness.IA.Node;
import intothedwarfness.Classes.Map;
import java.awt.image.BufferedImage;
import intothedwarfness.Classes.Song;
import intothedwarfness.Classes.Point;
import intothedwarfness.Interfaces.Drawable;
import intothedwarfness.Interfaces.Collidable;

public class Enemy extends Character implements Drawable, Collidable {
    // Constants
    private final Map MAP;
    private final BufferedImage SPRITE;
    private final int IMGSIZE, TILESIZE;
    private final ArrayList<Song> SONGS;
    // Enemy's configuration
    private boolean endedPath, followingPlayer;
    private int xPos, yPos, actualStage, life, enemyType;
    // Enemy's lists
    private List<Node> path;
    private ArrayList<Point> pivots;
    private final ArrayList<Collidable> COLLIDABLES;
    // Animation
    private int drawRef, startLine, animation, endLine;
    private int cont, wait, atkCont, hitCont, deadCont, atkTimer;
    private boolean looking2Right, attacking, hitted, died, running, idle;

    /* *********************** Class Constructor **************************** */
    public Enemy(int x, int y, int stage, BufferedImage spriteSheet, 
            ArrayList<Song> songs, Map map, ArrayList<Collidable> collidables, 
            int typeEnemy) {
        
        this.enemyType = typeEnemy;  
        //Each enemy have a image size
        switch (this.enemyType) {
            case 3:
                this.IMGSIZE = 96;
                break;
            default:
                this.IMGSIZE = 32;
        }
        //Also each enemy have a number of lives
        switch (this.enemyType) {
            case 0:
                this.life = 1;
                break;
            case 2:
                this.life = 3;
                break;
            case 1:
                this.life = 2;
                break;
            case 3:
                this.life = 4;
                break;
        }
        //Constants
        this.MAP = map;
        this.TILESIZE = 64;
        this.SONGS = songs;
        this.SPRITE = spriteSheet;
        //Choose enemy settings
        this.yPos = y;
        this.xPos = x;
        this.wait = 40;
        this.endedPath = true;
        this.actualStage = stage;      
        this.pivots = new ArrayList();
        this.COLLIDABLES = collidables;
        //Enemy's animation       
        this.looking2Right = true;
        setPivot();
    }
    
    /* **************************Class Methods******************************* */
    
    /**
     * Method that takes the player's current position and assembles the four 
     * pivots used in collision
     */
    private void setPivot() {
        this.pivots.clear();
        if (enemyType == 0) {
            this.pivots.add(new Point(this.xPos, this.yPos + 32));
            this.pivots.add(new Point(this.xPos + IMGSIZE, this.yPos + 32));
            this.pivots.add(new Point(this.xPos, this.yPos + IMGSIZE));
            this.pivots.add(new Point(this.xPos + IMGSIZE, this.yPos +IMGSIZE));
        }
        if (enemyType == 3) {
            this.pivots.add(new Point(this.xPos, this.yPos));
            this.pivots.add(new Point(this.xPos + IMGSIZE, this.yPos));
            this.pivots.add(new Point(this.xPos, this.yPos + IMGSIZE));
            this.pivots.add(new Point(this.xPos + IMGSIZE, this.yPos+IMGSIZE));
        } else {
            this.pivots.add(new Point(this.xPos, this.yPos));
            this.pivots.add(new Point(this.xPos + TILESIZE, this.yPos));
            this.pivots.add(new Point(this.xPos, this.yPos + TILESIZE));
            this.pivots.add(new Point(this.xPos+TILESIZE, this.yPos+TILESIZE));
        }
        // Pivots position:
        // at 0: Left Top
        // at 1: Right Top 
        // at 2: Left Down
        // at 3: Right Down
    }
    
    /**
     * Method that makes the enemy movie, using the path list
     */
    private void move() {
            if (!endedPath) {
                // If path size equals 1, he ended the path
                if (path.size() == 1) {
                    idle = true;
                    this.cont = 0;
                    this.path = null;
                    this.running = false;
                    this.endedPath = true;
                    setPivot();
                    return;
                }
                // Get the first node in path list 
                Node currentPos = ((LinkedList<Node>) path).getFirst();
                if (((LinkedList<Node>) path).size() != 1 &&
                     this.path != null && 
                     !died) {
                    // While not ended the path, keep moving
                    this.endedPath = false;
                    this.running = true;
                    this.idle = false;
                    Node next = ((LinkedList<Node>) path).get(1);
                    if (currentPos.getX() != next.getX()) {
                        //Moving up
                        if (currentPos.getX() < next.getX()) {
                            this.yPos = this.yPos + 4;
                        }
                        //Moving down
                        if (currentPos.getX() > next.getX()) {
                            this.yPos = this.yPos - 4;
                        }
                        //Remove the node from the path
                        if (this.yPos % 64 == 0) {
                            ((LinkedList<Node>) path).removeFirst();
                        }
                    } else if (currentPos.getY() != next.getY()) {
                        //Moving to the left
                        if (currentPos.getY() < next.getY()) {
                            this.xPos = this.xPos + 4;
                            this.looking2Right = true;
                        }
                        //Moving to the right
                        if (currentPos.getY() > next.getY()) {
                            this.xPos = this.xPos - 4;
                            this.looking2Right = false;
                        }
                        //Remove the node from the path
                        if (this.xPos % 64 == 0) {
                            ((LinkedList<Node>) path).removeFirst();
                        }
                    }
                    //Check if collide with the player
                    collision();
                }
            }
        //If ended the path, wait until move it again
        if (endedPath && !followingPlayer) {
            this.idle = true;
            wait += 1;
            if (wait >= 75) {
                wait = 0;
                this.endedPath = false;
            }
        }
        //set the new pivots
        setPivot();
    }
    
    
    /**
     * Method that control the animation of the enemies
     */
    private void animate() {
        // Counters of animations
        // There are two standard animations that are continuously being
        // incremented: Idle and Running, they use the same counters
        this.cont += 1;
        // The others animations need another's counters because of when called,
        // they have to start from 0. At the start of animation, play de sfx
        // from the current enemy.
        if (this.attacking) {
            if (atkCont == 0) {
                if (enemyType == 0) {
                    playSong(12);
                }
                if (enemyType == 1) {
                    playSong(3);
                }
                if (enemyType == 2) {
                    playSong(6);
                }
                if (enemyType == 3) {
                    playSong(9);
                }
            }
            this.atkCont += 1;
            this.atkTimer += 1;
        }
        if (this.hitted) {
            if (hitCont == 0) {
                if (enemyType == 1) {
                    playSong(5);
                }
                if (enemyType == 2) {
                    playSong(8);
                }
                if (enemyType == 3) {
                    playSong(11);
                }
            }
            this.hitCont += 1;
        }
        if (this.died) {
            if (deadCont == 0) {
                if (enemyType == 0) {
                    playSong(13);
                }
                if (enemyType == 1) {
                    playSong(4);
                }
                if (enemyType == 2) {
                    playSong(7);
                }
                if (enemyType == 3) {
                    playSong(10);
                }
            }
            this.deadCont += 1;
        }

        // The animations begin to divide by categories, starting in:
        // alive enemy or dead enemy
        // Alive enemy
        if (!this.died) {
            // Then it is divided by:
            // is looking to the right, movement and action
            if (!this.attacking && !this.hitted) {
                //Idle and Running while looking to the Right
                if (this.looking2Right) {
                    //If stopped
                    if (this.idle) {
                        //Spider
                        if (this.enemyType == 0) {
                            startAnimation(8, 0, 4);
                        }
                        //Fire Elemental
                        if (this.enemyType == 1) {
                            startAnimation(1, 0, 5);
                        }
                        //Gladiator
                        if (this.enemyType == 2) {
                            startAnimation(5, 0, 5);
                        }
                        //Minotaur
                        if (this.enemyType == 3) {
                            startAnimation(0, 0, 4);
                        }
                    }
                    //If are running
                    if (this.running) {
                        //Spider
                        if (this.enemyType == 0) {
                            startAnimation(9, 0, 5);
                        }
                        //Fire Elemental
                        if (this.enemyType == 1) {
                            startAnimation(0, 0, 7);
                        }
                        //Gladiator
                        if (this.enemyType == 2) {
                            startAnimation(6, 0, 7);
                        }
                        //Minotaur
                        if (this.enemyType == 3) {
                            startAnimation(1, 0, 7);
                        }
                    }
                }
                //Idle and Running while looking to the Left
                if (!this.looking2Right) {
                    //If stopped
                    if (this.idle) {
                        //Spider
                        if (this.enemyType == 0) {
                            startAnimation(0, 0, 4);
                        }
                        //Fire Elemental
                        if (this.enemyType == 1) {
                            startAnimation(6, 0, 5);
                        }
                        //Gladiator
                        if (this.enemyType == 2) {
                            startAnimation(0, 0, 5);
                        }
                        //Minotaur
                        if (this.enemyType == 3) {
                            startAnimation(10, 0, 4);
                        }
                    }
                    //If Running
                    if (this.running) {
                        //Spider
                        if (this.enemyType == 0) {
                            startAnimation(1, 0, 5);
                        }
                        //Fire Elemental
                        if (this.enemyType == 1) {
                            startAnimation(5, 0, 7);
                        }
                        //Gladiator
                        if (this.enemyType == 2) {
                            startAnimation(1, 0, 7);
                        }
                        //Minotaur
                        if (this.enemyType == 3) {
                            startAnimation(11, 0, 7);
                        }
                    }
                }
                //Stop condition of animations of the type "movement"
                //All the animations of moving types use the same counter
                if (this.cont >= this.endLine) {
                    this.cont = this.startLine;
                }
                this.drawRef = this.cont;
            }

            //Attack animations
            if (this.attacking) {
                if (this.looking2Right) {
                    //Spider
                    if (this.enemyType == 0) {
                        startAnimation(10, 0, 8);
                    }
                    //Fire Elemental
                    if (this.enemyType == 1) {
                        startAnimation(2, 0, 7);
                    }
                    //Gladiator
                    if (this.enemyType == 2) {
                        startAnimation(7, 0, 6);
                    }
                    //Minotaur
                    if (this.enemyType == 3) {
                        startAnimation(6, 0, 8);
                    }
                }
                if (!this.looking2Right) {
                    //Spider
                    if (this.enemyType == 0) {
                        startAnimation(2, 0, 8);
                    }
                    //Fire Elemental
                    if (this.enemyType == 1) {
                        startAnimation(7, 0, 7);
                    }
                    //Gladiator
                    if (this.enemyType == 2) {
                        startAnimation(2, 0, 6);
                    }
                    //Minotaur
                    if (this.enemyType == 3) {
                        startAnimation(16, 0, 7);
                    }
                }
                //Stop condition of animations of the type "atatck"
                if (this.atkCont >= this.endLine) {
                    this.atkCont = 0;
                    this.cont = 0;
                    this.attacking = false;
                }
                this.drawRef = this.atkCont;
            }
            //If hitted
            if (this.hitted) {
                if (this.looking2Right) {
                    //Spider
                    if (this.enemyType == 0) {
                        startAnimation(14, 0, 8);
                    }
                    //Fire Elemental
                    if (this.enemyType == 1) {
                        startAnimation(3, 0, 5);
                    }
                    //Gladiator
                    if (this.enemyType == 2) {
                        startAnimation(8, 0, 2);
                    }
                    //Minotaur
                    if (this.enemyType == 3) {
                        startAnimation(8, 0, 2);
                    }
                }
                if (!this.looking2Right) {
                    //Spider
                    if (this.enemyType == 0) {
                        startAnimation(6, 0, 8);
                    }
                    //Fire Elemental
                    if (this.enemyType == 1) {
                        startAnimation(8, 0, 5);
                    }
                    //Gladiator
                    if (this.enemyType == 2) {
                        startAnimation(3, 0, 2);
                    }
                    //Minotaur
                    if (this.enemyType == 3) {
                        startAnimation(18, 0, 2);
                    }
                }
                if (this.hitCont >= this.endLine) {
                    this.cont = 0;
                    this.hitCont = 0;
                    this.hitted = false;
                    this.life -= 1;

                    if (this.life == 0) {
                        died = true;
                    }
                }
                this.drawRef = this.hitCont;
            }
        }

        //Dead Enemy
        if (this.died) {
            this.followingPlayer = false;
            if (this.looking2Right) {
                //Spider
                if (this.enemyType == 0) {
                    startAnimation(14, 0, 8);
                }
                //Fire Elemental
                if (this.enemyType == 1) {
                    startAnimation(4, 0, 7);
                }
                //Gladiator
                if (this.enemyType == 2) {
                    startAnimation(9, 0, 6);
                }
                //Minotaur
                if (this.enemyType == 3) {
                    startAnimation(9, 0, 5);
                }
            }
            if (!this.looking2Right) {
                //Spider
                if (this.enemyType == 0) {
                    startAnimation(6, 0, 8);
                }
                //Fire Elemental
                if (this.enemyType == 1) {
                    startAnimation(9, 0, 7);
                }
                //Gladiator
                if (this.enemyType == 2) {
                    startAnimation(4, 0, 6);
                }
                //Minotaur
                if (this.enemyType == 3) {
                    startAnimation(19, 0, 5);
                }
            }
            if (this.deadCont >= this.endLine) {
                this.deadCont = this.endLine;
            }
            this.drawRef = this.deadCont;
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
        this.startLine= startLine;
        this.animation = animation;
        this.endLine= endLine;
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
        for (Collidable c : this.COLLIDABLES) {
            int rSide_C = (c.getPivotRT().getX() + c.getPivotRD().getX());
            int lSide_C = (c.getPivotLT().getX() + c.getPivotLD().getX());
            int topSide_C = (c.getPivotRT().getY() + c.getPivotLT().getY());
            int underSide_C = (c.getPivotRD().getY() + c.getPivotLD().getY());

            // Is the right edge of the player to the right of the left edge of 
            // the object?
            if (rSide > lSide_C) {
                // Is the left edge of the player to the left of the right edge 
                // of the object?
                if (lSide < rSide_C) {
                    // The bottom edge of the player is below the top edge of 
                    // the object?
                    if (underSide > topSide_C) {
                        // Is the top edge of the player above the bottom edge 
                        // of the object?
                        if (topSide < underSide_C) {
                            if ("PlayerType".equals(c.getType())) {
                                this.atkTimer += 1;

                                if (atkTimer >= 5) {
                                    this.attacking = true;
                                    if (attacking && atkCont == 0) {
                                        c.gotHit();
                                    }
                                    atkTimer = 0;
                                }
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
     * Method that get the area of the enemy
     * 
     * @return a vector with the rect area of the enemy 
     */
    private int[] getArea() {
        int resp[] = new int[4];
        resp[0] = this.xPos - 192;
        resp[1] = this.yPos - 192;
        resp[2] = this.xPos + 192;
        resp[3] = this.yPos + 192;
        return resp;
    }
    
    /**
     * Method that check if the player is in range of the enemy area
     * 
     * @param player : the game player
     * @return : a boolean that tell if he's in
     */
    public boolean inRange(Player player) {
        if (player.getXPosition() > getArea()[0] && 
            player.getXPosition() < getArea()[2]) {
            if (player.getYPosition() > getArea()[1] && 
                player.getYPosition() < getArea()[3]) {
                this.followingPlayer = true;
                this.endedPath = false;
                return true;
            }
        }
        this.followingPlayer = false;
        return false;
    }
    
    /**
     * Method that call all the other enemy's methods to update
     * 
     */
    @Override
    public void update() {
        if (!hitted && !attacking && path != null) {
            move();
        }   
        animate();
    }
    
    /**
     * Method that paint the enemy
     *
     * @param g : the graphic context
     */
    @Override
    public void paintComponent(Graphics g) {
        // Get a piece of the Image based on animation counters and super matrix
        if (this.enemyType == 0 || this.enemyType == 2 || this.enemyType == 1) {
            BufferedImage image = SPRITE.getSubimage(
                    super.tile_32x32[drawRef][animation].getSrcX1(),
                    super.tile_32x32[drawRef][animation].getSrcY1(),
                    IMGSIZE, IMGSIZE);
            g.drawImage(image, xPos, yPos, 64, 64, null);
        }
        if (this.enemyType == 3) {
            BufferedImage image = SPRITE.getSubimage(
                    super.tile_96x96[drawRef][animation].getSrcX1(),
                    super.tile_96x96[drawRef][animation].getSrcY1(),
                    IMGSIZE, IMGSIZE);
            g.drawImage(image, xPos-10, yPos, 96, 96, null);
        }
        //If following the player, draws the exclamation point
        if (this.followingPlayer) {
            BufferedImage alert;
            switch (this.enemyType) {
                case 0:
                    alert = SPRITE.getSubimage(
                            super.tile_32x32[8][0].getSrcX1(), 
                            super.tile_32x32[8][0].getSrcY1(), 
                            IMGSIZE, IMGSIZE);
                    g.drawImage(alert, xPos, yPos, 64, 64 + 32, null);
                    break;
                case 1:
                    alert = SPRITE.getSubimage(
                            super.tile_32x32[7][1].getSrcX1(), 
                            super.tile_32x32[7][1].getSrcY1(), 
                            IMGSIZE, IMGSIZE);
                    g.drawImage(alert, xPos, yPos, 64, 64, null);
                    break;
                case 2:
                    alert = SPRITE.getSubimage(
                            super.tile_32x32[7][0].getSrcX1(), 
                            super.tile_32x32[7][0].getSrcY1(), 
                            IMGSIZE, IMGSIZE);
                    g.drawImage(alert, xPos, yPos, 64, 64, null);
                    break;
            }
        }
    }

    /**
     * Tell to the enemy that he got hitted
     */
    @Override
    public void gotHit() {
        this.hitted = true;
    }

    /**
     * @return the enemy's x position on the screen
     */
    public int getXPosition() {
        return this.xPos;
    }

    /**
     * @return the enemy's y position on the screen
     */
    public int getYPosition() {
        return this.yPos;
    }

    /**
     * @return the enemy's actual node in NodeMap 
     */
    public Node getNodePos() {
        return this.MAP.getNode(yPos / 64, xPos / 64);
    }

    /**
     * Method that set's the enemy path
     * 
     * @param path : the current enemy path
     */
    public void setPath(List<Node> path) {
        this.path = path;
    }

    /**
     * Method that tells if the enemy ended the path
     *
     * @return
     */
    public boolean endedPath() {
        return this.endedPath;
    }
    
    /**
     * Methos that tell if the enemy died
     * 
     * @return 
     */
    public boolean died() {
        if (this.life == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method that tell if the current stage is the enemy map 
     * 
     * @param map : the game map
     * @return 
     */
    @Override
    public Boolean isStage(Map map) {
        if (map.actualStage == this.actualStage) {
            return true;
        }
        return false;
    }

    /**
     * Method that get the enemy type
     * 
     * @return : a string with the enemy type
     */
    @Override
    public String getType() {
        if (this.enemyType == 3) {
            return "BossType";
        } else {
            return "EnemyType";
        }
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
}