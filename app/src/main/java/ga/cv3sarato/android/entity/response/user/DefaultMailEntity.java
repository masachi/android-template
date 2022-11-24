package ga.cv3sarato.android.entity.response.user;

import com.google.gson.annotations.SerializedName;

public class DefaultMailEntity {

    @SerializedName("email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
