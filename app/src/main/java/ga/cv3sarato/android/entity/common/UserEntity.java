package ga.cv3sarato.android.entity.common;

import com.google.gson.annotations.SerializedName;
import ga.cv3sarato.android.entity.response.user.AppConfigEntity;
import ga.cv3sarato.android.entity.response.user.ProfileEntity;

import java.util.List;

public class UserEntity {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("uid")
    private String uid;

    @SerializedName("app_modules")
    private List<AppConfigEntity> appConfig;

    @SerializedName("user")
    private ProfileEntity profile;

    @SerializedName("tenant")
    private TenantEntity tenant;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public List<AppConfigEntity> getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(List<AppConfigEntity> appConfig) {
        this.appConfig = appConfig;
    }

    public ProfileEntity getProfile() {
        return profile;
    }

    public void setProfile(ProfileEntity profile) {
        this.profile = profile;
    }

    public TenantEntity getTenant() {
        return tenant;
    }

    public void setTenant(TenantEntity tenant) {
        this.tenant = tenant;
    }

    public class Apps {

        @SerializedName("apps")
        private List<AppConfigEntity> apps;

        public List<AppConfigEntity> getApps() {
            return apps;
        }

        public void setApps(List<AppConfigEntity> apps) {
            this.apps = apps;
        }
    }
}
