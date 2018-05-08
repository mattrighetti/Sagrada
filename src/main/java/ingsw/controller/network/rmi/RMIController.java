package ingsw.controller.network.rmi;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.*;
import ingsw.view.SceneUpdater;

import java.rmi.Remote;

public class RMIController implements Remote, ResponseHandler, NetworkType {
    private RMIHandler rmiHandler;
    private RMIUserObserver rmiUserObserver;
    private Response ack;
    private SceneUpdater sceneUpdater;
    private boolean isUserLogged; // TODO fai response specifica

    public void connect() {
        rmiHandler = new RMIHandler(this);
        rmiUserObserver = new RMIUserObserver(this);
    }

    public void setSceneUpdater(SceneUpdater sceneUpdater) {
        this.sceneUpdater = sceneUpdater;
    }

    /* HANDLER PART */

    @Override
    public void handle(LoginUserResponse loginUserResponse) {

    }

    @Override
    public void handle(IntegerResponse integerResponse) {

    }

    @Override
    public void handle(ChosenPatternCardResponse chosenPatternCardResponse) {

    }

    @Override
    public void handle(MessageResponse messageResponse) {

    }

    /* NETWORK TYPE PART*/

    @Override
    public boolean loginUser(String username) {
        ack = (LoginUserResponse) new LoginUserRequest(username).handle(rmiHandler);
        if (((LoginUserResponse) ack).user != null) {
            isUserLogged = true;
            sceneUpdater.updateConnectedUsers(((LoginUserResponse) ack).connectedUsers);
        } else
            isUserLogged = false;

        return isUserLogged;
    }
}
