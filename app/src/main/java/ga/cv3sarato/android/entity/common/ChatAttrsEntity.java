package ga.cv3sarato.android.entity.common;

import com.google.gson.annotations.SerializedName;

public class ChatAttrsEntity {

    @SerializedName("name")
    private String name;

    @SerializedName("avatar")
    private String avatar;

    public ChatAttrsEntity(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
