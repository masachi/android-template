package ga.cv3sarato.android.entity.request.user;

import com.google.gson.annotations.SerializedName;

public class EmailCodeVerifyEntity {

    public EmailCodeVerifyEntity(String email, String authCode) {
        this.email = email;
        this.authCode = authCode;
    }

    public EmailCodeVerifyEntity() {
    }

    @SerializedName("email")
    private String email;

    @SerializedName("auth_code")
    private String authCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
