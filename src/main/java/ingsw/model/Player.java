package ingsw.model;

import ingsw.controller.network.socket.UserObserver;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.model.cards.privateoc.PrivateObjectiveCard;
import ingsw.view.RemoteView;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;

public class Player {
    private User user;
    private PrivateObjectiveCard privateObjectiveCard;
    private PatternCard patternCard;
    private Color pedina;

    //TODO decide which collection to use for cards sets and add the others methods and the favor tokens(int or class)

    public Player(User user) {
        this.user = user;
    }

    public void setPatternCard(PatternCard patternCard) {
        this.patternCard = patternCard;
    }

    public PatternCard getPatternCard() {
        return patternCard;
    }

    public void setPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        this.privateObjectiveCard = privateObjectiveCard;
    }

    public PrivateObjectiveCard getPrivateObjectiveCard() { return privateObjectiveCard; }

    //Get the User instance of this player

    public User getUser() {
        return user;
    }


    public String getPlayerUsername() { return getUser().getUsername(); }

    public UserObserver getUserObserver(){ return user.getUserObserver(); }

    public void notifyDraft() {
        try {
            getUser().getUserObserver().receiveDraftNotification();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //Get a Private Card of this player
}
