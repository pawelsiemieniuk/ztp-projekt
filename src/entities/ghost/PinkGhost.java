package entities.ghost;

import com.googlecode.lanterna.TextColor;

import entities.ghost.behavior.IBehavior;
import entities.ghost.behavior.RunBehavior;
import game.EventListener;
import maze.Field;
import maze.Side;

public class PinkGhost implements EventListener, IGhost {
    private TextColor color;
    private boolean hostile;
    private IBehavior behavior;

    public PinkGhost() {
        this.color 	  = TextColor.ANSI.MAGENTA_BRIGHT;
        this.hostile  = true;
        this.behavior = new RunBehavior();
    }

    public Side getNextMove(Field[][] fields) {
        System.out.println("Ghost of color " + color + " is moving.");
        return behavior.CalculateNextMove(this, fields);
    }

    public void setBehavior(IBehavior newBehavior) {
        this.behavior = newBehavior;
        System.out.println("Behavior updated to: " + newBehavior.getClass().getSimpleName());
    }

    public Boolean isHostile() {
        return hostile;
    }

    public void setHostility(boolean hostile) {
        this.hostile = hostile;
    }

    public TextColor getColor() {
    	return this.color;
    }

    @Override
    public void update(String data) {
        if (data.equals("PLAYER_GOT_POWER")) {
            System.out.println("Player ate power cookie. Ghost is running randomly!");
            setHostility(false);
        } else if (data.equals("PLAYER_LOST_POWER")) {
            System.out.println("Player lost power. Ghost is back to chasing player.");
            setHostility(true);
        } else {
            System.out.println("Unknown event received: " + data);
        }
    }
}
