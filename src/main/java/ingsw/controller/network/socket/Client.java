package ingsw.controller.network.socket;

import ingsw.controller.network.commands.Ping;
import ingsw.controller.network.commands.Request;
import ingsw.controller.network.commands.Response;

import java.io.*;
import java.net.Socket;

/**
 * Class the takes the request generated in the ClientController, writes them to the socket stream
 * and then reads the response incoming from the server e passes them to the ClientController
 */
public class Client {
    private final String host;
    private final int port;
    private Socket connection;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    /**
     * Set the socket port and host
     *
     * @param host Host
     * @param port Port
     */
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Connects to the socket with data given in the constructor and
     * creates the Stream()
     *
     * @throws IOException
     */
    public void connect() throws IOException {
        connection = new Socket(host, port);
        objectOutputStream = new ObjectOutputStream(connection.getOutputStream());
        objectInputStream = new ObjectInputStream(connection.getInputStream());
    }

    /**
     * Close the streams and the connection
     */
    void close() {
        try {
            objectInputStream.close();
            objectOutputStream.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the request to a stream
     *
     * @param request Request written on the stream
     */
    void request(Request request) {
        try {
            objectOutputStream.writeObject(request);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Exception on network: " + e.getMessage());
        }
    }

    /**
     * Read the response from the inputStream
     *
     * @return Response
     */
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

    /**
     * Sending a ping to the server
     * @param ping ping
     */
    void ackPing(Ping ping) {
        try {
            objectOutputStream.writeObject(ping);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
