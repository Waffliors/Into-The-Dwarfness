/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Class player, responsible for creating the character of the player and all  *
 * the tasks that the corresponding                                            *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package intothedwarfness.Classes;

import intothedwarfness.Interfaces.Drawable;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends Character implements Drawable{
    
    /*------------------------------------------------------------------------*
     *------------------------ Class Variables -------------------------------*
     *------------------------------------------------------------------------*/
    private final float speed;
    private int xPosition, yPosition;
    private final BufferedImage SpriteSheet;
    
    /*------------------------------------------------------------------------*
     *----------------------- Class Constructor ------------------------------*
     *------------------------------------------------------------------------*/
    public Player(BufferedImage spriteSheet){
        this.speed = (float) 0.5;
        this.xPosition = 64;
        this.yPosition = 60;
        this.SpriteSheet = spriteSheet;
    }
    
   /*------------------------------------------------------------------------*
    *------------------------- Class Methods --------------------------------*
    *------------------------------------------------------------------------*/
    public void move(KeyEvent e){
        //If left arrow
        if (e.getKeyCode() == 37 || e.getKeyChar() == 'a'){
            this.xPosition = this.xPosition - 64;
        }
        //If right arrow
        if (e.getKeyCode() == 39) {
            this.xPosition = this.xPosition + 64;
        }
        //If up arrow
        if (e.getKeyCode() == 38){
           this.yPosition = this.yPosition - 64;
        }
        //If down arrow
        if (e.getKeyCode() == 40) {
            this.yPosition = this.yPosition + 64;
        }
    }

    @Override
    public void update() {}

    @Override
    public void collision() {}


    
    @Override
    public void paintComponent(Graphics g){
        //drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer)
            BufferedImage image = SpriteSheet.getSubimage(128, 0, 32, 32);
            g.drawImage(image, getXPosition(), getYPosition(), 64, 64, null);
        
        //for (int i = 32; i<160; i++){
        //g.drawImage(SpriteSheet, 0,0,64,64, 0,0,32,32, null);
        //    System.out.println("cont");
        //}
        //g.drawImage(this.SpriteSheet, getXPosition(), getyPosition(),600, 600, null);

    }
    
    public int getXPosition() {return this.xPosition;}

    public int getYPosition() {return this.yPosition;}

    @Override
    public Image draw() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
