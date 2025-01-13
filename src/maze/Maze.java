package maze;

import entities.Pacman;

public class Maze {
    private Field pacmanField; 

    public Maze() {
        this.pacmanField = new Field(); 
    }

    public Field CheckoutField(Side side) {
        if (pacmanField == null) {
            throw new IllegalStateException("Pacman is not in the maze.");
        }
        return pacmanField.getNeighbour(side);
    }

    public Field MovePacman(Side side) {
        if (pacmanField == null) {
            throw new IllegalStateException("Pacman is not in the maze.");
        }

        Field nextField = CheckoutField(side); 

        if (nextField == Field.getWallField()) {
            System.out.println("Pacman hit a wall and cannot move.");
            return pacmanField; 
        }

        pacmanField.removePacman(); 
        nextField.placePacman(new Pacman(0, 0));
        pacmanField = nextField;

        System.out.println("Pacman moved to the new field.");
        return pacmanField;
    }
}
