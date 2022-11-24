package ga.cv3sarato.android.im_sdk.net.config;

public class TestConfig extends NetConfig{

    public TestConfig() {
        baseURL = "https://DOMAIN/";
        timeout = 8000;
    }

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
