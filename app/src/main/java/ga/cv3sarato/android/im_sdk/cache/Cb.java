package ga.cv3sarato.android.im_sdk.cache;



public class Cb {
    private int code = 0;
    private String message = "ok";
    private Object data = null;

    public static int CodeOk = 0;
    public static int CodeNo = 1;

    private Cb(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Cb Ok() {
        return new Cb(Cb.CodeOk, "ok");
    }

    public static Cb No() {
        return new Cb(Cb.CodeNo, "no");
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
