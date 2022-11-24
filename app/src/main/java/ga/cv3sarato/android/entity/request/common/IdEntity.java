package ga.cv3sarato.android.entity.request.common;

import com.google.gson.annotations.SerializedName;

public class IdEntity {

    @SerializedName("id")
    private String id;

    public IdEntity(String id) {
        this.id = id;
    }

    public IdEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
