package intothedwarfness.IA;

import java.util.List;
import java.util.ArrayList;
import intothedwarfness.Classes.Point;
import intothedwarfness.Interfaces.Collidable;

public class Node implements Collidable{
    
    private int MOVEMENT_COST;
    private int x, y;
    private Point LT,RT,LD, RD; 
    private int ID;
    private boolean blocked, visited;
    private Node father;
    private ArrayList<Node> neighbors = new ArrayList();

    //IA VARIABLES
    private int h, g, f;
    
    public Node(int x, int y, int xM, int yM){
        //Initialize the pivot LT = (0, 0)
        this.LT = new Point(x,y);
        //Initialize the pivot RT = (64, 0)
        this.RT = new Point(x+64,y);
        //Initialize the pivot LD = (0, 64)
        this.LD = new Point(x,y+64);
        //Initialize the pivot RD = (64, 64)
        this.RD = new Point(x+64,y+64);
        this.x = xM;
        this.y = yM;
        this.MOVEMENT_COST = 10;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    
    public boolean isBlocked() {
        return blocked;
    }
    
    public void setBloqueado(boolean blocked) {
        this.blocked = blocked;
    }
    
    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
    public List<Node> getNeighbors() {
        return neighbors;
    }
    
    public Node getFather() {
        return father;
    }

    public void setFather(Node father) {
        this.father = father;
    }

    public int getId() {
        return ID;
    }

    public float getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public float getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public float getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }
    
    /**
     * Sets the G score based on the parent's G score and the movement cost.
     *
     * @param parent The node prior to this one.
     */
    public void setG(Node parent) {
        g = (int) (parent.getG() + MOVEMENT_COST);
    }

    /**
     * Calculates and return the G score, without changing it on the class.
     *
     * @param parent The node prior to this one.
     * @return This node's G score.
     */
    public int calculateG(Node parent) {
        return (int) (parent.getG() + MOVEMENT_COST);
    }

    /**
     * Sets the H score based on the goal's position.
     *
     * @param goal The final node on the path.
     */
    public void setH(Node goal) {
        h = (Math.abs(getX() - goal.getX()) + Math.abs(getY() - goal.getY())) * MOVEMENT_COST;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //Getters of the pivots
    public Point getLT() {
        return LT;
    }

    public Point getRT() {
        return RT;
    }

    public Point getLD() {
        return LD;
    }

    public Point getRD() {
        return RD;
    }
    
    public void showNeigh(){
        System.out.println("Vizinhos do nó "+this.x+" "+ this.y+ ": "+this.neighbors);
    }
    
    public void addNeighbor(Node no){
        this.neighbors.add(no);
        
    }
    
    @Override
    public String toString(){
        return "(x: "+x+", y: "+y+") = " +this.isBlocked();

        //return "Pivot LT: "+LT+
        //       "\nPivot RT: "+RT+
        //        "\nPivot LD: "+LD+
        //        "\nPivot RD: "+RD+
        //        "\nEstá bloqueado? "+this.isBlocked()+"\n\n";

    }

    @Override
    public Point getPivotLT() {
        return LT;
    }

    @Override
    public Point getPivotRT() {
         return RT;
    }

    @Override
    public Point getPivotLD() {
        return LD;
    }

    @Override
    public Point getPivotRD() {
        return RD;
    }


}
