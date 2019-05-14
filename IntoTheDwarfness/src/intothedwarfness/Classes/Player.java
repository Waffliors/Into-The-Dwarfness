/******************************************************************************
 ** Class Player, here the player will be created and all operations related **
 ** to him like walking, detecting collision and drawing will meet.          **
 ******************************************************************************/
package intothedwarfness.Classes;


import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import intothedwarfness.Interfaces.Drawable;

public class Player extends Character implements Drawable {
/* ***************************Class Variables******************************** */
    private boolean move;
    private int xPos, yPos, actualStage;
    private final BufferedImage SpriteSheet;
    private boolean[][] collideMap;
    private int direction;
    private int life;
    private Map map;
    private char currentMove;
    private int look;

    private int xAnim, yAnim;

/* **************************Class Constructor******************************* */
    public Player(BufferedImage spriteSheet, boolean[][] collideMap, Map map) {
        this.life = 4;
        this.xPos = 480;
        this.yPos = 114;
        this.actualStage = 1;
        this.collideMap = collideMap;
        this.SpriteSheet = spriteSheet;
        this.move = false;
        this.map = map;
        this.currentMove = '.';
        this.look = 2;
    }

    public void setLook(char view) {
        if(view == 'a'){
            this.look = 1;
            
        }
        if(view == 'd'){
            this.look = 2;
        }

        this.xAnim = 0;
        this.yAnim = 0;
    }

/* ****************************Class Methods********************************* */
    public void move(char key) {
        //checkstage(key, map);
        if (key == 'a' && collision(key)) {
            this.xPos = this.xPos - 8;
        }
        if (key == 'd' && collision(key)) {
            this.xPos = this.xPos + 8;
        }
        if (key == 'w' && collision(key)) {
            this.yPos = this.yPos - 8;
        }
        if (key == 's' && collision(key)) {
            this.yPos = this.yPos + 8;
        }
    }
    public void setCurrentMove(char currentMove) {
        if(currentMove == 'd'){
            look = 2;
        }
        if(currentMove == 'a'){
            look = 1;
        }
        this.currentMove = currentMove;
    }

/* *************************Overridden Methods******************************* */
    @Override
    public void update() {
        move(this.currentMove);
        checkStage(this.currentMove, map);
        this.xAnim += 32;
        
        //If the player is looking to the Right
        if (look == 2) {
            System.out.println("here");
            if (currentMove == 'a' || currentMove == 'w' || currentMove == 's' || currentMove == 'd') {
                if (xAnim >= 256) {
                    xAnim = 0;
                }
                this.yAnim = 32;
            }
            //System.out.println("X: "+xPos+" Y: "+yPos);
            if (currentMove == '.') {
                if (xAnim >= 160) {
                    xAnim = 0;
                }
                yAnim = 0;
            }
        }
        //If the player is looking to the Left
        if (look == 1) {
            if (currentMove == 'a' || currentMove == 'w' || currentMove == 's' || currentMove == 'd') {
                if (xAnim >= 256) {
                    xAnim = 0;
                }
                this.yAnim = 192;
            }
            //System.out.println("X: "+xPos+" Y: "+yPos);
            if (currentMove == '.') {
                if (xAnim >= 160) {
                    xAnim = 0;
                }
                yAnim = 160;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        BufferedImage image = SpriteSheet.getSubimage(xAnim, yAnim, 32, 32);
        g.drawImage(image, xPos, yPos, 64, 64, null);
    }
    public boolean collision(char key) {
        return true;
    }
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
        if (actualStage == 3){
            if (key == 'a' && xPos == 8 && (yPos >= 256 && yPos <= 384)){
                this.xPos = 952;
                this.actualStage = 2;
            }
            if (key == 's' && yPos == 698 && (xPos >= 128 && xPos <= 834)){
                this.yPos = 18;
                this.actualStage = 5;
            }
            if (key == 'w' && yPos == 10 && (xPos >= 128 && xPos <= 834)){
                this.yPos = 690;
                this.actualStage = 4;
            }
        }
        //Check the entries in stage 4
        if (actualStage == 4) {
            if (key == 's' && yPos == 698 && (xPos >= 128 && xPos <= 834)){
                this.yPos = 18;
                this.actualStage = 3;
            }
            if (key == 'w' && yPos == 58 && (xPos >=464 && xPos <= 486)){
                this.yPos = 682;
                this.actualStage = 7;
            }
        }
        //Check the entries in stage 5
        if (actualStage == 5) {
            if (key == 'w' && yPos == 10 && (xPos >= 128 && xPos <= 834)){
                this.yPos = 690;
                this.actualStage = 3;
            }
            if (key == 'd' && xPos == 960 && (yPos >= 258 && yPos <= 386)){
                this.xPos = 10;
                this.actualStage = 6;
            }
        }
        //Check the entries in stage 6
        if (actualStage == 6) {
            if (key == 'a' && xPos == 2 && (yPos >= 258 && yPos <= 386)){
                this.xPos = 952;
                this.actualStage = 5;
            }
        }
        //Check the entries in stage 7
        if(actualStage == 7){
            if (key == 's' && yPos == 690 && (xPos >= 464 && xPos <= 488)){
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
            if (key == 'w' && yPos == 18 && (xPos >= 424 && xPos <= 528)){
                this.yPos = 698;
                this.actualStage = 2;
            }
        }
        map.stageCreator(actualStage);
        this.collideMap = map.getgUnblockedT();
    }

    public int getXPosition() {
        return this.xPos;
    }

    public int getYPosition() {
        return this.yPos;
    }

    @Override
    public Boolean isStage(Map map) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
