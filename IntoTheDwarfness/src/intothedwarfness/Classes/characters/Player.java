/** ****************************************************************************
 **     Player class                                                         **
 **                                                                          **
 ** The player object is one that will be manipulated by the player, the     **
 ** class is responsible for manipulating the object through the keyboard    **
 ** events passed by the Window                                              **
 **                                                                          **
 ***************************************************************************** */
package intothedwarfness.Classes.characters;

import java.awt.Graphics;
import java.util.ArrayList;
//import intothedwarfness.IA.Node;
import intothedwarfness.Classes.Map;
import java.awt.image.BufferedImage;
import intothedwarfness.Classes.Song;
import intothedwarfness.Classes.Point;
import intothedwarfness.Classes.Window;
import intothedwarfness.IA.Node;
import intothedwarfness.Interfaces.Drawable;
import intothedwarfness.Interfaces.Collidable;

public class Player extends Character implements Drawable, Collidable {

    /* ***************************Class Variables******************************** */
    //Constants
    private final Map MAP;
    private final int IMGSIZE, TILESIZE;
    private final BufferedImage SPRITE;
    private final ArrayList<Song> SONGS;
    //Position
    private char currentMove;
    private ArrayList<Point> pivots;
    private ArrayList<Enemy> ENEMIES;
    private ArrayList<Collidable> collidables;
    private int xPos, yPos, actualStage, life;

    //Animation
    private int cont, atkCont, hitCont, deadCont;
    private int drawRef, startLine, animation, endLine;
    private boolean looking2Right, attacking, hitted, died, running;
    private ArrayList<BufferedImage> health_bar_image;

    /* **************************Class Constructor******************************* */
    public Player(BufferedImage spriteSheet, ArrayList<Song> songs, Map map, ArrayList<BufferedImage> health_bar) {
        //Player's settings
        this.life = 6;
        this.MAP = map;
        this.xPos = 512;
        this.yPos = 128;
        this.IMGSIZE = 32;
        this.SONGS = songs;
        this.TILESIZE = 64;
        this.actualStage = 1;
        this.currentMove = '.';
        this.SPRITE = spriteSheet;
        this.pivots = new ArrayList();
        this.collidables = new ArrayList();
        this.health_bar_image = health_bar;

        //Player's animation
        this.died = false;
        this.hitted = false;
        this.running = false;
        this.attacking = false;
        this.looking2Right = true;

        setPivot();
        initializeCollidables();
    }

    /* ********************Auxiliary methods of the Constructor****************** */
    private void setPivot() {
        this.pivots.clear();
        // Pivots position:
        // at 0: Left Top
        // at 1: Right Top 
        // at 2: Left Down
        // at 3: Right Down
        this.pivots.add(new Point(this.xPos, this.yPos));
        this.pivots.add(new Point(this.xPos + TILESIZE, this.yPos));
        this.pivots.add(new Point(this.xPos, this.yPos + TILESIZE));
        this.pivots.add(new Point(this.xPos + TILESIZE, this.yPos + TILESIZE));
    }

    public void recieveCollidables(ArrayList<Enemy> enemies) {
        this.ENEMIES = enemies;

        for (Enemy enemy : this.ENEMIES) {
            if (enemy.isStage(this.MAP)) {
                collidables.add(enemy);
            }
        }

    }

    public void initializeCollidables() {
        this.collidables.clear();

        MAP.getNodeMap();
        for (int i = 0; i < MAP.getNodeMap().length; i++) {
            for (int j = 0; j < MAP.getNodeMap()[0].length; j++) {
                if (MAP.getNodeMap()[i][j].isBlocked()) {
                    collidables.add(MAP.getNodeMap()[i][j]);
                }
            }
        }
    }

    /* ****************************Class Methods********************************* */
    //Method that receives the char of the window's KeyEvent and set the state
    public void setCurrentMove(char currentMove) {
        //Caps Lock filter
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
        //Set the current move
        if (!died) {
            if (currentMove == 'd' || currentMove == 'a'
                    || currentMove == 's' || currentMove == 'w') {
                running = true;
            }
            if (currentMove == 'd') {
                looking2Right = true;
            }
            if (currentMove == 'a') {
                looking2Right = false;
            }
            if (currentMove == ' ') {
                //System.out.println("ATTACKING");
                this.attacking = true;
            }
            if (currentMove == 'h') {
                this.hitted = true;
            }
            this.currentMove = currentMove;
        }
    }

