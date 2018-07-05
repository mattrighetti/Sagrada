package ingsw.controller.network.commands;

public interface RequestHandler {

    /**
     * Handle the Ack
     *
     * @param ack
     * @return
     */
    Response handle(Ack ack);

    /**
     * Handle the logoutRequest
     *
     * @param logoutRequest
     * @return
     */
    Response handle(LogoutRequest logoutRequest);

    /**
     * Handle the EndTurnRequest
     *
     * @param endTurnRequest
     * @return
     */
    Response handle(EndTurnRequest endTurnRequest);

    /**
     * Handle the placeDiceRequest
     * @param placeDiceRequest
     * @return
     */
    Response handle(PlaceDiceRequest placeDiceRequest);

    /**
     * Handle the loginUserRequest
     *
     * @param loginUserRequest
     * @return
     */
    Response handle(LoginUserRequest loginUserRequest);

    /**
     * Handle the draftDiceRequest
     *
     * @param draftDiceRequest
     * @return
     */
    Response handle(DraftDiceRequest draftDiceRequest);

    /**
     * Handle the joinMatchRequest
     *
     * @param joinMatchRequest
     * @return
     */
    Response handle(JoinMatchRequest joinMatchRequest);

    /**
     * Handle the bundleDataRequest
     *
     * @param bundleDataRequest
     * @return
     */
    Response handle(BundleDataRequest bundleDataRequest);

    /**
     * Handle the createMatchRequest
     *
     * @param createMatchRequest
     * @return
     */
    Response handle(CreateMatchRequest createMatchRequest);

    /**
     * Handle the reJoinMatchRequest
     *
     * @param reJoinMatchRequest
     * @return
     */
    Response handle(ReJoinMatchRequest reJoinMatchRequest);

    /**
     * Handle the useToolCardRequest
     *
     * @param useToolCardRequest
     * @return
     */
    Response handle(UseToolCardRequest useToolCardRequest);

    /**
     * Handle the readHistoryRequest
     *
     * @param readHistoryRequest
     * @return
     */
    Response handle(ReadHistoryRequest readHistoryRequest);

    /**
     * Handle the moveToolCardRequest
     *
     * @param moveToolCardRequest
     * @return
     */
    Response handle(MoveToolCardRequest moveToolCardRequest);

    /**
     * Handle the finishedMatchesRequest
     *
     * @param finishedMatchesRequest
     * @return
     */
    Response handle(FinishedMatchesRequest finishedMatchesRequest);

    /**
     * Handle the chosenPatternCardRequest
     *
     * @param chosenPatternCardRequest
     * @return
     */
    Response handle(ChosenPatternCardRequest chosenPatternCardRequest);

}
