//Classe Window: Responsável por criar a janela de jogo e capturar os eventos
package intothedwarfness.Classes;
import intothedwarfness.Classes.States.GameStateManager;
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window  extends JFrame  implements  KeyListener
{
    private static GameStateManager gsm;
    public Window(String title)
    {
        //Atribuindo características para a exibição da tela
        super (title);
        setFocusable(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.addKeyListener(this); 
    }
    
    //Inicializa os estados do jogo
    public void init() 
    {
        gsm = new GameStateManager();
        gsm.init();
    }

    //GameLoop
    public void run() 
    {
        boolean done = true;
        while (!done) {
            try {
                tick();
                repaint();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Clocks
    public void tick() 
    {
        gsm.tick();
    }
    
    //Inicializa a tela no seu estado puro
    @Override
    public void paint(Graphics g)
    {        
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, super.getContentPane().getSize().width, 
                         super.getContentPane().getSize().height);
        
        gsm.render(g);
    }

    
    /*
     * Métodos sobrescritos da KeyListener:
     *
     * KeyTyped: Escuta a tecla que foi uma vez pressionada
     * KeyPressed: Escuta a tecla que está sendo constantemente pressionada
     * KeyReleased: Escuta a tecla que foi solta
     *
     */
    
    @Override
    public void keyTyped(KeyEvent e) 
    {
        System.out.println("Key "+e.getKeyChar()+" typed!");
    }
    @Override
    public void keyPressed(KeyEvent e) 
    {
        System.out.println("Key"+e.getKeyChar()+" pressed!");
    }
    @Override
    public void keyReleased(KeyEvent e) 
    {
        System.out.println("Key "+e.getKeyChar()+" released!");
    }
}
