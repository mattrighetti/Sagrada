package ingsw.controller;

import ingsw.controller.network.commands.*;
import ingsw.model.*;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.Tuple;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.Whitebox;

import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerTest {

    Controller controller;

    @BeforeEach
    void setUp() throws RemoteException {
        controller = new Controller("Test",10, 10, SagradaGame.get());
        Whitebox.setInternalState(controller,"gameManager",mock(GameManager.class));
        Whitebox.setInternalState(controller,"sagradaGame",mock(SagradaGame.class));
    }

    @Test
    void Controller() {
        SagradaGame sagradaGame = mock(SagradaGame.class);
        Controller controllerToTest = new Controller("Match",30,5,sagradaGame);

        assertEquals(controllerToTest.getMatchName(),"Match");
        assertEquals(Whitebox.getInternalState(controllerToTest,"hasStarted"),false);
        assertEquals(Whitebox.getInternalState(controllerToTest,"maxJoinMatchSeconds"),5);
        assertTrue(Whitebox.getInternalState(controllerToTest,"sagradaGame") != null);
        assertTrue(Whitebox.getInternalState(controllerToTest,"controllerTimer") != null);
        assertTrue(Whitebox.getInternalState(controllerToTest,"playerList") != null);
        assertEquals(Whitebox.getInternalState(controllerToTest,"maxTurnSeconds"), 30);
        assertEquals(controllerToTest.getConnectedUsers(),0);
        assertEquals(controllerToTest.getPlayerList(),Whitebox.getInternalState(controllerToTest,"playerList"));
    }

    @Test
    void loginUser() throws NoSuchFieldException, IllegalAccessException, RemoteException {
        Field field = controller.getClass().getDeclaredField("playerList");
        field.setAccessible(true);
        for (int i = 0; i < 3; i++) {
            controller.loginUser(new User(String.valueOf(i)));
            List<Player> playerList = (List<Player>) field.get(controller);
            assertEquals(playerList.size(), i+1);
            assertTrue(playerList.get(i).getPlayerUsername().equals(String.valueOf(i)));
        }
        field.setAccessible(false);
    }

    @Test
    void deactivateUser() throws RemoteException {

        User user1 = new User("1");
        controller.loginUser(user1);
        controller.deactivateUser(user1);

        ArrayList list = (ArrayList) Whitebox.getInternalState(controller, "playerList");
        Player player = (Player) list.get(0);
        assertTrue(!player.getUser().isActive());

    }

    @Test
    void assignPatternCard() throws RemoteException {
        PatternCard patternCard = mock(PatternCard.class);

        controller.assignPatternCard("username",patternCard);
        verify((GameManager)Whitebox.getInternalState(controller,"gameManager"),times(1)).setPatternCardForPlayer("username",patternCard);

    }

    @Test
    void createMatch() throws NoSuchFieldException, IllegalAccessException, RemoteException {
        controller.createMatch();
        assertTrue(!Whitebox.getInternalState(controller,"gameManager").equals(null));
    }

    @Test
    void endTurn() throws NoSuchFieldException, IllegalAccessException, RemoteException {
        controller.endTurn("Player");
        verify((GameManager)Whitebox.getInternalState(controller,"gameManager"),times(1)).endTurn("Player");
    }

    @Test
    void draftDice() throws RemoteException, IllegalAccessException, NoSuchFieldException {

        controller.draftDice("Player");

        verify((GameManager)Whitebox.getInternalState(controller,"gameManager"),times(1)).draftDiceFromBoard();
    }

    @Test
    void sendAck() throws RemoteException {
        controller.sendAck();
        verify((GameManager)Whitebox.getInternalState(controller,"gameManager"),times(1)).receiveAck();

    }

    @Test
    void placeDice() throws RemoteException {
        Dice dice = mock(Dice.class);
        controller.placeDice(dice,1,1);
        verify((GameManager)Whitebox.getInternalState(controller,"gameManager"),times(1)).placeDiceForPlayer(dice,1,1);

    }

    @Test
    void useToolCard() throws RemoteException {
        controller.useToolCard("ToolCard");
        verify((GameManager)Whitebox.getInternalState(controller,"gameManager"),times(1)).useToolCard("ToolCard");

    }

    @Test
    void removeMatch() {
        controller.removeMatch();
        verify((SagradaGame)Whitebox.getInternalState(controller,"sagradaGame"),times(1)).removeMatch(controller);
    }

    @Test
    void toolCardMove1() throws RemoteException {
        GrozingPliersRequest grozingPliersRequest = new GrozingPliersRequest(mock(Dice.class),true);
        controller.toolCardMove(grozingPliersRequest);
        verify((GameManager)Whitebox.getInternalState(controller,"gameManager"),times(1)).grozingPliersMove(grozingPliersRequest.selectedDice,grozingPliersRequest.increase);
    }

    @Test
    void toolCardMove2() throws RemoteException, NoSuchFieldException {
        FluxBrushRequest fluxBrushRequest1 = new FluxBrushRequest(mock(Dice.class));
        controller.toolCardMove(fluxBrushRequest1);

        FluxBrushRequest fluxBrushRequest2 = new FluxBrushRequest(mock(Dice.class),1,1);
        controller.toolCardMove(fluxBrushRequest2);

        FluxBrushRequest fluxBrushRequest3 = new FluxBrushRequest();
        controller.toolCardMove(fluxBrushRequest3);

        verify((GameManager) Whitebox.getInternalState(controller,"gameManager"),times(1)).fluxBrushMove(fluxBrushRequest2.selectedDice,fluxBrushRequest2.row,fluxBrushRequest2.column);
        verify((GameManager) Whitebox.getInternalState(controller,"gameManager"),times(1)).fluxBrushMove(fluxBrushRequest1.selectedDice);
        verify((GameManager) Whitebox.getInternalState(controller,"gameManager"),times(1)).fluxBrushMove();


    }

    @Test
    void toolCardMove3() throws RemoteException {
        FluxRemoverRequest fluxRemoverRequest1 = new FluxRemoverRequest(mock(Dice.class));
        controller.toolCardMove(fluxRemoverRequest1);

        FluxRemoverRequest fluxRemoverRequest2 = new FluxRemoverRequest(mock(Dice.class),1);
        controller.toolCardMove(fluxRemoverRequest2);

        FluxRemoverRequest fluxRemoverRequest3 = new FluxRemoverRequest(mock(Dice.class),1,1);
        controller.toolCardMove(fluxRemoverRequest3);

        FluxRemoverRequest fluxRemoverRequest4 = new FluxRemoverRequest();
        controller.toolCardMove(fluxRemoverRequest4);

        verify((GameManager) Whitebox.getInternalState(controller,"gameManager"),times(1)).fluxRemoverMove(fluxRemoverRequest1.selectedDice);
        verify((GameManager) Whitebox.getInternalState(controller,"gameManager"),times(1)).fluxRemoverMove(fluxRemoverRequest2.selectedDice,fluxRemoverRequest2.chosenValue);
        verify((GameManager) Whitebox.getInternalState(controller,"gameManager"),times(1)).fluxRemoverMove(fluxRemoverRequest3.selectedDice,fluxRemoverRequest3.rowIndex,fluxRemoverRequest3.columnIndex);
        verify((GameManager) Whitebox.getInternalState(controller,"gameManager"),times(1)).fluxRemoverMove();
    }

    @Test
    void toolCardMove4() throws RemoteException {
        GrindingStoneRequest grindingStoneRequest = new GrindingStoneRequest(mock(Dice.class));
        controller.toolCardMove(grindingStoneRequest);
        verify((GameManager) Whitebox.getInternalState(controller,"gameManager"),times(1)).grindingStoneMove(grindingStoneRequest.selectedDice);

    }

    @Test
    void toolCardMove5() throws RemoteException {
        CopperFoilBurnisherRequest copperFoilBurnisherRequest = new CopperFoilBurnisherRequest(mock(Tuple.class),mock(Tuple.class));
        controller.toolCardMove(copperFoilBurnisherRequest);
        verify((GameManager) Whitebox.getInternalState(controller,"gameManager"),times(1)).copperFoilBurnisherMove(copperFoilBurnisherRequest.dicePosition,copperFoilBurnisherRequest.position);

    }

    @Test
    void toolCardMove6() throws RemoteException {
        CorkBackedStraightedgeRequest corkBackedStraightedgeRequest = new CorkBackedStraightedgeRequest(mock(Dice.class),1,1);
        controller.toolCardMove(corkBackedStraightedgeRequest);
        verify((GameManager) Whitebox.getInternalState(controller,"gameManager"),times(1)).corkBackedStraightedgeMove(corkBackedStraightedgeRequest.selectedDice,corkBackedStraightedgeRequest.row,corkBackedStraightedgeRequest.column);

    }

    @Test
    void toolCardMove7() throws RemoteException {
        LensCutterRequest lensCutterRequest = new LensCutterRequest(1,"DiceOne","DiceTwo");
        controller.toolCardMove(lensCutterRequest);
        verify((GameManager) Whitebox.getInternalState(controller,"gameManager"),times(1)).lensCutterMove(lensCutterRequest.roundIndex,lensCutterRequest.roundTrackDice,lensCutterRequest.poolDice);
    }

    @Test
    void toolCardMove8() throws RemoteException {
        EglomiseBrushRequest eglomiseBrushRequest = new EglomiseBrushRequest(mock(Tuple.class),mock(Tuple.class));
        controller.toolCardMove(eglomiseBrushRequest);
        verify((GameManager) Whitebox.getInternalState(controller,"gameManager"),times(1)).eglomiseBrushMove(eglomiseBrushRequest.dicePosition,eglomiseBrushRequest.position);

    }

    @Test
    void toolCardMove9() throws RemoteException {
        LathekinRequest lathekinRequest = new LathekinRequest(mock(Tuple.class),mock(Tuple.class),true);
        controller.toolCardMove(lathekinRequest);
        verify((GameManager) Whitebox.getInternalState(controller,"gameManager"),times(1)).lathekinMove(lathekinRequest.dicePosition,lathekinRequest.position,lathekinRequest.doubleMove);
    }

    @Test
    void toolCardMove10() throws RemoteException {
        RunningPliersRequest runningPliersRequest = new RunningPliersRequest(mock(Dice.class),1,1);
        controller.toolCardMove(runningPliersRequest);
        verify((GameManager) Whitebox.getInternalState(controller,"gameManager"),times(1)).runningPliersMove(runningPliersRequest.selectedDice,runningPliersRequest.rowIndex,runningPliersRequest.ColumnIndex);
    }


    @Test
    void toolCardMove11() throws RemoteException {
        TapWheelRequest tapWheelRequest = new TapWheelRequest(1);
        controller.toolCardMove(tapWheelRequest);
        verify((GameManager) Whitebox.getInternalState(controller,"gameManager"),times(1)).tapWheelMove(tapWheelRequest.dice,tapWheelRequest.phase,tapWheelRequest.dicePosition,tapWheelRequest.position,tapWheelRequest.doubleMove);
    }
}