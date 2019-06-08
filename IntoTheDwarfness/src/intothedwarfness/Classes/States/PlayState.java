/*******************************************************************************
 **      PlayState Class                                                      **
 **                                                                           **
 ** The state for when the game is running                                    **
 ******************************************************************************/
package intothedwarfness.Classes.States;

public class PlayState extends GameState {

    /* **************************Class Methods******************************* */
    
    /**
     * Initialize the state
     */
    @Override
    public void init() {
        System.out.println("Initialize play");
    }

    /**
     * @return the game state type
     */
    @Override
    public String getType() {
        return "PlayState";
    }
}
