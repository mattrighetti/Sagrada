package ingsw.model.cards.toolcards;

import ingsw.model.GameManager;
import ingsw.model.cards.Card;

/**
 * Class that has a price and the methods to manage it
 */
public abstract class ToolCard extends Card {
    private int price;


    /**
     * Creates a new tool card
     */
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

    /**
     * Returns the tool card price
     * @return Tool card price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Increasese the tool card price
     */
    public void increasePrice() {
        price = 2;
    }

    /**
     * Method used by the tool cards that waits until a tool card move is dona
     * @param gameManager Gamemanager instance on which the toolcard call the methods
     */
    protected void waitForToolCardAction(GameManager gameManager) {
        synchronized (gameManager.getToolCardLock()) {
            try {
                gameManager.getToolCardLock().wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    /**
     * Method that contains the main steps of every toolcard
     * @param gameManager gameManager on which the toolcard invoke methods
     */
    public abstract void action(GameManager gameManager);
}
