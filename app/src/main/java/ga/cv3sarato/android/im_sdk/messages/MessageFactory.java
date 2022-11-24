package ga.cv3sarato.android.im_sdk.messages;

import ga.cv3sarato.android.im_sdk.im_client.ImClient;
import ga.cv3sarato.android.im_sdk.im_file.ImFile;
import ga.cv3sarato.android.im_sdk.utils.object.Objects;

import java.util.HashMap;

public class MessageFactory {

    public static Message createMessage(ImClient imClient, MessageInfo message) {
        switch (message.getMessageType()) {
            case TEXT:
                TextMessage textMessage = new TextMessage("");
                textMessage.message = message;
                return textMessage;
            case FILE:
                break;
            case IMAGE:
                HashMap<String, Object> file = new HashMap<>();
                file.put("file", message.getFile());
                file.put("uri", message.getUri());
                ImFile imFile = imClient.createFile(file);
                ImageMessage imageMessage = new ImageMessage(imFile);
                imageMessage.message = message;
                return imageMessage;
            default:
                TypedMessage typedMessage = new TypedMessage();
                typedMessage.message = message;
                return typedMessage;
        }

        return null;
    }

    public static Message updateMessageMentioned2IoFalse(ImClient imClient, MessageInfo messageObj , String uid) {
        Message message = createMessage(imClient, messageObj);
        message.message.setIo(false);
        message.message.setIsRead(0);
        message.updateMetioned(uid);
        return message;
    }

    public static Message updateMessageIoFalse (ImClient imClient, MessageInfo messageObj) {
        messageObj.setIsRead(0);
        return createMessage(imClient, messageObj);
    }

    public static Message updateMessageIoTrue (ImClient imClient, MessageInfo messageObj) {
        messageObj.setIsRead(0);
        return createMessage(imClient, messageObj);
    }
}
