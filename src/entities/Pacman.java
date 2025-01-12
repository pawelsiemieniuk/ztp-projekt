package entities;

public class Pacman {
    private static Pacman pacman;

    public Pacman() {
        System.out.println("Pacman instance created!");
    }

    public static Pacman GetPacman() {
        if (pacman == null) {
            pacman = new Pacman();
        }
        return pacman;
    }

    public void move(String direction) {
        System.out.println("Pacman moves " + direction);
    }
}
