package ingsw.controller.network.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class StopListenerResponseTest {
    private StopListenerResponse stopListenerResponse;
    private ResponseHandler responseHandler;

    @BeforeEach
    void setUp() {
        stopListenerResponse = mock(StopListenerResponse.class);
        responseHandler = mock(ResponseHandler.class);
    }

    @Test
    void handle() {
        doNothing().when(stopListenerResponse).handle(responseHandler);
    }
}