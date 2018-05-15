package ingsw.controller.network.rmi;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.*;
import ingsw.view.SceneUpdater;

import java.rmi.Remote;
import java.rmi.RemoteException;

public class RMIController implements ResponseHandler, NetworkType {
    private RMIHandler rmiHandler;
    private RMIUserObserver rmiUserObserver;
    private Response ack;
    private SceneUpdater sceneUpdater;
    private boolean isUserLogged;

    public void connect() throws RemoteException {
        rmiUserObserver = new RMIUserObserver(this);
        rmiHandler = new RMIHandler(this, rmiUserObserver);
    }

    public void setSceneUpdater(SceneUpdater sceneUpdater) {
        this.sceneUpdater = sceneUpdater;
    }



    /* HANDLER PART */



    @Override
    public void handle(LoginUserResponse loginUserResponse) {
        if (loginUserResponse.user != null) {
            loginUserResponse.user.addListener(rmiUserObserver);
            System.out.println("New connection >>> " + loginUserResponse.user.getUsername());
            isUserLogged = true;
            sceneUpdater.updateConnectedUsers(loginUserResponse.connectedUsers);
        } else {
            isUserLogged = false;
        }
    }

    @Override
    public void handle(IntegerResponse integerResponse) {
        System.out.println("Connected Users: " + integerResponse.number);
        sceneUpdater.updateConnectedUsers(integerResponse.number);
    }

    @Override
    public void handle(ChosenPatternCardResponse chosenPatternCardResponse) {

    }

    @Override
    public void handle(MessageResponse messageResponse) {

    }

    @Override
    public void handle(CreateMatchResponse createMatchResponse) {

    }


    /* NETWORK TYPE PART*/



    @Override
    public boolean loginUser(String username) throws RemoteException {
        ack = new LoginUserRequest(username).handle(rmiHandler);
        ack.handle(this);

        return isUserLogged;
    }

    @Override
    public void createMatch(String matchName) throws RemoteException {
        ack = new CreateMatchRequest(matchName).handle(rmiHandler);
        ack.handle(this);
    }
}
