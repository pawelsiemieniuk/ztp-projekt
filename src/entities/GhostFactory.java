package entities;

import java.util.List;


public interface GhostFactory {
    Ghost createGhost(GhostColor color, Behavior behavior);
}
