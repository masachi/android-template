package ga.cv3sarato.android.im_sdk.net.config;

public class PrdConfig extends NetConfig{

    public PrdConfig() {
        baseURL = "https://DOMAIN/";
        timeout = 8000;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getBaseURL() {

        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }
}
