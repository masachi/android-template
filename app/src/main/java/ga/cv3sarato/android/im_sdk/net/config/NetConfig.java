package ga.cv3sarato.android.im_sdk.net.config;

public class NetConfig {
    protected String baseURL;
    protected int timeout;

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
