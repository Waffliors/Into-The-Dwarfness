package intothedwarfness.Classes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Tilemap
{
	private BufferedImage image;
	private int destX1, destY1, destX2, destY2, srcX1, srcY1, srcX2, srcY2;
	private ArrayList<Tilemap> TilemapList = new ArrayList<>();
	
	public Tilemap(int destX1, int destY1, int destX2, int destY2, int srcX1, int srcY1, int srcX2, int srcY2) 
	{
		this.destX1 = destX1;
		this.destY1 = destY1;
		this.destX2 = destX2;
		this.destY2 = destY2;
		this.srcX1 = srcX1;
		this.srcY1 = srcY1;
		this.srcX2 = srcX2;
		this.srcY2 = srcY2;			
	}
	
	public void Tile() 
	{
		Tilemap left_border_1 = new Tilemap(this.destX1, this.destY2, this.destX2, this.destY2, this.srcX1, this.srcY1, this.srcX2, this.srcY2);
		TilemapList.add(left_border_1);
		
		System.out.println(TilemapList);
		/*
		try
		{
			image = ImageIO.read(new File("src/Images/Dungeon_Tileset.png"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}*/
	}
	
	public int getDestX1() 
	{
		return destX1;
	}

	public void setDestX1(int destX1) 
	{
		this.destX1 = destX1;
	}

	public int getDestY1() 
	{
		return destY1;
	}

	public void setDestY1(int destY1) 
	{
		this.destY1 = destY1;
	}

	public int getDestX2() 
	{
		return destX2;
	}

	public void setDestX2(int destX2) 
	{
		this.destX2 = destX2;
	}

	public int getDestY2() 
	{
		return destY2;
	}

	public void setDestY2(int destY2) 
	{
		this.destY2 = destY2;
	}

	public int getSrcX1() 
	{
		return srcX1;
	}

	public void setSrcX1(int srcX1) {
		this.srcX1 = srcX1;
	}

	public int getSrcY1() 
	{
		return srcY1;
	}

	public void setSrcY1(int srcY1) 
	{
		this.srcY1 = srcY1;
	}

	public int getSrcX2() 
	{
		return srcX2;
	}

	public void setSrcX2(int srcX2) 
	{
		this.srcX2 = srcX2;
	}

	public int getSrcY2() 
	{
		return srcY2;
	}

	public void setSrcY2(int srcY2) 
	{
		this.srcY2 = srcY2;
	}
}
