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
    public int Eat() {
    	return value + super.Eat();
    }
}
