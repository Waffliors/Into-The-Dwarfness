

package intothed;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import java.awt.Graphics;

public class Window  extends JFrame{
    private final int width;
    private final int height;
    
    //Construtor
    public Window(int width, int height, String title) {
        super (title);
        this.width = width;
        this.height = height;
        
        this.setSize(width, height);
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        System.out.println(this.getContentPane().getAlignmentX());
        
    }
    
    @Override
    public void paint(Graphics g){
        
        g.setColor(Color.black);
        g.fillRect(0,0, this.width, this.height);

    }
}
