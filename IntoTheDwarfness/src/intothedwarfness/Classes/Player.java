/******************************************************************************
 ** Class Player, here the player will be created and all operations related **
 ** to him like walking, detecting animationlision and drawing will meet.    **
 ******************************************************************************/
package intothedwarfness.Classes;

import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import intothedwarfness.Interfaces.Drawable;

public class Player extends Character implements Drawable {
/* ***************************Class Variables******************************** */
    //Player's settings
    private Map map;
    private char currentMove;
    private boolean[][] collideMap;
    private ArrayList<Song> songs;
    private final BufferedImage SpriteSheet;
    private int xPos, yPos, actualStage, life, IMGSIZE;
    
    //Player's animation
    private int cont, atkCont, hitCont, deadCont;
    private int drawRef, startLine, animation, endLine;
    private boolean looking2Right, attacking, gotHit, died, running;

/* **************************Class Constructor******************************* */
    public Player(BufferedImage spriteSheet, ArrayList<Song> songs, Map map) {
        //Player's settings
        this.life = 4;
        this.map = map;
        this.xPos = 480;
        this.yPos = 114;
        this.IMGSIZE = 32;
        this.songs = songs;
        this.actualStage = 1;
        this.currentMove = '.';
        this.SpriteSheet = spriteSheet;
        
        //Player's animation
        this.died = false;
        this.gotHit = false;
        this.running = false;
        this.attacking = false;
        this.looking2Right = true;
    }

/* ****************************Class Methods********************************* */
    //Method that moves the player by 8 pixels
    private void move(char key) {
        //Only made if it's not dead or collide with the stage objects
        if (!died) {
            if (key == 'a' && collision(key))
                this.xPos = this.xPos - 8;       
            if (key == 'd' && collision(key))
                this.xPos = this.xPos + 8;           
            if (key == 'w' && collision(key))
                this.yPos = this.yPos - 8;          
            if (key == 's' && collision(key))
                this.yPos = this.yPos + 8;
        }
    }
    //Method that check the collisions
    private boolean collision(char key) {
        return true;
    }
    //Method that verifies if the player will change the stage
    private void checkStage(char key, Map map) {
        //Check the entries in stage 1
        if (actualStage == 1) {
            if (key == 's' && yPos == 698 && (xPos >= 424 && xPos <= 536)){
                this.yPos = 18;
                this.actualStage = 2;
            }
        }
        //Check the entries in stage 2
        if (actualStage == 2) {
            if (key == 'w' && yPos == 10 && (xPos >= 424 && xPos <= 536)){
                this.yPos = 690;
                this.actualStage = 1;
            }
            if (key == 's' && yPos == 706 && (xPos >= 424 && xPos <= 536)){
                this.yPos = 26;
                this.actualStage = 8;
            }
            if (key == 'd' && xPos == 960 && (yPos >= 256 && yPos <= 384)){
                this.xPos = 16;
                this.actualStage = 3;
            }
        }
        //Check the entries in stage 3
        if (actualStage == 3) {
            if (key == 'a' && xPos == 8 && (yPos >= 256 && yPos <= 384)) {
                this.xPos = 952;
                this.actualStage = 2;
            }
            if (key == 's' && yPos == 698 && (xPos >= 128 && xPos <= 834)) {
                this.yPos = 18;
                this.actualStage = 5;
            }
            if (key == 'w' && yPos == 10 && (xPos >= 128 && xPos <= 834)) {
                this.yPos = 690;
                this.actualStage = 4;
            }
        }
        //Check the entries in stage 4
        if (actualStage == 4) {
            if (key == 's' && yPos == 698 && (xPos >= 128 && xPos <= 834)) {
                this.yPos = 18;
                this.actualStage = 3;
            }
            if (key == 'w' && yPos == 58 && (xPos >= 464 && xPos <= 486)) {
                this.yPos = 682;
                this.actualStage = 7;
            }
        }
        //Check the entries in stage 5
        if (actualStage == 5) {
            if (key == 'w' && yPos == 10 && (xPos >= 128 && xPos <= 834)) {
                this.yPos = 690;
                this.actualStage = 3;
            }
            if (key == 'd' && xPos == 960 && (yPos >= 258 && yPos <= 386)) {
                this.xPos = 10;
                this.actualStage = 6;
            }
        }
        //Check the entries in stage 6
        if (actualStage == 6) {
            if (key == 'a' && xPos == 2 && (yPos >= 258 && yPos <= 386)) {
                this.xPos = 952;
                this.actualStage = 5;
            }
        }
        //Check the entries in stage 7
        if (actualStage == 7) {
            if (key == 's' && yPos == 690 && (xPos >= 464 && xPos <= 488)) {
                this.yPos = 74;
                this.actualStage = 4;
            }
            if (key == 'w' && yPos == 258 && xPos == 480) {
                this.yPos = 384;
                this.xPos = 768;
                this.actualStage = 8;
            }
        }
        //Check the entries in stage 8
        if (actualStage == 8) {
            if (key == 'w' && yPos == 18 && (xPos >= 424 && xPos <= 528)) {
                this.yPos = 698;
                this.actualStage = 2;
            }
        }
        //After check, create the stage and the collide matrix
        map.stageCreator(actualStage);
        this.collideMap = map.getgUnblockedT();
    }
    //Method that defines the settings of the current animation
    private void startAnimation(int animation, int startLine, int endLine) {
        this.startLine= startLine;
        this.animation = animation;
        this.endLine= endLine;
    }
    //Method that checks the conditions to defines the animations to be drawn
    private void animate() {
        //Counters of animations
        //There are two standard animations that are continuously being 
        //incremented: Idle and Running, they use the same counters
        this.cont+= 1;
        //The others animations need another's counters because of when called, 
        //they have to start from 0
        if (attacking) {
            atkCont += 1;
        }
        if (gotHit) {
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
            if (!attacking && !gotHit) {
                //Idle and Running while looking to the Right
                if (looking2Right) {
                    if (currentMove == 'a' || currentMove == 'w' || 
                        currentMove == 's' || currentMove == 'd') {
                        startAnimation(1, 0, 8);
                    }
                    if (currentMove == '.') {
                        startAnimation(0, 0, 5);
                    }
                }
                //Idle and Running while looking to the Left
                if(!looking2Right){
                    if (currentMove == 'a' || currentMove == 'w' || 
                        currentMove == 's' || currentMove == 'd') {
                        startAnimation(6,0,8);
                    }
                    if (currentMove == '.') {
                        startAnimation(5,0,5);
                    } 
                }
                //Play the song of this animation
                if((cont%2 == 1 ) && running){
                    playsong(4);
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
                if (atkCont == 1){
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
            if (gotHit) {
                if (looking2Right) {
                    startAnimation(3, 0, 4);
                }
                if (!looking2Right) {
                    startAnimation(8, 0, 4);
                }
                //Play song of this animation
                if(hitCont == 1){
                    playsong(2);
                }
                //Stop condition of animations of the type "get hit", also 
                //decrease the player's life
                if (hitCont == endLine) {
                    cont= 0;
                    hitCont = 0;
                    gotHit = false;
                    life -= 1;
                    if (life == 0){
                        died = true;
                    }
                }
                this.drawRef = hitCont;
            }
        }
        
        //Dead Player
        if (died){
            if (looking2Right){
                startAnimation(4,0,7);
            }
            if (!looking2Right){
                startAnimation(9,0,7);
            }
            //Play song of this animation
                if(deadCont == 1){
                    playsong(3);
                }
            //Stop condition of animations of the type "died" 
            if (deadCont == this.endLine){
                deadCont = endLine-1;
            }
            this.drawRef = deadCont;
        }
    }
    //Method that play the player's songs
    private void playsong(int ref){
        songs.get(ref).playSoundOnce();
    }
    //Method called in the window that says where the player is looking
    public void setLook(char view) {
        //Key filter
        if(currentMove != 'a' && currentMove != 's' &&
           currentMove != 'd' && currentMove != 'w' &&
           currentMove != '.' && currentMove != ' ' &&
           currentMove != 'h'){
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
    //Method that receives the char of the window's KeyEvent and set the 
    //player's actual state
    public void setCurrentMove(char currentMove) {
        //Key filter
        if(currentMove != 'a' && currentMove != 's' &&
           currentMove != 'd' && currentMove != 'w' &&
           currentMove != '.' && currentMove != ' ' &&
           currentMove != 'h'){
            return;
        }
        if (!died) {
            if (currentMove == 'd' || currentMove == 'a' || currentMove == 's' || currentMove == 'w') {
                running = true;
            }
            if (currentMove == 'd') {
                looking2Right = true;
            }
            if (currentMove == 'a') {
                looking2Right = false;
            }
            if (currentMove == ' ') {
                this.attacking = true;
            }
            if (currentMove == 'h') {
                this.gotHit = true;
            }
            this.currentMove = currentMove;
        }
    }
    //Method that get the player's x position on the screen   
    public int getXPosition() {
        return this.xPos;
    }
    //Method that get the player's y position on the screen
    public int getYPosition() {
        return this.yPos;
    }

/* *************************Overridden Methods******************************* */
    @Override
    public void update() {
        //Moves the player to the saved position
        move(this.currentMove);
        //Check if the player has to change stage
        checkStage(this.currentMove, map);
        //Do the animations
        animate();
        //Play the current song
    }
    @Override
    public void paintComponent(Graphics g) {
        //Get a piece of the Image
        BufferedImage image = SpriteSheet.getSubimage(
                super.spriteTiles[drawRef][animation].getSrcX1(), 
                super.spriteTiles[drawRef][animation].getSrcY1(),
                IMGSIZE, IMGSIZE);
        //Draw in the player's position
        g.drawImage(image, xPos, yPos, 64, 64, null);
    }
    @Override
    public Boolean isStage(Map map) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
