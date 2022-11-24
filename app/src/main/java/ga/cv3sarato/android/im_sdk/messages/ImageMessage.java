package ga.cv3sarato.android.im_sdk.messages;

import ga.cv3sarato.android.im_sdk.im_file.ImFile;

public class ImageMessage extends FileMessage {
    private ImFile imFile;

    public ImageMessage(ImFile imFile) {
        super(imFile);

        this.message.setMessageType(MessageType.IMAGE);
        this.message.setMessage("[图片]");
    }

    public String getRatioUri(int length) {
        return this.message.getUri() + "?x-oss-process=image/resize,l_" + String.valueOf(length);
    }
}
