package ingsw.controller.network.socket;

import ingsw.controller.network.commands.LoginUserResponse;
import ingsw.controller.network.commands.Request;
import ingsw.controller.network.commands.Response;
import ingsw.model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable, JoinedUserObserver {
    private Socket clientSocket;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;
    private boolean stop;

    private ServerController serverController;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

        this.serverController = new ServerController(this);
    }

    @Override
    public void run() {
        try {
            do {
                Response response = ((Request) objectInputStream.readObject()).handle(serverController);
                if (response != null) {
                    respond(response);
                }
            } while (!stop);
        } catch (Exception e) {
            // TODO Handle Excpetions
        }
    }

    private void respond(Response response) {
        try {
            objectOutputStream.writeObject(response);
        } catch (IOException e) {
            System.err.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }

    public void stop() {
        stop = true;
    }

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


    @Override
    public void onJoin(User user) {
        respond(new LoginUserResponse(user));
    }
}