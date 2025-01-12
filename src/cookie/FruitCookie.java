package cookie;

public class FruitCookie extends ExtraCookie {
    private int value;
    public FruitCookie(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void Eat() {
        System.out.println("You ate a FruitCookie and gained " + value + " points!");
        applyPoints();
    }

    private void applyPoints() {
        System.out.println("Points added to the player's score: " + value);
    }
}
