package ingsw.controller.network.socket;

import ingsw.controller.network.commands.Request;
import ingsw.controller.network.commands.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private final String host;
    private final int port;
    private Socket connection;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        connection = new Socket(host, port);
        objectOutputStream = new ObjectOutputStream(connection.getOutputStream());
        objectInputStream = new ObjectInputStream(connection.getInputStream());
    }

    public void close() throws IOException {
        objectInputStream.close();
        objectOutputStream.close();
        connection.close();
    }

    public void request(Request request) {
        try {
            objectOutputStream.writeObject(request);
        } catch (IOException e) {
            System.err.println("Exception on network: " + e.getMessage());
        }
    }

    public Response nextResponse() {
        try {
            return ((Response) objectInputStream.readObject());
        } catch (IOException e) {
            System.err.println("Exception on network: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Wrong deserialization: " + e.getMessage());
        }

        return null;
    }
}
