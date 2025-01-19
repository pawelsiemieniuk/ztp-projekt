package cookie;

public class PowerCookie extends ExtraCookie {
    private int value; 

    public PowerCookie(ICookie wrappedCookie, int value) {
        super(wrappedCookie);
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
        System.out.println("You ate a PowerCookie and gained a bonus of " + value + "!");
        applyEffect();
    }

    private void applyEffect() {
        System.out.println("Effect applied: Player receives a bonus of " + value);
    }
}
