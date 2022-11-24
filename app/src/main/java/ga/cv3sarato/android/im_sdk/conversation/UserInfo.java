package ga.cv3sarato.android.im_sdk.conversation;

import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("conversation_id")
    private String conversationId;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("is_mute")
    private int isMute;

    @SerializedName("is_top")
    private int isTop;

    @SerializedName("name")
    private String name;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("update_time")
    private long updateTime;

    @SerializedName("create_time")
    private long createTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getIsMute() {
        return isMute;
    }

    public void setIsMute(int isMute) {
        this.isMute = isMute;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
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

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
