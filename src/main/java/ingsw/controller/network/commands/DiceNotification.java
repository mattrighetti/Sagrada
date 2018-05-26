package ingsw.controller.network.commands;

import ingsw.model.Dice;
import ingsw.utilities.NotificationType;

import java.util.List;

public class DiceNotification extends Notification {
    private List<Dice> dice;

    public DiceNotification(List<Dice> dice) {
        super(NotificationType.DRAFT_DICE);
        this.dice = dice;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        //TODO
    }
}
