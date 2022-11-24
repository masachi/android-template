package ga.cv3sarato.android.im_sdk.messages;

public class TextMessage extends Message {

    public TextMessage(String message) {
        super();
        if(message == null){
            throw new NullPointerException();
        }
        this.message.setMessage(message);
        this.message.setMessageType(MessageType.TEXT);
    }
}
