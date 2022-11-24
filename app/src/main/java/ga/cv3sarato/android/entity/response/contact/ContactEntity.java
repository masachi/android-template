package ga.cv3sarato.android.entity.response.contact;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ContactEntity {

    public ContactEntity(String title, List<ContactItem> items) {
        this.title = title;
        this.items = items;
    }

    String title;

    List<ContactItem> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ContactItem> getItems() {
        return items;
    }

    public void setItems(List<ContactItem> items) {
        this.items = items;
    }

    public class ContactItem implements Serializable {
        private String alphabet;

        @SerializedName("id")
        private String id;

        @SerializedName("user_id")
        private String user_id;

        @SerializedName("uid")
        private String uid;

        @SerializedName("name")
        private String name;

        @SerializedName("avatar")
        private String avatar;

        @SerializedName("country_code")
        private String country_code;

        @SerializedName("mobile")
        private String mobile;

        @SerializedName("email")
        private String email;

        public String getAlphabet() {
            return alphabet;
        }

        public void setAlphabet(String alphabet) {
            this.alphabet = alphabet;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
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

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof ContactItem)) {
                 return false;
            }

            return ((ContactItem) obj).getId().equals(this.getId());
        }
    }
}
