package ingsw.controller.network;

import ingsw.model.Dice;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.Tuple;
import ingsw.view.SceneUpdater;

/**
 * Interface used by the View classes to call methods on network level(on ClientController and RMIController).
 */
public interface NetworkType {

    /**
     * Set the sceneUpdater to call in the network level.
     *
     * @param sceneUpdater FXML Controller
     */
    void setSceneUpdater(SceneUpdater sceneUpdater);

    /**
     * Send a login request with a specified username
     *
     * @param username Player username
     */
    void loginUser(String username);

    /**
     * Send a create match request.
     *
     * @param matchName Match name
     */
    void createMatch(String matchName);

    /**
     * Send a request to join a specified match(so it request to join a controller).
     *
     * @param matchName Controller name that the View wants to join
     */
    void joinExistingMatch(String matchName);

    /**
     * Send a request for drafting the dice. This is called by the first player of every round
     */
    void draftDice();

    /**
     * Send an ack to the Controller
     */
    void sendAck();

    /**
     * Send a place dice request
     *
     * @param dice Dice a player want to place
     * @param columnIndex Column index
     * @param rowIndex Row index
     */
    void placeDice(Dice dice, int columnIndex, int rowIndex);

    /**
     * Send a use tool card request
     *
     * @param string Tool card name
     */
    void useToolCard(String string);



    /*
      -------------------TOOLCARD METHODS-------------------
      All these methods will be incapsulated into the same
      toolcard request but with different parameter set
      every time.
      -----------------------------------------------------*/



    /**
     * Grozing Pliers request
     *
     * @param dice Selected dice
     * @param increase Increase/decrease value
     */
    void grozingPliersMove(Dice dice, boolean increase);

    /**
     * Flux Brush request
     *
     * @param dice Selected dice
     */
    void fluxBrushMove (Dice dice);

    /**
     * Flux Brush request
     *
     * @param selectedDice Selected dice
     * @param rowIndex Row index
     * @param columnIndex Column index
     */
    void fluxBrushMove(Dice selectedDice, int rowIndex, int columnIndex);

    /**
     * Flux Brush request
     */
    void fluxBrushMove();

    /**
     * Flux Remover request
     *
     * @param dice Selected dice
     */
    void fluxRemoverMove(Dice dice);

    /**
     * Flux Remover request
     *
     * @param dice Selected dice
     * @param chosenValue new face up value
     */
    void fluxRemoverMove(Dice dice, int chosenValue);

    /**
     * Flux Remover request
     */
    void fluxRemoverMove();

    /**
     * Flux Remover request
     *
     * @param selectedDice Selected dice
     * @param rowIndex Row index
     * @param columnIndex Column index
     */
    void fluxRemoverMove(Dice selectedDice, int rowIndex, int columnIndex);

    /**
     * Copper Foil Burnisher request
     *
     * @param dicePosition Initial dice position
     * @param position Final dice position
     */
    void copperFoilBurnisherMove(Tuple dicePosition, Tuple position);

    /**
     * Cork Backed Straightedge request
     *
     * @param selectedDice Selected dice
     * @param row Row index
     * @param column Column index
     */
    void corkBackedStraightedgeMove(Dice selectedDice, int row, int column);

    /**
     * Lathekin request
     *
     * @param dicePosition Initial dice position
     * @param position Final dice position or second dice position in case of double move
     * @param doubleMove DoubleMove
     */
    void lathekinMove(Tuple dicePosition, Tuple position, boolean doubleMove);

    /**
     * Choose pattern card request
     *
     * @param patternCard Chosen pattern card
     */
    void choosePatternCard(PatternCard patternCard);

    /**
     * End turn request
     *
     * @param player Current player
     */
    void endTurn(String player);

    /**
     * Grinding Stone request
     *
     * @param dice Selected dice
     */
    void grindingStoneMove(Dice dice);

    /**
     * Lens Cutter request
     *
     * @param roundIndex Round index
     * @param roundTrackDice Roundtrack dice
     * @param poolDice PoolDice
     */
    void lensCutter(int roundIndex, String roundTrackDice, String poolDice);

    /**
     * Running Pliers request
     *
     * @param selectedDice Selected Dice to place
     * @param rowIndex Row index
     * @param columnIndex Column index
     */
    void runningPliersMove(Dice selectedDice, int rowIndex, int columnIndex);

    /**
     * Tap Wheel request
     *
     * @param dice Dice from the round track
     * @param phase Phase of Tap Wheel
     */
    void tapWheelMove(Dice dice, int phase);

    /**
     * Tap Wheel request
     *
     * @param dicePosition Dice initial position
     * @param position Dice final position or second dice position(in case of double move)
     * @param phase Phase of Tap Wheel
     * @param doubleMove DoubleMove
     */
    void tapWheelMove(Tuple dicePosition, Tuple position, int phase, boolean doubleMove);

    /**
     * End Tap wheel toolcard request
     *
     * @param endTapWheel End phase Tap Wheel
     */
    void tapWheelMove(int endTapWheel);

    /**
     * Request for a match history
     *
     * @param matchName Name of the match
     */
    void requestHistory(String matchName);

    /**
     * Request data for the lobby
     */
    void requestBundleData();

    /**
     * Request for matches already ended
     */
    void requestFinishedMatches();
}
