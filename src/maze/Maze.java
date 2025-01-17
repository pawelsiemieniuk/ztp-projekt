package maze;

import entities.ghost.*;
import entities.Pacman;
import cookie.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Maze {
    private Field[][] fields;
    private Field pacmanField = null;
    private ArrayList<Field> ghostsFields = new ArrayList<>();
    private int mazeWidth = 0;
    private int mazeHeight = 0;
    Random random = new Random(); 
    
    public Maze() {}

    public void GenerateMaze(int width, int height) {
        mazeWidth = width;
        mazeHeight = height;
        fields = new Field[width][height];
        
        int[][] classicMazeLayout = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0},
            {0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0},
            {0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0},
            {0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0},
            {0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0},
            {0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0},
            {0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0},
            {0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0},
            {0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0},
            {0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}
        };

        for (int y = 0; y < mazeHeight; y++) {
            for (int x = 0; x < mazeWidth; x++) {
                if (y < classicMazeLayout.length && x < classicMazeLayout[y].length) {
                    boolean isWall = classicMazeLayout[y][x] == 0;
                    fields[x][y] = new Field(x, y, isWall);
                } else {
                    fields[x][y] = new Field(x, y, true); // Domyślnie ściana
                }
            }
        }

        fields[0][height / 2] = new Field(0, height / 2, false);
        fields[width - 1][height / 2] = new Field(width - 1, height / 2, false);

        // Dodawanie Pacmana
        Pacman pacman = Pacman.getPacman();
        PlacePacman(pacman, 1, 1);
     // Przekaż referencję Pacmana do każdego ducha
        for (Field ghostField : ghostsFields) {
            if (ghostField.hasGhost()) {
                ghostField.getGhost().setPacman(pacman);
            }
        }
        placeGhostsRandomly();
      
    }

    private void createPath(Field[][] fields2, int i, int j, int k, int l) {
		// TODO Auto-generated method stub
		
	}

	public Field[][] getFields() {
        return fields;
    }
	private void generatePathsWithDFS(int startX, int startY) {
	    Random random = new Random();
	    ArrayList<int[]> directions = new ArrayList<>();
	    directions.add(new int[] {0, -2}); // UP
	    directions.add(new int[] {0, 2}); // DOWN
	    directions.add(new int[] {-2, 0}); // LEFT
	    directions.add(new int[] {2, 0}); // RIGHT

	    // Startujemy od komórki (startX, startY)
	    fields[startX][startY] = new Field(startX, startY, false);

	    // Mieszamy kierunki dla losowości
	    Collections.shuffle(directions, random);

	    for (int[] direction : directions) {
	        int newX = startX + direction[0];
	        int newY = startY + direction[1];

	        // Sprawdzamy, czy nowa komórka jest w granicach i jest ścianą
	        if (newX > 0 && newX < mazeWidth - 1 && newY > 0 && newY < mazeHeight - 1 && fields[newX][newY].isWall()) {
	            // Tworzymy ścieżkę między bieżącą komórką a nową
	            fields[startX + direction[0] / 2][startY + direction[1] / 2] = new Field(startX + direction[0] / 2, startY + direction[1] / 2, false);
	            fields[newX][newY] = new Field(newX, newY, false);

	            // Rekurencyjne generowanie ścieżek
	            generatePathsWithDFS(newX, newY);
	        }
	    }
	}

    public void PlacePacman(Pacman pacman, int startX, int startY) {
        Field startField = fields[startX][startY];
        startField.placePacman(pacman);
        pacmanField = startField;
    }

    private void placeRandomCookies(int numberOfCookies) {
        Random random = new Random();
        int basicCookiesCount = 0;
        int fruitCookiesCount = 0;
        int powerCookiesCount = 0;

        for (int i = 0; i < numberOfCookies; i++) {
            int x, y;
            do {
                x = random.nextInt(mazeWidth);
                y = random.nextInt(mazeHeight);
            } while (fields[x][y].isWall() || fields[x][y].hasCookie() || fields[x][y].hasPacman() || fields[x][y].hasGhost());

            // Losowanie rodzaju ciasteczka
            int cookieType = random.nextInt(3); // 0 - Basic, 1 - Fruit, 2 - Power
            Cookie cookie;
            switch (cookieType) {
                case 0:
                    cookie = new BasicCookie(10 + random.nextInt(11)); // Wartość 10-20
                    basicCookiesCount++;
                    break;
                case 1:
                    cookie = new FruitCookie(new BasicCookie(20 + random.nextInt(21)), 50 + random.nextInt(51)); // Wartość bazowa 20-40, owoc 50-100
                    fruitCookiesCount++;
                    break;
                case 2:
                    cookie = new PowerCookie(new BasicCookie(30 + random.nextInt(21)), 100 + random.nextInt(101)); // Wartość bazowa 30-50, moc 100-200
                    powerCookiesCount++;
                    break;
                default:
                    cookie = new BasicCookie(10);
                    break;
            }
            fields[x][y].placeOnField(cookie);
        }

        System.out.println("Cookies placed:");
        System.out.println("Basic: " + basicCookiesCount);
        System.out.println("Fruit: " + fruitCookiesCount);
        System.out.println("Power: " + powerCookiesCount);
    }

    
    // Pobiera sąsiednie pole w podanym kierunku
    public Field CheckoutField(Field currentField, Side side) {
        if (currentField == null) {
            throw new IllegalArgumentException("Current field cannot be null.");
        }

        int curFieldPosX = currentField.getX();
        int curFieldPosY = currentField.getY();
        int nxtFieldPosX = curFieldPosX;
        int nxtFieldPosY = curFieldPosY;

        // Oblicz współrzędne następnego pola
        switch (side) {
            case UP:
                nxtFieldPosY = (curFieldPosY - 1 < 0) ? mazeHeight - 1 : curFieldPosY - 1;
                break;
            case DOWN:
                nxtFieldPosY = (curFieldPosY + 1 >= mazeHeight) ? 0 : curFieldPosY + 1;
                break;
            case LEFT:
                nxtFieldPosX = (curFieldPosX - 1 < 0) ? mazeWidth - 1 : curFieldPosX - 1;
                break;
            case RIGHT:
                nxtFieldPosX = (curFieldPosX + 1 >= mazeWidth) ? 0 : curFieldPosX + 1;
                break;
        }

        Field nextField = fields[nxtFieldPosX][nxtFieldPosY];

        // Sprawdź, czy pole jest dostępne
        if (nextField == null || nextField.isWall() || nextField.hasGhost()) {
            System.out.println("Field (" + nxtFieldPosX + ", " + nxtFieldPosY + ") is not accessible.");
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
    
 // Rozmieszczanie duchów w losowych miejscach
    private void placeGhostsRandomly() {
        Random random = new Random();
        String[] ghostColors = {"Red", "Blue", "Pink", "Orange"};
        for (String color : ghostColors) {
            int x, y;
            do {
                x = random.nextInt(mazeWidth);
                y = random.nextInt(mazeHeight);
            } while (fields[x][y].isWall() || fields[x][y].hasCookie() || fields[x][y].hasPacman() || fields[x][y].hasGhost());
            IGhost ghost;
            switch (color) {
                case "Red": ghost = new RedGhost(); break;
                case "Blue": ghost = new BlueGhost(); break;
                case "Pink": ghost = new PinkGhost(); break;
                case "Orange": ghost = new OrangeGhost(); break;
                default: throw new IllegalStateException("Unexpected ghost color: " + color);
            }
            fields[x][y].placeOnField(ghost);
            ghostsFields.add(fields[x][y]);
        }
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
    		if (moveDirection == null) {
    		    System.out.println("Ghost " + ghostField.getGhost().getColor() + " has no valid move.");
    		    continue; // Pomijamy ten duch
    		}
    		
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
