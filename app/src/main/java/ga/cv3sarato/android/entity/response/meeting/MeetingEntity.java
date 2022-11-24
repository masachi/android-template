package ga.cv3sarato.android.entity.response.meeting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeetingEntity {

    public MeetingEntity() {
        vars = new Vars();
    }

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("room_name")
    @Expose
    public String roomName;
    @SerializedName("max_user")
    @Expose
    public Long maxUser;
    @SerializedName("expired_at")
    @Expose
    public Long expiredAt;
    @SerializedName("vars")
    @Expose
    public Vars vars;
    @SerializedName("tenant_id")
    @Expose
    public String tenantId;

    public class Vars {
        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("ticket")
        @Expose
        public String ticket;
    }
}
