package ingsw.view.nodes;

import javafx.scene.layout.Pane;

public class DicePane extends Pane {
    int rowIndex;
    int columnIndex;

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
}