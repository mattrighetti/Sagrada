package ingsw.controller.network.commands;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;

class BundleDataResponseTest {
    private BundleDataResponse bundleDataResponse;
    private RMIController rmiController;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        bundleDataResponse = new BundleDataResponse(0, new ArrayList<>(), new ArrayList<>(), new HashMap<>());
        rmiController = Mockito.mock(RMIController.class);
        clientController = Mockito.mock(ClientController.class);
    }

    @Test
    void handle() {
        bundleDataResponse.handle(rmiController);
        Mockito.verify(rmiController, Mockito.times(1)).handle(bundleDataResponse);
        bundleDataResponse.handle(clientController);
        Mockito.verify(clientController, Mockito.times(1)).handle(bundleDataResponse);
    }

    @Test
    void getUserStatsData() {
    }
}