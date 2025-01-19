package maze;

import java.util.Map;

import cookie.BasicCookie;
import cookie.ICookie;
import cookie.FruitCookie;
import cookie.PowerCookie;
import entities.ghost.IGhost;
import entities.Pacman;

public class Field {
    private Map<Side, Field> neighbourField; 
    private Pacman pacman;                  
    private IGhost ghost;                   
    private ICookie cookie;
    private Boolean isWall;

    private int x;  // Dodane dla współpracy z klasą Maze
    private int y;  // Dodane dla współpracy z klasą Maze

    public Field(int x, int y) {
        this(x, y, false);
    }

    public Field(int x, int y, Boolean isWall) {
        this.x = x;
        this.y = y;
        this.pacman = null;
        this.ghost  = null;
        this.cookie = null;
        this.isWall = isWall;
    }

    public Boolean placeOnField(Object object) {
        Boolean objPlaced = false;
        if (object.getClass().equals(Pacman.class)) {
            placePacman((Pacman) object);
            objPlaced = true;
        } else if (object instanceof IGhost) {
            placeGhost((IGhost) object);
            objPlaced = true;
        } else if (object instanceof ICookie) {
            placeCookie((ICookie) object);
            objPlaced = true;
        }
        return objPlaced;
    }

    public Boolean hasGhost() {
        return ghost != null;
    }

    public IGhost getGhost() {
        return ghost;
    }

    public Boolean hasCookie() {
        return cookie != null;
    }

    public ICookie getCookie() {
        return cookie;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public void setNeighbour(Side side, Field field) {
        neighbourField.put(side, field);
    }

    public Field getNeighbour(Side side) {
        return neighbourField.getOrDefault(side, null);
    }

    public Boolean isWall() {
        return isWall;
    }

    public void placePacman(Pacman pacman) {
        // Sprawdź, czy na polu jest duch
        /*if (hasGhost()) {
            System.out.println("Pacman encountered a ghost at field (" + x + ", " + y + "). Losing a life.");
            pacman.loseLife(); // Pacman traci życie
        }*/

        // Umieść Pacmana na polu
        if (this.pacman != null) {
            System.out.println("Pacman is already on this field.");
            return;
        }
        this.pacman = pacman;
    }

    public void removePacman() {
        this.pacman = null;
    }

    public boolean hasPacman() {
        return pacman != null;
    }

    public void placeGhost(IGhost ghost) {
        this.ghost = ghost;
    }

    public void removeGhost() {
        this.ghost = null;
    }

    public void placeCookie(ICookie cookie) {
        this.cookie = cookie;
    }

    public void removeCookie() {
        this.cookie = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Field(" + x + ", " + y + ")";
    }
}
