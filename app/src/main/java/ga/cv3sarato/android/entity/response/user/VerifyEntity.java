package ga.cv3sarato.android.entity.response.user;

import com.google.gson.annotations.SerializedName;

public class VerifyEntity {

    @SerializedName("key")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
