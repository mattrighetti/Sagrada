package ingsw.controller.network.commands;

public interface ResponseHandler {

    /**
     * The ResponseHandler handle the loginUserResponse
     *
     * @param loginUserResponse
     */
    void handle(LoginUserResponse loginUserResponse);

    /**
     * The ResponseHandler handle the integerResponse
     *
     * @param integerResponse
     */
    void handle(IntegerResponse integerResponse);

    /**
     * The ResponseHandler handle the createMatchResponse
     *
     * @param createMatchResponse
     */
    void handle(CreateMatchResponse createMatchResponse);

    /**
     * The ResponseHandler handle the joinedMatchResponse
     *
     * @param joinedMatchResponse
     */
    void handle(JoinedMatchResponse joinedMatchResponse);

    /**
     * The ResponseHandler handle the updateViewResponse
     *
     * @param updateViewResponse
     */
    void handle(UpdateViewResponse updateViewResponse);

    /**
     * The ResponseHandler handle the patternCardNotification
     *
     * @param patternCardNotification
     */
    void handle(PatternCardNotification patternCardNotification);

    /**
     * The ResponseHandler handle the logoutResponse
     *
     * @param logoutResponse
     */
    void handle(LogoutResponse logoutResponse);

    /**
     * The ResponseHandler handle the boardDataResponse
     *
     * @param boardDataResponse
     */
    void handle(BoardDataResponse boardDataResponse);

    /**
     * The ResponseHandler handle the notification
     *
     * @param notification
     */
    void handle(Notification notification);

    /**
     * The ResponseHandler handle the draftedDiceResponse
     *
     * @param draftedDiceResponse
     */
    void handle(DraftedDiceResponse draftedDiceResponse);

    /**
     * The ResponseHandler handle the useToolCardResponse
     *
     * @param useToolCardResponse
     */
    void handle(UseToolCardResponse useToolCardResponse);

    /**
     * The ResponseHandler handle the roundTrackNotification
     *
     * @param roundTrackNotification
     */
    void handle(RoundTrackNotification roundTrackNotification);

    /**
     * The ResponseHandler handle the reJoinResponse
     *
     * @param reJoinResponse
     */
    void handle(ReJoinResponse reJoinResponse);

    /**
     * The ResponseHandler handle the loseNotification
     *
     * @param loseNotification
     */
    void handle(LoseNotification loseNotification);

    /**
     * The ResponseHandler handle the victoryNotification
     *
     * @param victoryNotification
     */
    void handle(VictoryNotification victoryNotification);

    /**
     * The ResponseHandler handle the timeOutResponse
     *
     * @param timeOutResponse
     */
    void handle(TimeOutResponse timeOutResponse);

    /**
     * The ResponseHandler handle the
     *
     * @param rankingDataResponse rankingDataResponse
     */
    void handle(RankingDataResponse rankingDataResponse);

    /**
     * The ResponseHandler handle the bundleDataResponse
     *
     * @param bundleDataResponse
     */
    void handle(BundleDataResponse bundleDataResponse);

    /**
     * The ResponseHandler handle the historyResponse
     *
     * @param historyResponse
     */
    void handle(HistoryResponse historyResponse);

    /**
     * The ResponseHandler handle the finishedMatchesResponse
     *
     * @param finishedMatchesResponse
     */
    void handle(FinishedMatchesResponse finishedMatchesResponse);

    /**
     * The ResponseHandler handle the endTurnResponse
     *
     * @param endTurnResponse
     */
    void handle(EndTurnResponse endTurnResponse);

    /**
     * The ResponseHandler handle the availablePositionsResponse
     *
     * @param availablePositionsResponse
     */
    void handle(AvailablePositionsResponse availablePositionsResponse);
}
