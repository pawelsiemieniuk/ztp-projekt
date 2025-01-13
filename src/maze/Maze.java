package maze;

import entities.Ghost;
import entities.GhostColor;
import entities.Pacman;
import cookie.BasicCookie;
import cookie.Cookie;
import cookie.FruitCookie;
import cookie.PowerCookie;

public class Maze {
    private Field[][] fields;  // Siatka pól
    private Field pacmanField; // Aktualna pozycja Pacmana

    // Konstruktor inicjalizujący labirynt
    public Maze() {//Field[][] fields) {
        //this.fields = fields;
        this.pacmanField = null; // Pacman na początku nie jest w labiryncie
    }
    
    public void GenerateMaze(int width, int height) {
        this.fields = new Field[width][height];
    	for(int x = 0; x < width; x++) {
    		for(int y = 0; y < height; y++) {
    			if(x == 0 || y == 0 || x == width - 1 || y == height - 1) {
    				fields[x][y] = new Field(x, y, true);
    			} else {
    				fields[x][y] = new Field(x, y);
    			}
    		}
    	}
    	Ghost red 	 = new Ghost(GhostColor.RED);
    	Ghost blue 	 = new Ghost(GhostColor.BLUE);
    	Ghost pink 	 = new Ghost(GhostColor.PINK);
    	Ghost orange = new Ghost(GhostColor.ORANGE);
    	
    	Pacman pacman = Pacman.getPacman();
    	
    	Cookie basic = new BasicCookie(20);
    	Cookie fruit = new FruitCookie(new BasicCookie(20), 100);
    	Cookie power = new PowerCookie(new BasicCookie(20), 100);
    	
    	fields[9][10].placeOnField(red);
    	fields[10][10].placeOnField(blue);
    	fields[11][10].placeOnField(pink);
    	fields[12][10].placeOnField(orange);
    	
    	fields[11][13].placeOnField(pacman);
    	
    	fields[10][7].placeOnField(basic);
    	fields[11][7].placeOnField(fruit);
    	fields[12][7].placeOnField(power);
    	
    	fields[9][11]  = new Field(9, 11, true);
    	fields[10][11] = new Field(10, 11, true);
    	fields[11][11] = new Field(11, 11, true);
    	fields[12][11] = new Field(12, 11, true);
    }
    
    public Field[][] getFields() {
    	return fields;
    }

    // Umieszcza Pacmana w początkowej pozycji
    public void placePacman(Pacman pacman, int startX, int startY) {
        if (startX < 0 || startY < 0 || startY >= fields.length || startX >= fields[0].length) {
            throw new IllegalArgumentException("Invalid start position for Pacman.");
        }

        Field startField = fields[startY][startX];
        if (startField.hasPacman()) {
            throw new IllegalStateException("Pacman is already on the field.");
        }

        startField.placePacman(pacman); // Umieszcza Pacmana na starcie
        pacmanField = startField;      // Aktualizuje referencję do aktualnego pola Pacmana
    }

    // Pobiera sąsiednie pole w podanym kierunku
    public Field checkoutField(Field currentField, Side side) {
        if (currentField == null) {
            throw new IllegalArgumentException("Current field cannot be null.");
        }

        Field nextField = currentField.getNeighbour(side);
        if (nextField.isWall()) {
            System.out.println("Wall field detected. Pacman cannot move there.");
            return null;
        }
        return nextField;
    }

    // Ruch Pacmana w określonym kierunku
    public Field movePacman(Side side) {
        if (pacmanField == null) {
            throw new IllegalStateException("Pacman is not in the maze.");
        }

        Field nextField = checkoutField(pacmanField, side);
        if (nextField == null || nextField.hasGhost()) {
            System.out.println("Pacman cannot move to the next field. Either a wall or a ghost is blocking the way.");
            return pacmanField;
        }

        pacmanField.removePacman(); // Usuwa Pacmana z bieżącego pola
        nextField.placePacman(pacmanField.getPacman()); // Przenosi Pacmana na nowe pole
        pacmanField = nextField; // Aktualizuje pozycję Pacmana

        System.out.println("Pacman moved to: (" + nextField.getX() + ", " + nextField.getY() + ")");
        return pacmanField;
    }

    // Wypisuje stan labiryntu dla debugowania
    public void printMazeState() {
        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields[y].length; x++) {
                Field field = fields[y][x];
                if (field == pacmanField) {
                    System.out.print("P "); // Pole z Pacmanem
                } else if (field.hasGhost()) {
                    System.out.print("G "); // Pole z duchem
                } else if (field.hasCookie()) {
                    System.out.print("C "); // Pole z ciasteczkiem
                } else {
                    System.out.print(". "); // Puste pole
                }
            }
            System.out.println();
        }
    }
}
