package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGhostCreationStrategy implements GhostCreationStrategy {
    private static final GhostColor[] COLORS = GhostColor.values();
    private Random random = new Random();

    @Override
    public List<Ghost> createGhosts(int numberOfGhosts) {
        List<Ghost> ghosts = new ArrayList<>();
        for (int i = 0; i < numberOfGhosts; i++) {
            GhostColor randomColor = COLORS[random.nextInt(COLORS.length)];
            ghosts.add(new Ghost(randomColor));
        }
        return ghosts;
    }
}
