package ingsw.controller.network.commands;

public interface RequestHandler {

    Response handle(LoginUserRequest loginUserRequest);

    Response handle(ChosenPatternCardRequest chosenPatternCardRequest);

    Response handle(CreateMatchRequest createMatchRequest);

    Response handle(JoinMatchRequest joinMatchRequest);

    Response handle(LogoutRequest logoutRequest);

    Response handle(DraftDiceRequest draftDiceRequest);
}
