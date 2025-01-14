package entities.ghost;

import entities.ghost.behavior.IBehavior;
import entities.ghost.behavior.RunBehavior;
import game.EventListener;
import maze.Field;
import maze.Side;

public class PinkGhost implements EventListener, IGhost {
    private GhostColor color;
    private boolean hostile;
    private IBehavior behavior;
    //private int x; // Pozycja X ducha
    //private int y; // Pozycja Y ducha

    //public Ghost(GhostColor color, int x, int y) {
    public PinkGhost(GhostColor color) {
        this.color = color;
        //this.x = x;
        //this.y = y;
        this.hostile = true;
        this.behavior = new RunBehavior(); // Domyślne zachowanie
    }

    public Side getNextMove(Field[][] fields) {
        System.out.println("Ghost of color " + color + " is moving.");
        return behavior.CalculateNextMove(this, fields); // Przekazujemy referencję do ducha
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

    public GhostColor getColor() {
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

	
	public RedGhost createGhost(GhostColor color, IBehavior behavior) {
		// TODO Auto-generated method stub
		return null;
	}
}
