package ga.cv3sarato.android.utils.cache;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class ConversationCache extends RealmObject {

    @PrimaryKey
    private String id;

    @Required
    private String type;

    @Required
    private String title;

    private String icon;

    private String remark;

    @Required
    private String manager_id;

    @Required
    private String access;

    private int is_silent;

    private UserCache conversation_user;

    private RealmList<UserCache> conversation_users;

    private String attrs;

    private int unread_count = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getManager_id() {
        return manager_id;
    }

    public void setManager_id(String manager_id) {
        this.manager_id = manager_id;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public int getIs_silent() {
        return is_silent;
    }

    public void setIs_silent(int is_silent) {
        this.is_silent = is_silent;
    }

    public UserCache getConversation_user() {
        return conversation_user;
    }

    public void setConversation_user(UserCache conversation_user) {
        this.conversation_user = conversation_user;
    }

    public RealmList<UserCache> getConversation_users() {
        return conversation_users;
    }

    public void setConversation_users(RealmList<UserCache> conversation_users) {
        this.conversation_users = conversation_users;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    public int getUnread_count() {
        return unread_count;
    }

    public void setUnread_count(int unread_count) {
        this.unread_count = unread_count;
    }
}
