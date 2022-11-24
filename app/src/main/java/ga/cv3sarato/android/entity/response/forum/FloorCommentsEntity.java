package ga.cv3sarato.android.entity.response.forum;

import com.google.gson.annotations.SerializedName;
import ga.cv3sarato.android.entity.common.PaginationEntity;

import java.util.List;

public class FloorCommentsEntity {

    @SerializedName("pagination")
    private PaginationEntity pagination;

    @SerializedName("list")
    private List<ForumFloorsEntity.Floor.FloorCommentEntity> comments;

    public PaginationEntity getPagination() {
        return pagination;
    }

    public void setPagination(PaginationEntity pagination) {
        this.pagination = pagination;
    }

    public List<ForumFloorsEntity.Floor.FloorCommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<ForumFloorsEntity.Floor.FloorCommentEntity> comments) {
        this.comments = comments;
    }
}
