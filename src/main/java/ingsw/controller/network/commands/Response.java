package ingsw.controller.network.commands;

import java.io.Serializable;

/**
 * Interface that every Response sent to the Client must implement
 */
public interface Response extends Serializable {

    /**
     * Method that tells which class must handle the Response
     * @param responseHandler class that declares by who the Response will be handled
     */
    void handle(ResponseHandler responseHandler);

}
