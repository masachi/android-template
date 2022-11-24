package ga.cv3sarato.android.entity.response.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompanyEntity implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("pid")
    private String pid;

    @SerializedName("name")
    private String name;

    @SerializedName("avatar")
    private String icon;

    @SerializedName("subsets")
    private ArrayList<CompanyEntity> subsets;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public ArrayList<CompanyEntity> getSubsets() {
        return subsets;
    }

    public void setSubsets(ArrayList<CompanyEntity> subsets) {
        this.subsets = subsets;
    }
}
