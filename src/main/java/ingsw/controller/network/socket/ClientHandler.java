package ingsw.controller.network.socket;

import ingsw.controller.network.commands.*;
import ingsw.utilities.ControllerTimer;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

/**
 * Class that reads request sent from the client and writes the server responses via Socket
 */
public class ClientHandler implements Runnable, UserObserver, Serializable {
    private static final String ERROR_IN = "Errors in closing - ";

    private transient Socket clientSocket;
    private final transient ObjectInputStream objectInputStream;
    private final transient ObjectOutputStream objectOutputStream;
    private transient boolean stop = false;
    private transient ControllerTimer controllerTimer;

    private ServerController serverController;

    /**
     * Creates a new ClientHandler with a given socket, creates the streams and a ServerController
     * @param clientSocket ClientSocket
     * @throws IOException
     */
    ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        this.serverController = new ServerController(this);
        controllerTimer = new ControllerTimer();
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
                if (response instanceof Ping) {
                    controllerTimer.cancelTimer();
                    controllerTimer.setPingActive(false);
                } else
                    respond(response);
            }

        } catch (EOFException | SocketException e) {
            if (!stop) {
                System.out.print("EOF: ");
                shutdownClientHandler();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void shutdownClientHandler() {
        controllerTimer.cancelTimer();
        serverController.deactivateUser();
        close();
    }

    /**
     * Method that serializes objects and sends them to the other end of the connection
     *
     * @param response response to send
     */
    private void respond(Response response) {
        try {
            objectOutputStream.writeObject(response);
            objectOutputStream.reset();
        } catch (IOException e) {
            System.err.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }

    private void checkConnection() {
        if (!stop) {
            respond(new Ping());
            controllerTimer.startPingReceiveTimer(this);
        }
    }

    private void pingTimer() {
        new Thread(() -> {
            do {

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }

                checkConnection();

            } while (!stop);
        }).start();
    }

    private void stop() {
        stop = true;
    }

    /**
     * Method that closes ClientHandler connection
     */
    public void close() {
        System.out.println("Closing down connection");
        stop();
        if (objectInputStream != null) {
            try {
                objectInputStream.close();
            } catch (IOException e) {
                System.err.println(ERROR_IN + e.getMessage());
            }
        }

        if (objectOutputStream != null) {
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                System.err.println(ERROR_IN + e.getMessage());
            }
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            System.err.println(ERROR_IN + e.getMessage());
        }
    }

    /**
     * Get the ServeController
     * @return ServerController
     */
    public ServerController getServerController() {
        return serverController;
    }

    /*
     *
     * USER OBSERVER METHODS
     *
     */

    /**
     * Method that sends the number of connected Users to the User
     *
     * @param numberOfConnectedUsers number of connected Users to the game
     */
    @Override
    public void onJoin(int numberOfConnectedUsers) {
        respond(new IntegerResponse(numberOfConnectedUsers));
    }

    /**
     * Check if the user is active
     */
    @Override
    public void checkIfActive() {

    }

    /**
     * Activate Pinger
     */
    @Override
    public void activatePinger() {
        pingTimer();
    }

    /**
     * Method that sends a Response through the network
     *
     * @param response response to be delivered to the Client-side
     */
    @Override
    public void sendResponse(Response response) {
        respond(response);
    }

    /**
     * Method that sends a Notification to the User
     *
     * @param notification notification to be sent
     */
    @Override
    public void receiveNotification(Notification notification) {
        respond(notification);
    }

    /**
     * Method that notifies the User that it's his turn to play
     *
     * @param booleanMapGrid list of every available position where the die can be placed
     */
    @Override
    public void activateTurnNotification(Map<String, Boolean[][]> booleanMapGrid) {
        respond(new StartTurnNotification(booleanMapGrid));
    }

    /**
     * Send a victory notification with the points of the match
     * @param score Score
     *
     */
    @Override
    public void notifyVictory(int score) {
        respond(new VictoryNotification(score));
    }

    /**
     * Send a lost notification with the points of the match
     * @param score Score
     *
     */
    @Override
    public void notifyLost(int score) {
        respond(new LoseNotification(score));
    }
}
