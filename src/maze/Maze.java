package maze;

import entities.ghost.*;
import entities.Pacman;
import cookie.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Maze {
	private final static int BASIC_COOKIE_VALUE = 10,
							 FRUIT_COOKIE_VALUE = 50,
							 POWER_COOKIE_VALUE = 100;
	
	private int PACMAN_START_POSITION_X 		= 9,
			    PACMAN_START_POSITION_Y 		= 15,
			    RED_GHOST_START_POSITION_X 	 	= 8,
			    BLUE_GHOST_START_POSITION_X 	= 9,
			    PINK_GHOST_START_POSITION_X   	= 10,
			    ORANGE_GHOST_START_POSITION_X 	= 11,
			    GHOST_START_POSITION_Y 		 	= 9;
	
	
    private Field[][] fields;
    private Field pacmanField = null;
    private ArrayList<Field> ghostsFields = new ArrayList<>();
    private int mazeWidth = 0;
    private int mazeHeight = 0;
    Random random = new Random(); 
    
    public Maze() {}

    public void GenerateMaze(int width, int height) {
    	GenerateMaze();
    }
    
    public void GenerateMaze() {
        //mazeWidth = width;
        //mazeHeight = height;
        //fields = new Field[width][height];
        int[][] classicMazeLayout = {
    		{1,1,1,1,1,1,1,9,1,9,1,9,1,1,1,1,1,1,1,1,1},
    		{1,0,8,0,0,0,1,9,1,9,1,9,1,0,0,8,1,0,0,0,1},
    		{1,0,1,0,1,0,1,9,1,9,1,9,1,0,1,0,0,0,1,0,1},
    		{1,0,1,0,1,0,1,1,1,9,1,1,1,0,1,1,1,0,1,0,1},
    		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
    		{1,0,1,0,1,1,1,1,1,9,1,1,1,0,1,0,1,1,1,0,1},
    		{1,0,1,0,0,0,1,9,9,9,9,9,0,0,1,0,0,0,1,0,1},
    		{1,0,1,0,1,0,1,9,1,1,1,9,1,0,1,0,1,0,1,0,1},
    		{1,0,0,0,1,0,0,9,1,3,1,9,1,0,0,0,1,0,0,0,1},
    		{1,1,1,0,1,1,1,9,9,4,1,7,1,1,1,2,1,1,1,0,1},
    		{1,1,1,0,1,1,1,9,9,5,1,9,1,1,1,0,1,1,1,0,1},
    		{1,0,0,0,1,0,0,9,1,6,1,9,1,0,0,0,1,0,0,0,1},
    		{1,0,1,0,1,0,1,9,1,1,1,9,1,0,1,0,1,0,1,0,1},
    		{1,0,1,0,0,0,1,9,9,9,9,9,0,0,1,0,0,0,1,0,1},
    		{1,0,1,0,1,1,1,1,1,9,1,1,1,0,1,0,1,1,1,0,1},
    		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
    		{1,0,1,0,1,0,1,1,1,9,1,1,1,0,1,1,1,0,1,0,1},
    		{1,0,1,0,1,0,1,9,1,9,1,9,1,0,1,0,0,0,1,0,1},
    		{1,0,8,0,0,0,1,9,1,9,1,9,1,0,0,8,1,0,0,0,1},
    		{1,1,1,1,1,1,1,9,1,9,1,9,1,1,1,1,1,1,1,1,1}//,
    		//{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        };
    	/*
        int[][] classicMazeLayout = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,0,1},
			{1,0,0,0,0,1,0,0,0,1,1,0,0,0,1,0,0,0,0,1},
			{1,1,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,1,1},
			{9,9,9,1,0,1,0,0,0,0,0,0,0,0,1,0,1,9,9,9},
			{1,1,1,1,0,1,0,1,1,0,0,1,1,0,1,0,1,1,1,1},
			{0,0,0,0,0,0,0,1,3,4,5,6,1,0,0,0,0,0,0,0},
			{1,1,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,1,1},
			{0,0,0,1,0,1,0,0,0,0,0,0,0,0,1,0,1,0,0,0},
			{1,1,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,1,1},
			{1,0,0,1,0,0,0,0,0,2,0,0,0,0,0,0,1,0,0,1},
			{1,1,0,1,0,1,0,1,1,1,1,1,1,0,1,0,1,0,1,1},
			{1,0,0,0,0,1,0,0,0,1,1,0,0,0,1,0,0,0,0,1},
			{1,0,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
        */
        
        mazeWidth = 20;
        mazeHeight = 21;
        fields = new Field[20][21];
        

        for (int y = 0; y < mazeHeight; y++) {
            for (int x = 0; x < mazeWidth; x++) {
            	if(classicMazeLayout[x][y] == 1) {
            		fields[x][y] = new Field(x, y, true);
            	} else {
            		fields[x][y] = new Field(x, y);
            	}
                switch(classicMazeLayout[x][y]) {
                	case 0:
                		fields[x][y].placeCookie(new BasicCookie(BASIC_COOKIE_VALUE));
                		break;
                	case 7:
                		fields[x][y].placeCookie(new FruitCookie(new BasicCookie(BASIC_COOKIE_VALUE), FRUIT_COOKIE_VALUE));
                		break;
                	case 8:
                		fields[x][y].placeCookie(new PowerCookie(new BasicCookie(BASIC_COOKIE_VALUE), POWER_COOKIE_VALUE));
                		break;
	            	case 2:
	            		fields[x][y].placePacman(Pacman.getPacman());
	            		pacmanField = fields[x][y];
	            		break;
	            	case 3:
	            		fields[x][y].placeGhost(new RedGhost());
	            		ghostsFields.add(fields[x][y]);
	            		break;
	            	case 4:
	            		fields[x][y].placeGhost(new BlueGhost());
	            		ghostsFields.add(fields[x][y]);
	            		break;
	            	case 5:
	            		fields[x][y].placeGhost(new PinkGhost());
	            		ghostsFields.add(fields[x][y]);
	            		break;
	            	case 6:
	            		fields[x][y].placeGhost(new OrangeGhost());
	            		ghostsFields.add(fields[x][y]);
	            		break;
	            	case 9: 
	            		// Empty field
	            		break;
            	}
            }
        }

        ResetPacmanPosition();
        ResetGhostsPosition();
    }

	public Field[][] getFields() {
        return fields;
    }

    public void PlacePacman(Pacman pacman, int startX, int startY) {
        Field startField = fields[startX][startY];
        startField.placePacman(pacman);
        pacmanField = startField;
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
        if (nextField == null || nextField.isWall()) {
            return null;
        }

        return nextField;
    }

    public Field[] ResetPacmanPosition() {
    	Field prevField = pacmanField;
    	Field nextField;
    	
    	Pacman pacman = pacmanField.getPacman();
    	pacman.resetMouthState();
    	pacmanField.removePacman();
    	
    	nextField = fields[PACMAN_START_POSITION_X][PACMAN_START_POSITION_Y] = new Field(PACMAN_START_POSITION_X, PACMAN_START_POSITION_Y);
    	PlacePacman(pacman, PACMAN_START_POSITION_X, PACMAN_START_POSITION_Y);
    	
    	Field[] fieldsToUpdate = { prevField, nextField };
    	
    	return fieldsToUpdate;
    }

    public ArrayList<Field> ResetGhostsPosition() {
		ArrayList<Field> fieldsToUpdate = new ArrayList<Field>();
		ArrayList<Field> ghostsFieldsToRemove = new ArrayList<Field>();
		ArrayList<Field> ghostsFieldsToAdd = new ArrayList<Field>();

    	for(Field ghostField : ghostsFields) {
    		if(!ghostField.hasGhost()) {
    			throw new IllegalStateException("Ghost field has no ghost.");
    		}
    		
    		int newGhostFieldX = 0;
    		int newGhostFieldY = GHOST_START_POSITION_Y;
    		IGhost ghost = ghostField.getGhost();
    		
    		if(ghost instanceof RedGhost) 		  { newGhostFieldX = RED_GHOST_START_POSITION_X; }
    		else if(ghost instanceof BlueGhost)   { newGhostFieldX = BLUE_GHOST_START_POSITION_X; }
    		else if(ghost instanceof PinkGhost)   { newGhostFieldX = PINK_GHOST_START_POSITION_X; }
    		else if(ghost instanceof OrangeGhost) { newGhostFieldX = ORANGE_GHOST_START_POSITION_X; }
    		
    		Field newField = new Field(newGhostFieldX, newGhostFieldY);
    		fields[newGhostFieldX][newGhostFieldY] = newField;
    		
    		newField.placeOnField(ghost);

    		ghostField.removeGhost();										// Usunięcie ducha z poprzedniego pola
    		
    		ghostsFieldsToRemove.add(ghostField);
    		ghostsFieldsToAdd.add(newField);
    		
			fieldsToUpdate.add(ghostField);
			fieldsToUpdate.add(newField);
    	}
    	for(Field fieldToRemove : ghostsFieldsToRemove) {
    		ghostsFields.remove(fieldToRemove);
    	}
    	for(Field fieldToAdd : ghostsFieldsToAdd) {
    		ghostsFields.add(fieldToAdd);
    	}
		return fieldsToUpdate;
    }
    
    // Ruch Pacmana w określonym kierunku
    public Field[] MovePacman(Side side) {
        if (pacmanField == null) {
            throw new IllegalStateException("Pacman is not in the maze.");
        }
        
        Pacman.getPacman().setFacingSide(side);
        Pacman.getPacman().switchMouthState();

        Field nextField = CheckoutField(pacmanField, side);
        if (nextField == null) {
            Field[] fieldsToUpdate = {};
            return fieldsToUpdate;
        }
        
        Field[] fieldsToUpdate = { pacmanField, nextField };
        
        nextField.placePacman(Pacman.getPacman()); // Przenosi Pacmana na nowe pole
        pacmanField.removePacman(); 					// Usuwa Pacmana z bieżącego pola
        pacmanField = nextField; 						// Aktualizuje pozycję Pacmana

        return fieldsToUpdate;
    }
    
 // Rozmieszczanie duchów w losowych miejscach
    /*private void placeGhostsRandomly() {
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
    }*/
    
    public ArrayList<Field> MoveGhosts() {
		ArrayList<Field> fieldsToUpdate = new ArrayList<Field>();
		ArrayList<Field> ghostsFieldsToRemove = new ArrayList<Field>();
		ArrayList<Field> ghostsFieldsToAdd = new ArrayList<Field>();
		
    	for(Field ghostField : ghostsFields) {
    		if(!ghostField.hasGhost()) {
    			throw new IllegalStateException("Ghost field has no ghost.");
    		}
    		Side moveDirection = ghostField.getGhost().getNextMove(ghostField, fields);
    		if (moveDirection == null) {
    		    continue;
    		}
    		
    		Field nextField = CheckoutField(ghostField, moveDirection);
    		if(nextField == null || nextField.hasGhost()) {
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
    
    public ArrayList<IGhost> getGhosts() {
    	ArrayList<IGhost> ghosts = new ArrayList<IGhost>();
    	for(Field field : ghostsFields) {
    		ghosts.add(field.getGhost());
    	}
    	return ghosts;
    }
}
