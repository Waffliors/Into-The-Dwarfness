/*******************************************************************************
 **      GameStateManeger Class                                               **
 **                                                                           **
 ** This is the game estate manager class, he's the one responsible for the   ** 
 ** management of the  game states                                            **
 ******************************************************************************/
package intothedwarfness.Classes.States;

public class GameStateManager {

    public GameState currGameState;

    /* *********************** Class Constructor **************************** */
    public GameStateManager() {
        //Set the initial gamestate
        currGameState = new PauseState();
    }

    /* **************************Class Methods******************************* */
    /**
     * Initialize the game state
     */
    public void init() {
        currGameState.init();
    }

    /**
     * @return the type of the state
     */
    public String getType() {
        return currGameState.getType();
    }

    /**
     * Switch the state of the game
     *
     * @param newState : the new state
     */
    public void switchState(GameState newState) {
        currGameState = newState;
        currGameState.init();
    }
}
