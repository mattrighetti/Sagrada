package ingsw.controller.network;

public interface NetworkType {

    boolean loginUser(String username);

    void createMatch(String matchName);

    boolean joinExistingMatch(String matchName);
}
