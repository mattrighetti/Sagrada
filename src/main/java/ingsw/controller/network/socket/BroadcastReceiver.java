package ingsw.controller.network.socket;

import ingsw.controller.network.commands.Response;

import java.util.concurrent.atomic.AtomicBoolean;

public class BroadcastReceiver implements Runnable {
    private ClientController clientController;
    private AtomicBoolean running = new AtomicBoolean(false);
    private Thread receiver;

    public BroadcastReceiver(ClientController clientController) {
        this.clientController = clientController;
    }

    public void start() {
        receiver = new Thread(this);
        running.set(true);
        receiver.start();
    }

    public boolean isRunning() {
        return running.get();
    }

    public void stop() {
        running.set(false);
        receiver.interrupt();
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
            response = clientController.getClient().nextResponse();
            if (response != null) {
                response.handle(clientController);
            } else
                running.set(false);
        } while (running.get());
    }
}
