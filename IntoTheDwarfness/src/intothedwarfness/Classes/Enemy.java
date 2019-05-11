/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intothedwarfness.Classes;

import intothedwarfness.Interfaces.Drawable;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author matheus.vrsilva
 */
public class Enemy extends Character  implements Drawable {

    /*------------------------------------------------------------------------*
     *------------------------ Class Variables -------------------------------*
     *------------------------------------------------------------------------*/
    private int life;
    private int moveMax;
    private boolean move;
    private int direction;
    private final float speed;
    private boolean[][] collideMap;
    private int xPos, yPos, actualStage;
    private final BufferedImage SpriteSheet;

    private int contmove;

    private int xAnim, yAnim;

    /*------------------------------------------------------------------------*
     *----------------------- Class Constructor ------------------------------*
     *------------------------------------------------------------------------*/
    public Enemy(int xPos, int yPos, int stage, BufferedImage spriteSheet, boolean[][] collideMap) {
        this.life = 4;
        this.yPos = yPos;
        this.xPos = xPos;
        this.moveMax = 0;
        this.contmove = 0;
        this.move = false;
        this.actualStage = stage;
        this.speed = (float) 0.5;
        this.collideMap = collideMap;
        this.SpriteSheet = spriteSheet;

        this.xAnim = 0;
        this.yAnim = 0;
    }
    
    public int getXPosition() {
        return this.xPos;
    }
    
    public int getYPosition() {
        return this.yPos;
    }
    
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

    private void inMove(int ref) {
        contmove += 1;
        if (contmove > 8) {
            contmove = 0;
            this.move = false;

            System.out.println("x: " + this.xPos + " y: " + this.yPos);
            return;
        }

        if (ref == 1) {
            this.xPos -= 8;
        }
        if (ref == 2) {
            this.xPos += 8;
        }
        if (ref == 3) {
            this.yPos -= 8;
        }
        if (ref == 4) {
            this.yPos += 8;
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        BufferedImage image = SpriteSheet.getSubimage(xAnim, yAnim, 32, 32);
        g.drawImage(image, getXPosition(), getYPosition(), 64, 64, null);
    }
    
    @Override
    public boolean collision(int ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
