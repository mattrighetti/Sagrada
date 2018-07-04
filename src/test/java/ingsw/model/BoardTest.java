package ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.Whitebox;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Board board;
    List<Player> playerlist;

    @BeforeEach
    void setUp(){
        ArrayList publicObjectiveCards = new ArrayList();
        ArrayList toolCards = new ArrayList();
        playerlist = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            User user = new User("player" + i);
            playerlist.add(new Player(user));
        }
        board = new Board(publicObjectiveCards, toolCards);
    }

    @Test
    void setupDiceBag(){
        int[] counterDice = new int[6];
        for (int i = 0; i < 10; i++){

            List<Dice> draftedDice = board.draftDice(playerlist.size());

            for (Dice dice : draftedDice) {

                if (dice.getDiceColor() != null) {

                    switch (dice.getDiceColor()) {
                        case BLANK:
                            counterDice[0]++;
                            break;
                        case BLUE:
                            counterDice[1]++;
                            break;
                        case GREEN:
                            counterDice[2]++;
                            break;
                        case PURPLE:
                            counterDice[3]++;
                            break;
                        case RED:
                            counterDice[4]++;
                            break;
                        case YELLOW:
                            counterDice[5]++;
                            break;
                    }
                }
            }
        }

        assertEquals(0, counterDice[0]);
        for (int j = 1; j < 6; j++) assertEquals(18, counterDice[j]);
    }

    @Test
    void draftDice() {
        List<Dice> draftedDice = board.draftDice(playerlist.size());
        assertEquals( (playerlist.size() * 2) + 1, draftedDice.size());
        for (Dice dice : draftedDice) {
            assertNotEquals(null, dice.getFaceUpValue());
        }

    }

    @Test
    void draftOneDice() {
        List<Dice> diceBag = (List<Dice>) Whitebox.getInternalState(board,"diceBag");
        int oldSize = diceBag.size();

        Dice dice = board.draftOneDice();

        diceBag = (List<Dice>) Whitebox.getInternalState(board,"diceBag");

        assertNotEquals(0,dice.getFaceUpValue());
        assertEquals(oldSize - 1, diceBag.size());
    }

    @Test
    void addDiceToBag() {
        List<Dice> diceBag = (List<Dice>) Whitebox.getInternalState(board,"diceBag");
        int oldSize = diceBag.size();

        Dice dice = new Dice(Color.BLUE);
        dice.roll();
        board.addDiceToBag(dice);

        diceBag = (List<Dice>) Whitebox.getInternalState(board,"diceBag");

        assertEquals(oldSize + 1, diceBag.size());
        assertTrue(diceBag.contains(dice));
    }

    @Test
    void setgetDraftedDice() {
        List<Dice> diceList = new ArrayList<>();
        diceList.add(new Dice(Color.YELLOW));
        diceList.add(new Dice(Color.RED));
        diceList.add(new Dice(Color.GREEN));
        diceList.add(new Dice(Color.PURPLE));

        board.setDraftedDice(diceList);
        boolean verify = true;
        for (Dice dice : board.getDraftedDice()) {
            if (!diceList.contains(dice)){
                verify = false;
                break;
            }
        }
        assertTrue(verify);
    }
}