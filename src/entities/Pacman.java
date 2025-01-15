package entities;

public class Pacman {
    private static volatile Pacman pacman;
    
    private Boolean openMouth = true;
    private int lives = 3;
    private int score = 0;
    
    private Pacman() {
    }
    
    public static Pacman getPacman() {
        if (pacman == null) {
            synchronized(Pacman.class) {
                if (pacman == null) {
                	pacman = new Pacman();
                }
            }
        }
        return pacman;
    }
    
    public void switchMouthState() {
    	openMouth = !openMouth;
    }
    
    public Boolean isMouthOpen() {
    	return openMouth;
    }
    
    public void setLives(int lives) {
    	this.lives = lives;
    }
    public int getLives() {
    	return lives;
    }
    
    public void setScore(int score) {
    	this.score = score;
    }
    public int getScore() {
    	return score;
    }
    /*private Pacman(Field startField, Maze maze) {
        this.currentField = startField;
        this.maze = maze;
        System.out.println("Pacman instance created at starting field (" + startField.getX() + ", " + startField.getY() + ")");
    }

    // Singleton 
    public static Pacman GetPacman(Field startField, Maze maze) {
        if (pacman == null) {
            pacman = new Pacman(startField, maze);
        }
        return pacman;
    }
    
    public void move(Side direction) {
        Field nextField = maze.checkoutField(currentField, direction);
        if (nextField != null) {
            if (!nextField.hasGhost()) {
                currentField.removePacman(); 
                nextField.placePacman(this);  
                currentField = nextField;  
                System.out.println("Pacman moves " + direction + " to position (" + currentField.getX() + ", " + currentField.getY() + ")");
            } else {
                System.out.println("Pacman cannot move: Ghost is in the way!");
            }
        } else {
            System.out.println("Pacman cannot move: Wall or out of bounds.");
        }
    }

    public Field getCurrentField() {
        return currentField;
    }

    public int getX() {
        return currentField.getX();
    }

    public int getY() {
        return currentField.getY();
    }*/
}
