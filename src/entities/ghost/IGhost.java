package entities.ghost;

import com.googlecode.lanterna.TextColor;

import maze.Field;
import maze.Side;

public interface IGhost {
	public TextColor getColor();
	public Side getNextMove(Field[][] fields);
}
