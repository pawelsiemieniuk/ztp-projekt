package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGhostCreationStrategy implements GhostCreationStrategy {
    private static final GhostColor[] COLORS = GhostColor.values();
    private Random random = new Random();

    @Override
    public List<RedGhost> createGhosts(int numberOfGhosts) {
        List<RedGhost> ghosts = new ArrayList<>();
        for (int i = 0; i < numberOfGhosts; i++) {
            GhostColor randomColor = COLORS[random.nextInt(COLORS.length)];
            ghosts.add(new RedGhost(randomColor));
        }
        return ghosts;
    }
}
