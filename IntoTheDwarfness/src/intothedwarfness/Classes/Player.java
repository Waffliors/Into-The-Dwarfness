/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Class player, responsible for creating the character of the player and all  *
 * the tasks that the corresponding                                            *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package intothedwarfness.Classes;

import intothedwarfness.Interfaces.Drawable;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends Character implements Drawable {

    /*------------------------------------------------------------------------*
     *------------------------ Class Variables -------------------------------*
     *------------------------------------------------------------------------*/
    private final float speed;
    private boolean move;
    private int moveMax;
    private int xPos, yPos, actualStage;
    private final BufferedImage SpriteSheet;
    private boolean[][] collideMap;
    private int direction;
    private int life;
    
    private int contmove;
    
    private int xAnim, yAnim;

    /*------------------------------------------------------------------------*
     *----------------------- Class Constructor ------------------------------*
     *------------------------------------------------------------------------*/
    public Player(BufferedImage spriteSheet, boolean [][]collideMap) {
        this.life = 4;
        this.speed = (float) 0.5;
        this.moveMax = 0;
        this.xPos = 512;
        this.yPos = 128;
        this.actualStage = 1;
        this.collideMap = collideMap;
        this.SpriteSheet = spriteSheet;
        this.move = false;
        this.contmove = 0;
        
        this.xAnim = 0;
        this.yAnim = 0;
    }

    /*------------------------------------------------------------------------*
    *------------------------- Class Methods --------------------------------*
    *------------------------------------------------------------------------*/
    @Override
    public void update() {
        xAnim += 32;
        if (move) {
            inMove(direction);
            if (xAnim == 160) {
                xAnim = 0;
            }
        }

        if (!move) {
            if (xAnim == 160) {
                xAnim = 0;
            }
        }

    }

    @Override
    public boolean collision(int ref) {
        int x = 0, y = 0;
        
        switch(ref){
            case 4: // left
                x = (this.xPos - 64) / 64;
                y = this.yPos / 64;
            break;
            
            case 6: // right
                x = (this.xPos + 64) / 64;
                y = this.yPos / 64;
            break;
            
            case 8: // up
                x = this.xPos / 64;
                y = (this.yPos - 64) / 64;
            break;
            
            case 2: // down
                x = this.xPos / 64;
                y = (this.yPos + 64) / 64;
            break;
        }
        
        if (y < collideMap.length && x < collideMap[0].length)
            return collideMap[y][x];
        return false;
    }

    @Override
    public void paintComponent(Graphics g) {
        BufferedImage image = SpriteSheet.getSubimage(xAnim, yAnim, 32, 32);
        g.drawImage(image, getXPosition(), getYPosition(), 64, 64, null);
    }

    public void move(KeyEvent e, Map map) {
        checkstage(e,map);        
        if (!move) {

            if (e.getKeyChar() == 'a' && collision(4)) {
                //this.xPos = this.xPos - 64;
                this.xAnim = 0;
                this.yAnim = 160;
                this.move = true;
                this.direction = 1;
            }
            if (e.getKeyChar() == 'd' && collision(6)) {
                //this.xPos = this.xPos + 64;
                this.xAnim = 0;
                this.yAnim = 0;
                this.move = true;
                this.direction = 2;
            }
            if (e.getKeyChar() == 'w' && collision(8)) {
                //this.yPos = this.yPos - 64;
                this.move = true;
                this.direction = 3;
            }
            if (e.getKeyChar() == 's' && collision(2)) {
                //this.yPos = this.yPos + 64;
                this.move = true;
                this.direction = 4;
            }
        }
    }
    
    private void inMove(int ref){
        contmove+=1;
        if(contmove > 8){
        contmove=0;
        this.move = false;
        
        System.out.println("x: "+this.xPos+" y: "+this.yPos);
        return;
        }
            
        

        if (ref == 1) {
            this.xPos -= 8;
        }
        if (ref == 2) {
            this.xPos+= 8;
        }
        if (ref == 3) {
            this.yPos -= 8;
        }
        if (ref == 4) {
            this.yPos += 8;
        }
    }
    
    private void checkstage(KeyEvent e, Map map) {

        //Check the entries in stage 1
        if (e.getKeyChar() == 's' && yPos == 704 && (xPos == 448 || xPos == 512) && actualStage == 1) {
            this.yPos = 0;
            map.stage2();
            this.actualStage = 2;
        }

        //Check the entries in stage 2
        if (e.getKeyChar() == 'w' && yPos == 64 && (xPos == 448 || xPos == 512) && actualStage == 2) {
            this.yPos = 768;
            map.stage1();
            this.actualStage = 1;
        }
        if (e.getKeyChar() == 's' && yPos == 704 && (xPos == 448 || xPos == 512) && actualStage == 2) {
            this.yPos = -64;
            map.stage8();
            this.actualStage = 8;
        }
        if (e.getKeyChar() == 'd' && xPos == 960 && (yPos == 320 || yPos == 384) && actualStage == 2) {
            this.xPos = -64;
            map.stage3();
            this.actualStage = 3;

        }
        //Check the entries in stage 3
        if (e.getKeyChar() == 'a' && xPos == 0 && (yPos == 320 || yPos == 384) && actualStage == 3) {
            this.xPos = 1024;
            map.stage2();
            this.actualStage = 2;

        }
        if (e.getKeyChar() == 's' && yPos == 704 && (xPos >= 128 && xPos <= 834) && actualStage == 3) {
            this.yPos = -64;
            map.stage5();
            this.actualStage = 5;
        }
        if (e.getKeyChar() == 'w' && yPos == 0 && (xPos >= 128 && xPos <= 834) && actualStage == 3) {
            this.yPos = 768;
            map.stage4();
            this.actualStage = 4;
        }
        //Check the entries in stage 4
        if (e.getKeyChar() == 's' && yPos == 704 && (xPos >= 128 && xPos <= 834) && actualStage == 4) {
            this.yPos = -64;
            map.stage3();
            this.actualStage = 3;
        }
        if (e.getKeyChar() == 'w' && yPos == 64 && (xPos == 448 || xPos == 512) && actualStage == 4) {
            this.yPos = 768;
            map.stage7();
            this.actualStage = 7;
        }
        //Check the entries in stage 5
        if (e.getKeyChar() == 'w' && yPos == 0 && (xPos >= 128 && xPos <= 834) && actualStage == 5) {
            this.yPos = 768;
            map.stage3();
            this.actualStage = 3;
        }

        if (e.getKeyChar() == 'd' && xPos == 960 && (yPos == 320 || yPos == 384) && actualStage == 5) {
            this.xPos = -64;
            map.stage6();
            this.actualStage = 6;
        }
        //Check the entries in stage 6
        if (e.getKeyChar() == 'a' && xPos == 0 && (yPos == 320 || yPos == 384) && actualStage == 6) {
            this.xPos = 1024;
            map.stage5();
            this.actualStage = 5;
        }
        //Check the entries in stage 7
         if (e.getKeyChar() == 's' && yPos == 704 && (xPos == 448 || xPos == 512) && actualStage == 7) {
            this.yPos = 0;
            map.stage4();
            this.actualStage = 4;
        }
         if (e.getKeyChar() == 'w' && yPos == 256 && xPos == 512 && actualStage == 7) {
            this.yPos = 384;
            this.xPos = 768;
            map.stage8();
            this.actualStage = 8;
        }
         if (e.getKeyChar() == 'w' && yPos == 64 && (xPos == 448 || xPos == 512) && actualStage == 8) {
            this.yPos = 768;
            map.stage2();
            this.actualStage = 2;
        }
         
         
         
        
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
