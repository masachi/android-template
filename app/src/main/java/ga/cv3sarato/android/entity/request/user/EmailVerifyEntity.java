package ga.cv3sarato.android.entity.request.user;

import com.google.gson.annotations.SerializedName;

public class EmailVerifyEntity {

    public EmailVerifyEntity(String type, String email, String authCode) {
        this.type = type;
        this.email = email;
        this.authCode = authCode;
    }

    public EmailVerifyEntity() {
    }

    @SerializedName("type")
    private String type;

    @SerializedName("email")
    private String email;

    @SerializedName("auth_code")
    private String authCode;

    public void setType(String type) {
        this.type = type;
    }

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

    public String getType() {
        return type;
    }
}
