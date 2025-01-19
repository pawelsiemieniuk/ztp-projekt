package entities.ghost;

import com.googlecode.lanterna.TextColor;

import entities.Pacman;
import entities.ghost.behavior.Chase;
import entities.ghost.behavior.IBehavior;
import entities.ghost.behavior.Run;
import game.IEventListener;
import maze.Field;
import maze.Side;

public class RedGhost implements IEventListener, IGhost {
    private TextColor color;
    private boolean hostile;
    private IBehavior behavior;
    private Field currentField;
    private Pacman pacman;

    public RedGhost() {
        this.color = TextColor.ANSI.RED_BRIGHT;
        this.hostile = true;
        this.behavior = new Chase();
    }

    public Side getNextMove(Field[][] fields) {
        System.out.println("Ghost of color " + color + " is moving.");
        return behavior.CalculateNextMove(this, fields);
    }

    public void setBehavior(IBehavior newBehavior) {
        this.behavior = newBehavior;
        System.out.println("Behavior updated to: " + newBehavior.getClass().getSimpleName());
    }
    
    private boolean ignoreCollision = false; // Czy duch ignoruje kolizje z Pac-Manem?

    public boolean isIgnoringCollision() {
        return ignoreCollision;
    }

    public void setIgnoreCollision(boolean ignoreCollision) {
        this.ignoreCollision = ignoreCollision;
    }


    public Boolean isHostile() {
        return hostile;
    }

    public TextColor getColor() {
        return this.color;
    }

    @Override
    public void update(String data) {
        if (data.equals("PLAYER_GOT_POWER")) {
            setBehavior(new Run());
            System.out.println("Player ate power-up. Ghost is running!");
        } else if (data.equals("PLAYER_LOST_POWER")) {
            setBehavior(new Chase());
            System.out.println("Player lost power-up. Ghost is chasing again!");
        }
    }

    @Override
    public Field getCurrentField() {
        return currentField;
    }

    public void setCurrentField(Field field) {
        this.currentField = field;
    }

    @Override
    public Pacman getPacman() {
        return pacman;
    }

    @Override
    public void setPacman(Pacman pacman) {
        this.pacman = pacman;
    }

    @Override
    public IBehavior getBehavior() {
        return behavior;
    }
    
    public void Kill() {
    	hostile = false;
    }
}
