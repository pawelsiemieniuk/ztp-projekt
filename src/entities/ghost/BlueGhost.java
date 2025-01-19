package entities.ghost;

import com.googlecode.lanterna.TextColor;

import entities.Pacman;
import entities.ghost.behavior.Ambush;
import entities.ghost.behavior.IBehavior;
import entities.ghost.behavior.Run;
import game.IEventListener;
import maze.Field;
import maze.Side;

public class BlueGhost implements IEventListener, IGhost {
    private TextColor color;
    private boolean hostile;
    private IBehavior behavior;
    
    private int value = 100;
    
    public BlueGhost() {
        this.color = TextColor.ANSI.BLUE_BRIGHT;
        this.hostile = true;
        this.behavior = new Ambush();
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


	@Override
	public IBehavior getBehavior() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int Kill() {
		return value;
	}
}
