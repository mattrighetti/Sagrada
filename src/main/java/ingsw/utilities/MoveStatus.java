package ingsw.utilities;

import java.io.Serializable;

public class MoveStatus implements Serializable {
    private String username;
    private String move;
    private String status;

    public MoveStatus(String username, String move) {
        this.username = username;
        this.move = move;
        this.status = username + " " + move;
    }

    public String getUsername() {
        return username;
    }

    public String getMove() {
        return move;
    }

    @SuppressWarnings("unused")
    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return  " - " + username + " " + move + "\n";
    }
}
