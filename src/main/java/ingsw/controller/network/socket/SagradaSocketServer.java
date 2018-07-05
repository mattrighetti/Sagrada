package ingsw.controller.network.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class that listens for socket connection and submit a new ClientHandler
 * to an Executors.newCachedThreadPool()
 */
public class SagradaSocketServer {
    private final ServerSocket serverSocket;
    private final ExecutorService pool;
    private boolean stop;

    /**
     * Creates a new ServerSocket that listens for connection at
     * <code>port</code>
     * @param port Connection port
     * @throws IOException
     */
    public SagradaSocketServer(int port) throws IOException {
        stop = false;
        serverSocket = new ServerSocket(port);
        pool = Executors.newCachedThreadPool();
        System.out.println("Server started on port: " + port);
    }

    /**
     * Creates a new ServerSocket with the <code>port</code> passed as parameter
     * and submit the new ClientHandler to the pool
     */
    public void run() throws IOException {
        do {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New connection from -> " + clientSocket.getRemoteSocketAddress());
            pool.submit(new ClientHandler(clientSocket));
        } while (!stop);
    }

    /**
     * Closes the ServerSocket and the ThreadPool
     */
    public void close() {
        try {
            stop = true;
            serverSocket.close();
            pool.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
