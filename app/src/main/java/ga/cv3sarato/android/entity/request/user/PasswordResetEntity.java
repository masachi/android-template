package ga.cv3sarato.android.entity.request.user;

import com.google.gson.annotations.SerializedName;

public class PasswordResetEntity {

    public PasswordResetEntity(String authKey, String newPassword) {
        this.authKey = authKey;
        this.newPassword = newPassword;
    }

    public PasswordResetEntity() {
    }

    @SerializedName("auth_code_key")
    private String authKey;

    @SerializedName("new_password")
    private String newPassword;

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
