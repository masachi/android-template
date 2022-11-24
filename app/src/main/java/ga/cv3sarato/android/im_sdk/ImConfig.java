package ga.cv3sarato.android.im_sdk;

import ga.cv3sarato.android.im_sdk.net.ImTokenGetter;
import ga.cv3sarato.android.im_sdk.model.UserInfo;
import ga.cv3sarato.android.im_sdk.type.Env;

public class ImConfig {
    private String oid;
    private String uid;
    private String platform;
    private Env env = Env.Development;
    private String client_id;
    private UserInfo user_info;
    private String ws_url;
    private ImTokenGetter tokenGetter;


    public String getWsUrl() {
        switch (this.env) {
            case Development:
                this.ws_url = "https://DOMAIN:10443";
                break;
            case Test:
                this.ws_url = "https://DOMAIN:20443";
                break;
            case Prd:
                this.ws_url = "https://DOMAIN:20443";
                break;
            default:
                this.ws_url = "wss://chat2.hylaa.net:10443";
                break;
        }

        return this.ws_url;
    }

    public String getWsQueryConfig() {
        if (this.uid == null || this.platform == null || this.client_id == null) {
            throw new NullPointerException();
        }

        String query = "user_id=" +
                        this.uid +
                        "&name=" +
                        this.user_info.getName() +
                        "&avatar=" +
                        this.user_info.getAvatar() +
                        "&client_type=" +
                        this.platform +
                        "&client_id=" +
                        this.client_id;

        return query;
    }

    public String getOid() {
        return oid;
    }

    public String getUid() {
        return uid;
    }

    public String getPlatform() {
        return platform;
    }

    public Env getEnv() {
        return env;
    }

    public String getClient_id() {
        return client_id;
    }

    public UserInfo getUser_info() {
        return user_info;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setEnv(Env env) {
        this.env = env;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public void setUser_info(UserInfo user_info) {
        this.user_info = user_info;
    }

    public ImTokenGetter getTokenGetter() {
        return tokenGetter;
    }

    public void setTokenGetter(ImTokenGetter tokenGetter) {
        this.tokenGetter = tokenGetter;
    }
}
