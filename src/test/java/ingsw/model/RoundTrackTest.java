package ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoundTrackTest {

    RoundTrack roundTrack;

    @BeforeEach
    void setUp() {
        Set<Dice> diceSet = new HashSet<>();
        diceSet.add(new Dice(Color.GREEN));
        diceSet.add(new Dice(Color.BLUE));
        List<Round> rounds = new LinkedList<>();
        rounds.add(new Round(diceSet));
        roundTrack = new RoundTrack(rounds);
    }

    @Test
    void linkedListNotNullTest() {
        assertNotNull(roundTrack.getRounds());
        assertEquals(1 , roundTrack.getRounds().size());
    }
}