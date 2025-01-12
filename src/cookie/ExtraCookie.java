package cookie;

public abstract class ExtraCookie implements Cookie {
    private Cookie wrappedCookie;

    public ExtraCookie(Cookie cookie) {
        this.wrappedCookie = cookie;
    }

    public void CookieDecorator(Cookie cookie) {
        this.wrappedCookie = cookie;
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
}
