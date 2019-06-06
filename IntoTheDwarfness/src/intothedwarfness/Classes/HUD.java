package intothedwarfness.Classes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import intothedwarfness.Classes.characters.Player;
import intothedwarfness.Interfaces.Drawable;

public class HUD implements Drawable
{
	private Player life;
	private ArrayList<BufferedImage> health_bar_image;
	

    //Loading the HUD images
	
	public HUD(ArrayList<BufferedImage> HUD, Player player)
	{
		this.life = player;
		this.health_bar_image = HUD;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		g.drawImage(health_bar_image.get(life.getLife()), 27, 50, 27 + 125, 50 + 32, 0, 0, 125, 32, null);
		g.drawImage(health_bar_image.get(7), 700, 50, 700 + 50, 50 + 50, 0, 0, 400, 400, null);
		g.drawImage(health_bar_image.get(8), 850, 50, 850 + 50, 50 + 50, 0, 0, 400, 400, null);
	}

	@Override
	public Boolean isStage(Map map) 
	{
		return null;
	}

}