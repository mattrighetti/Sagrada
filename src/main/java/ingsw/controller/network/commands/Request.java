package ingsw.controller.network.commands;

import java.io.Serializable;

/**
 * Interface that every Request to the server must implement
 */
public interface Request extends Serializable {

    /**
     * Method that tells which class must handle the Response
     * @param requestHandler class that declares by who the Request will be handled
     */
    Response handle(RequestHandler requestHandler);
}
