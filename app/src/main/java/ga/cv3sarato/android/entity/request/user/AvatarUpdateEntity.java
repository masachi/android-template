package ga.cv3sarato.android.entity.request.user;

import com.google.gson.annotations.SerializedName;

public class AvatarUpdateEntity {

    public AvatarUpdateEntity(String avatar) {
        this.avatar = avatar;
    }

    @SerializedName("avatar")
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
