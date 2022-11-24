package ga.cv3sarato.android.entity.common;

import com.google.gson.annotations.SerializedName;

public class FiltersEntity {

    public FiltersEntity(String scope) {
        this.scope = scope;
    }

    public FiltersEntity(String startTime, String endTime, String scope) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.scope = scope;
    }

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("end_time")
    private String endTime;

    @SerializedName("scope")
    private String scope;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
