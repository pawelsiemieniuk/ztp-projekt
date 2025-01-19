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
    private IBehavior behavior;
    
    private int value = 100;
    
    private boolean isHostile;
    private boolean isDead;
    
    public RedGhost() {
        this.color = TextColor.ANSI.RED_BRIGHT;
        this.isHostile = true;
        this.behavior = new Chase();
    }

    public Side getNextMove(Field ghostField, Field[][] fields) {
        //System.out.println("Ghost of color " + color + " is moving.");
        return behavior.CalculateNextMove(this, ghostField, fields);
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
        return isHostile;
    }
    public Boolean isDead() {
    	return isDead;
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
    
    public int Kill() {
    	isDead = true;
    	return value;
    }
}
