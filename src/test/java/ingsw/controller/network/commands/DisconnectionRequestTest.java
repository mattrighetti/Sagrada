package ingsw.controller.network.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DisconnectionRequestTest {
    private DisconnectionRequest disconnectionRequest;
    private RequestHandler requestHandler;

    @BeforeEach
    void setUp() {
        disconnectionRequest = new DisconnectionRequest();
        requestHandler = mock(RequestHandler.class);
    }

    @Test
    void handle() {
        disconnectionRequest.handle(requestHandler);
        verify(requestHandler).handle(disconnectionRequest);
    }
}