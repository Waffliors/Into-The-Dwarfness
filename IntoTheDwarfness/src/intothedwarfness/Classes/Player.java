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
    private int xPos, yPos;
    private final BufferedImage SpriteSheet;
    private boolean[][] collideMap;

    /*------------------------------------------------------------------------*
     *----------------------- Class Constructor ------------------------------*
     *------------------------------------------------------------------------*/
    public Player(BufferedImage spriteSheet, boolean [][]collideMap) {
        this.speed = (float) 0.5;
        this.xPos = 512;
        this.yPos = 64;
        this.collideMap = collideMap;
        this.SpriteSheet = spriteSheet;
    }

    /*------------------------------------------------------------------------*
    *------------------------- Class Methods --------------------------------*
    *------------------------------------------------------------------------*/
    @Override
    public void update() {
    }

    @Override
    public boolean collision(int ref) {
        boolean resp = false;
        switch(ref){
            case 4: // left
                System.out.println("Left");
                resp = true;
            break;
            
            case 6: // right
                System.out.println("Right");
                resp = true;
            break;
            
            case 8: // up
                System.out.println("Up");
            break;
            
            case 2: // down
                System.out.println("Down");
            break;
        }
        
        return resp;
    }

    @Override
    public void paintComponent(Graphics g) {
        BufferedImage image = SpriteSheet.getSubimage(128, 0, 32, 32);
        g.drawImage(image, getXPosition(), getYPosition(), 64, 64, null);
    }

    public void move(KeyEvent e) {
        if (e.getKeyChar() == 'a' && collision(4)) {
            this.xPos = this.xPos - 64;
        }
        if (e.getKeyChar() == 'd' && collision(6)) {
            this.xPos = this.xPos + 64;
        }
        if (e.getKeyChar() == 'w' && collision(8)) {
            this.yPos = this.yPos - 64;
        }
        if (e.getKeyChar() == 's' && collision(2)) {
            this.yPos = this.yPos + 64;
        }
    }

    public int getXPosition() {
        return this.xPos;
    }

    public int getYPosition() {
        return this.yPos;
    }

}
