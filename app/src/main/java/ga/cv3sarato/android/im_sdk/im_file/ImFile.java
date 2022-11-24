package ga.cv3sarato.android.im_sdk.im_file;

import ga.cv3sarato.android.im_sdk.net.NetApi;

import java.util.HashMap;
import java.util.Map;

public class ImFile {
    private String file;
    private String uri;
    private NetApi api;
    private HashMap<String, Object> fileObj;

    public ImFile(NetApi api, HashMap<String, Object> fileObj) {
        this.api = api;
        this.fileObj = fileObj;

        this.file = (String) fileObj.get("file");
        this.uri = (String) fileObj.get("uri");
    }

    public Object save() {
        return null;
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
}
