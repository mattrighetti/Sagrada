package ingsw.controller.network.commands;

import java.rmi.RemoteException;

public class JoinMatchRequest implements Request {
    public String matchName;

    public JoinMatchRequest(String matchName) {
        this.matchName = matchName;
    }

    @Override
    public Response handle(RequestHandler requestHandler) throws RemoteException {
        return requestHandler.handle(this);
    }
}
