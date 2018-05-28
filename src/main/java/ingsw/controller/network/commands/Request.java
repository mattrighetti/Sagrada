package ingsw.controller.network.commands;

import java.io.Serializable;

/**
 * Interface that every Request to the server must implement
 */
public interface Request extends Serializable {
    Response handle(RequestHandler requestHandler);
}
