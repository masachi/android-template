package ga.cv3sarato.android.entity.request.user;

import com.google.gson.annotations.SerializedName;

public class EmailCodeEntity {

    public EmailCodeEntity(String type, String email) {
        this.type = type;
        this.email = email;
    }

    public EmailCodeEntity() {
    }

    @SerializedName("type")
    private String type;

    @SerializedName("email")
    private String email;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
