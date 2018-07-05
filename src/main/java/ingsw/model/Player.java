package ingsw.model;

import ingsw.controller.network.commands.Notification;
import ingsw.controller.network.commands.Response;
import ingsw.controller.network.socket.UserObserver;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.model.cards.privateoc.PrivateObjectiveCard;
import ingsw.utilities.NotificationType;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Class that represents the user during a match. It contains also all the game data like
 * the score, the favorTokens, the patterncard and the private objective card.
 */
public class Player implements Serializable {
    private int score;
    private User user;
    private int favorTokens;
    private PatternCard patternCard;
    private PrivateObjectiveCard privateObjectiveCard;

    /**
     * Set the user linked to the player
     * @param user User to link
     */
    public Player(User user) {
        this.user = user;
    }

    /**
     * Updates the score
     * @param score New score
     */
    void setScore(int score) {
        this.score = score;
    }

    /**
     * Get the score
     * @return Actual score
     */
    public int getScore() {
        return score;
    }

    /**
     * Set the pattern card.
     * @param patternCard Pattern card to set
     */
    public void setPatternCard(PatternCard patternCard) {
        this.patternCard = patternCard;
        this.favorTokens = patternCard.getDifficulty();
    }

    /**
     * Returns the pattern card
     * @return Actual pattern card
     */
    public PatternCard getPatternCard() {
        return patternCard;
    }

    /**
     * Set the private objective card
     * @param privateObjectiveCard Private objective card to set
     */
    void setPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        this.privateObjectiveCard = privateObjectiveCard;
    }

    /**
     * Returns the private objective card
     * @return Actual private objective card
     */
    public PrivateObjectiveCard getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    //Get the User instance of this player

    /**
     * Returns the user
     * @return Current user instance
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the player username
     * @return player username
     */
    public String getPlayerUsername() {
        return getUser().getUsername();
    }

    /**
     * Returns the UserObserver
     * @return Actual instance of UserObserver
     * @throws RemoteException
     */
    public UserObserver getUserObserver() throws RemoteException {
        return user.getUserObserver();
    }

    /**
     * Send a Response to the player. It calls the interface that is implemented by:
     * - RMIUserObserver -> RMI
     * - ClientHandler -> Socket
     * @param response
     */
    public void sendResponse(Response response) {
        try {
            user.getUserObserver().sendResponse(response);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends to the player the draft notification
     */
    void notifyDraft() {
        try {
            getUserObserver().receiveNotification(new Notification(NotificationType.DRAFT_DICE));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the number of favor tokens
     * @return Actual number of favor tokens
     */
    public int getFavourTokens() {
        return favorTokens;
    }

    /**
     * Decrease the number of favor tokens using the int passed as argument.
     * @param favorTokens Tokens to subtract
     */
    public void decreaseFavorTokens(int favorTokens) {
        this.favorTokens = this.favorTokens - favorTokens;
    }

}
