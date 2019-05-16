package intothedwarfness.Classes.characters;

import java.util.Random;
import intothedwarfness.Classes.Map;
import intothedwarfness.Classes.Song;
import intothedwarfness.Interfaces.Drawable;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Spider extends Character implements Drawable{
/* ***************************Class Variables******************************** */
    //Spider's settings
    private Map map;
    private char currentMove;
    private boolean[][] collideMap;
    private ArrayList<Song> songs;
    private final BufferedImage SpriteSheet;
    private int xPos, yPos, actualStage, life, IMGSIZE;
    private Random moveDirect;
    private int temp, path;
    private boolean completedPath;
    
    //Spider's animation
    private int cont, atkCont, hitCont, deadCont;
    private int drawRef, startLine, animation, endLine;
    private boolean looking2Right, attacking, gotHit, died, running;

/* **************************Class Constructor******************************* */
    public Spider(int initX, int initY, int stage, BufferedImage spriteSheet, ArrayList<Song> songs, Map map){
        //Spider's settings
        this.life = 4;
        this.map = map;
        this.xPos = initX;
        this.yPos = initY;
        this.IMGSIZE = 32;
        this.songs = songs;
        this.moveDirect = new Random();
        this.actualStage = stage;
        this.currentMove = '.';
        this.SpriteSheet = spriteSheet;
        this.completedPath = false;
        
        this.drawRef = 0;
        this.animation = 0;
        
        //Spider's animation
        this.died = false;
        this.gotHit = false;
        this.running = false;
        this.attacking = false;
        this.looking2Right = true;  
}   

/* ****************************Class Methods********************************* */
    private void move(){
        temp+=1;
        System.out.println("Temp atual: "+temp);
        
        if(temp < 75)
            this.xPos+=4;
        else if(temp>75)
            this.yPos+=4;
        else if(temp < 75 && temp < 140)
            this.xPos -=4;
        
    }
    private void animate(){
    }
    
    
    @Override
    public void update() {
        if (!completedPath) {
            move();
        }
        
        if (completedPath){
            geratePath();
        }
        animate();
        
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
        BufferedImage image = SpriteSheet.getSubimage(
                super.spriteTiles[drawRef][animation].getSrcX1(), 
                super.spriteTiles[drawRef][animation].getSrcY1(),
                IMGSIZE, IMGSIZE);
        //Draw in the player's position
        g.drawImage(image, xPos, yPos, 64, 64, null);
    }

    private void geratePath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
