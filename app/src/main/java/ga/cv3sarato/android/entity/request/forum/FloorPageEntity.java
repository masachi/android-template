package ga.cv3sarato.android.entity.request.forum;

import com.google.gson.annotations.SerializedName;
import ga.cv3sarato.android.entity.common.PaginationEntity;

import java.util.HashMap;

public class FloorPageEntity {

    public FloorPageEntity() {
    }

    public FloorPageEntity(String postId) {
        this.postId = postId;
    }

    public FloorPageEntity(String postId, PaginationEntity pagination) {
        this.postId = postId;
        this.pagination = pagination;
    }

    public FloorPageEntity(String postId, int commentSize, PaginationEntity pagination) {
        this.postId = postId;
        this.commentSize = commentSize;
        this.pagination = pagination;
    }

    public FloorPageEntity(String postId, int commentSize, String keywords, PaginationEntity pagination) {
        this.postId = postId;
        this.commentSize = commentSize;
        this.keywords = keywords;
        this.pagination = pagination;
    }

    @SerializedName("post_id")
	private String postId;

    @SerializedName("comment_size")
	private int commentSize = 3;

    @SerializedName("keywords")
	private String keywords;

    @SerializedName("pagination")
	private PaginationEntity pagination;

    @SerializedName("filters")
	private HashMap<String, String> filters;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public int getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(int commentSize) {
        this.commentSize = commentSize;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public PaginationEntity getPagination() {
        return pagination;
    }

    public void setPagination(PaginationEntity pagination) {
        this.pagination = pagination;
    }

    public HashMap<String, String> getFilters() {
        return filters;
    }

    public void setFilters(HashMap<String, String> filters) {
        this.filters = filters;
    }
}
