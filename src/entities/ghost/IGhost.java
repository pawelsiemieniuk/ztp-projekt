package entities.ghost;
import entities.ghost.behavior.IBehavior;

import com.googlecode.lanterna.TextColor;

import maze.Field;
import maze.Side;

public interface IGhost {
    Side getNextMove(Field ghostField, Field[][] fields); // Decyzja o ruchu
    void setBehavior(IBehavior behavior); // Ustawianie zachowania
    IBehavior getBehavior(); // Pobieranie aktualnego zachowania
	TextColor getColor();
	
	public int Kill();
}
