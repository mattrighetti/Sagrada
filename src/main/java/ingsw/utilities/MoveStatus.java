package ingsw.utilities;

import java.io.Serializable;

/**
 * Class that has a username and a move, used to track the match history
 * and a status which is the join of the union  of the previous two fields
 */
public class MoveStatus implements Serializable {
    private String username;
    private String move;
    private String status;

    /**
     * Create a new MoveStatus
     * @param username Player
     * @param move Move
     */
    public MoveStatus(String username, String move) {
        this.username = username;
        this.move = move;
        this.status = username + " " + move;
    }

    /**
     * Get the player username
     * @return playe username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the player move
     * @return playe move
     */
    public String getMove() {
        return move;
    }

    /**
     * Get the player status
     * @return playe status
     */
    @SuppressWarnings("unused")
    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return  " - " + username + " " + move + "\n";
    }
}
