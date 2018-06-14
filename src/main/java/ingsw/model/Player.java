package ingsw.model;

import ingsw.controller.network.commands.Notification;
import ingsw.controller.network.socket.UserObserver;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.model.cards.privateoc.PrivateObjectiveCard;
import ingsw.utilities.NotificationType;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Player implements Serializable {
    private User user;
    private PrivateObjectiveCard privateObjectiveCard;
    private PatternCard patternCard;
    private int favorTokens;

    public Player(User user) {
        this.user = user;
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

    public UserObserver getUserObserver() {
        return user.getUserObserver();
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

    //Get a Private Card of this player
}
