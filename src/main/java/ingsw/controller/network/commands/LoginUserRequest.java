package ingsw.controller.network.commands;

import java.rmi.RemoteException;

public class LoginUserRequest implements Request {
    public String username;

    public LoginUserRequest(String username) {
        this.username = username;
    }

    @Override
    public Response handle(RequestHandler requestHandler) throws RemoteException {
        return requestHandler.handle(this);
    }
}
