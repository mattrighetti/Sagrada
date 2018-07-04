package ingsw.controller.network.socket;

import ingsw.controller.network.commands.Ping;
import ingsw.controller.network.commands.Request;
import ingsw.controller.network.commands.Response;
import ingsw.controller.network.commands.StopListener;

import java.io.*;
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

    void close() {
        try {
            objectInputStream.close();
            objectOutputStream.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void request(Request request) {
        try {
            objectOutputStream.writeObject(request);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Exception on network: " + e.getMessage());
        }
    }

    Response nextResponse() {
        try {
            return ((Response) objectInputStream.readObject());
        } catch (StreamCorruptedException e) {
            System.err.println("Closing socket");
        } catch (EOFException e) {
            System.err.println("Server not reachable, closing connection");
            close();
        } catch (IOException e) {
            System.err.println("Exception on network");
        } catch (ClassNotFoundException e) {
            System.err.println("Wrong deserialization");
        }

        return null;
    }

    void ackPing(Ping ping) {
        try {
            objectOutputStream.writeObject(ping);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
