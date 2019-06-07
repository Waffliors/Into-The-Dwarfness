package intothedwarfness.Classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import intothedwarfness.Classes.characters.Player;
import intothedwarfness.Interfaces.Drawable;
import java.net.MalformedURLException;

public class HUD implements Drawable {

    private final Player player;
    private final ArrayList<BufferedImage> hud;
    private String fName;
    private InputStream is;
    private Font font;

    //Loading the HUD images
    public HUD(ArrayList<BufferedImage> HUD, Player player) throws MalformedURLException {
        this.player = player;
        this.hud = HUD;        
        this.fName = "/Resources/RetroGaming.ttf";
        this.is = HUD.class.getResourceAsStream(fName);

        try {
            this.font = Font.createFont(Font.TRUETYPE_FONT, this.is);

        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(this.hud.get(player.getLife()), 27, 50, 27 + 125, 50 + 32, 0, 0, 125, 32, null);
        g.drawImage(this.hud.get(7), 700, 50, 700 + 50, 50 + 50, 0, 0, 400, 400, null);
        g.drawImage(this.hud.get(8), 850, 50, 850 + 50, 50 + 50, 0, 0, 400, 400, null);

        g.setColor(new Color(209, 209, 209));
        g.setFont(font.deriveFont(40.f));
        g.drawString("" + player.getEnemiesKilledCount(), 760, 85);
        g.drawString("" + player.getBossKilledCount(), 913, 85);
        
        if(player.getBossKilledCount()>=1){
            BufferedImage image = this.hud.get(9).getSubimage(0,0,64, 64);
            g.drawImage(image, 30, 100, 32, 32, null);
        }
    }
    
    public void paintPauseMenu(Graphics g) {
        
        g.drawImage(this.hud.get(11), 0, 0, 1024, 768, null);
        g.drawImage(this.hud.get(10), 0, 0, 1024, 768, null);        
    }

    @Override
    public Boolean isStage(Map map) {
        return null;
    }

}
