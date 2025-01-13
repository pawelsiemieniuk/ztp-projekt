package entities;

import java.util.List;

public interface GhostCreationStrategy {
    List<Ghost> createGhosts(int numberOfGhosts); 
}
