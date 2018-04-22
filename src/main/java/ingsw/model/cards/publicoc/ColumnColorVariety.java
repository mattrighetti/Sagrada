package ingsw.model.cards.publicoc;

import ingsw.model.Color;
import ingsw.model.cards.patterncard.Box;

import java.util.LinkedList;
import java.util.List;

public class ColumnColorVariety extends PublicObjectiveCard {

    public ColumnColorVariety() {
        super("ColumnColorVariety", 1);
    }

    @Override
    public int check(List<List<Box>> grid) {
        int minValue = 0;

        List<Color> colorList = new LinkedList<>();

        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 4; j++){
                if( !colorList.contains(grid.get(j).get(i).getColor())) {
                    colorList.add(grid.get(j).get(i).getColor());
                }
            }
            if(colorList.size() == 4){
                minValue++;
            }
            colorList.removeAll(colorList);
        }
        return minValue;
    }
}
