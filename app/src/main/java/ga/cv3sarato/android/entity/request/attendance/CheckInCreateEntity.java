package ga.cv3sarato.android.entity.request.attendance;

import com.google.gson.annotations.SerializedName;

public class CheckInCreateEntity {

    public CheckInCreateEntity(String place, double longitude, double latitude, String remark) {
        this.place = place;
        this.longitude = longitude;
        this.latitude = latitude;
        this.remark = remark;
    }

    public CheckInCreateEntity() {
    }

    @SerializedName("place")
	private String place;

    @SerializedName("longitude")
	private double longitude;

    @SerializedName("latitude")
	private double latitude;

    @SerializedName("remark")
	private String remark;


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
