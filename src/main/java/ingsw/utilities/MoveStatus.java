package ingsw.utilities;

import java.io.Serializable;

public class MoveStatus implements Serializable {
    private String username;
    private String move;
    private String status;

    public MoveStatus(String username, String move, String status) {
        this.username = username;
        this.move = move;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public String getMove() {
        return move;
    }

    public String getStatus() {
        return status;
    }
}
