/*******************************************************************************
 ** Abstract Class Character, responsible for serving as the basis for the    **
 ** creation of the player, the enemies and the bosses of the phases          **
 ******************************************************************************/
package intothedwarfness.Classes.characters;

import javax.swing.JPanel;
import intothedwarfness.Classes.Tile;

public abstract class Character  extends JPanel {
  
    public abstract void update();
    //The class initialize the base matrix for the paintComponnent
    protected Tile[][] tile_32x32 = loadTile(32);    
    protected Tile[][] tile_64x64 = loadTile(64);
    protected Tile[][] tile_96x96 = loadTile(96);
   
    /**
     * Method that initialize the matrix
     * 
     * @param tileSize : the size of the tile
     * @return : the matrix created
     */
    private Tile[][] loadTile(int tileSize) {
        Tile[][] spriteT = new Tile [20][20];
        int x, y, srcX1, srcY1, srcX2, srcY2, id = -1;
        
        for (y = 0; y < 20; y++) {
            srcY1 = tileSize * y;
            srcY2 = srcY1 + tileSize;
            for (x = 0; x < 20; x++) {
                srcX1 = tileSize * x;
                srcX2 = srcX1 + tileSize;
                spriteT[x][y] = (new Tile(srcX1, srcY1, srcX2, srcY2, id += 1));
            }
        }
        return spriteT;
    }
}
