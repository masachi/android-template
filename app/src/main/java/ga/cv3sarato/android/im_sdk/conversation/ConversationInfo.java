package ga.cv3sarato.android.im_sdk.conversation;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ConversationInfo {

    @SerializedName("id")
    public String conversationId;

    @SerializedName("title")
    public String title;

    @SerializedName("icon")
    public String icon;

    @SerializedName("type")
    public ConversationType type;

    @SerializedName("access")
    public String access;

    @SerializedName("is_silent")
    public int isSilent;

    @SerializedName("manager_id")
    public String managerId;

    @SerializedName("conversation_user")
    public UserInfo conversationUser;

    @SerializedName("remark")
    public String remark;

    @SerializedName("user_ids")
    public List<String> userIds;

    @SerializedName("conversation_users")
    public List<UserInfo> conversationUsers;

    @SerializedName("unread_count")
    public int unreadCount;

    @SerializedName("attrs")
    public String attrs;

    public ConversationInfo() {
        this.userIds = new ArrayList<>();
        this.conversationUsers = new ArrayList<>();
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ConversationType getType() {
        return type;
    }

    public void setType(ConversationType type) {
        this.type = type;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public int getIsSilent() {
        return isSilent;
    }

    public void setIsSilent(int isSilent) {
        this.isSilent = isSilent;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public UserInfo getConversationUser() {
        return conversationUser;
    }

    public void setConversationUser(UserInfo conversationUser) {
        this.conversationUser = conversationUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public List<UserInfo> getConversationUsers() {
        return conversationUsers;
    }

    public void setConversationUsers(List<UserInfo> conversationUsers) {
        this.conversationUsers = conversationUsers;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }
}
