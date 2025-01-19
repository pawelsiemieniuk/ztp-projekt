package entities.ghost;
import entities.Pacman;
import entities.ghost.behavior.IBehavior;

import com.googlecode.lanterna.TextColor;

import maze.Field;
import maze.Side;

public interface IGhost {
    Side getNextMove(Field[][] fields); // Decyzja o ruchu
    void setBehavior(IBehavior behavior); // Ustawianie zachowania
    IBehavior getBehavior(); // Pobieranie aktualnego zachowania
    Field getCurrentField(); // Aktualne pole ducha
    //void setCurrentField(Field field); // Ustawianie pola ducha
    Pacman getPacman();
	TextColor getColor();
	void setPacman(Pacman pacman);
	
	public void Kill();
}
