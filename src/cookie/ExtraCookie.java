package cookie;

public abstract class ExtraCookie implements Cookie {
    private Cookie wrappedCookie;

    public ExtraCookie(Cookie wrappedCookie) {
        this.wrappedCookie = wrappedCookie;
    }

    @Override
    public void Eat() {
        if (wrappedCookie != null) {
            wrappedCookie.Eat();
        }
    }

    public Cookie getWrappedCookie() {
        return wrappedCookie;
    }

    public void setWrappedCookie(Cookie wrappedCookie) {
        this.wrappedCookie = wrappedCookie;
    }
}
