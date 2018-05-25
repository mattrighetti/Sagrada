package ingsw.controller.network;

public interface NetworkType {

    void loginUser(String username);

    void createMatch(String matchName);

    void joinExistingMatch(String matchName);
}
