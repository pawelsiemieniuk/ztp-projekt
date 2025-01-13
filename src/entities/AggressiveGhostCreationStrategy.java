package entities;

import java.util.ArrayList;
import java.util.List;

public class AggressiveGhostCreationStrategy implements GhostCreationStrategy {
    @Override
    public List<Ghost> createGhosts(int numberOfGhosts) {
        List<Ghost> ghosts = new ArrayList<>();
        for (int i = 0; i < numberOfGhosts; i++) {
            Ghost ghost = new Ghost(GhostColor.RED); // Zakładamy, że agresywne duchy są czerwone
            ghost.setBehavior(new AggressiveBehavior());
            ghost.setHostility(true);
            ghosts.add(ghost);
        }
        return ghosts;
    }
}
