package ingsw.view.nodes;

import ingsw.model.Dice;
import javafx.scene.control.Button;

public class DiceButton extends Button {
    private Dice dice;

    public DiceButton(Dice dice) {
        super();
        this.dice = dice;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }
}
