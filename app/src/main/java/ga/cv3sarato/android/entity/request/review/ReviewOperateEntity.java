package ga.cv3sarato.android.entity.request.review;

import com.google.gson.annotations.SerializedName;

public class ReviewOperateEntity {

    public ReviewOperateEntity(String id, String remark) {
        this.id = id;
        this.remark = remark;
    }

    @SerializedName("id")
    private String id;

    @SerializedName("remark")
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
