package ga.cv3sarato.android.entity.request.forum;

import com.google.gson.annotations.SerializedName;
import ga.cv3sarato.android.entity.file.FileEntity;
import ga.cv3sarato.android.utils.textInput.MentionEditText;

import java.util.List;

public class FloorCreateEntity {

    @SerializedName("post_id")
	private String postId;

    @SerializedName("content")
	private String content;

    @SerializedName("is_anonymous")
	private int isAnonymous;

    @SerializedName("files")
	private List<FileEntity> files;

    @SerializedName("mentions")
	private List<MentionEditText.AtEntity> mentions;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(int isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public List<FileEntity> getFiles() {
        return files;
    }

    public void setFiles(List<FileEntity> files) {
        this.files = files;
    }

    public List<MentionEditText.AtEntity> getMentions() {
        return mentions;
    }

    public void setMentions(List<MentionEditText.AtEntity> mentions) {
        this.mentions = mentions;
    }
}
