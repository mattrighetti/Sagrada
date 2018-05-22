package ingsw.controller.network.commands;

import java.rmi.RemoteException;

public class CreateMatchRequest implements Request {
    public String matchName;

    public CreateMatchRequest(String matchName) {
        this.matchName = matchName;
    }

    @Override
    public Response handle(RequestHandler requestHandler) throws RemoteException {
        requestHandler.handle(this);
        return null;
    }
}
