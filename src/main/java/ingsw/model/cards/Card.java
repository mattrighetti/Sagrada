package ingsw.model.cards;

import java.io.Serializable;

public abstract class Card implements Serializable {
    String name;

    public Card(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Card{" +
                "'" + name + "'" +
                '}';
    }

    public String getName() {
        return name;
    }
}
