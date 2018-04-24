package ingsw.model;

import ingsw.model.cards.patterncard.PatternCard;
import ingsw.model.cards.privateoc.PrivateObjectiveCard;
import ingsw.view.RemoteView;

import java.util.Set;

public class Player {
    private final RemoteView remoteView;
    private User user;
    private PrivateObjectiveCard privateObjectiveCard;
    private WindowFrame windowFrame;

    //TODO decide which collection to use for cards sets and add the others methods and the favor tokens(int or class)

    public Player(User user, RemoteView remoteView) {
        this.user = user;
        this.remoteView = remoteView;
    }

    public PrivateObjectiveCard getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    public void setPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        this.privateObjectiveCard = privateObjectiveCard;
    }

    //-----METHODS----

    //Get the User instance of this player
    public User getUser() {
        return user;
    }

    public void showAvailablePatterCard(Set<PatternCard> patternCards) {
        remoteView.displayPatternCardsToChoose(patternCards);
    }

    //Get a Private Card of this player
}
