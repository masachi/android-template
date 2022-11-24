package ga.cv3sarato.android.entity.request.forum;

import com.google.gson.annotations.SerializedName;
import ga.cv3sarato.android.entity.file.FileEntity;
import ga.cv3sarato.android.utils.textInput.MentionEditText;

import java.util.List;

public class ForumNewPostsEntity {

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("files")
    private List<FileEntity> files;

    @SerializedName("is_anonymous")
    private int anonymous;

    @SerializedName("mentions")
    private List<MentionEditText.AtEntity> mentions;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<FileEntity> getFiles() {
        return files;
    }

    public void setFiles(List<FileEntity> files) {
        this.files = files;
    }

    public int getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(int anonymous) {
        this.anonymous = anonymous;
    }

    public List<MentionEditText.AtEntity> getMentions() {
        return mentions;
    }

    public void setMentions(List<MentionEditText.AtEntity> mentions) {
        this.mentions = mentions;
    }
}
