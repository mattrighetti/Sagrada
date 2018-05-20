package ingsw.controller.network.commands;

import ingsw.model.Dice;

import java.util.List;

public class DiceNotification extends Notification {
    private List<Dice> dice;

    public DiceNotification(List<Dice> dice) {
        this.dice = dice;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        //TODO
    }
}
