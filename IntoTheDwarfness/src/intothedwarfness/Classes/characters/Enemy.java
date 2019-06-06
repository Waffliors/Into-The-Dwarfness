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

public class Enemy extends Character implements Drawable, Collidable {

    /* *************************Class Variables****************************** */
    private List<Node> path;

    
    //Constants
    private final int IMGSIZE, TILESIZE;
    private final BufferedImage SPRITE;
    private final ArrayList<Song> SONGS;
    private final Map MAP;
    //Position
    private List<Integer> range;
    private char currentMove;
    private ArrayList<Point> pivots;
    private int xPos, yPos, actualStage, life, enemyType;
    private final ArrayList<Collidable> collidables;
    private boolean endedPath, followingPlayer, canPlaySong;
    //Animation
    private int cont, wait, atkCont, hitCont, deadCont, atkTimer;
    private int drawRef, startLine, animation, endLine;
    private boolean looking2Right, attacking, hitted, died, running, idle;

    /* ***********************Class Constructor****************************** */
    public Enemy(int x, int y, int stage, BufferedImage spriteSheet, 
            ArrayList<Song> songs, Map map, ArrayList<Collidable> collidables, 
            int enemyType) {
        
        
        this.enemyType = enemyType;
        switch (this.enemyType) {
            case 0:
                this.IMGSIZE = 32;
                break;
            case 2:
                this.IMGSIZE = 32;
                break;
            case 1:
                this.IMGSIZE = 32;
                break;
            case 3:
                this.IMGSIZE = 96;
                break;
            default:
                this.IMGSIZE = 0;
        }


        this.TILESIZE = 64;
        this.life = 4;
        this.yPos = y;
        this.xPos = x;
        this.actualStage = stage;
        this.pivots = new ArrayList();
        this.collidables = collidables;
        this.SPRITE = spriteSheet;
        this.SONGS = songs;
        this.MAP = map;
        this.endedPath = true;
        this.wait = 40;
        this.range = new ArrayList();
        this.followingPlayer = false;
        this.canPlaySong = true;
        
        
        //Player's animation
        
        this.died = false;
        this.hitted = false;
        this.running = false;
        this.attacking = false;        
        this.looking2Right = true;
        
        setPivot();
    }
    
    /* ********************Auxiliary methods of the Constructor****************** */
    private void setPivot() {
        this.pivots.clear();
        // Pivots position:
        // at 0: Left Top
        // at 1: Right Top 
        // at 2: Left Down
        // at 3: Right Down

//        if(enemyType == 3){
//        this.pivots.add(new Point(this.xPos, this.yPos));
//        this.pivots.add(new Point(this.xPos + IMGSIZE, this.yPos));
//        this.pivots.add(new Point(this.xPos, this.yPos + IMGSIZE));
//        this.pivots.add(new Point(this.xPos + IMGSIZE, this.yPos + IMGSIZE));
//            
//        }else{
        this.pivots.add(new Point(this.xPos, this.yPos));
        this.pivots.add(new Point(this.xPos + TILESIZE, this.yPos));
        this.pivots.add(new Point(this.xPos, this.yPos + TILESIZE));
        this.pivots.add(new Point(this.xPos + TILESIZE, this.yPos + TILESIZE));
    }



    /* ****************************Class Methods********************************* */
    public int getActualStage() {
        return actualStage;
    }

    @Override
    public void update() {
        if (!hitted && !attacking) {
            move();
        }
        
        animate();
        //setPivot();
    }

