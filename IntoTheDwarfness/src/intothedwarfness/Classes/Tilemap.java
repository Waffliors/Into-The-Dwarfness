/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Class Tile Map, where the Tiles will be created to be used in class Map    *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package intothedwarfness.Classes;

public class TileMap {
    
    /*------------------------------------------------------------------------*
     *------------------------ Class Variables -------------------------------*
     *------------------------------------------------------------------------*/
    private final int srcX1, srcY1, srcX2, srcY2;
    
    /*------------------------------------------------------------------------*
     *----------------------- Class Constructor ------------------------------*
     *------------------------------------------------------------------------*/
    public TileMap(int srcX1, int srcY1, int srcX2, int srcY2) {
        this.srcX1 = srcX1;
        this.srcY1 = srcY1;
        this.srcX2 = srcX2;
        this.srcY2 = srcY2;
    }

     /*------------------------------------------------------------------------*
     *------------------------- Class Methods --------------------------------*
     *------------------------------------------------------------------------*/
	public int getSrcX1() {return srcX1;}
	public int getSrcY1() {return srcY1;}
	public int getSrcX2() {return srcX2;}
	public int getSrcY2() {return srcY2;}

	@Override
	public String toString(){
		return "" + this.srcX1 + " " + this.srcY1 + " " + this.srcX2 + " " + this.srcY2;
	}
}
