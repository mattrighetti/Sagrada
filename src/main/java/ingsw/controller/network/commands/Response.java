package ingsw.controller.network.commands;

import java.io.Serializable;

public interface Response extends Serializable {

    void handle(ResponseHandler responseHandler);

}