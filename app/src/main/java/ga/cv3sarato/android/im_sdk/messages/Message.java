package ga.cv3sarato.android.im_sdk.messages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Message {
    protected MessageInfo message;

    public Message() {
        this.message = new MessageInfo();
        this.message.setMessageId(UUID.randomUUID().toString());
        this.message.setSendTime(String.valueOf(new Date().getTime()));
        this.message.setMentionList(new ArrayList<String>());
    }

    public void updateMetioned(String uid) {
        this.message.setMentionedMe(
                !this.message.getSendBy().equals(uid)
                        &&
                (this.message.isMentionedAll() || this.message.getMentionList().indexOf(uid) > -1)
        );
    }

    public List<String> getMetionList() {
        return this.message.getMentionList();
    }

    public void setMetionList (List<String> metionList) {
        this.message.setMentionList(metionList);
    }

    public Message metionAll(boolean value) {
        this.message.setMentionedAll(value);
        return this;
    }

    public MessageInfo getMessage() {
        return message;
    }

    public void setMessage(MessageInfo message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Message) {
            Message currentMessage = (Message) obj;
            return currentMessage.getMessage().getMessageId().equals(this.getMessage().getMessageId());
        }

        return false;
    }
}
