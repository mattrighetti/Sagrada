package ingsw.utilities;

import java.util.Timer;
import java.util.TimerTask;

public class ControllerTimer {
    Timer timer;

    public ControllerTimer(int seconds) {
        this.timer = new Timer();
        timer.schedule(new LaunchMatch(), seconds*1000);
    }

    class LaunchMatch extends TimerTask {
        @Override
        public void run() {
            // TODO broadcast to every users message that match has began
        }
    }

}
