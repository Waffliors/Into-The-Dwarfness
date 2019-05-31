/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intothedwarfness.Classes.characters;

import intothedwarfness.Classes.Map;
import intothedwarfness.IA.Node;
import intothedwarfness.Interfaces.Drawable;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import intothedwarfness.IA.AStar;

/**
 *
 * @author matheus.vrsilva
 */
public class Enemy extends Character implements Drawable {

    /*------------------------------------------------------------------------*
     *------------------------ Class Variables -------------------------------*
     *------------------------------------------------------------------------*/
    private int life;
    private int moveMax;
    private boolean move, turn;
    private int direction;
    private Node[][] collideMap;
    private int xPos, yPos;
    private int  actualStage;
    private final BufferedImage SpriteSheet;
    private int x;
    
    private int contmove;

    private int xAnim, yAnim;

    //ia example
    private int y;
    private int sx;
    private int sy;

    private int speed;

    private boolean walking;
    private boolean fixing;
    private List<Node> path;

    /*------------------------------------------------------------------------*
     *----------------------- Class Constructor ------------------------------*
     *------------------------------------------------------------------------*/
    public Enemy(int xPos, int yPos, int stage, BufferedImage spriteSheet, Node[][] collideMap) {
        this.life = 4;
        this.yPos = yPos;
        this.xPos = xPos;
        this.moveMax = 0;
        this.contmove = 0;
        this.move = true;
        this.turn = false;
        this.direction = 1; // esquerda
        this.actualStage = stage;
        this.collideMap = collideMap;
        this.SpriteSheet = spriteSheet;
        
        this.xAnim = 0;
        this.yAnim = 0;
        
        //ia example
        this.sx = 0;
        this.sy = 0;
        speed = 2;

        walking = false;
        fixing = false;
        path = null;
    }

    
    
    
    @Override
    public void update()
	{
    	move();
//		if (fixing)
//		{
//			fix();
//		}
//		if (walking)
//		{
//  			walk();
//		}
	}
    
    public void move()
    {    	
    	if (path == null) {
    		//walking = false;
    		return;
    	}
    	if (path.size() <= 0) {
    		path = null;
    		return;
    	}
    	
    	Node currentPos = ((LinkedList<Node>) path).getFirst();
    	if (((LinkedList<Node>) path).size() != 1) {
    		Node next = ((LinkedList<Node>) path).get(1);
    		if (currentPos.getX() != next.getX()) 
    		{
    			yPos += (currentPos.getX() < next.getX() ? 8 : -8);
    			if(yPos % 64 == 0)
    			{
    				((LinkedList<Node>) path).removeFirst();
    			}
    		}    	

    		else if (currentPos.getY() != next.getY()) 
    		{
    			xPos += (currentPos.getY() < next.getY() ? 8 : -8);
    			if(xPos % 64 == 0)
    			{
    				((LinkedList<Node>) path).removeFirst();
    			}
    		}
    	}
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
    
    @Override
    public Boolean isStage(Map map) {
        if (map.actualStage == this.actualStage) {
            return true;
        }
        return false;
    }

    @Override
    public void paintComponent(Graphics g) {
        BufferedImage image = SpriteSheet.getSubimage(xAnim, yAnim, 32, 32);
        g.drawImage(image, (int) getXPosition(), (int) getYPosition(), 64, 64, null);
    }

    public boolean collision(int ref) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
