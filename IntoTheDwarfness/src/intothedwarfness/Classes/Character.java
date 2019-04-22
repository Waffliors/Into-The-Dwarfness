/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Abstract Class Character, responsible for serving as the basis for the     *
 * creation of the player, the enemies and the bosses of the phases            *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package intothedwarfness.Classes;
public abstract class Character {

    public abstract void update();
    public abstract void collision();
    
}
