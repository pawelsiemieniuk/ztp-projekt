package maze;

import entities.ghost.IGhost;
import entities.ghost.BlueGhost;
import entities.ghost.OrangeGhost;
import entities.ghost.PinkGhost;
import entities.ghost.RedGhost;
import entities.Pacman;

import java.util.ArrayList;

import cookie.BasicCookie;
import cookie.Cookie;
import cookie.FruitCookie;
import cookie.PowerCookie;

public class Maze {
    private Field[][] fields;  // Siatka pól
    private Field pacmanField = null; // Aktualna pozycja Pacmana
    private ArrayList<Field> ghostsFields = new ArrayList<Field>();

    private int mazeWidth  = 0;
    private int mazeHeight = 0;
    
    public Maze() {
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
    	IGhost red 	  = new RedGhost();
    	IGhost blue   = new BlueGhost();
    	IGhost pink   = new PinkGhost();
    	IGhost orange = new OrangeGhost();
    	
    	Pacman pacman = Pacman.getPacman();
    	
    	Cookie basic = new BasicCookie(20);
    	Cookie fruit = new FruitCookie(new BasicCookie(20), 100);
    	Cookie power = new PowerCookie(new BasicCookie(20), 100);
    	
    	fields[9][10].placeOnField(red);
    	fields[10][10].placeOnField(blue);
    	fields[11][10].placeOnField(pink);
    	fields[12][10].placeOnField(orange);
    	
    	ghostsFields.add(fields[9][10]);
    	ghostsFields.add(fields[10][10]);
    	ghostsFields.add(fields[11][10]);
    	ghostsFields.add(fields[12][10]);
    	
    	PlacePacman(pacman, 11, 13);
    	
    	fields[10][7].placeOnField(basic);
    	fields[11][7].placeOnField(fruit);
    	fields[12][7].placeOnField(power);
    	
    	
    	fields[9][11]  = new Field(9, 11, true);
    	fields[10][11] = new Field(10, 11, true);
    	fields[11][11] = new Field(11, 11, true);
    	fields[12][11] = new Field(12, 11, true);
    	
    	fields[0][10] = new Field(0,10);
    	fields[width-1][10] = new Field(width-1,10);
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

        Field nextField = null;
        int curFieldPosX = currentField.getX(),
        	curFieldPosY = currentField.getY(),
        	nxtFieldPosX,
        	nxtFieldPosY;
        switch(side) {
        	case UP:
        		nxtFieldPosX = curFieldPosX;
        		nxtFieldPosY = (curFieldPosY - 1 < 0) ? mazeHeight - 1 : curFieldPosY - 1;
        		nextField = fields[nxtFieldPosX][nxtFieldPosY];
        		break;
        	case DOWN:
        		nxtFieldPosX = curFieldPosX;
        		nxtFieldPosY = (curFieldPosY + 1 >= mazeHeight) ? 0 : curFieldPosY + 1;
        		nextField = fields[nxtFieldPosX][nxtFieldPosY];
        		break;
        	case LEFT:
        		nxtFieldPosX = (curFieldPosX -1 < 0) ? mazeWidth - 1 : curFieldPosX - 1;
        		nxtFieldPosY = curFieldPosY;
        		nextField = fields[nxtFieldPosX][nxtFieldPosY];
        		break;
        	case RIGHT:
        		nxtFieldPosX = (curFieldPosX + 1 >= mazeWidth) ? 0 : curFieldPosX + 1;
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
        pacmanField.removePacman(); 					// Usuwa Pacmana z bieżącego pola
        pacmanField = nextField; 						// Aktualizuje pozycję Pacmana

        System.out.println("Pacman moved to: (" + nextField.getX() + ", " + nextField.getY() + ")");
        return fieldsToUpdate;
    }
    
    public ArrayList<Field> MoveGhosts() {
		ArrayList<Field> fieldsToUpdate = new ArrayList<Field>();
		ArrayList<Field> ghostsFieldsToRemove = new ArrayList<Field>();
		ArrayList<Field> ghostsFieldsToAdd = new ArrayList<Field>();
		System.out.println(ghostsFields.toString());
    	for(Field ghostField : ghostsFields) {
    		if(!ghostField.hasGhost()) {
    			throw new IllegalStateException("Ghost field has no ghost.");
    		}
    		Side moveDirection = ghostField.getGhost().getNextMove(fields);
    		
    		Field nextField = CheckoutField(ghostField, moveDirection);
    		if(nextField == null || nextField.hasGhost()) {
    			System.out.println("Stupid " + ghostField.getGhost().getColor().toString() + " ghost hitting a wall or another ghost");
    			continue;
    		}
    		
    		
    		Boolean ghostMoved = nextField.placeOnField(ghostField.getGhost());	// Dodanie ducha do nowego pola
    		if(ghostMoved) {
	    		ghostField.removeGhost();										// Usunięcie ducha z poprzedniego pola
	    		
	    		ghostsFieldsToRemove.add(ghostField);
	    		ghostsFieldsToAdd.add(nextField);
	    		
				fieldsToUpdate.add(ghostField);
				fieldsToUpdate.add(nextField);
    		}
    	}
    	for(Field fieldToRemove : ghostsFieldsToRemove) {
    		ghostsFields.remove(fieldToRemove);
    	}
    	for(Field fieldToAdd : ghostsFieldsToAdd) {
    		ghostsFields.add(fieldToAdd);
    	}
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
