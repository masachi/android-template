package ga.cv3sarato.android.utils.cache;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class MessageCache extends RealmObject {

    @PrimaryKey
    private String message_id;

    @Required
    private String message_type;

    @Required
    private String message;

    private String file;

    private String uri;

    @Required
    private String conversation_id;

    private long send_time;

    private String send_by;

    private boolean io;

    private int is_read = 0;

    private String attrs;

    private RealmList<String> mention_list;

    private boolean mentioned_me = false;

    private boolean mentioned_all = false;

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        this.conversation_id = conversation_id;
    }

    public long getSend_time() {
        return send_time;
    }

    public void setSend_time(long send_time) {
        this.send_time = send_time;
    }

    public String getSend_by() {
        return send_by;
    }

    public void setSend_by(String send_by) {
        this.send_by = send_by;
    }

    public boolean isIo() {
        return io;
    }

    public void setIo(boolean io) {
        this.io = io;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    public RealmList<String> getMention_list() {
        return mention_list;
    }

    public void setMention_list(RealmList<String> mention_list) {
        this.mention_list = mention_list;
    }

    public boolean isMentioned_me() {
        return mentioned_me;
    }

    public void setMentioned_me(boolean mentioned_me) {
        this.mentioned_me = mentioned_me;
    }

    public boolean isMentioned_all() {
        return mentioned_all;
    }

    public void setMentioned_all(boolean mentioned_all) {
        this.mentioned_all = mentioned_all;
    }
}
