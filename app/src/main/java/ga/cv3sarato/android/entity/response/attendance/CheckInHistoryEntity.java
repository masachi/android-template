package ga.cv3sarato.android.entity.response.attendance;

import com.google.gson.annotations.SerializedName;

public class CheckInHistoryEntity {

    @SerializedName("id")
	private String id;

    @SerializedName("date")
	private String date;

    @SerializedName("time")
	private String time;

    @SerializedName("place")
	private String place;

    @SerializedName("longitude")
	private double longitude;

    @SerializedName("latitude")
	private double latitude;

    @SerializedName("remark")
	private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
