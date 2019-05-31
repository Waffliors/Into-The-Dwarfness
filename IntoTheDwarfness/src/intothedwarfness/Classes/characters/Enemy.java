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
    	int length = path.size();
    	
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

    		//		System.out.println(next.getX() + " " + );
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

//	public void followPath(List<Node> path)
//	{
//		this.path = path;
//		if (walking)
//		{
//			fixing = true;
//			walking = false;
//		}
//		else
//		{
//			walking = true;
//		}
//	}

//	private void fix()
//	{
//		if (sx > 0)
//		{
//			sx -= speed;
//			if (sx < 0)
//			{
//				sx = 0;
//			}
//		}
//		if (sx < 0)
//		{
//			sx += speed;
//			if (sx > 0)
//			{
//				sx = 0;
//			}
//		}
//		if (sy > 0)
//		{
//			sy -= speed;
//			if (sy < 0)
//			{
//				sy = 0;
//			}
//		}
//		if (sy < 0)
//		{
//			sy += speed;
//			if (sy > 0)
//			{
//				sy = 0;
//			}
//		}
//		if (sx == 0 && sy == 0)
//		{
//			fixing = false;
//			walking = true;
//		}
//	}
	

//	private void walk()
//	{
//		if (path == null)
//		{
//			walking = false;
//			return;
//		}
//		if (path.size() <= 0)
//		{
//			walking = false;
//			path = null;
//			return;
//		}
//		Node next = ((List<Node>) path).get(0);
//		if (next.getX() != x)
//		{
//			sx += (next.getX() < x ? -speed : speed);
//			if (sx % 32 == 0)
//			{
//				((LinkedList<Node>) path).removeFirst();
//				if (sx > 0)
//					x++;
//				else
//					x--;
//				sx %= 32;
//			}
//		}
//		else if (next.getY() != y)
//		{
//			sy += (next.getY() < y ? -speed : speed);
//			if (sy % 32 == 0)
//			{
//				((LinkedList<Node>) path).removeFirst();
//				if (sy > 0)
//					y++;
//				else
//					y--;
//				sy %= 32;
//			}
//		}
//	}

	public int getSx()
	{
		return sx;
	}

	public void setSx(int sx)
	{
		this.sx = sx;
	}

	public int getSy()
	{
		return sy;
	}

	public void setSy(int sy)
	{
		this.sy = sy;
	}
        
        
        
        
    public float getXPosition() {
        return this.xPos;
    }

    public float getYPosition() {
        return this.yPos;
    }
//
//    private void inMove(int ref) {
//        contmove += 1;
//        if (contmove > 8) {
//            contmove = 0;
//            //this.move = false;
//
//            return;
//        }
//
//        if (ref == 1) {
//            this.xPos -= 8;
//        }
//        if (ref == 2) {
//            this.xPos += 8;
//        }
//        if (ref == 3) {
//            this.yPos -= 8;
//        }
//        if (ref == 4) {
//            this.yPos += 8;
//        }
//    }

    
    
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
