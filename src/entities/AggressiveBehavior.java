package entities;

public class AggressiveBehavior implements Behavior {
    @Override
    public void move() {
        System.out.println("The ghost is aggressively chasing Pacman!");
    }
}
