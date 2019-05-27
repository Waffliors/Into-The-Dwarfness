package intothedwarfness.IA;

import java.util.List;
import java.util.ArrayList;
import intothedwarfness.Classes.Point;
import intothedwarfness.Interfaces.Collidable;

public class Node implements Collidable{
    
    private Point LT,RT,LD, RD; 

    private int ID;
    private boolean blocked, visited, isTransition;
    private Node father;
    private List<Node> neighbors = new ArrayList();

    //IA VARIABLES
    private float h, g, f;
    
    public Node(int x, int y){
        //Initialize the pivot LT = (0, 0)
        this.LT = new Point(x,y);
        //Initialize the pivot RT = (64, 0)
        this.RT = new Point(x+64,y);
        //Initialize the pivot LD = (0, 64)
        this.LD = new Point(x,y+64);
        //Initialize the pivot RD = (64, 64)
        this.RD = new Point(x+64,y+64);
        this.isTransition = false;
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

    public void setH(float h) {
        this.h = h;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }
    
    public void setTransition (boolean transition){
        this.isTransition = transition;
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
    
    @Override
    public String toString(){
        return "Pivot LT: "+LT+
               "\nPivot RT: "+RT+
                "\nPivot LD: "+LD+
                "\nPivot RD: "+RD+
                "\nEstá bloqueado? "+this.isBlocked()+"\n\n";

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
