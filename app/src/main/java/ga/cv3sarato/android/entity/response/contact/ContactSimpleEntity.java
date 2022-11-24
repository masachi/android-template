package ga.cv3sarato.android.entity.response.contact;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContactSimpleEntity {

    @SerializedName("staffs")
    private List<ContactEntity.ContactItem> staffs;

    public List<ContactEntity.ContactItem> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<ContactEntity.ContactItem> staffs) {
        this.staffs = staffs;
    }
}
