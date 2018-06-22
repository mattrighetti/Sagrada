package ingsw.controller.network.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SagradaSocketServer {
    private final ServerSocket serverSocket;
    private final ExecutorService pool;

    public SagradaSocketServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        pool = Executors.newCachedThreadPool();
        System.out.println("Server started on port: " + port);
    }


    public void run() throws IOException {
        do {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New connection from -> " + clientSocket.getRemoteSocketAddress());
            pool.submit(new ClientHandler(clientSocket));
        } while (true);
    }

    public void close() {
        try {
            pool.shutdown();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
