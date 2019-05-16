/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Abstract Class Character, responsible for serving as the basis for the     *
 * creation of the player, the enemies and the bosses of the phases            *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package intothedwarfness.Classes.characters;
import intothedwarfness.Classes.Tile;
import javax.swing.JPanel;

public abstract class Character  extends JPanel {
    Tile[][] spriteTiles = loadTile();    
    
    public abstract void update();
    private Tile[][] loadTile() {
        Tile[][] spriteT = new Tile [20][20];
        int x, y, srcX1, srcY1, srcX2, srcY2, id = -1;
        
        for (y = 0; y < 20; y++) {
            srcY1 = 32 * y;
            srcY2 = srcY1 + 32;
            for (x = 0; x < 20; x++) {
                srcX1 = 32 * x;
                srcX2 = srcX1 + 32;
                spriteT[x][y] = (new Tile(srcX1, srcY1, srcX2, srcY2, id += 1));
            }
        }
        return spriteT;
    }
}
