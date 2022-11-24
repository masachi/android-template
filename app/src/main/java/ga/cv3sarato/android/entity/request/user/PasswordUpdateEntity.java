package ga.cv3sarato.android.entity.request.user;

import android.databinding.DataBindingUtil;

import com.google.gson.annotations.SerializedName;

public class PasswordUpdateEntity {

    public PasswordUpdateEntity(String oldPassword, String new_password) {
        this.oldPassword = oldPassword;
        this.new_password = new_password;
    }

    public PasswordUpdateEntity() {
    }

    @SerializedName("old_password")
    private String oldPassword;

    @SerializedName("new_password")
    private String new_password;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }
}
