package ga.cv3sarato.android.entity.response.common;

import com.google.gson.annotations.SerializedName;

public class FileInfoEntity {

    @SerializedName("id")
	private String id;

    @SerializedName("name")
	private String name;

    @SerializedName("type")
	private String type;

    @SerializedName("mime")
	private String mime;

    @SerializedName("metadata")
	private String metadata;

    @SerializedName("created_by")
	private String createdBy;

    @SerializedName("created_at")
	private long createdAt;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
