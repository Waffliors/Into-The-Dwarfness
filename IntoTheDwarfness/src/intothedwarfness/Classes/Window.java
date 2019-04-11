
package intothed;
import javax.swing.JFrame;

public class Window  extends JFrame{
    
    //Construtor
    public Window(int width, int height, String title) {
        super (title);
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
