package entities;

public class SimpleGhostFactory implements GhostFactory {

    @Override
    public Ghost createGhost(GhostColor color, Behavior behavior) {
        Ghost ghost = new Ghost(color);
        ghost.setBehavior(behavior);
        return ghost;
    }
}
