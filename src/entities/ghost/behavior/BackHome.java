package entities.ghost.behavior;

import entities.ghost.IGhost;
import maze.Field;
import maze.Side;

import java.util.LinkedList;
import java.util.Queue;

public class BackHome implements IBehavior {
    @Override
    public Side CalculateNextMove(IGhost ghost, Field[][] fields) {
        Field ghostField = ghost.getCurrentField();
        Field homeField = ghost.getCurrentField();

        if (ghostField == null || homeField == null) {
            return null; // Jeśli dane są niepełne, brak ruchu
        }

        // Znajdź najkrótszą ścieżkę do pozycji domowej
        Field nextField = findShortestPath(ghostField, homeField, fields);

        if (nextField == null) {
            return null; // Jeśli nie znaleziono ścieżki
        }

        // Oblicz kierunek, w którym duch musi się poruszyć
        return determineDirection(ghostField, nextField);
    }

    private Field findShortestPath(Field start, Field target, Field[][] fields) {
        // Kolejka do BFS
        Queue<Field> queue = new LinkedList<>();
        queue.add(start);

        // Mapa odwiedzonych pól
        boolean[][] visited = new boolean[fields.length][fields[0].length];
        visited[start.getX()][start.getY()] = true;

        // Mapa poprzedników (do śledzenia ścieżki)
        Field[][] predecessors = new Field[fields.length][fields[0].length];

        while (!queue.isEmpty()) {
            Field current = queue.poll();

            // Jeśli dotarliśmy do celu, odtwórz ścieżkę
            if (current.equals(target)) {
                return reconstructPath(predecessors, start, target);
            }

            // Przetwarzaj sąsiednie pola
            for (Side side : Side.values()) {
                Field neighbor = getNeighbor(current, side, fields);

                if (neighbor != null && !visited[neighbor.getX()][neighbor.getY()] && !neighbor.isWall()) {
                    queue.add(neighbor);
                    visited[neighbor.getX()][neighbor.getY()] = true;
                    predecessors[neighbor.getX()][neighbor.getY()] = current;
                }
            }
        }

        return null; // Jeśli nie znaleziono ścieżki
    }

    private Field reconstructPath(Field[][] predecessors, Field start, Field target) {
        Field current = target;
        Field previous = null;

        // Rekonstrukcja ścieżki od końca do początku
        while (current != null && !current.equals(start)) {
            previous = current;
            current = predecessors[current.getX()][current.getY()];
        }

        return previous; // Zwraca pierwsze pole na ścieżce
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

    private Side determineDirection(Field current, Field next) {
        if (next.getX() == current.getX()) {
            if (next.getY() < current.getY()) {
                return Side.UP;
            } else {
                return Side.DOWN;
            }
        } else if (next.getY() == current.getY()) {
            if (next.getX() < current.getX()) {
                return Side.LEFT;
            } else {
                return Side.RIGHT;
            }
        }

        return null;
    }
}
