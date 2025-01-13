package entities;

import java.util.List;

public class GhostFactory {
    private GhostCreationStrategy strategy;

    public GhostFactory(GhostCreationStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(GhostCreationStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Ghost> createGhosts(int numberOfGhosts) {
        return strategy.createGhosts(numberOfGhosts);
    }
}
