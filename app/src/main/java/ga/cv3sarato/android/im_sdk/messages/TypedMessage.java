package ga.cv3sarato.android.im_sdk.messages;

public class TypedMessage extends Message {

    public TypedMessage() {
        super();
    }

    public Message setText(String text) {
        this.message.setMessage(text);
        return this;
    }

    public String getText() {
        return this.message.getMessage();
    }

    public Message setAttributes(String attrs) {
        this.message.setAttrs(attrs);
        return this;
    }

    public String getAttributes() {
        return this.message.getAttrs();
    }
}
