package ingsw.view.nodes;

import ingsw.model.Dice;
import javafx.scene.control.Button;

public class DiceButton extends Button {
    private Dice dice;
    private int buttonIndex;

    public DiceButton(Dice dice, int buttonIndex) {
        super();
        this.dice = dice;
        this.buttonIndex = buttonIndex;
    }

    public Dice getDice() {
        return dice;
    }

    public int getButtonIndex() {
        return buttonIndex;
    }
}
