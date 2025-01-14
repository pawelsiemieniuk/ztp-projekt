package maze;

import java.util.Map;

import cookie.BasicCookie;
import cookie.Cookie;
import cookie.FruitCookie;
import cookie.PowerCookie;
import entities.ghost.RedGhost;
import entities.Pacman;

public class Field {
    private Map<Side, Field> neighbourField; 
    private Pacman pacman;                  
    private RedGhost ghost;                   
    private Cookie cookie;
    
    private Boolean isWall;
    //private static final Field WALL_FIELD = new Field();
    private int x;  // Dodane dla współpracy z klasą Maze
    private int y;  // Dodane dla współpracy z klasą Maze

    public Field(int x, int y) {
    	this(x, y, false);
    }

    public Field(int x, int y, Boolean isWall) {
        this.x = x;
        this.y = y;
        //this.neighbourField = new HashMap<>();
        this.pacman = null;
        this.ghost  = null;
        this.cookie = null;
        this.isWall = isWall;
    }
    
    public Boolean placeOnField(Object object) {
    	Boolean objPlaced = false;
    	if(object.getClass().equals(Pacman.class)) {
    		placePacman((Pacman)object);
    		objPlaced = true;
    	} else if(object.getClass().equals(RedGhost.class)) {
    		placeGhost((RedGhost)object);
    		objPlaced = true;
    	} else if(object.getClass().equals(BasicCookie.class)
    			  || object.getClass().equals(FruitCookie.class)
    			  || object.getClass().equals(PowerCookie.class)) {
    		placeCookie((Cookie)object);
    		objPlaced = true;
    	}
    	return objPlaced;
    }

    public Boolean hasGhost() {
        return ghost != null;
    }

    public RedGhost getGhost() {
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
    	return neighbourField.getOrDefault(side, null);
    	//return neighbourField.getOrDefault(side, getWallField());
    }
    /*
    public static Field getWallField() {
        return WALL_FIELD;
    }*/
    
    public Boolean isWall() {
    	return isWall;
    };

    public void placePacman(Pacman pacman) {
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

    public void placeGhost(RedGhost ghost) {
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
