package ga.cv3sarato.android.entity.response.forum;

import com.google.gson.annotations.SerializedName;
import ga.cv3sarato.android.entity.common.CreatorEntity;
import ga.cv3sarato.android.entity.common.PaginationEntity;
import ga.cv3sarato.android.entity.file.FileEntity;

import java.util.List;

public class ForumPostsEntity {

    @SerializedName("pagination")
    PaginationEntity pagination;

    @SerializedName("list")
    private List<ForumItem> posts;

    public List<ForumItem> getPosts() {
        return posts;
    }

    public PaginationEntity getPagination() {
        return pagination;
    }

    public void setPagination(PaginationEntity pagination) {
        this.pagination = pagination;
    }

    public void setPosts(List<ForumItem> posts) {
        this.posts = posts;
    }

    public class ForumItem {
        @SerializedName("id")
        private String id;

        @SerializedName("title")
        private String title;

        @SerializedName("content")
        private String content;

        @SerializedName("floor_count")
        private int floorCount;

        @SerializedName("like_count")
        private int likeCount;

        @SerializedName("follow_count")
        private int followCount;

        @SerializedName("create_time")
        private String createTime;

        @SerializedName("update_time")
        private String updateTime;

        @SerializedName("is_top")
        private int isTop;

        @SerializedName("created_by")
        private String createdBy;

        @SerializedName("staff_id")
        private String staffId;

        @SerializedName("tenant_id")
        private String tenantId;

        @SerializedName("files")
        private List<FileEntity> files;

        @SerializedName("creator")
        private CreatorEntity creator;

        @SerializedName("is_like")
        private int isLike;

        @SerializedName("is_follow")
        private int isFollow;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public int getFloorCount() {
            return floorCount;
        }

        public void setFloorCount(int floorCount) {
            this.floorCount = floorCount;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getFollowCount() {
            return followCount;
        }

        public void setFollowCount(int followCount) {
            this.followCount = followCount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getIsTop() {
            return isTop;
        }

        public void setIsTop(int isTop) {
            this.isTop = isTop;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getStaffId() {
            return staffId;
        }

        public void setStaffId(String staffId) {
            this.staffId = staffId;
        }

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        public List<FileEntity> getFiles() {
            return files;
        }

        public void setFiles(List<FileEntity> files) {
            this.files = files;
        }

        public CreatorEntity getCreator() {
            return creator;
        }

        public void setCreator(CreatorEntity creator) {
            this.creator = creator;
        }

        public int getIsLike() {
            return isLike;
        }

        public void setIsLike(int isLike) {
            this.isLike = isLike;
        }

        public int getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(int isFollow) {
            this.isFollow = isFollow;
        }
    }
}
