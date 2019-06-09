/*******************************************************************************
 **      Tile Class                                                           **
 **                                                                           **
 ** Create the tiles that whill be like pieces of the screen and will be used **
 ** during the game                                                           **
 ******************************************************************************/

package intothedwarfness.Classes;
public class Tile {

    private final int SRCX1, SRCY1, SRCX2, SRCY2;
    private boolean blocked;

    /* *********************** Class Constructor **************************** */
    public Tile(int srcX1, int srcY1, int srcX2, int srcY2, int id) {
        //Constants
        this.SRCX1 = srcX1;
        this.SRCY1 = srcY1;
        this.SRCX2 = srcX2;
        this.SRCY2 = srcY2;
        this.blocked = false;
    }

    /* ************************* Class Methods ****************************** */
    
    /**
     * @return if Tile is blocked 
     */
    public boolean isBlocked() {
        return blocked;
    }

    /**
     * Set if Tile is blocked
     * @param blocked 
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    /**
     * @return the SRCX1
     */
    public int getSrcX1() {
        return SRCX1;
    }

    /**
     * @return the SRCY1
     */
    public int getSrcY1() {
        return SRCY1;
    }

    /**
     * @return the SRCX2
     */
    public int getSrcX2() {
        return SRCX2;
    }

    /**
     * @return the SRCY2
     */
    public int getSrcY2() {
        return SRCY2;
    }

    /**
     * @return a string that contains the attributes of the Tile 
     */
    @Override
    public String toString() {
        return "" + this.SRCX1 + " " + this.SRCY1 + " " + this.SRCX2 + " " + this.SRCY2;
    }
}
