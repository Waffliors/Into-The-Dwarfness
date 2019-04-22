/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Class player, responsible for creating the character of the player and all  *
 * the tasks that the corresponding                                            *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package intothedwarfness.Classes;
import intothedwarfness.Interfaces.Drawable;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random; 



public class Player extends Character implements Drawable{
    private Random rand; 
    private float speed;
    private int xPosition;
    private int yPosition;
    private int cont;
    private Image defImage;
    private ArrayList<Image> AnimationList = new ArrayList();
    /*-------------------------- Constructor ---------------------------------*/
    public Player(){
        this.speed = (float) 0.5;
        this.xPosition = 10;
        this.yPosition = 10;
        this.cont = 0;
        this.rand = new Random();
        this.defImage = Toolkit.getDefaultToolkit().getImage("images\\Dwarf_01.png");

        this.AnimationList.add(Toolkit.getDefaultToolkit().getImage("images\\Dwarf_01.png"));
        this.AnimationList.add(Toolkit.getDefaultToolkit().getImage("images\\Dwarf_02.png"));
        this.AnimationList.add(Toolkit.getDefaultToolkit().getImage("images\\Dwarf_03.png"));
        this.AnimationList.add(Toolkit.getDefaultToolkit().getImage("images\\Dwarf_04.png"));
        this.AnimationList.add(Toolkit.getDefaultToolkit().getImage("images\\Dwarf_05.png"));
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
    public Image draw() {
        if (this.cont > 6)
            this.cont = 0;
        cont++;
        this.defImage = this.AnimationList.get(cont);
        return this.defImage;
    }
    
    public int getXPosition() {
        return this.xPosition;
    }

    public int getyPosition() {
        return this.yPosition;
    }
    
}
