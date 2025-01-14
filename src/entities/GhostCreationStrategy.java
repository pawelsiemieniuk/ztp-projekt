package entities;

import java.util.List;

public interface GhostCreationStrategy {
    List<RedGhost> createGhosts(int numberOfGhosts); 
}
