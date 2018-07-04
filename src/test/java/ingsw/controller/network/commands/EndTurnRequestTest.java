package ingsw.controller.network.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EndTurnRequestTest {
    private EndTurnRequest endTurnRequest;
    private RequestHandler requestHandler;

    @BeforeEach
    void setUp() {
        requestHandler = mock(RequestHandler.class);
        endTurnRequest = new EndTurnRequest(anyString());
    }

    @Test
    void handle() {
        endTurnRequest.handle(requestHandler);
        verify(requestHandler).handle(endTurnRequest);
    }
}