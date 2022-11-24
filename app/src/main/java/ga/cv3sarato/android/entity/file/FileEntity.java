package ga.cv3sarato.android.entity.file;

import com.google.gson.annotations.SerializedName;

public class FileEntity {

    public FileEntity(String id, String name, String downloadUrl, String viewUrl, String extension) {
        this.id = id;
        this.name = name;
        this.downloadUrl = downloadUrl;
        this.viewUrl = viewUrl;
        this.extension = extension;
    }

    public FileEntity(String id, String name, String downloadUrl, String viewUrl, String extension, String mimeType) {
        this.id = id;
        this.name = name;
        this.downloadUrl = downloadUrl;
        this.viewUrl = viewUrl;
        this.extension = extension;
        this.mimeType = mimeType;
    }

    public FileEntity(String id, String name, String downloadUrl, String viewUrl, String extension, String mimeType, int size, int width, int height) {
        this.id = id;
        this.name = name;
        this.downloadUrl = downloadUrl;
        this.viewUrl = viewUrl;
        this.extension = extension;
        this.mimeType = mimeType;
        this.size = size;
        this.width = width;
        this.height = height;
    }

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("download_url")
    private String downloadUrl;

    @SerializedName("view_url")
    private String viewUrl;

    @SerializedName("extension")
    private String extension;

    @SerializedName("mime_type")
    private String mimeType;

    @SerializedName("size")
    private int size;

    @SerializedName("width")
    private int width;

    @SerializedName("height")
    private int height;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
