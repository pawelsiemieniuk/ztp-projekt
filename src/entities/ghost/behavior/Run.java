package entities.ghost.behavior;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entities.ghost.IGhost;
import maze.Field;
import maze.Side;

public class Run implements IBehavior {
    @Override
    public Side CalculateNextMove(IGhost ghost, Field[][] fields) {
        Field ghostField = ghost.getCurrentField();
        Field pacmanField = null;
        if (ghost.getPacman() != null) {
            pacmanField = ghost.getPacman().getCurrentField();
        }

        if (ghostField == null || pacmanField == null) {
            return getRandomSide(); // Jeśli dane są niepełne, wykonaj losowy ruch
        }


        // Znajdź możliwe ruchy, które są legalne (nie prowadzą na ścianę)
        List<Side> legalMoves = getLegalMoves(ghostField, fields);

        if (legalMoves.isEmpty()) {
            return null; // Brak możliwych ruchów
        }

        // Wybierz ruch, który oddala ducha od Pacmana
        Side bestMove = null;
        double maxDistance = -1;

        for (Side side : legalMoves) {
            Field nextField = getNeighbor(ghostField, side, fields);
            double distanceToPacman = calculateDistance(nextField, pacmanField);

            if (distanceToPacman > maxDistance) {
                maxDistance = distanceToPacman;
                bestMove = side;
            }
        }

        return bestMove != null ? bestMove : getRandomSide(); // Jeśli nie można się oddalić, losowy ruch
    }

    private List<Side> getLegalMoves(Field currentField, Field[][] fields) {
        List<Side> legalMoves = new ArrayList<>();

        for (Side side : Side.values()) {
            Field neighbor = getNeighbor(currentField, side, fields);
            if (neighbor != null && !neighbor.isWall()) {
                legalMoves.add(side);
            }
        }

        return legalMoves;
    }

    private Field getNeighbor(Field field, Side side, Field[][] fields) {
        int x = field.getX();
        int y = field.getY();

        switch (side) {
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
        }

        // Sprawdź, czy sąsiad jest w granicach
        if (x >= 0 && y >= 0 && x < fields.length && y < fields[0].length) {
            return fields[x][y];
        }

        return null;
    }

    private double calculateDistance(Field field1, Field field2) {
        int dx = field1.getX() - field2.getX();
        int dy = field1.getY() - field2.getY();
        return Math.sqrt(dx * dx + dy * dy); // Odległość euklidesowa
    }

    private Side getRandomSide() {
        Side[] sides = Side.values();
        Random rand = new Random();
        return sides[rand.nextInt(sides.length)];
    }
}
