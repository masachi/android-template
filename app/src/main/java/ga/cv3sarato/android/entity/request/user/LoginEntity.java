package ga.cv3sarato.android.entity.request.user;

import com.google.gson.annotations.SerializedName;

public class LoginEntity {

    @SerializedName("user_id")
    private String userId;

    public LoginEntity(String userId) {
        this.userId = userId;
    }

    public LoginEntity() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
