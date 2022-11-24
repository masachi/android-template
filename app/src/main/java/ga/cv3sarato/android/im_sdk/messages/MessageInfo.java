package ga.cv3sarato.android.im_sdk.messages;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MessageInfo {

    @SerializedName("message_id")
    public String messageId;

    @SerializedName("message_type")
    public MessageType messageType;

    @SerializedName("message")
    public String message;

    @SerializedName("conversation_id")
    public String conversationId;

    @SerializedName("send_time")
    public String sendTime;

    @SerializedName("send_by")
    public String sendBy;

    @SerializedName("file")
    public String file;

    @SerializedName("uri")
    public String uri;

    @SerializedName("io")
    public boolean io;

    @SerializedName("attrs")
    public String attrs;

    @SerializedName("is_read")
    public int isRead;

    @SerializedName("mention_list")
    public List<String> mentionList;

    @SerializedName("mentioned_all")
    public boolean mentionedAll;

    @SerializedName("mentioned_me")
    public boolean mentionedMe;

    MessageInfo() {
        this.mentionList = new ArrayList<>();
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendBy() {
        return sendBy;
    }

    public void setSendBy(String sendBy) {
        this.sendBy = sendBy;
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

    public boolean isIo() {
        return io;
    }

    public void setIo(boolean io) {
        this.io = io;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public List<String> getMentionList() {
        return mentionList;
    }

    public void setMentionList(List<String> mentionList) {
        this.mentionList = mentionList;
    }

    public boolean isMentionedAll() {
        return mentionedAll;
    }

    void setMentionedAll(boolean mentionedAll) {
        this.mentionedAll = mentionedAll;
    }

    public boolean isMentionedMe() {
        return mentionedMe;
    }

    public void setMentionedMe(boolean mentionedMe) {
        this.mentionedMe = mentionedMe;
    }
}
