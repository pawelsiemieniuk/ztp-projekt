package entities.ghost;

import java.util.List;

import entities.ghost.behavior.IBehavior;


public interface IGhost {
    IGhost createGhost(GhostColor color, IBehavior behavior);
}
