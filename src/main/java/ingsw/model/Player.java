package ingsw.model;

import ingsw.model.cards.privateoc.PrivateObjectiveCard;
import java.util.Set;

public class Player {
    //TODO decide which collection to use for cards sets and add the others methods and the favor tokens(int or class)

    private User user;
    private Set<PrivateObjectiveCard> privateObjectiveCards;

    //-----METHODS----

    //Get the User instance of this player
    public User getUser(){ return user}

    //Get a Private Card of this player
    //public PrivateObjectiveCard getPrivateObjectiveCard(){ return }

}
