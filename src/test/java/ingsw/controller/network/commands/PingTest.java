package ingsw.controller.network.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PingTest {
    private Ping ping;
    private RequestHandler requestHandler;
    private ResponseHandler responseHandler;

    @BeforeEach
    void setUp() {
        ping = new Ping();
        requestHandler = mock(RequestHandler.class);
        responseHandler = mock(ResponseHandler.class);
    }

    @Test
    void handle() {
        Response returnedPing = ping.handle(requestHandler);
        assertNotNull(returnedPing);
    }

    @Test
    void handle1() {
        ping = mock(Ping.class);
        doNothing().when(ping).handle(responseHandler);
    }
}