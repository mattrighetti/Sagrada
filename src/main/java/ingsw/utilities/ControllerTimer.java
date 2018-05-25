package ingsw.utilities;

import ingsw.controller.Controller;
import ingsw.controller.network.Message;

import java.util.Timer;
import java.util.TimerTask;

public class ControllerTimer {
    Timer timer;
    Controller controller;

    public ControllerTimer(Controller controller) {
        this.timer = new Timer();
        this.controller = controller;
    }

    public void startTimer(int seconds) {
        timer.schedule(new LaunchMatch(), seconds * 1000);
    }

    public void cancelTimer() {
        timer.cancel();
    }

    class LaunchMatch extends TimerTask {
        @Override
        public void run() {
            Broadcaster.broadcastMessageToAll(controller.getPlayerList(),
                    new Message(
                            controller.getPlayerList().get(controller.getPlayerList().size() - 1).getPlayerUsername(),
                            "Match starting"));
            controller.createMatch();
        }
    }

}
