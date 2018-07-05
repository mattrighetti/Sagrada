package ingsw.controller.network.commands;

public interface RequestHandler {

    Response handle(Ack ack);

    Response handle(LogoutRequest logoutRequest);

    Response handle(EndTurnRequest endTurnRequest);

    Response handle(PlaceDiceRequest placeDiceRequest);

    Response handle(LoginUserRequest loginUserRequest);

    Response handle(DraftDiceRequest draftDiceRequest);

    Response handle(JoinMatchRequest joinMatchRequest);

    Response handle(BundleDataRequest bundleDataRequest);

    Response handle(CreateMatchRequest createMatchRequest);

    Response handle(ReJoinMatchRequest reJoinMatchRequest);

    Response handle(UseToolCardRequest useToolCardRequest);

    Response handle(ReadHistoryRequest readHistoryRequest);

    Response handle(MoveToolCardRequest moveToolCardRequest);

    Response handle(FinishedMatchesRequest finishedMatchesRequest);

    Response handle(ChosenPatternCardRequest chosenPatternCardRequest);

}