    //Method that moves the player
    private void move(char key) {
        //save the current position
        int antXPos = this.xPos;
        int antYPos = this.yPos;

        if (!died) {
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
            //set the new pivots
            setPivot();
            //if had collision, return to the old position
            if (collision()) {
                this.xPos = antXPos;
                this.yPos = antYPos;
            }
            checkStage(this.currentMove, MAP);
        }

    }

    //Method that check the collisions
    public boolean collision() {
        //get the sides of the player
        int rSide = (this.getPivotRT().getX() + this.getPivotRD().getX());
        int lSide = (this.getPivotLT().getX() + this.getPivotLD().getX());
        int topSide = (this.getPivotRT().getY() + this.getPivotLT().getY());
        int underSide = (this.getPivotRD().getY() + this.getPivotLD().getY());

        //for each colliding object to pick up the sides of the object and 
        //compare with those of the player
        for (Collidable c : this.collidables) {
            int rSide_C = (c.getPivotRT().getX() + c.getPivotRD().getX());
            int lSide_C = (c.getPivotLT().getX() + c.getPivotLD().getX());
            int topSide_C = (c.getPivotRT().getY() + c.getPivotLT().getY());
            int underSide_C = (c.getPivotRD().getY() + c.getPivotLD().getY());

            // Is the right edge of the player to the right of the left edge of the object?
            if (rSide > lSide_C) {
                // Is the left edge of the player to the left of the right edge of the object?
                if (lSide < rSide_C) {
                    // The bottom edge of the player is below the top edge of the object?
                    if (underSide > topSide_C) {
                        // Is the top edge of the player above the bottom edge of the object?
                        if (topSide < underSide_C) {
                            if ("EnemyType".equals(c.getType())) {
                                if (attacking && atkCont == 0) {
                                    c.gotHit();
                                    System.out.println("Acertou inimigo");
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

    //Method that verifies if the player will change the stage
    public int checkStage(char key, Map map) {
        int lastStage = this.actualStage;
        //Check the entries in stage 1
        if (actualStage == 1) {
            if (key == 's' && yPos >= 704 && (xPos >= 448 && xPos <= 512)) {
                this.yPos = 0;
                this.actualStage = 2;
            }
        }
        //Check the entries in stage 2
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
        //Check the entries in stage 3
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
        //Check the entries in stage 4
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
        //Check the entries in stage 5
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
        //Check the entries in stage 6
        if (actualStage == 6) {
            if (key == 'a' && xPos <= 0 && (yPos >= 256 && yPos <= 384)) {
                this.xPos = 960;
                this.actualStage = 5;
            }
        }
        //Check the entries in stage 7
        if (actualStage == 7) {
            if (key == 's' && yPos >= 704 && (xPos >= 448 && xPos <= 512)) {
                this.yPos = 64;
                this.actualStage = 4;
            }
            if (key == 'w' && yPos <= 272 && xPos == 480) {
                this.yPos = 384;
                this.xPos = 768;
                this.actualStage = 8;
            }
        }
        //Check the entries in stage 8
        if (actualStage == 8) {
            if (key == 'w' && yPos <= 0 && (xPos >= 448 && xPos <= 512)) {
                this.yPos = 704;
                this.actualStage = 2;
            }
        }

        if (lastStage == this.actualStage) {
            return 0;
        }
        //After check, create the stage and the collide list
        map.stageCreator(actualStage);
        initializeCollidables();
        return this.actualStage;
    }

    //Method that defines the settings of the current animation
    private void startAnimation(int animation, int startLine, int endLine) {
        this.startLine = startLine;
        this.animation = animation;
        this.endLine = endLine;
    }

    //Method that play the player's songs
    private void playsong(int ref) {
        SONGS.get(ref).playSoundOnce();
    }

    //Method that checks the conditions to defines the animations to be drawn
    private void animate() {
        //Counters of animations
        //There are two standard animations that are continuously being 
        //incremented: Idle and Running, they use the same counters
        this.cont += 1;
        //The others animations need another's counters because of when called, 
        //they have to start from 0
        if (attacking) {
            atkCont += 1;
        }
        if (hitted) {
            hitCont += 1;
        }
        if (died) {
            deadCont += 1;
        }

        //The animations begin to divide by categories, starting in: 
        //alive player or dead player
        //Alive player
        if (!died) {
            //Then it is divided by:
            //is looking to the right, movement and action
            if (!attacking && !hitted) {
                //Idle and Running while looking to the Right
                if (looking2Right) {
                    if (currentMove == 'a' || currentMove == 'w'
                            || currentMove == 's' || currentMove == 'd') {
                        startAnimation(1, 0, 8);
                    }
                    if (currentMove == '.') {
                        startAnimation(0, 0, 5);
                    }
                }
                //Idle and Running while looking to the Left
                if (!looking2Right) {
                    if (currentMove == 'a' || currentMove == 'w'
                            || currentMove == 's' || currentMove == 'd') {
                        startAnimation(6, 0, 8);
                    }
                    if (currentMove == '.') {
                        startAnimation(5, 0, 5);
                    }
                }

                //Stop condition of animations of the type "movement"
                if (cont == this.endLine) {
                    cont = this.startLine;
                }
                //All the animations of moving types use the same counter
                this.drawRef = cont;
            }

            //Attack animations
            if (attacking) {
                if (looking2Right) {
                    startAnimation(2, 0, 7);
                }
                if (!looking2Right) {
                    startAnimation(7, 0, 7);
                }
                //Play the song of this animation
                if (atkCont == 1) {
                    playsong(1);
                }
                //Stop condition of animations of the type "atatck"
                if (atkCont == this.endLine) {
                    atkCont = 0;
                    cont = 0;
                    attacking = false;
                }
                this.drawRef = atkCont;
            }

            //Animations to get hit
            if (hitted) {
                if (looking2Right) {
                    startAnimation(3, 0, 4);
                }
                if (!looking2Right) {
                    startAnimation(8, 0, 4);
                }
                //Play song of this animation
                if (hitCont == 1) {
                    playsong(2);
                }
                //Stop condition of animations of the type "get hit", also 
                //decrease the player's life
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

        //Dead Player
        if (died) {
            if (looking2Right) {
                startAnimation(4, 0, 7);
            }
            if (!looking2Right) {
                startAnimation(9, 0, 7);
            }
            //Play song of this animation
            if (deadCont == 1) {
                playsong(3);
            }
            //Stop condition of animations of the type "died" 
            if (deadCont == this.endLine) {
                deadCont = endLine - 1;
            }
            this.drawRef = deadCont;
        }
    }

    //Method called in the window that says where the player is looking
    public void setLook(char view) {
        //Key filter
        if (currentMove != 'a' && currentMove != 's'
                && currentMove != 'd' && currentMove != 'w'
                && currentMove != '.' && currentMove != ' '
                && currentMove != 'h') {
            return;
        }
        if (!died) {
            if (view == 'a') {
                this.looking2Right = false;
            }
            if (view == 'd') {
                this.looking2Right = true;
            }
        }
        //Also resets the motion animations counter
        running = false;
        this.cont = 0;
    }

    //Method that get the player's x position on the screen   
    public int getXPosition() {
        return this.xPos;
    }

    //Method that get the player's y position on the screen
    public int getYPosition() {
        return this.yPos;
    }

    public ArrayList<Collidable> getCollidables() {
        return collidables;
    }

    public Node getNodePos() {
        return this.MAP.getNode(yPos / 64, xPos / 64);
    }

    public void setNewCollidables(ArrayList<Enemy> newEnemies) {
        this.ENEMIES = newEnemies;

    }

    public ArrayList<Enemy> getEnemies() {
        return this.ENEMIES;
    }

    /* *************************Overridden Methods******************************* */
    @Override
    public void update() {
        //Moves the player to the saved position
        move(this.currentMove);
        //Do the animations
        animate();
        //Play the current song
    }

    @Override
    public void paintComponent(Graphics g) {
        //Get a piece of the Image
        BufferedImage image = SPRITE.getSubimage(
                super.tile_32x32[drawRef][animation].getSrcX1(),
                super.tile_32x32[drawRef][animation].getSrcY1(),
                IMGSIZE, IMGSIZE);
        //Draw in the player's position
        g.drawImage(image, xPos, yPos, 64, 64, null);
        // Draw Player's life bar
        g.drawImage(health_bar_image.get(this.life), 15, 50, 15 + 125, 50 + 32, 0, 0, 125, 32, null);

    }

    @Override
    public Boolean isStage(Map map) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Point getPivotLT() {
        return this.pivots.get(0);
    }

    @Override
    public Point getPivotRT() {
        return this.pivots.get(1);
    }

    @Override
    public Point getPivotLD() {
        return this.pivots.get(2);
    }

    @Override
    public Point getPivotRD() {
        return this.pivots.get(3);
    }

    @Override
    public String getType() {
        return "PlayerType";
    }

    @Override
    public void gotHit() {
        this.hitted = true;
    }

    public int getActualStage() {
        return actualStage;
    }

    public int getLife() {
        return this.life;
    }
}
