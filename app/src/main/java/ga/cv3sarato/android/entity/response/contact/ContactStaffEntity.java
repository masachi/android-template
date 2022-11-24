package ga.cv3sarato.android.entity.response.contact;

import com.google.gson.annotations.SerializedName;
import ga.cv3sarato.android.entity.response.contact.ContactEntity;

import java.util.List;
import java.util.TreeMap;

public class ContactStaffEntity {

    @SerializedName("staffs")
    TreeMap<String, List<ContactEntity.ContactItem>> staff;

    public TreeMap<String, List<ContactEntity.ContactItem>> getStaff() {
        return staff;
    }

    public void setStaff(TreeMap<String, List<ContactEntity.ContactItem>> staff) {
        this.staff = staff;
    }
}
