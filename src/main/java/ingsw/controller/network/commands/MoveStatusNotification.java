package ingsw.controller.network.commands;

import ingsw.utilities.MoveStatus;
import ingsw.utilities.NotificationType;

import java.util.List;

public class MoveStatusNotification extends Notification {
    public List<MoveStatus> moveStatuses;

    public MoveStatusNotification(List<MoveStatus> moveStatuses) {
        super(NotificationType.HISTORY_UPDATE);
        this.moveStatuses = moveStatuses;
    }
}
