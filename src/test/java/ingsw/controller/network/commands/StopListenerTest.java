package ingsw.controller.network.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class StopListenerTest {
    private StopListener stopListener;
    private RequestHandler requestHandler;

    @BeforeEach
    void setUp() {
        requestHandler = mock(RequestHandler.class);
        stopListener = new StopListener();
    }

    @Test
    void handle() {
        Response response = stopListener.handle(requestHandler);
        assertNotNull(response);
    }
}