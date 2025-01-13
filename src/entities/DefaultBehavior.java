package entities;

import java.util.Random;

public class DefaultBehavior implements Behavior {
    private Random random = new Random();

    public void move(Ghost ghost) {
        int newX = ghost.getX() + random.nextInt(3) - 1; // -1, 0 lub 1
        int newY = ghost.getY() + random.nextInt(3) - 1; // -1, 0 lub 1

        ghost.setX(newX);
        ghost.setY(newY);

        System.out.println("Ghost moved randomly to (" + newX + ", " + newY + ")");
    }
}
