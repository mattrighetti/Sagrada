package ingsw.model.cards;

public abstract class Card {
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
