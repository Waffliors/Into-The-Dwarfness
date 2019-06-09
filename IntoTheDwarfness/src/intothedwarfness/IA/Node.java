/*******************************************************************************
 **      Node Class                                                           **
 **                                                                           **
 ** Where the nodes used for the AI ​​will be initialized and their attributes  **
 ** will be set                                                               **                                                                     **
 ******************************************************************************/
package intothedwarfness.IA;

import java.util.List;
import java.util.ArrayList;
import intothedwarfness.Classes.Point;
import intothedwarfness.Interfaces.Collidable;

public class Node implements Collidable{
    //Constants
    private final int X, Y, ID;
    private final Point LT,RT,LD, RD; 
    //AI variables
    private float f, h, g;
    //Node settings
    private Node father;
    private boolean blocked, visited;
    private ArrayList<Node> neighbors = new ArrayList();

    /* *********************** Class Constructor **************************** */
    public Node(int x, int y, int xM, int yM, int id){
        //Create the pivots of the Node
        this.LT = new Point(x,y);
        this.RT = new Point(x+64,y);
        this.LD = new Point(x,y+64);
        this.RD = new Point(x+64,y+64);
        //Set the Constants
        this.X = xM;
        this.Y = yM;
        this.ID = id;
        //Set the Nodes config
        this.blocked = false;
        this.father = null;
    }
    
    /* **************************Class Methods******************************* */
    
    /**
     * @return the Node x position
     */
    public int getX() {
        return X;
    }
    
    /**
     * @return the Node y position
     */
    public int getY() {
        return Y;
    }

    /**
     * @return if the Node is blocked
     */
    public boolean isBlocked() {
        return blocked;
    }
    
    /**
     * @param blocked set if the Node is blocked or not
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
    
    /**
     * @return if the node is Visited 
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * @param visited set if the node is visited 
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
    /**
     * @return the Node neighbors list 
     */
    public List<Node> getNeighbors() {
        return neighbors;
    }
    
    /**
     * @return the Node father
     */
    public Node getFather() {
        return this.father;
    }

    /**
     * @param father set the Node father 
     */
    public void setFather(Node father) {
        this.father = father;
    }

    /**
     * @return the Node ID 
     */
    public int getId() {
        return ID;
    }

    /**
     * @return the Node Heuristic 
     */
    public float getH() {
        return h;
    }

    /**
     * @param h set the Node Heuristic
     */
    public void setH(float h) {
        this.h = h;
    }

    /**
     * @return the Node cost 
     */
    public float getG() {
        return g;
    }
    
    /**
     * @param g set the Node cost
     */
    public void setG(float g) {
        this.g = g;
    }

    /**
     * @return the Node fitness 
     */
    public float getF() {
        return f;
    }

    /**
     * @param f set the Node fitness 
     */
    public void setF(float f) {
        this.f = f;
    }

    /**
     * Show the neighbors of the Node
     */
    public void showNeigh() {
        System.out.println("Vizinhos do nó " + this.X + " " + this.Y + ": " + this.neighbors);
    }

    /**
     * Add a Node to this neighbors list
     *
     * @param node
     */
    public void addNeighbor(Node node) {
        this.neighbors.add(node);
    }

    /**
     * Print the Node
     * @return a string with the Node informations
     */
    @Override
    public String toString(){
        return "(x: "+X+", y: "+Y+") = " +this.isBlocked();

    }

    /**
     * @return the Object type 
     */
    @Override
    public String getType() {
        return "NodeType";
    }
    
    /**
     * @return the LeftTop pivot
     */
    @Override
    public Point getPivotLT() {
        return this.LT;
    }

    /**
     * @return the RightTop pivot
     */
    @Override
    public Point getPivotRT() {
        return this.RT;
    }

    /**
     * @return the LeftDown pivot
     */
    @Override
    public Point getPivotLD() {
        return this.LD;
    }

    /**
     * @return the RightDown pivot
     */
    @Override
    public Point getPivotRD() {
        return this.RD;
    }

    @Override
    public void gotHit() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
