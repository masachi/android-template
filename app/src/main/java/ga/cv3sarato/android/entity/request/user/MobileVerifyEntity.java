package ga.cv3sarato.android.entity.request.user;

import com.google.gson.annotations.SerializedName;

public class MobileVerifyEntity {

    public MobileVerifyEntity(String type, String countryCode, String mobile, String authCode) {
        this.type = type;
        this.countryCode = countryCode;
        this.mobile = mobile;
        this.authCode = authCode;
    }

    public MobileVerifyEntity() {
    }

    @SerializedName("type")
    private String type;

    @SerializedName("country_code")
    private String countryCode;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("auth_code")
    private String authCode;

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

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getType() {
        return type;
    }
}
