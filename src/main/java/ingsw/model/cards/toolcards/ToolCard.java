package ingsw.model.cards.toolcards;

import ingsw.model.cards.Card;

public abstract class ToolCard extends Card {

    public ToolCard(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "ToolCard{" +
                "name='" + getName() + '\'' +
                '}';
    }

    public abstract void action();
}
