package intothedwarfness.Classes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import intothedwarfness.Classes.characters.Player;
import intothedwarfness.Interfaces.Drawable;

public class HUD implements Drawable
{
	private int life, xPos, yPos;
	private ArrayList<BufferedImage> health_bar_image;
	

    //Loading the HUD images
	
	public HUD(ArrayList<BufferedImage> HUD, Player Player)
	{
		this.life = Player.getLife();
		this.health_bar_image = HUD;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		g.drawImage(health_bar_image.get(life), 15, 50, 15 + 125, 50 + 32, 0, 0, 125, 32, null);
	}

	@Override
	public Boolean isStage(Map map) 
	{
		return null;
	}

}
