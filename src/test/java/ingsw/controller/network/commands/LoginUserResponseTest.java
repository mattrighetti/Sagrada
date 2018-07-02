package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import ingsw.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LoginUserResponseTest {
    private LoginUserResponse loginUserResponse;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        loginUserResponse = new LoginUserResponse(Mockito.mock(User.class));
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        loginUserResponse.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(loginUserResponse);
        loginUserResponse.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(loginUserResponse);
    }
}