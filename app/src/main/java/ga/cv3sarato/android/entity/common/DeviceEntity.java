package ga.cv3sarato.android.entity.common;

import android.os.Build;

import ga.cv3sarato.android.BuildConfig;

import java.util.TimeZone;

public class DeviceEntity {
    private String device_id;
    private int version_code;
    private String version_name;
    private String device_type;
    private String time_zone;
    private String device_model;
    private String device_token;

    public DeviceEntity() {
        this.device_id = Build.FINGERPRINT;
        this.version_code = BuildConfig.VERSION_CODE;
        this.version_name = BuildConfig.VERSION_NAME;
        this.device_type = "Android";
        this.time_zone = TimeZone.getDefault().getID();
        this.device_model = Build.MODEL;
        this.device_token = "";
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }
}
