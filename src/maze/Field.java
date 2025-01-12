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

    public Field() {
        this.neighbourField = new HashMap<>();
        this.pacman = null;
        this.ghost = null;
        this.cookie = null;
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

    public void setNeighbour(Side side, Field field) {
        neighbourField.put(side, field);
    }

    public Field getNeighbour(Side side) {
        return neighbourField.getOrDefault(side, getWallField());
    }

    public static Field getWallField() {
        return WALL_FIELD;
    }

    public void placePacman(Pacman pacman) {
        this.pacman = pacman;
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
}
