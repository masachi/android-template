package ga.cv3sarato.android.entity.common;

import com.google.gson.annotations.SerializedName;

public class TenantEntity {

    @SerializedName("id")
	private String id;

    @SerializedName("pid")
	private String pid;

    @SerializedName("name")
	private String name;

    @SerializedName("logo")
	private String logo;

    @SerializedName("slogan")
	private String slogan;

    @SerializedName("homepage")
	private String homepage;

    @SerializedName("email_homepage")
	private String emailHomepage;

    @SerializedName("community_name")
	private String communityName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getEmailHomepage() {
        return emailHomepage;
    }

    public void setEmailHomepage(String emailHomepage) {
        this.emailHomepage = emailHomepage;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }
}
