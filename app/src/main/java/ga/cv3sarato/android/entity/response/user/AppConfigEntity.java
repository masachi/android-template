package ga.cv3sarato.android.entity.response.user;

import com.google.gson.annotations.SerializedName;

public class AppConfigEntity {

    public AppConfigEntity(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @SerializedName("code")
    private String code;

    @SerializedName("name")
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
