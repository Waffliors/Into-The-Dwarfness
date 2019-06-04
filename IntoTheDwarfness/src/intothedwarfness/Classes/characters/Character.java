/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Abstract Class Character, responsible for serving as the basis for the     *
 * creation of the player, the enemies and the bosses of the phases            *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package intothedwarfness.Classes.characters;
import intothedwarfness.Classes.Tile;

import javax.swing.JPanel;

public abstract class Character  extends JPanel {
    Tile[][] tile_16x16 = loadTile(16);
    Tile[][] tile_32x32 = loadTile(32);    
    Tile[][] tile_64x64 = loadTile(64);
    
    public abstract void update();
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
