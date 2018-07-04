package ingsw.controller.network.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class JoinMatchRequestTest {
    private JoinMatchRequest joinMatchRequest;
    private RequestHandler requestHandler;

    @BeforeEach
    void setUp() {
        requestHandler = mock(RequestHandler.class);
        joinMatchRequest = new JoinMatchRequest(anyString());
    }

    @Test
    void handle() {
        joinMatchRequest.handle(requestHandler);
        verify(requestHandler).handle(joinMatchRequest);
    }
}