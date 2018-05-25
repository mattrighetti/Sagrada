package ingsw.utilities;

import ingsw.controller.Controller;
import ingsw.controller.network.Message;

import java.util.Timer;
import java.util.TimerTask;

public class ControllerTimer {
    private Timer timer;
    private Controller controller;

    public ControllerTimer(Controller controller) {
        this.timer = new Timer("TimerThread");
        this.controller = controller;
    }

    public void startLoginTimer(int loginSeconds) {
        timer.schedule(new LaunchMatch(), loginSeconds * 1000);
    }

    public void startTurnTimer() {
        timer.schedule(new EndTurn(), 60 * 1000);
    }

    public void cancelTimer() {
        timer.cancel();
    }

    public class LaunchMatch extends TimerTask {
        @Override
        public void run() {
            Broadcaster.broadcastMessageToAll(controller.getPlayerList(),
                    new Message(
                            "Ciao",
                            "Match starting"));
            controller.createMatch();
        }
    }

    class EndTurn extends TimerTask {
        @Override
        public void run() {
            // TODO timerTask to end turn
        }
    }


}
