package entities;

import game.EventListener;

public class Ghost implements EventListener {
    private GhostColor color;
    private boolean hostile; 
    private Behavior behavior; 

    public Ghost(GhostColor color) {
        this.color = color;
        this.hostile = false;
        this.behavior = new DefaultBehavior();
    }

    public void move() {
        System.out.println("Ghost of color " + color + " is moving.");
        behavior.move();
    }

    public void setBehavior(Behavior newBehavior) {
        this.behavior = newBehavior;
        System.out.println("Behavior updated to: " + newBehavior.getClass().getSimpleName());
    }

    public Boolean isHostile() {
        return hostile;
    }

    public void setHostility(boolean hostile) {
        this.hostile = hostile;
    }

    @Override
    public void update(String data) {
        if (data.equals("PLAYER_NEAR")) {
            System.out.println("Player detected near. Ghost is becoming hostile!");
            setHostility(true);
        } else if (data.equals("PLAYER_FAR")) {
            System.out.println("Player moved away. Ghost is calm now.");
            setHostility(false);
        } else {
            System.out.println("Unknown event received: " + data);
        }
    }
}

interface Behavior {
    void move();
}

class DefaultBehavior implements Behavior {
    @Override
    public void move() {
        System.out.println("Moving randomly in the environment.");
    }
}

