/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intothedwarfness.Interfaces;

import intothedwarfness.Classes.Map;
import java.awt.Graphics;

public interface Drawable{
    public void paintComponent(Graphics g);
    public Boolean isStage(Map map);
}
