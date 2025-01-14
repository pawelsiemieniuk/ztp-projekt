package entities;

import java.util.ArrayList;
import java.util.List;

public class SingleColorGhostCreationStrategy implements GhostCreationStrategy {
    private GhostColor color;

    public SingleColorGhostCreationStrategy(GhostColor color) {
        this.color = color;
    }

    @Override
    public List<Ghost> createGhosts(int numberOfGhosts) {
        List<Ghost> ghosts = new ArrayList<>();
        for (int i = 0; i < numberOfGhosts; i++) {
            ghosts.add(new Ghost(color));
        }
        return ghosts;
    }
}
