package cookie;

public abstract class ExtraCookie implements ICookie {
    private ICookie wrappedCookie;

    public ExtraCookie(ICookie wrappedCookie) {
        this.wrappedCookie = wrappedCookie;
    }

    @Override
    public int Eat() {
        if (wrappedCookie != null) {
            return wrappedCookie.Eat();
        }
        return 0;
    }

    public ICookie getWrappedCookie() {
        return wrappedCookie;
    }

    public void setWrappedCookie(ICookie wrappedCookie) {
        this.wrappedCookie = wrappedCookie;
    }
}
