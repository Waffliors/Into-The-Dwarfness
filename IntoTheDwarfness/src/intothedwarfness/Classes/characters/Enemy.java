/** ****************************************************************************
 ** Class "enemy", here the enemies will be drawn, according to what will be **
 ** received, a different type of enemy will be drawn with their own         **
 ** characteristics and movements                                            **
 ***************************************************************************** */
package intothedwarfness.Classes.characters;

import java.util.List;
import java.awt.Graphics;
import java.util.LinkedList;
import intothedwarfness.IA.Node;
import java.awt.image.BufferedImage;
import intothedwarfness.Classes.Map;
import intothedwarfness.Classes.Point;
import intothedwarfness.Classes.Song;
import intothedwarfness.Interfaces.Collidable;
import intothedwarfness.Interfaces.Drawable;
import java.util.ArrayList;

public class Enemy extends Character implements Drawable {

    /* *************************Class Variables****************************** */
    private List<Node> path;

    
    //Constants
    private final int IMGSIZE, TILESIZE;
    private final BufferedImage SPRITE;
    private final ArrayList<Song> SONGS;
    private final Map MAP;
    //Position
    private char currentMove;
    private ArrayList<Point> pivots;
    private ArrayList<Collidable> collidables;
    private int xPos, yPos, actualStage, life;
    //Animation
    private int cont, atkCont, hitCont, deadCont;
    private int drawRef, startLine, animation, endLine;
    private boolean looking2Right, attacking, gotHit, died, running, idle;
    /* ***********************Class Constructor****************************** */
    public Enemy(int xPos, int yPos, int stage, BufferedImage spriteSheet, ArrayList<Song> songs, Map map) {
        this.life = 4;
        this.yPos = yPos;
        this.xPos = xPos;
        this.actualStage = stage;
        this.SPRITE = spriteSheet;
        this.IMGSIZE = 32;
        this.TILESIZE = 64;
        this.SONGS = songs;
        this.MAP = map;
        
        this.looking2Right = true;
    }

    /* ****************************Class Methods********************************* */
    @Override
    public void update() {
        animate();
        move();
        
    }

    public void move() {
        if (path == null) {
            idle = true;
            this.running = false;
            this.cont = 0;
            return;
        }
        if (path.size() <= 0) {
            path = null;
            return;
        }

        Node currentPos = ((LinkedList<Node>) path).getFirst();
        if (((LinkedList<Node>) path).size() != 1 && this.path != null) {
            System.out.println("ligooou");
            this.running = true;
            this.idle = false;

            Node next = ((LinkedList<Node>) path).get(1);
            if (currentPos.getX() != next.getX()) {
                //yPos += (currentPos.getX() < next.getX() ? 8 : -8);
                if (currentPos.getX() < next.getX()) {
                    yPos += 4;
                }
                if (currentPos.getX() > next.getX()) {
                    yPos -= 4;
                }
                if (yPos % 64 == 0) {
                    ((LinkedList<Node>) path).removeFirst();
                }

            } else if (currentPos.getY() != next.getY()) {
                //xPos += (currentPos.getY() < next.getY() ? 8 : -8);

                if (currentPos.getY() < next.getY()) {
                    xPos += 4;
                    looking2Right = true;
                }
                if (currentPos.getY() > next.getY()) {
                    xPos -= 4;
                    looking2Right = false;
                }

                if (xPos % 64 == 0) {
                    ((LinkedList<Node>) path).removeFirst();
                }
            }
        }
    }

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
           // if (!attacking && !gotHit) {
                //Idle and Running while looking to the Right
                if (looking2Right) {
                    if (idle) {
                        startAnimation(5, 0, 5);
                    }
                    if (running) {
                        startAnimation(6, 0, 8);
                    }
                }
                                
                //Idle and Running while looking to the Left
               if (!looking2Right) {
                if (idle) {
                    startAnimation(0, 0, 5);
                }
                if (running) {
                    startAnimation(1, 0, 8);
                }
            }
                //Play the song of this animation
                if((cont%2 == 1 ) && running){
                    playsong(4);
                }
                //Stop condition of animations of the type "movement"
                //All the animations of moving types use the same counter
        
                if (cont == this.endLine) {
                    cont = this.startLine;
                }
                this.drawRef = cont;
            }
        //}
    }
    
    //Method that defines the settings of the current animation
    private void startAnimation(int animation, int startLine, int endLine) {
        this.startLine= startLine;
        this.animation = animation;
        this.endLine= endLine;
    }
    
    //Method that play the player's songs
    private void playsong(int ref){
        SONGS.get(ref).playSoundOnce();
    }

    public void setPath(List<Node> path) {
        this.path = path;
    }

    public int getXPosition() {
        return this.xPos;
    }

    public int getYPosition() {
        return this.yPos;
    }

    public Node getNodePos(){
        return this.MAP.getNode(yPos/64, xPos/64);  
    }
    public void getNewPath(){
        
    }
    
    @Override
    public Boolean isStage(Map map) {
        if (map.actualStage == this.actualStage) {
            return true;
        }
        return false;
    }

    @Override
    public void paintComponent(Graphics g) {
        //Get a piece of the Image
        BufferedImage image = SPRITE.getSubimage(
                super.spriteTiles[drawRef][animation].getSrcX1(), 
                super.spriteTiles[drawRef][animation].getSrcY1(),
                IMGSIZE, IMGSIZE);
        //Draw in the player's position
        g.drawImage(image, xPos, yPos, 64, 64, null);
        
    }

    public boolean collision(int ref) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
