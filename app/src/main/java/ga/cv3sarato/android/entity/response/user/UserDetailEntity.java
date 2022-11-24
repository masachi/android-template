package ga.cv3sarato.android.entity.response.user;

import com.google.gson.annotations.SerializedName;

public class UserDetailEntity {

    @SerializedName("id")
	private String id;

    @SerializedName("uid")
	private String uid;

    @SerializedName("username")
	private String username;

    @SerializedName("nickname")
	private String nickname;

    @SerializedName("avatar")
	private String avatar;

    @SerializedName("credential_type")
	private String credential_type;

    @SerializedName("credential")
	private String credential;

    @SerializedName("country_code")
	private String country_code;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCredential_type() {
        return credential_type;
    }

    public void setCredential_type(String credential_type) {
        this.credential_type = credential_type;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
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
