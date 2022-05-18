package server;

import java.io.Serializable;

public class Reaction implements Serializable {
    String type;
    private int point;

    private int count;

    public Reaction(String type, int point) {
        this.type = type;
        this.point = point;
        this.count = 0;
    }

    public int getReactionPoint() {
        return this.point;
    }

    public void increment() { this.count++; }
}
