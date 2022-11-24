package ga.cv3sarato.android.im_sdk.conversation;

import java.util.List;

public class ConversationMembers {

    private String id;
    private List<String> conversation_users;

    public ConversationMembers(String id, List<String> conversation_users) {
        this.id = id;
        this.conversation_users = conversation_users;
    }

    public List<String> getConversation_users() {
        return conversation_users;
    }

    public void setConversation_users(List<String> conversation_users) {
        this.conversation_users = conversation_users;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
