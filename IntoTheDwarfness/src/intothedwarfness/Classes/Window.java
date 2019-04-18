package intothedwarfness.Classes;

import intothedwarfness.Classes.States.GameStateManager;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import java.awt.Graphics;

public class Window  extends JFrame
{
    private final int width;
    private final int height;
    private static GameStateManager gsm;
    
    //Construtor
    public Window(int width, int height, String title) 
    {
        super (title);
        this.width = width;
        this.height = height;
        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    
        
    public void init() {
        gsm = new GameStateManager();
        gsm.init();
    }

    public void run() {
        boolean done = true;

        while (!done) {
            try {
                tick();
                this. repaint();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void tick() {
        gsm.tick();
    }
    
    @Override
    public void paint(Graphics g)
    {        
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, super.getContentPane().getSize().width, super.getContentPane().getSize().height);
        
        gsm.render(g);
    }
}
