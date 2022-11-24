package ga.cv3sarato.android.entity.request.user;

import com.google.gson.annotations.SerializedName;

public class MobileCodeEntity {

    public MobileCodeEntity(String type, String countryCode, String mobile) {
        this.type = type;
        this.countryCode = countryCode;
        this.mobile = mobile;
    }

    public MobileCodeEntity() {
    }

    @SerializedName("type")
    private String type;

    @SerializedName("country_code")
    private String countryCode;

    @SerializedName("mobile")
    private String mobile;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
