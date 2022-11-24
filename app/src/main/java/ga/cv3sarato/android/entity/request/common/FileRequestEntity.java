package ga.cv3sarato.android.entity.request.common;

import com.google.gson.annotations.SerializedName;

public class FileRequestEntity {

    public FileRequestEntity(String name) {
        this.name = name;
    }

    public FileRequestEntity(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type = "OSS";

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
}
