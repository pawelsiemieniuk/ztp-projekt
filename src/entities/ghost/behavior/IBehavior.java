package entities.ghost.behavior;

import entities.ghost.IGhost;
import maze.Field;
import maze.Side;

public interface IBehavior {
    public Side CalculateNextMove(IGhost ghost, Field ghostField, Field[][] fields);
}
