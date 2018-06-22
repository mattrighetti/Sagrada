package ingsw.controller.network.socket;

import ingsw.controller.network.commands.Response;

import java.util.concurrent.atomic.AtomicBoolean;

public class BroadcastReceiver implements Runnable {
    private ClientController clientController;
    private AtomicBoolean running = new AtomicBoolean(false);
    private Thread receiver;
    private boolean bool = true;

    public BroadcastReceiver(ClientController clientController) {
        this.clientController = clientController;
    }

    private void start() {
        receiver = new Thread(this);
        running.set(true);
        receiver.start();
    }

    public boolean isRunning() {
        return running.get();
    }

    public void stop() {
        bool = false;
    }

    public void restart() {
        if (!receiver.isAlive()) {
            this.start();
        } else
            System.err.println("Thread already running");
    }

    @Override
    public void run() {
        Response response;
        do {

            if (bool) {
                response = clientController.getClient().nextResponse();
                System.out.println("Broadcast Reading");
                if (response != null) {
                    response.handle(clientController);
                }
            } else {
                System.out.println("Broadcaster stopped");
                break;
            }
        } while (true);
    }
}
