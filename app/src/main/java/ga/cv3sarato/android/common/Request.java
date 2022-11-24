package ga.cv3sarato.android.common;


import java.util.HashMap;

public class Request<T> {

    public Request(T body, HashMap<String, String> secure) {
        this.body = body;
        this.secure = secure;
    }

    public Request(T body) {
        this.body = body;
    }

    public Request() {
    }

    private T body;
    private HashMap<String, String> secure = null;

    public void setSecure(HashMap<String, String> secure) {
        this.secure = secure;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public T getBody() {
        return body;
    }
}
