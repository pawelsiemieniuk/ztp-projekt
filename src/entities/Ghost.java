package entities;

import game.EventListener;

public class Ghost implements EventListener {
    private GhostColor color;
    private boolean hostile;
    private Behavior behavior;
    private int x; // Pozycja X ducha
    private int y; // Pozycja Y ducha

    public Ghost(GhostColor color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.hostile = false;
        this.behavior = new DefaultBehavior(); // Domyślne zachowanie
    }

    public void move() {
        System.out.println("Ghost of color " + color + " is moving.");
        behavior.move(this); // Przekazujemy referencję do ducha
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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
