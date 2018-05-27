package ingsw.controller.network.socket;

import ingsw.controller.network.Message;
import ingsw.controller.network.commands.*;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;

public class ClientHandler implements Runnable, UserObserver, Serializable {
    private transient Socket clientSocket;
    private transient final ObjectInputStream objectInputStream;
    private transient final ObjectOutputStream objectOutputStream;
    private transient boolean stop = false;

    private ServerController serverController;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        this.serverController = new ServerController(this);
    }

    /**
     * Method that receives every Request sent by the user and passes them to the ServerController
     * and then waits for the Response given by the ServerController and handles it
     */
    @Override
    public void run() {
        try {
            do {
                readResponse();
            } while (!stop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Read a request incoming from ClientController and wait for a response from the ServerController.
     * After that the response returned is passed as parameter to response().
     */
    private void readResponse() {
        try {
            Response response = ((Request) objectInputStream.readObject()).handle(serverController);
            if (response != null) {
                if (response instanceof LogoutResponse) {
                    respond(response);
                    close();
                }
                else
                    respond(response);
            }

        } catch (NullPointerException e) {
            System.err.println("Catching null");
            respond(null);
        } catch (EOFException e) {
          close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("The class is probably not Serializable");
            e.printStackTrace();
        }
    }


    /**
     * Method that serializes objects and sends them to the other end of the connection
     *
     * @param response response to send
     */
    private void respond(Response response) {
        try {
            objectOutputStream.writeObject(response);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }

    public void stop() {
        stop = true;
    }

    /**
     * Method that closes ClientHandler connection
     */
    public void close() {
        stop = true;
        if (objectInputStream != null) {
            try {
                objectInputStream.close();
            } catch (IOException e) {
                System.err.println("Errors in closing - " + e.getMessage());
            }
        }

        if (objectOutputStream != null) {
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                System.err.println("Errors in closing - " + e.getMessage());
            }
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Errors in closing - " + e.getMessage());
        }
    }

    /*
     *
     * USER OBSERVER METHODS
     *
     */

    @Override
    public void onJoin(int numberOfConnectedUsers) {
        respond(new IntegerResponse(numberOfConnectedUsers));
    }

    @Override
    public void sendMessage(Message message) {
        respond(new MessageResponse(message));
    }

    @Override
    public void sendResponse(Response response) {
        respond(response);
    }

    @Override
    public void receiveNotification(Notification notification) {
        respond(notification);
    }

    @Override
    public void activateTurnNotification(List<Boolean[][]> booleanListGrid) {
        respond(new StartTurnNotification(booleanListGrid));
    }
}
