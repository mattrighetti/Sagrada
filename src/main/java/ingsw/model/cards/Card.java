package ingsw.model.cards;

import java.io.Serializable;

/**
 * Abstract class of a card. Extended by:
 * - Public Objective card
 * - Private Objective card
 * - Pattern card
 * - Tool card
 */
public abstract class Card implements Serializable {
    String name;

    /**
     * Set the card name
     * @param name Tool card name
     */
    public Card(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Card{" +
                "'" + name + "'" +
                '}';
    }

    /**
     * Returns the tool card name
     * @return Tool card name
     */
    public String getName() {
        return name;
    }
}