    public void move() {
         if (!this.died){
        if (!endedPath) {
            if (path.size() == 1) {
                idle = true;
                this.running = false;
                this.cont = 0;
                this.endedPath = true;
                setPivot();
                return;
            }
            if (path.size() <= 0) {
                path = null;
                setPivot();
                return;
            }
            //save the current position
            int antXPos = this.xPos;
            int antYPos = this.yPos;

            Node currentPos = ((LinkedList<Node>) path).getFirst();
            if (((LinkedList<Node>) path).size() != 1 && this.path != null && !died) {
                this.endedPath = false;
                this.running = true;
                this.idle = false;

                Node next = ((LinkedList<Node>) path).get(1);
                if (currentPos.getX() != next.getX()) {
                    //yPos += (currentPos.getX() < next.getX() ? 8 : -8);
                    if (currentPos.getX() < next.getX()) {
                        this.yPos = this.yPos + 4;
                    }
                    if (currentPos.getX() > next.getX()) {
                        this.yPos = this.yPos - 4;
                    }
                    if (this.yPos % 64 == 0) {
                        ((LinkedList<Node>) path).removeFirst();
                    }

                } else if (currentPos.getY() != next.getY()) {
                    //xPos += (currentPos.getY() < next.getY() ? 8 : -8);

                    if (currentPos.getY() < next.getY()) {
                        this.xPos = this.xPos + 4;
                        this.looking2Right = true;
                    }
                    if (currentPos.getY() > next.getY()) {
                        this.xPos = this.xPos - 4;
                        this.looking2Right = false;
                    }

                    if (this.xPos % 64 == 0) {
                        ((LinkedList<Node>) path).removeFirst();
                    }
                }
                //if had collision, return to the old position
                if (collision()) {
                    //this.xPos = antXPos;
                    //this.yPos = antYPos;
                }
            }
        }
        

        if (endedPath) {
            this.idle = true;
            wait += 1;
            if (wait >= 75) {
                wait = 0;
                this.endedPath = false;
            }
        }
        
        //set the new pivots
        setPivot();
    }
    }
    
