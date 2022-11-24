package ga.cv3sarato.android.entity.response.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileEntity {
    
    @SerializedName("id")
	private String id;

    @SerializedName("uid")
	private String uid;

    @SerializedName("credential_type")
	private String credentialType;

    @SerializedName("credential")
	private String credential;

    @SerializedName("nickname")
	private String name;

    @SerializedName("avatar")
	private String avatar;

    @SerializedName("country_code")
	private String countryCode;

    @SerializedName("mobile")
	private String mobile;

    @SerializedName("email")
	private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
