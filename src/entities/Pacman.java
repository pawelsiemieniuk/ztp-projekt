package entities;

public class Pacman {
    private static Pacman pacman; 
    private int x; 
    private int y; 

    public Pacman(int x, int y) {
        this.x = x;
        this.y = y;
        System.out.println("Pacman instance created at position (" + x + ", " + y + ")!");
    }

    // Metoda Singleton do uzyskania instancji Pacmana
    public static Pacman GetPacman(int x, int y) {
        if (pacman == null) {
            pacman = new Pacman(x, y);
        }
        return pacman;
    }

    public static Pacman GetPacman() {
        if (pacman == null) {
            throw new IllegalStateException("Pacman has not been initialized. Call GetPacman(int x, int y) first.");
        }
        return pacman;
    }

    public void move(String direction) {
        switch (direction.toLowerCase()) {
            case "up":
                y--;
                break;
            case "down":
                y++;
                break;
            case "left":
                x--;
                break;
            case "right":
                x++;
                break;
            default:
                System.out.println("Invalid direction. Use up, down, left, or right.");
                return;
        }
        System.out.println("Pacman moves " + direction + " to position (" + x + ", " + y + ")");
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