    public boolean collision() {
        //get the sides of the player
        int rSide = (this.getPivotRT().getX() + this.getPivotRD().getX());
        int lSide = (this.getPivotLT().getX() + this.getPivotLD().getX());
        int topSide = (this.getPivotRT().getY() + this.getPivotLT().getY());
        int underSide = (this.getPivotRD().getY() + this.getPivotLD().getY());

        //for each colliding object to pick up the sides of the object and 
        //compare with those of the player
        for (Collidable c : this.collidables) {
            int rSide_C = (c.getPivotRT().getX() + c.getPivotRD().getX());
            int lSide_C = (c.getPivotLT().getX() + c.getPivotLD().getX());
            int topSide_C = (c.getPivotRT().getY() + c.getPivotLT().getY());
            int underSide_C = (c.getPivotRD().getY() + c.getPivotLD().getY());

            // Is the right edge of the player to the right of the left edge of the object?
            if (rSide > lSide_C) {
                // Is the left edge of the player to the left of the right edge of the object?
                if (lSide < rSide_C) {
                    // The bottom edge of the player is below the top edge of the object?
                    if (underSide > topSide_C) {
                        // Is the top edge of the player above the bottom edge of the object?
                        if (topSide < underSide_C) {
                            if ("PlayerType".equals(c.getType())) {
                                this.atkTimer += 1;
                                
                                if (atkTimer >= 5) {
                                this.attacking = true;
                                    if (attacking && atkCont == 0) {
                                        c.gotHit();
                                        System.out.println("Acertou inimigo");
                                    }
                                    atkTimer = 0;
                                }
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    
    private void animate() {
        this.cont+= 1;

        if (this.attacking) {
            this.atkCont += 1;
            this.atkTimer +=1;
        }
        if (this.hitted) {
            this.hitCont += 1;
        }
        if (this.died) {
            this.deadCont += 1;
        }
        
        
        if (!this.died) {
            if (!this.attacking && !this.hitted) {
                //Idle and Running while looking to the Right
                if (this.looking2Right) {
                    if (this.idle) {
                        //aranha
                        if (this.enemyType == 0) {
                            startAnimation(8, 0, 4);
                        }
                        //fire elemental
                        if (this.enemyType == 1) {
                            startAnimation(1, 0, 5);
                        }
                        //gladiador
                        if (this.enemyType == 2) {
                            startAnimation(5, 0, 5);
                        }
                        //minotauro
                        if (this.enemyType == 3) {
                            startAnimation(0, 0, 4);
                        }

                    }
                    if (this.running) {
                        //aranha
                        if (this.enemyType == 0) {
                            startAnimation(9, 0, 5);
                        }
                        //fire elemental
                        if (this.enemyType == 1) {
                            startAnimation(0, 0, 7);
                        }
                        //gladiador
                        if (this.enemyType == 2) {
                            startAnimation(6, 0, 7);
                        }
                        //minotauro
                        if (this.enemyType == 3) {
                            startAnimation(1, 0, 7);
                        }
                    }
                }

                //Idle and Running while looking to the Left
                if (!this.looking2Right) {
                    if (this.idle) {
                        if (this.enemyType == 0) {
                            startAnimation(0, 0, 4);
                        }
                        //fire elemental
                        if (this.enemyType == 1) {
                            startAnimation(4, 0, 4);
                        }
                        //gladiador
                        if (this.enemyType == 2) {
                            startAnimation(0, 0, 5);
                        }
                        //minotauro
                        if (this.enemyType == 3) {
                            startAnimation(10, 0, 4);
                        }
                    }
                    if (this.running) {
                        //aranha
                        if (this.enemyType == 0) {
                            startAnimation(1, 0, 5);
                        }
                        //fire elemental
                        if (this.enemyType == 1) {
                            startAnimation(4, 0, 4);
                        }
                        //gladiador
                        if (this.enemyType == 2) {
                            startAnimation(1, 0, 7);
                        }
                        //minotauro
                        if (this.enemyType == 3) {
                            startAnimation(11, 0, 7);
                        }
                    }
                }
                //Stop condition of animations of the type "movement"
                //All the animations of moving types use the same counter

                if (this.cont >= this.endLine) {
                    this.cont = this.startLine;
                }
                this.drawRef = this.cont;
            }
            
            
            //Attack animations
            if (this.attacking) {
                if (this.looking2Right) {
                    //aranha
                    if (this.enemyType == 0) {
                        startAnimation(10, 0, 8);
                    }
                    //fire elemental
                    if (this.enemyType == 1) {
                        startAnimation(0, 0, 4);
                    }
                    //gladiador
                    if (this.enemyType == 2) {
                        startAnimation(7, 0, 6);
                    }
                    //minotauro
                    if (this.enemyType == 3) {
                        startAnimation(6, 0, 8);
                    }
                }
                if (!this.looking2Right) {
                    //aranha
                    if (this.enemyType == 0) {
                        startAnimation(2, 0, 8);
                    }
                    //fire elemental
                    if (this.enemyType == 1) {
                        startAnimation(3, 0, 4);
                    }
                    //gladiador
                    if (this.enemyType == 2) {
                        startAnimation(2, 0, 6);
                    }
                    //minotauro
                    if (this.enemyType == 3) {
                        startAnimation(16, 0, 7);
                    }
                }
                //Play the song of this animation
                if (atkCont == 1) {
                    playsong(1);
                }
                //Stop condition of animations of the type "atatck"
                if (this.atkCont >= this.endLine) {
                    this.atkCont = 0;
                    this.cont = 0;
                    this.attacking = false;
                }
                this.drawRef = this.atkCont;
            }
            
            if (this.hitted) {
                if (this.looking2Right) {
                    //aranha
                    if (this.enemyType == 0) {
                        startAnimation(14, 0, 8);
                    }
                    //gladiador
                    if (this.enemyType == 2) {
                        startAnimation(8, 0, 2);
                    }
                    //minotauro
                    if (this.enemyType == 3) {
                        startAnimation(8, 0, 2);
                    }
                }
                if (!this.looking2Right) {
                    //aranha
                    if (this.enemyType == 0) {
                        startAnimation(6, 0, 8);
                    }
                    //gladiador
                    if (this.enemyType == 2) {
                        startAnimation(3, 0, 2);
                    }
                    //minotauro
                    if (this.enemyType == 3) {
                        startAnimation(18, 0, 2);
                    }
                }
                if (this.hitCont >= this.endLine) {
                    this.cont = 0;
                    this.hitCont = 0;
                    this.hitted = false;
                    this.life -= 1;

                    if (this.life == 0) {
                        died = true;
                    }
                }
                this.drawRef = this.hitCont;
            }
        }
        //Dead Player
        if (this.died) {
            this.followingPlayer = false;
            if (this.looking2Right) {
                //aranha
                if (this.enemyType == 0) {
                    startAnimation(14, 0, 8);
                }
                //fire elemental
                if (this.enemyType == 1) {
                    startAnimation(2, 0, 4);
                }
                //gladiador
                if (this.enemyType == 2) {
                    startAnimation(7, 0, 6);
                }
                //minotauro
                if (this.enemyType == 3) {
                    startAnimation(6, 0, 8);
                }
            }
            if (!this.looking2Right) {
                //aranha
                if (this.enemyType == 0) {
                    startAnimation(6, 0, 8);
                }
                //fire elemental
                if (this.enemyType == 1) {
                    startAnimation(5, 0, 4);
                }
                //gladiador
                if (this.enemyType == 2) {
                    startAnimation(2, 0, 6);
                }
                //minotauro
                if (this.enemyType == 3) {
                    startAnimation(16, 0, 7);
                }
            }

            if (this.deadCont >= this.endLine) {
                this.deadCont = this.endLine;
            }
            this.drawRef = this.deadCont;
        }
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
    
    public boolean endedPath(){
        return this.endedPath;
    }
    
    private int[] getArea (){
        int resp[] = new int[4];
        resp[0] = this.xPos - 192;
        resp[1] = this.yPos - 192;
        resp[2] = this.xPos + 192;
        resp[3] = this.yPos + 192;
        return resp;
    }
    
    public boolean inRange(Player player) {
        if (player.getXPosition() > getArea()[0] && player.getXPosition() < getArea()[2]) {
            if (player.getYPosition() > getArea()[1] && player.getYPosition() < getArea()[3]) {
                this.followingPlayer = true;
                this.endedPath = false;
                return true;
            }
        }
        this.followingPlayer = false;
        return false;
    }
    
    @Override
    public Point getPivotLT() {
        return this.pivots.get(0);
    }

    @Override
    public Point getPivotRT() {
        return this.pivots.get(1);
    }

    @Override
    public Point getPivotLD() {
        return this.pivots.get(2);
    }

    @Override
    public Point getPivotRD() {
        return this.pivots.get(3);
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
        //Draw the sprite
        if (this.enemyType == 0 || this.enemyType == 2 || this.enemyType == 1) {
            BufferedImage image = SPRITE.getSubimage(
                    super.tile_32x32[drawRef][animation].getSrcX1(),
                    super.tile_32x32[drawRef][animation].getSrcY1(),
                    IMGSIZE, IMGSIZE);
            g.drawImage(image, xPos, yPos, 64, 64, null);
        }
        
        if (this.enemyType == 3) {
            BufferedImage image = SPRITE.getSubimage(
                    super.tile_96x96[drawRef][animation].getSrcX1(),
                    super.tile_96x96[drawRef][animation].getSrcY1(),
                    IMGSIZE, IMGSIZE);
            g.drawImage(image, xPos-10, yPos, 96, 96, null);
        }
        
        
        if (this.followingPlayer) {
            BufferedImage alert;
            //Draw e exclamation
            switch (this.enemyType) {
                case 0:
                    alert = SPRITE.getSubimage(super.tile_32x32[8][0].getSrcX1(), super.tile_32x32[8][0].getSrcY1(), IMGSIZE, IMGSIZE);
                    g.drawImage(alert, xPos, yPos, 64, 64 + 32, null);
                    break;
                case 1:
                    alert = SPRITE.getSubimage(super.tile_16x16[5][0].getSrcX1(), super.tile_32x32[5][0].getSrcY1(), IMGSIZE, IMGSIZE);
                    g.drawImage(alert, xPos, yPos, 64, 64, null);
                    break;
                case 2:
                    alert = SPRITE.getSubimage(super.tile_32x32[7][0].getSrcX1(), super.tile_32x32[7][0].getSrcY1(), IMGSIZE, IMGSIZE);
                    g.drawImage(alert, xPos, yPos, 64, 64, null);
                    break;
                case 3:
                    break;
            }
        }
        
    }

    @Override
    public String getType() {
        return "EnemyType";
    }

    @Override
    public void gotHit() {
        System.out.println("APANHOU");
        this.hitted = true;
    }
}