package ingsw.view;

import ingsw.controller.network.socket.SagradaSocketServer;

public class LaunchServer {
    public static void main(String[] args) throws Exception {
        SagradaSocketServer sagradaSocketServer = new SagradaSocketServer(8000);

        try {
            sagradaSocketServer.run();
        } finally {
            sagradaSocketServer.close();
        }
    }
}
