package ga.cv3sarato.android.entity.request.forum;

import com.google.gson.annotations.SerializedName;
import ga.cv3sarato.android.utils.textInput.MentionEditText;

import java.util.List;

public class FloorCommentCreateEntity {

    public FloorCommentCreateEntity() {
    }

    public FloorCommentCreateEntity(String floorId, String content, String pid, int isAnonymous) {
        this.floorId = floorId;
        this.content = content;
        this.pid = pid;
        this.isAnonymous = isAnonymous;
    }

    @SerializedName("floor_id")
	private String floorId;

    @SerializedName("content")
	private String content;

    @SerializedName("pid")
	private String pid;

    @SerializedName("is_anonymous")
	private int isAnonymous;

    @SerializedName("mentions")
	private List<MentionEditText.AtEntity> mentionedIds;

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(int isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public List<MentionEditText.AtEntity> getMentionedIds() {
        return mentionedIds;
    }

    public void setMentionedIds(List<MentionEditText.AtEntity> mentionedIds) {
        this.mentionedIds = mentionedIds;
    }
}
