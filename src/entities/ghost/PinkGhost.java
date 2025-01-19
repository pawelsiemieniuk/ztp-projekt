package entities.ghost;

import com.googlecode.lanterna.TextColor;

import entities.Pacman;
import entities.ghost.behavior.Chase;
import entities.ghost.behavior.IBehavior;
import entities.ghost.behavior.Run;
import game.IEventListener;
import maze.Field;
import maze.Side;

public class PinkGhost implements IEventListener, IGhost {
    private TextColor color;
    private boolean hostile;
    private IBehavior behavior;

    private int value = 100;
    
    public PinkGhost() {
        this.color 	  = TextColor.ANSI.MAGENTA_BRIGHT;
        this.hostile  = true;
        this.behavior = new Run();
    }

    public Side getNextMove(Field ghostField, Field[][] fields) {
        //System.out.println("Ghost of color " + color + " is moving.");
        return behavior.CalculateNextMove(this, ghostField, fields);
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

    public void update(String data) {
    	System.out.println(data);
        if (data.equals("PLAYER_GOT_POWER")) {
            System.out.println("Player ate power cookie. Ghost is running randomly!");
            setBehavior(new Run());
            setHostility(false);
        } else if (data.equals("PLAYER_LOST_POWER")) {
            System.out.println("Player lost power. Ghost is back to chasing player.");
            setBehavior(new Chase());
            setHostility(true);
        } else {
            System.out.println("Unknown event received: " + data);
        }
    }

	@Override
	public IBehavior getBehavior() {
		// TODO Auto-generated method stub
		return null;
	}
    public int Kill() {
    	hostile = false;
    	return value;
    }
}
