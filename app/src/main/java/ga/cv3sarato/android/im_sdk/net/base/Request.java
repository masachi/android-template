package ga.cv3sarato.android.im_sdk.net.base;

public class Request<T> {
    private T body;

    public Request(T body) {
        this.body = body;
    }

    public Request() {
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
