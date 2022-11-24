package ga.cv3sarato.android.im_sdk.messages;

import ga.cv3sarato.android.im_sdk.im_file.ImFile;

public class FileMessage extends TypedMessage {
    private ImFile imFile;

    public FileMessage(ImFile imFile) {
        this.imFile = imFile;
        this.message.setFile(imFile.getFile());
        this.message.setUri(imFile.getUri());
        this.message.setMessageType(MessageType.FILE);
    }

    public ImFile getImFile() {
        return imFile;
    }
}
