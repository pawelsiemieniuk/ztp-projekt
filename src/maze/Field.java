package maze;

import java.util.HashMap;
import java.util.Map;

import cookie.Cookie;
import entities.Ghost;
import entities.Pacman;

public class Field {
    private Map<Side, Field> neighbourField; 
    private Pacman pacman;                  
    private Ghost ghost;                   
    private Cookie cookie;               
    private static final Field WALL_FIELD = new Field();
    private int x;  // Dodane dla współpracy z klasą Maze
    private int y;  // Dodane dla współpracy z klasą Maze

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
        this.neighbourField = new HashMap<>();
        this.pacman = null;
        this.ghost = null;
        this.cookie = null;
    }

    private Field() {
        this(-1, -1);
    }

    public Boolean hasGhost() {
        return ghost != null;
    }

    public Ghost getGhost() {
        return ghost;
    }

    public Boolean hasCookie() {
        return cookie != null;
    }

    public Cookie getCookie() {
        return cookie;
    }
    // Getter dla Pacmana
    public Pacman getPacman() {
        return pacman;
    } 
    
    public void setNeighbour(Side side, Field field) {
        neighbourField.put(side, field);
    }

    public Field getNeighbour(Side side) {
        return neighbourField.getOrDefault(side, getWallField());
    }

    public static Field getWallField() {
        return WALL_FIELD;
    }


    public void placePacman(Pacman b) {
        if (this.pacman != null) {
            System.out.println("Pacman is already on this field.");
            return;
        }
        this.pacman = b;
    }

    public void removePacman() {
        this.pacman = null;
    }

    public boolean hasPacman() {
        return pacman != null;
    }

    public void placeGhost(Ghost ghost) {
        this.ghost = ghost;
    }

    public void removeGhost() {
        this.ghost = null;
    }

    public void placeCookie(Cookie cookie) {
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
