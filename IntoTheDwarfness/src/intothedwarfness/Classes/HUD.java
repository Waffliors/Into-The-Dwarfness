/*******************************************************************************
 **      HUD Class                                                            **
 **                                                                           **
 ** Create the GAME HUD, for it, get the player's information                 **
 ******************************************************************************/
package intothedwarfness.Classes;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.awt.image.BufferedImage;
import java.awt.FontFormatException;
import intothedwarfness.Interfaces.Drawable;
import intothedwarfness.Classes.characters.Player;

public class HUD implements Drawable {

    private final Player PLAYER;
    private final ArrayList<BufferedImage> HEALTH_BAR;
    private Font font;
    private String fName;
    private InputStream is;

    /* *********************** Class Constructor **************************** */
    public HUD(ArrayList<BufferedImage> healthBar, Player player) 
            throws FontFormatException, IOException {
        //Load the files used in HUD
        this.PLAYER = player;
        this.HEALTH_BAR = healthBar;
        this.fName = "/Resources/RetroGaming.ttf";
        this.is = HUD.class.getResourceAsStream(fName);
        this.font = Font.createFont(Font.TRUETYPE_FONT, this.is);
    }

    /* ************************* Class Methods ****************************** */
    
    /**
     * Paint the HUD in a graphic context
     * @param g : the graphic context
     */
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(this.HEALTH_BAR.get(PLAYER.getLife()), 
                    27, 50, 27 + 125, 50 + 32, 0, 0, 125, 32, null);
        g.drawImage(this.HEALTH_BAR.get(7), 
                    700, 50, 700 + 50, 50 + 50, 0, 0, 400, 400, null);
        g.drawImage(this.HEALTH_BAR.get(8),
                    850, 50, 850 + 50, 50 + 50, 0, 0, 400, 400, null);
        g.setColor(new Color(209, 209, 209));
        g.setFont(font.deriveFont(40.f));
        g.drawString("" + PLAYER.getEnemiesKilledCount(), 760, 85);
        g.drawString("" + PLAYER.getBossKilledCount(), 913, 85);
        
        //If the player killed a boss, show the key
        if(PLAYER.getBossKilledCount()>=1){
            BufferedImage image = this.HEALTH_BAR.get(9).getSubimage(0,0,64,89);
            g.drawImage(image, 30, 100, 32, 45, null);
        }
        
        if(PLAYER.getLife() == 0) {
            BufferedImage deathBackground = this.HEALTH_BAR.get(13);
            g.drawImage(deathBackground, 0, 0, 1024, 768, null);
            
            BufferedImage deathImage = this.HEALTH_BAR.get(12);
            g.drawImage(deathImage, 0, 0, 1024, 768, null);
        }
    }
    
    /**
     * Paint the pause image
     * 
     * @param g : the grapchic context
     */
    public void paintPauseMenu(Graphics g) {
        g.drawImage(this.HEALTH_BAR.get(11), 0, 0, 1024, 768, null);
        g.drawImage(this.HEALTH_BAR.get(10), 0, 0, 1024, 768, null);        
    }

    /**
     * @return the array list of life 
     */
    public ArrayList<BufferedImage> getHealthBar() {
        return HEALTH_BAR;
    }
    
    @Override
    public Boolean isStage(Map map) {
        return null;
    }
}
