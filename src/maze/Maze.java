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

    private int mazeWidth  = 0;
    private int mazeHeight = 0;
    
    // Konstruktor inicjalizujący labirynt
    public Maze() {//Field[][] fields) {
        //this.fields = fields;
        this.pacmanField = null; // Pacman na początku nie jest w labiryncie
    }
    
    public void GenerateMaze(int width, int height) {
    	mazeWidth  = width;
    	mazeHeight = height;
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
    	
    	//fields[11][13].placeOnField(pacman);
    	PlacePacman(pacman, 11, 13);
    	
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
    public void PlacePacman(Pacman pacman, int startX, int startY) {
        if (startX < 0 || startY < 0 || startY >= fields.length || startX >= fields[0].length) {
            throw new IllegalArgumentException("Invalid start position for Pacman.");
        }

        Field startField = fields[startX][startY];
        if (startField.hasPacman()) {
            throw new IllegalStateException("Pacman is already on the field.");
        }

        startField.placePacman(pacman); // Umieszcza Pacmana na starcie
        pacmanField = startField;      // Aktualizuje referencję do aktualnego pola Pacmana
    }

    // Pobiera sąsiednie pole w podanym kierunku
    public Field CheckoutField(Field currentField, Side side) {
        if (currentField == null) {
            throw new IllegalArgumentException("Current field cannot be null.");
        }

        //Field nextField = currentField.getNeighbour(side);
        Field nextField = null;
        int curFieldPosX = currentField.getX(),
        	curFieldPosY = currentField.getY(),
        	nxtFieldPosX,
        	nxtFieldPosY;
        switch(side) {
        	case UP:
        		nxtFieldPosX = curFieldPosX;
        		nxtFieldPosY = curFieldPosY - 1;
        		nextField = fields[nxtFieldPosX][nxtFieldPosY];
        		break;
        	case DOWN:
        		nxtFieldPosX = curFieldPosX;
        		nxtFieldPosY = curFieldPosY + 1;
        		nextField = fields[nxtFieldPosX][nxtFieldPosY];
        		break;
        	case LEFT:
        		nxtFieldPosX = curFieldPosX - 1;
        		nxtFieldPosY = curFieldPosY;
        		nextField = fields[nxtFieldPosX][nxtFieldPosY];
        		break;
        	case RIGHT:
        		nxtFieldPosX = curFieldPosX + 1;
        		nxtFieldPosY = curFieldPosY;
        		nextField = fields[nxtFieldPosX][nxtFieldPosY];
        		break;
        }
        if (nextField == null || nextField.isWall()) {
            System.out.println("Wall field detected. Pacman cannot move there.");
            return null;
        }
        return nextField;
    }

    // Ruch Pacmana w określonym kierunku
    public Field[] MovePacman(Side side) {
        if (pacmanField == null) {
            throw new IllegalStateException("Pacman is not in the maze.");
        }

        Field nextField = CheckoutField(pacmanField, side);
        if (nextField == null) {
            System.out.println("Pacman cannot move to the next field. A wall is blocking the way.");
            Field[] fieldsToUpdate = {};
            return fieldsToUpdate;
        }
        
        Field[] fieldsToUpdate = { pacmanField, nextField };
        
        nextField.placePacman(pacmanField.getPacman()); // Przenosi Pacmana na nowe pole
        pacmanField.removePacman(); // Usuwa Pacmana z bieżącego pola
        pacmanField = nextField; // Aktualizuje pozycję Pacmana

        System.out.println("Pacman moved to: (" + nextField.getX() + ", " + nextField.getY() + ")");
        return fieldsToUpdate;
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
