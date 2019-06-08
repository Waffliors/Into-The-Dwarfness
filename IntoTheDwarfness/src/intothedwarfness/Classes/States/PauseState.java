/*******************************************************************************
 **      PauseState Class                                                     **
 **                                                                           **
 ** The state for when the game is paused                                     **
 ******************************************************************************/
package intothedwarfness.Classes.States;
public class PauseState extends GameState {

    /* **************************Class Methods******************************* */
    
    /**
     * Initialize the state
     */
    @Override
    public void init() {System.out.println("Initialize pause");}

    /**
     * @return the game state type
     */
    @Override
    public String getType() {
        return "PauseState";
    }
}
