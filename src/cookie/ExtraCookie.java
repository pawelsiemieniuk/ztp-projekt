package cookie;

public abstract class ExtraCookie implements ICookie {
    private ICookie wrappedCookie;

    public ExtraCookie(ICookie wrappedCookie) {
        this.wrappedCookie = wrappedCookie;
    }

    @Override
    public void Eat() {
        if (wrappedCookie != null) {
            wrappedCookie.Eat();
        }
    }

    public ICookie getWrappedCookie() {
        return wrappedCookie;
    }

    public void setWrappedCookie(ICookie wrappedCookie) {
        this.wrappedCookie = wrappedCookie;
    }
}
