/*******************************************************************************
 **      Point Class                                                          **
 **                                                                           **
 ** Create the points used in the pivots of all the objetcs inside the game   **
 ******************************************************************************/
package intothedwarfness.Classes;

public class Point {
    private final int X,Y;
   
    /* *********************** Class Constructor **************************** */
    public Point(int x, int y){
        //Constants
        this.X = x;
        this.Y = y;
    }

    /**
     * @return the x position of the point
     */
    public int getX() {
        return this.X;
    }

    /**
     * @return the y position of the point 
     */
    public int getY() {
        return this.Y;
    }

    /**
     * @return a String that represent the point
     */
    @Override
    public String toString(){
        return "["+X+","+Y+"]"; 
    } 
}
