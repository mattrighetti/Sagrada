package ingsw.model.cards.toolcards;

import ingsw.model.cards.Card;

public abstract class ToolCard extends Card {
    private int price = 1;

    public ToolCard(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "ToolCard{" +
                "'" + getName() + "'" +
                '}';
    }

    public int getPrice() {
        return price;
    }

    public void increasePrice() {
        price = 2;
    }

    public abstract void action();
}
