package entities.ghost.behavior;

import entities.ghost.IGhost;
import maze.Field;
import maze.Side;

public class Ambush implements IBehavior {
    @Override
    public Side CalculateNextMove(IGhost ghost, Field[][] fields) {
        Field ghostField = ghost.getCurrentField();
        Field pacmanField = findPacman(fields);
        Side pacmanDirection = findPacmanDirection(fields);

        if (ghostField == null || pacmanField == null || pacmanDirection == null) {
            // Jeśli dane są niepełne, duch pozostaje w miejscu
            return null;
        }

        // Oblicz pozycję, w którą Pacman może się udać (3 pola naprzód)
        int targetX = pacmanField.getX();
        int targetY = pacmanField.getY();

        switch (pacmanDirection) {
            case UP:
                targetY = Math.max(0, targetY - 3);
                break;
            case DOWN:
                targetY = Math.min(fields[0].length - 1, targetY + 3);
                break;
            case LEFT:
                targetX = Math.max(0, targetX - 3);
                break;
            case RIGHT:
                targetX = Math.min(fields.length - 1, targetX + 3);
                break;
        }

        // Oblicz różnicę w pozycji celu względem ducha
        int ghostX = ghostField.getX();
        int ghostY = ghostField.getY();

        int deltaX = targetX - ghostX;
        int deltaY = targetY - ghostY;

        // Wybierz kierunek, który zbliża ducha do celu
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            // Priorytet na osi X
            if (deltaX > 0 && isValidMove(fields, ghostX + 1, ghostY)) {
                return Side.RIGHT;
            } else if (deltaX < 0 && isValidMove(fields, ghostX - 1, ghostY)) {
                return Side.LEFT;
            }
        }

        // Priorytet na osi Y, jeśli ruch w osi X jest niemożliwy
        if (deltaY > 0 && isValidMove(fields, ghostX, ghostY + 1)) {
            return Side.DOWN;
        } else if (deltaY < 0 && isValidMove(fields, ghostX, ghostY - 1)) {
            return Side.UP;
        }

        // Jeśli żaden ruch nie jest możliwy
        return null;
    }

    // Znajduje pole, na którym znajduje się Pacman
    private Field findPacman(Field[][] fields) {
        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields[y].length; x++) {
                if (fields[y][x].hasPacman()) {
                    return fields[y][x];
                }
            }
        }
        return null;
    }

    // Znajduje aktualny kierunek ruchu Pacmana
    private Side findPacmanDirection(Field[][] fields) {
        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields[y].length; x++) {
                Field field = fields[y][x];
                if (field.hasPacman() && field.getPacman().getDirection() != null) {
                    return (Side) field.getPacman().getDirection();
                }
            }
        }
        return null;
    }

    // Sprawdza, czy pole docelowe jest dostępne
    private boolean isValidMove(Field[][] fields, int x, int y) {
        if (x < 0 || y < 0 || x >= fields.length || y >= fields[0].length) {
            return false; // Poza granicami labiryntu
        }
        return !fields[x][y].isWall() && !fields[x][y].hasGhost();
    }
}
