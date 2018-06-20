package ingsw.controller.network.commands;

public interface ResponseHandler {

    void handle(LoginUserResponse loginUserResponse);

    void handle(IntegerResponse integerResponse);

    void handle(MessageResponse messageResponse);

    void handle(CreateMatchResponse createMatchResponse);

    void handle(JoinedMatchResponse joinedMatchResponse);

    void handle(UpdateViewResponse updateViewResponse);

    void handle(PatternCardNotification patternCardNotification);

    void handle(LogoutResponse logoutResponse);

    void handle(BoardDataResponse boardDataResponse);

    void handle(Notification notification);

    void handle(DraftedDiceResponse draftedDiceResponse);

    void handle(UseToolCardResponse useToolCardResponse);

    void handle(RoundTrackNotification roundTrackNotification);

    void handle(ReJoinResponse reJoinResponse);

    void handle(LoseNotification loseNotification);

    void handle(VictoryNotification victoryNotification);

    void handle(TimeOutResponse timeOutResponse);

    void handle(RankingDataResponse rankingDataResponse);

    void handle(BundleDataResponse bundleDataResponse);

    void handle(HistoryResponse historyResponse);

    void handle(FinishedMatchesResponse finishedMatchesResponse);

    void handle(EndTurnResponse endTurnResponse);

    void handle(AvailablePositionsResponse availablePositionsResponse);
}
