package ga.cv3sarato.android.entity.response.attendance;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckInRecordsEntity {

    @SerializedName("records")
    private List<CheckInHistoryEntity> records;

    public List<CheckInHistoryEntity> getRecords() {
        return records;
    }

    public void setRecords(List<CheckInHistoryEntity> records) {
        this.records = records;
    }
}
