package ingsw.controller.network.rmi;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.*;
import ingsw.view.SceneUpdater;

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


    /* NETWORK TYPE PART */
    /* METHODS EXECUTED BY THE VIEW TO MAKE ACTIONS */



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


    /* HANDLER PART */
    /* METHOD EXECUTED BY THE RMI HANDLER */



    @Override
    public void handle(LoginUserResponse loginUserResponse) {
        if (loginUserResponse.user != null) {
            loginUserResponse.user.addListener(rmiUserObserver);
            System.out.println("New connection >>> " + loginUserResponse.user.getUsername());
            isUserLogged = true;
            try {
                sceneUpdater.updateConnectedUsers(loginUserResponse.connectedUsers);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } else {
            isUserLogged = false;
        }
    }

    @Override
    public void handle(IntegerResponse integerResponse) {
        System.out.println("Connected Users: " + integerResponse.number);
        try {
            sceneUpdater.updateConnectedUsers(integerResponse.number);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(ChosenPatternCardResponse chosenPatternCardResponse) {

    }

    @Override
    public void handle(MessageResponse messageResponse) {
        System.out.println(messageResponse.message);
    }

    @Override
    public void handle(CreateMatchResponse createMatchResponse) {
        if (createMatchResponse != null) {
            System.out.println("Match created");
            try {
                sceneUpdater.updateExistingMatches(createMatchResponse.matchName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
