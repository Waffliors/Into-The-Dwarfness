package intothedwarfness.Interfaces;
import intothedwarfness.Classes.Point;


public interface Collidable {
    public abstract void gotHit();
    
    public String getType();

    public Point getPivotLT();

    public Point getPivotRT();

    public Point getPivotLD();

    public Point getPivotRD();
    
}
