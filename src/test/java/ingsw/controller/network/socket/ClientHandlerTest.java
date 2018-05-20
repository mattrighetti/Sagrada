package ingsw.controller.network.socket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import static org.mockito.Mockito.*;

class ClientHandlerTest {

    private ClientHandler clientHandler;
    private Socket socket;
    private OutputStream byteArrayOutputStream;
    private InputStream byteArrayInputStream;

    @BeforeEach
    void setUp() throws IOException {
//        socket = new Socket("localhost", 8840);
  //      byteArrayOutputStream = new ByteArrayOutputStream();
    //    byteArrayInputStream = new ByteArrayInputStream( "Hello World".getBytes());
      //  when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
//        when(socket.getInputStream()).thenReturn(byteArrayInputStream);

  //      clientHandler = new ClientHandler(socket);

    }


    @Test
    void onJoin() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {

//        clientHandler.onJoin(2);
  //      Method method = clientHandler.getClass().getMethod("respond" );
    //    method.setAccessible(true);
      //  verify(method, times(1)).invoke(new LoginUserResponse(new User("ciao"),2));
    }
}