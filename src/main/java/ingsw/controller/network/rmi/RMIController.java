package ingsw.controller.network.rmi;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.*;
import ingsw.view.SceneUpdater;

import java.rmi.RemoteException;

public class RMIController implements ResponseHandler, NetworkType {
    private RMIHandler rmiHandler;
    private RMIUserObserver rmiUserObserver;
    private Response response;
    private SceneUpdater sceneUpdater;

    public void connect() {
        try {
            rmiUserObserver = new RMIUserObserver(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        rmiHandler = new RMIHandler(this, rmiUserObserver);
    }

    public void setSceneUpdater(SceneUpdater sceneUpdater) {
        this.sceneUpdater = sceneUpdater;
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /* NETWORK TYPE PART */
    /* METHODS EXECUTED BY THE VIEW TO MAKE ACTIONS */


    /**
     * Method that logs in the user to SagradaGame
     *
     * @param username username chosen by the user
     * @return boolean value that indicates if the user has been successfully logged in to the game
     * @throws RemoteException throws RemoteException
     */
    @Override
    public void loginUser(String username) {
        response = new LoginUserRequest(username).handle(rmiHandler);
        response.handle(this);
    }

    @Override
    public void createMatch(String matchName) {
        new CreateMatchRequest(matchName).handle(rmiHandler);
    }

    @Override
    public void joinExistingMatch(String matchName) {
        response = new JoinMatchRequest(matchName).handle(rmiHandler);
        response.handle(this);
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /* HANDLER PART */
    /* METHOD EXECUTED BY THE RMI HANDLER */


    @Override
    public void handle(LoginUserResponse loginUserResponse) {
        if (loginUserResponse.user != null) {
            loginUserResponse.user.addListener(rmiUserObserver);
            System.out.println("New connection >>> " + loginUserResponse.user.getUsername());

            sceneUpdater.updateConnectedUsers(loginUserResponse.connectedUsers);
            sceneUpdater.updateExistingMatches(loginUserResponse.availableMatches);
            sceneUpdater.launchSecondGui();
        } else
            sceneUpdater.launchAlert();
    }

    @Override
    public void handle(IntegerResponse integerResponse) {
        System.out.println("Connected Users: " + integerResponse.number);

        sceneUpdater.updateConnectedUsers(integerResponse.number);
    }

    @Override
    public void handle(MessageResponse messageResponse) {
        System.out.println(messageResponse.message);
        sceneUpdater.launchThirdGui();
    }

    @Override
    public void handle(CreateMatchResponse createMatchResponse) {
        if (createMatchResponse.doubleString != null) {
            System.out.println("Match created");

            sceneUpdater.updateExistingMatches(createMatchResponse.doubleString);
        }
    }

    @Override
    public void handle(JoinedMatchResponse joinedMatchResponse) {

    }

    @Override
    public void handle(DiceMoveResponse diceMoveResponse) {
        //TODO
    }

    @Override
    public void handle(PatternCardNotification patternCardNotification) {
        //TODO
    }

    @Override
    public void handle(ChosenPatternCardResponse chosenPatternCardResponse) {

    }
}
