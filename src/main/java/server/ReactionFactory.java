package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReactionFactory implements Serializable {
    public Reaction createReaction(String type) {
        switch (type) {
            case "heart": return new Reaction("heart", 10);
            default: return null;
        }
    }

    public List<String> getReactions() {
        return new ArrayList<>();
    }
}
