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

public class Player extends Character implements Drawable{
    
    /*------------------------------------------------------------------------*
     *------------------------ Class Variables -------------------------------*
     *------------------------------------------------------------------------*/
    private final float speed;
    private int xPos, yPos;
    private final BufferedImage SpriteSheet;
    private Dimension screenSize;
    
    /*------------------------------------------------------------------------*
     *----------------------- Class Constructor ------------------------------*
     *------------------------------------------------------------------------*/
    public Player(BufferedImage spriteSheet){
        this.speed = (float) 0.5;
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.xPos = 512;
        this.yPos = 128;
        this.SpriteSheet = spriteSheet;
    }
    
   /*------------------------------------------------------------------------*
    *------------------------- Class Methods --------------------------------*
    *------------------------------------------------------------------------*/

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

    public void move(KeyEvent e) {
        if (e.getKeyChar() == 'a') {
            this.xPos = this.xPos - 64;
        }
        if (e.getKeyChar() == 'd') {
            this.xPos = this.xPos + 64;
        }
        if (e.getKeyChar() == 'w') {
            this.yPos = this.yPos - 64;
        }
        if (e.getKeyChar() == 's') {
            this.yPos = this.yPos + 64;
        }
    }
        
    public int getXPosition() {return this.xPos;}

    public int getYPosition() {return this.yPos;}
    
}
