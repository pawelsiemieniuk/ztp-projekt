package entities.ghost;

import com.googlecode.lanterna.TextColor;

import entities.ghost.behavior.IBehavior;
import entities.ghost.behavior.RunBehavior;
import game.EventListener;
import maze.Field;
import maze.Side;

public class RedGhost implements EventListener, IGhost {
    private TextColor color;
    private boolean hostile;
    private IBehavior behavior;

    public RedGhost() {
        this.color 	  = TextColor.ANSI.RED;
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

    private void setHostility(boolean hostile) {
        this.hostile = hostile;
    }

    public TextColor getColor() {
    	return this.color;
    }


    public void update(String data) {
        if (data.equals("PLAYER_GOT_POWER")) {
            System.out.println("Player ate power cookie. Ghost is running randomly!");
            setHostility(true);
        } else if (data.equals("PLAYER_LOST_POWER")) {
            System.out.println("Player lost power. Ghost is back to chasing player.");
            setHostility(false);
        } else {
            System.out.println("Unknown event received: " + data);
        }
    }
}