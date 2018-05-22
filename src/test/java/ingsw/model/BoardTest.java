package ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Board board;

    @BeforeEach
    void setUp(){
        HashSet publicObjectiveCards = new HashSet();
        HashSet toolCards = new HashSet();
        ArrayList<Player> playerlist = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            User user = new User("player" + i);
            playerlist.add(new Player(user));
        }
        board = new Board(publicObjectiveCards, toolCards, playerlist);
    }

    @Test
    void setupDiceBag(){
        int[] counterDice = new int[6];
        for (int i = 0; i < 18; i++){

            List<Dice> draftedDice = board.draftDice();

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
        List<Dice> draftedDice = board.draftDice();
        assertEquals(5, draftedDice.size());
        for (Dice dice : draftedDice) {
            assertNotEquals(null, dice.getFaceUpValue());
        }

    }
}