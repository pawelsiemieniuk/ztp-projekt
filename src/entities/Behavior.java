package entities;

import maze.Field;
import maze.Side;

public interface Behavior {
    void move(Ghost ghost);
    public Side CalculateNextMove(Ghost ghost, Field[][] fields);
}
