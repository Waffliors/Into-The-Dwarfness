package intothedwarfness.Classes;

public class ScreenPiece {
    private int x1, x2, y1, y2, ID;
    private boolean blocked;
    
    public ScreenPiece(int xStart, int xEnd, int yStart, int yEnd, int ID){
        this.blocked = false;
        
        this.x1 = xStart;
        this.x2 = xEnd;
        this.y1 = yStart;
        this.y2 = yEnd;
        
        this.ID = ID;
        
    }

    public int getID() {
        return ID;
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    public boolean isBlocked() {
        return blocked;
    }
    
    @Override
    public String toString(){
       String resp = "[x1: "+getX1()+"][x2: "+getX2()+"][y1: "+getY1()+"][y2: "+getY2()+"]";
       
       return resp;
        
    }
}
