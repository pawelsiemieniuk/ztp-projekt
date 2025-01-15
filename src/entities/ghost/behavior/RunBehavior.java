package entities.ghost.behavior;

import java.util.Random;

import entities.ghost.IGhost;
import maze.Field;
import maze.Side;

public class RunBehavior implements IBehavior {
	public Side CalculateNextMove(IGhost ghost, Field[][] fields) {
		Side[] sides = Side.values();
		Random rand = new Random();

		return sides[rand.nextInt(sides.length)];
	}

}
