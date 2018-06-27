package ingsw.controller;

import ingsw.controller.network.commands.*;
import ingsw.model.*;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.ControllerTimer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Controller extends UnicastRemoteObject implements RemoteController {
    private String matchName;
    private boolean hasStarted;
    private SagradaGame sagradaGame;
    private GameManager gameManager;
    private List<Player> playerList;
    private int maxTurnSeconds;
    private int maxJoinMatchSeconds;

    public Controller(String matchName, int maxTurnSeconds,
                      int maxJoinMatchSeconds, SagradaGame sagradaGame) throws RemoteException {
        super();
        hasStarted = false;
        this.sagradaGame = sagradaGame;
        this.matchName = matchName;
        playerList = new ArrayList<>();
        this.maxJoinMatchSeconds = maxJoinMatchSeconds;
        this.maxTurnSeconds = maxTurnSeconds;
    }

    public String getMatchName() {
        return matchName;
    }

    public int getConnectedUsers() {
        return playerList.size();
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Wait for new Users who connect and want to enter the match and build the list of the match players
     * When all the players are connected, start the match.
     *
     * @param user the user wants to join the match
     */
    public void loginUser(User user) throws RemoteException {
        if (!hasStarted) {
            playerList.add(new Player(user));
            if (playerList.size() == 2) {
                ControllerTimer.get().startLoginTimer(maxJoinMatchSeconds, this, hasStarted);
            }

            if (playerList.size() == 4) {
                ControllerTimer.get().cancelTimer();
                hasStarted = true;
                createMatch();
            }
        } else throw new RemoteException("Match has already started");
    }

    /**
     * Set the match: create a new instance of gameManager (who will handle the match)
     * Start the first phase of the match, the PatternCards choice
     */
    public void createMatch() {
        gameManager = new GameManager(playerList, maxTurnSeconds, this);
        gameManager.waitForEveryPatternCard(gameManager.pickPatternCards());
    }

    @Override
    public void endTurn(String currentPlayer) throws RemoteException {
        gameManager.endTurn(currentPlayer);
    }

    /**
     * Method that deactivates a user whenever he disconnects from the game
     *
     * @param user user that has to be disconnected
     * @throws RemoteException if the user has not been disconnected correctly
     */
    @Override
    public void deactivateUser(User user) throws RemoteException {
        for (Player player : playerList) {
            if (player.getPlayerUsername().equals(user.getUsername())) {
                player.getUser().setActive(false);
                System.err.println("Controller: User " + user.getUsername() + " has been deactivated");
            }
        }
    }

    /**
     * Assigns PatternCard to specified Player
     * Triggered by Command(PatterCard, String)
     *
     * @param username    the player's username who choose the PatternCard
     * @param patternCard the card choosen by the player
     */
    @Override
    public synchronized void assignPatternCard(String username, PatternCard patternCard) throws RemoteException {
        gameManager.setPatternCardForPlayer(username, patternCard);
        for (Player player : playerList) {
            if (player.getPlayerUsername().equals(username) &&
                    player.getPatternCard() != null &&
                    !player.getPatternCard().equals(patternCard))
                throw new RemoteException("Pattern card not assigned correctly");
        }
    }


    /**
     * After the first player of the round chooses "Draft Dice" on the View
     * this method is triggered to draft the dice calling the gameManager method
     */
    @Override
    public void draftDice(String username) throws RemoteException {
        gameManager.draftDiceFromBoard();
    }

    /**
     * Ack received from a player, it is used to control if every player has received data
     */
    @Override
    public void sendAck() throws RemoteException {
        gameManager.receiveAck();
    }

    /**
     *
     * @param dice selected to place from the player
     * @param rowIndex the row index where to place the die in the pattern card
     * @param columnIndex the column index where to place the die in the pattern card
     * @throws RemoteException may occur during the execution of a remote method call
     */
    @Override
    public void placeDice(Dice dice, int rowIndex, int columnIndex) throws RemoteException {
        gameManager.placeDiceForPlayer(dice, rowIndex, columnIndex);
    }

    /**
     * Request to use a tool card
     * @param toolCardName the tool card's name that the player wants to use
     */
    @Override
    public void useToolCard(String toolCardName) throws RemoteException {
        gameManager.useToolCard(toolCardName);
    }

    /**
     * GROZING PLIERS Tool Card move
     * it calls the game Manager tool card move method
     * @param grozingPliersRequest contains the die and a flag to specify the increasing or the decreasing choice
     */
    @Override
    public void toolCardMove(GrozingPliersRequest grozingPliersRequest) throws RemoteException {
        gameManager.grozingPliersMove(grozingPliersRequest.selectedDice, grozingPliersRequest.increase);
    }

    /**
     * FLUX BRUSH Tool Card move
     * it calls the game Manager tool card move methods based on the phase of the move
     * @param fluxBrushRequest contains the index to switch the method based on the phase of the move,
     *                         it can contains dice and the position where place the die
     */
    @Override
    public void toolCardMove(FluxBrushRequest fluxBrushRequest) throws RemoteException {
        switch (fluxBrushRequest.phase) {
            case 1:
                gameManager.fluxBrushMove(fluxBrushRequest.selectedDice);
                break;
            case 2:
                gameManager.fluxBrushMove(fluxBrushRequest.selectedDice, fluxBrushRequest.row, fluxBrushRequest.column);
                break;
            case 3:
                gameManager.fluxBrushMove();
                break;
            default:
                System.out.println("Not specified");
                break;
        }
    }

    /**
     * FLUX REMOVER Tool Card move
     * it calls the game Manager tool card move method based on the phase of the move
     * @param fluxRemoverRequest contains the index to switch the method based on the phase of the move and
     *                           other data needed for tool card move
     */
    @Override
    public void toolCardMove(FluxRemoverRequest fluxRemoverRequest) throws RemoteException {
        switch (fluxRemoverRequest.phase) {
            case 1:
                gameManager.fluxRemoverMove(fluxRemoverRequest.selectedDice);
                break;
            case 2:
                gameManager.fluxRemoverMove(fluxRemoverRequest.selectedDice, fluxRemoverRequest.chosenValue);
                break;
            case 3:
                gameManager.fluxRemoverMove(fluxRemoverRequest.selectedDice, fluxRemoverRequest.rowIndex, fluxRemoverRequest.columnIndex);
                break;
            case 4:
                gameManager.fluxRemoverMove();
                break;
            default:
                System.out.println("Not specified");
                break;
        }
    }

    /**
     * GRINDING STONE Tool Card move
     * it calls the game Manager tool card move method
     * @param grindingStoneRequest contains the selected die
     */
    public void toolCardMove(GrindingStoneRequest grindingStoneRequest) throws RemoteException {
        gameManager.grindingStoneMove(grindingStoneRequest.selectedDice);
    }

    /**
     * COPPER FOIL BURNISHER Tool Card move
     * it calls the game Manager tool card move method
     * @param moveToolCardRequest contains the selected die and the indexes of the position where to place it
     */
    @Override
    public void toolCardMove(CopperFoilBurnisherRequest moveToolCardRequest) throws RemoteException {
        gameManager.copperFoilBurnisherMove(moveToolCardRequest.dicePosition, moveToolCardRequest.position);
    }

    /**
     * CORK BACKED STRAIGHTEDGE Tool Card move
     * it calls the game Manager tool card move method
     * @param moveToolCardRequest contains the selected die and the indexes of the position where to place it
     */
    @Override
    public void toolCardMove(CorkBackedStraightedgeRequest moveToolCardRequest) throws RemoteException {
        gameManager.corkBackedStraightedgeMove(moveToolCardRequest.selectedDice, moveToolCardRequest.row, moveToolCardRequest.column);
    }

    /**
     * LENS CUTTER Tool Card move
     * it calls the game Manager tool card move method
     * @param moveToolCardRequest contains the selected die from the round track, round track index and the selected die from drafted dice
     */
    @Override
    public void toolCardMove(LensCutterRequest moveToolCardRequest) throws RemoteException {
        gameManager.lensCutterMove(moveToolCardRequest.roundIndex, moveToolCardRequest.roundTrackDice, moveToolCardRequest.poolDice);
    }

    /**
     * EGLOMISE BRUSH Tool Card move
     * it calls the game Manager tool card move method
     * @param moveToolCardRequest contains the selected die and the position where to place it
     */
    @Override
    public void toolCardMove(EglomiseBrushRequest moveToolCardRequest) throws RemoteException {
        gameManager.eglomiseBrushMove(moveToolCardRequest.dicePosition, moveToolCardRequest.position);
    }

    /**
     * LAHEKIN Tool Card move
     * it calls the game Manager tool card move method
     * @param lathekinRequest contains the dice position, the new position and the flag for dice switch
     */
    @Override
    public void toolCardMove(LathekinRequest lathekinRequest) throws RemoteException {
        gameManager.lathekinMove(lathekinRequest.dicePosition, lathekinRequest.position, lathekinRequest.doubleMove);
    }

    /**
     * RUNNING PLIERS Tool Card move
     * it calls the game Manager tool card move method
     * @param moveToolCardRequest contains the selected die and the position where to place it
     */
    @Override
    public void toolCardMove(RunningPliersRequest moveToolCardRequest) throws RemoteException {
        gameManager.runningPliersMove(moveToolCardRequest.selectedDice, moveToolCardRequest.rowIndex, moveToolCardRequest.ColumnIndex);
    }

    /**
     * TAP WHEEL tool card move
     * it calls the game Manager tool card move method
     * @param moveToolCardRequest request that contains the die and its positions, the new position and the flag for dice switch
     */
    @Override
    public void toolCardMove(TapWheelRequest moveToolCardRequest) throws RemoteException {
        gameManager.tapWheelMove(moveToolCardRequest.dice, moveToolCardRequest.phase, moveToolCardRequest.dicePosition, moveToolCardRequest.position, moveToolCardRequest.doubleMove);
    }

    /**
     *
     */
    public void removeMatch() {
        sagradaGame.removeMatch(this);
    }
}