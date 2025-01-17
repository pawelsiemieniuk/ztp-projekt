package entities.ghost.behavior;

import entities.ghost.IGhost;
import maze.Field;
import maze.Side;

public class ChaseBehavior implements IBehavior {
    @Override
    public Side CalculateNextMove(IGhost ghost, Field[][] fields) {
        Field ghostField = ghost.getCurrentField();
        Field pacmanField = findPacman(fields);

        if (ghostField == null || pacmanField == null) {
            // Jeśli nie można znaleźć ducha lub Pacmana, pozostaje w miejscu
            return null;
        }

        int ghostX = ghostField.getX();
        int ghostY = ghostField.getY();
        int pacmanX = pacmanField.getX();
        int pacmanY = pacmanField.getY();

        // Oblicz różnicę w pozycjach
        int deltaX = pacmanX - ghostX;
        int deltaY = pacmanY - ghostY;

        // Wybierz kierunek na podstawie różnic
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            // Priorytet w kierunku poziomym
            if (deltaX > 0 && isValidMove(fields, ghostX + 1, ghostY)) {
                return Side.RIGHT;
            } else if (deltaX < 0 && isValidMove(fields, ghostX - 1, ghostY)) {
                return Side.LEFT;
            }
        }

        // Jeśli pionowy ruch jest bardziej optymalny lub poziomy ruch niemożliwy
        if (deltaY > 0 && isValidMove(fields, ghostX, ghostY + 1)) {
            return Side.DOWN;
        } else if (deltaY < 0 && isValidMove(fields, ghostX, ghostY - 1)) {
            return Side.UP;
        }

        // W ostateczności spróbuj kierunków alternatywnych
        if (deltaX > 0 && isValidMove(fields, ghostX + 1, ghostY)) {
            return Side.RIGHT;
        } else if (deltaX < 0 && isValidMove(fields, ghostX - 1, ghostY)) {
            return Side.LEFT;
        }

        // Jeśli brak możliwości ruchu
        return null;
    }

    // Metoda znajduje pole z Pacmanem w labiryncie
    private Field findPacman(Field[][] fields) {
        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields[y].length; x++) {
                if (fields[y][x].hasPacman()) {
                    return fields[y][x];
                }
            }
        }
        return null; // Pacman nie znaleziony
    }

    // Sprawdza, czy ruch na pole (x, y) jest możliwy
    private boolean isValidMove(Field[][] fields, int x, int y) {
        if (x < 0 || y < 0 || x >= fields.length || y >= fields[0].length) {
            return false; // Poza granicami labiryntu
        }
        return !fields[x][y].isWall() && !fields[x][y].hasGhost();
    }
}
