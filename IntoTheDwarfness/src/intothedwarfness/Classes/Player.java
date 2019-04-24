/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Class player, responsible for creating the character of the player and all  *
 * the tasks that the corresponding                                            *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package intothedwarfness.Classes;
import intothedwarfness.Interfaces.Drawable;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random; 



public class Player extends Character implements Drawable{
    private Random rand; 
    private float speed;
    private int xPosition;
    private int yPosition;
    private int cont;
    private BufferedImage SpriteSheet;
    private ArrayList<BufferedImage> AnimationList = new ArrayList();
    /*-------------------------- Constructor ---------------------------------*/
    public Player(BufferedImage spriteSheet){
        this.speed = (float) 0.5;
        this.xPosition = 10;
        this.yPosition = 10;
        this.cont = 0;
        this.rand = new Random();
        this.SpriteSheet = spriteSheet;
    }
    
    /*---------------------------- Methods -----------------------------------*/
    public void move(KeyEvent e){
        //If left arrow
        if (e.getKeyCode() == 37){
            System.out.println("left");
            this.xPosition -= 10;
            System.out.println(this.xPosition);
        }
        //If right arrow
        if (e.getKeyCode() == 39) {
            System.out.println("right");
            this.xPosition += 10;
            System.out.println(this.xPosition);
        }
        //If up arrow
        if (e.getKeyCode() == 38){
           System.out.println("up");
            this.yPosition -= 10;
            System.out.println(this.yPosition);
        }
        //If down arrow
        if (e.getKeyCode() == 40) {
            System.out.println("down");
            this.yPosition += 10;
            System.out.println(this.yPosition);
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void collision() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BufferedImage draw() {
        return this.SpriteSheet;
    }
    
    public int getXPosition() {
        return this.xPosition;
    }

    public int getyPosition() {
        return this.yPosition;
    }
    
}
