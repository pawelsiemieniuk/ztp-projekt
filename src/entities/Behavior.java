package entities;

import maze.Field;
import maze.Side;

public interface Behavior {
    void move(IGhost ghost);
    public Side CalculateNextMove(IGhost ghost, Field[][] fields);
}
