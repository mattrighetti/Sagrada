package ingsw.model;

import ingsw.controller.network.commands.Notification;
import ingsw.controller.network.commands.Response;
import ingsw.controller.network.socket.UserObserver;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.model.cards.privateoc.PrivateObjectiveCard;
import ingsw.utilities.NotificationType;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Player implements Serializable {
    private int score;
    private User user;
    private int favorTokens;
    private PatternCard patternCard;
    private PrivateObjectiveCard privateObjectiveCard;

    public Player(User user) {
        this.user = user;
    }

    void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setPatternCard(PatternCard patternCard) {
        this.patternCard = patternCard;
        this.favorTokens = patternCard.getDifficulty();
    }

    public PatternCard getPatternCard() {
        return patternCard;
    }

    void setPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        this.privateObjectiveCard = privateObjectiveCard;
    }

    public PrivateObjectiveCard getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    //Get the User instance of this player

    public User getUser() {
        return user;
    }


    public String getPlayerUsername() {
        return getUser().getUsername();
    }

    public UserObserver getUserObserver() throws RemoteException {
        return user.getUserObserver();
    }

    public void sendResponse(Response response) {
        try {
            user.getUserObserver().sendResponse(response);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void notifyDraft() {
        try {
            getUserObserver().receiveNotification(new Notification(NotificationType.DRAFT_DICE));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void updateUser(User user) {
        this.user = user;
    }

    public int getFavourTokens() {
        return favorTokens;
    }

    public void decreaseFavorTokens(int favorTokens) {
        this.favorTokens = this.favorTokens - favorTokens;
    }

}
