package entities;

public class AggressiveBehavior implements Behavior {
    
    public void move(Ghost ghost) {
        // Zakładamy, że znamy pozycję Pacmana (przykładowo: 5, 5)
        int pacmanX = 5;
        int pacmanY = 5;

        int ghostX = ghost.getX();
        int ghostY = ghost.getY();

        // Porusz ducha w kierunku Pacmana
        if (ghostX < pacmanX) {
            ghost.setX(ghostX + 1);
        } else if (ghostX > pacmanX) {
            ghost.setX(ghostX - 1);
        }

        if (ghostY < pacmanY) {
            ghost.setY(ghostY + 1);
        } else if (ghostY > pacmanY) {
            ghost.setY(ghostY - 1);
        }

        System.out.println("Ghost moved aggressively to (" + ghost.getX() + ", " + ghost.getY() + ")");
    }
}
