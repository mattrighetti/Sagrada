package ingsw.view.nodes;

import ingsw.model.Dice;
import javafx.scene.layout.Pane;

public class DicePane extends Pane {
    private int rowIndex;
    private int columnIndex;
    private Dice dice;

    public DicePane(int rowIndex, int columnIndex) {
        super();
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public Dice getDice() {
        return dice;
    }
}
