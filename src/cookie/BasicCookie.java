package cookie;

public class BasicCookie implements ICookie {
    private int value; 

    public BasicCookie(int value) {
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
    	return value;
    }
}
