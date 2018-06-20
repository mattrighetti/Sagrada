package ingsw.model.cards.toolcards;

import ingsw.model.GameManager;
import ingsw.model.cards.Card;

public abstract class ToolCard extends Card {
    private int price;

    public ToolCard(String name) {
        super(name);
        price = 1;
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

    protected void waitForToolCardAction(GameManager gameManager) {
        synchronized (gameManager.toolCardLock) {
            try {
                gameManager.toolCardLock.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    public abstract void action(GameManager gameManager);
}
