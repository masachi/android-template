package ga.cv3sarato.android.entity.request.common;

import com.google.gson.annotations.SerializedName;

public class DateEntity {

    public DateEntity(String date) {
        this.date = date;
    }

    public DateEntity() {
    }

    @SerializedName("date")
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
