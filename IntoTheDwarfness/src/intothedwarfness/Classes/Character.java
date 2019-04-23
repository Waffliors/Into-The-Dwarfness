/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Abstract Class Character, responsible for serving as the basis for the     *
 * creation of the player, the enemies and the bosses of the phases            *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package intothedwarfness.Classes;
import intothedwarfness.IntoTheDwarfness;

public abstract class Character extends IntoTheDwarfness {
    public abstract void update();
    public abstract void collision(); 
}
