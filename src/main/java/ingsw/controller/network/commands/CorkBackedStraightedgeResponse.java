package ingsw.controller.network.commands;

import ingsw.utilities.ToolCardType;

import javax.jws.soap.SOAPBinding;

public class CorkBackedStraightedgeResponse extends UseToolCardResponse {

    public CorkBackedStraightedgeResponse() {
        super(ToolCardType.CORK_BACKED_STRAIGHT_EDGE);
    }
}
