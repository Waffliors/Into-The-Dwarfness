/******************************************************************************
 ** Class tile, used for map creation, collision detection and used as a node** 
 ** in artificial intelligence                                               **
 ******************************************************************************/
package intothedwarfness.Classes;

import intothedwarfness.IA.Node;
import java.util.ArrayList;
import java.util.List;

public class Tile {
/* ***************************Class Variables******************************** */
    //For map use
    private final int srcX1, srcY1, srcX2, srcY2;
    
    //For IA use
    //F(n) = G(n) + H(n)
    private float F, G, H;
    private final int id;
    private boolean blocked, visited;
    private Tile father;
    public  List<Tile> neighbors;
    
/* **************************Class Constructor******************************* */
    public Tile(int srcX1, int srcY1, int srcX2, int srcY2, int id) {
        //For map use
        this.srcX1 = srcX1;
        this.srcY1 = srcY1;
        this.srcX2 = srcX2;
        this.srcY2 = srcY2;
        
        //For IA use
        this.id = id;
        this.blocked = false;
        this.visited = false;
        this.neighbors = new ArrayList();
    }
    
/* ****************************Class Methods********************************* */
    
    //For map creation
    public int getSrcX1() {
        return srcX1;
    }
    public int getSrcY1() {
        return srcY1;
    }
    public int getSrcX2() {
        return srcX2;
    }
    public int getSrcY2() {
        return srcY2;
    }
    
    //For IA
    //Getters
    public boolean isBlocked() {
        return blocked;
    }
    public boolean isVisited(){
        return visited;
    }
    public int getId() {
        return id;
    }
    public Tile getFather(){
        return this.father;
    }
    public float getF(){
        return this.F;
    }
    public float getG(){
        return this.G;
    }
    public float getH(){
        return this.H;
    }
    //Setters
    
    public void setBlocked(boolean blocked){
        this.blocked = blocked;
    }
    public void setVisited(boolean visited){
        this.visited = visited;
    }
    public void setFather(Tile father){
        this.father = father;
    }
    public void setF(float F){
        this.F = F;
    }
    public void setG(float G){
        this.G = G;
    }
    public void setH(float H){
        this.H = H;
    }
    
/* *************************Overridden Methods******************************* */
    @Override
    public String toString() {
        return "" + this.srcX1 + " " + this.srcY1 + " " + this.srcX2 + " " + this.srcY2;
    }
}
