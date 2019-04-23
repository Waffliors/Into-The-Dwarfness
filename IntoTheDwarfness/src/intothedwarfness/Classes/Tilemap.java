package intothedwarfness.Classes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Tilemap {

    private BufferedImage image;
    private ArrayList<Object> TilemapList = new ArrayList();

    public Tilemap() {
        try {
            image = ImageIO.read(new File("src/Images/Dungeon_Tileset.png"));
            //TilemapList.add(image, 0, 0, 64, 64, 0, 0, 32, 32, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@Override
    public ArrayList<Object> tile() {
        return TilemapList;
    }
}
