package intothedwarfness;

import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import javax.imageio.ImageIO;
import intothedwarfness.Classes.Map;
import java.awt.image.BufferedImage;
import intothedwarfness.Classes.Player;
import intothedwarfness.Classes.ScreenPiece;
import intothedwarfness.Classes.Window;
import intothedwarfness.Classes.TileMap;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                             *                   
 *                            @Studio: Waffliors                               *
 *                             @Team: Bugnatron                                *
 *            @Devs: Matheus Vicente, Nathan Andre and Raphael Melo            *
 *                             GDD available at:                               *
 *                                                                             *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

public class IntoTheDwarfness{
    public static void main(String[] args) throws IOException, InterruptedException {
        
        ArrayList<BufferedImage> spriteList = loadImages();
        ArrayList<ScreenPiece> screenPieceList = loadScreenPieces();
        ArrayList<TileMap> mapTiles = loadTile();

        //Creating the Map that receives a image and the tiles
        Map map = new Map(mapTiles, spriteList.get(18), screenPieceList);

        //Initializing the Player
        Player player = new Player(spriteList.get(0));

        //Initializing game window:
        Window screen = new Window(player, map);

        screen.initialize();
        screen.run();
    }
    
    
    
    private static ArrayList<BufferedImage> loadImages() throws IOException {
        /*Function that load the Sprite Sheets of the game, see positions in 
         *"documentation/SpriteListPositions"*/
        ArrayList<BufferedImage> spriteSheetList = new ArrayList();
        for (int i = 1; i <= 19; i++) {
            spriteSheetList.add(ImageIO.read(new File("images/" + i + ".png")));
        }
        return spriteSheetList;
    }
    
    private static ArrayList<ScreenPiece> loadScreenPieces() {
        /*Function that load all de 192 pieces of the screen*/
        ArrayList<ScreenPiece> screenPieceList = new ArrayList();
        int x, y, srcX1, srcY1, srcX2 = 64, srcY2 = 64, id = -1;
        
        for (y = 0; y < 12; y++) {
            srcX1 = 0;
            srcX2 = 0;
            srcY1 = 64 * y;
            srcY2 = srcY1 + 64;

            for (x = 0; x < 16; x++) {
                srcX1 = 64 * x;
                srcX2 = srcX1 + 64;
                screenPieceList.add(new ScreenPiece(srcX1, srcX2, srcY1, srcY2, id+=1));
            }      
        }
        return screenPieceList;
    }

    private static ArrayList<TileMap> loadTile() {
        /* Function that load the tiles from map SpriteSheet*/
        ArrayList<TileMap> mapTiles = new ArrayList();
        int x, y,srcX1,srcY1, id=-1,
                srcX2 = 32,
                srcY2 = 32;

        for (y = 0; y < 20; y++) {
            srcX1 = 0;
            srcX2 = 0;
            srcY1 = 32 * y;
            srcY2 = srcY1 + 32;

            for (x = 0; x < 16; x++) {
                srcX1 = 32 * x;
                srcX2 = srcX1 + 32;

                mapTiles.add(new TileMap(srcX1, srcY1, srcX2, srcY2, id+=1));
            }
        }
        
        return mapTiles;
    }
    
    
}
