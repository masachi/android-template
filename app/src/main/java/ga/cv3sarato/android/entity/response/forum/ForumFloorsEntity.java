package ga.cv3sarato.android.entity.response.forum;

import com.google.gson.annotations.SerializedName;
import ga.cv3sarato.android.entity.common.CreatorEntity;
import ga.cv3sarato.android.entity.common.PaginationEntity;
import ga.cv3sarato.android.entity.file.FileEntity;

import java.util.List;

public class ForumFloorsEntity {

    @SerializedName("pagination")
    private PaginationEntity pagination;

    @SerializedName("list")
    private List<Floor> floors;

    public PaginationEntity getPagination() {
        return pagination;
    }

    public void setPagination(PaginationEntity pagination) {
        this.pagination = pagination;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public class Floor {

        @SerializedName("id")
        private String id;

        @SerializedName("content")
        private String content;

        @SerializedName("floor_number")
        private int floorNumber;

        @SerializedName("floor_comment_count")
        private int floorCommentCount;

        @SerializedName("create_time")
        private String createTime;

        @SerializedName("files")
        private List<FileEntity> files;

        @SerializedName("creator")
        private CreatorEntity creator;

        @SerializedName("pagination")
        private PaginationEntity pagination;

        @SerializedName("comments")
        private List<FloorCommentEntity> floorComments;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getFloorNumber() {
            return floorNumber;
        }

        public void setFloorNumber(int floorNumber) {
            this.floorNumber = floorNumber;
        }

        public int getFloorCommentCount() {
            return floorCommentCount;
        }

        public void setFloorCommentCount(int floorCommentCount) {
            this.floorCommentCount = floorCommentCount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public PaginationEntity getPagination() {
            return pagination;
        }

        public void setPagination(PaginationEntity pagination) {
            this.pagination = pagination;
        }

        public List<FloorCommentEntity> getFloorComments() {
            return floorComments;
        }

        public void setFloorComments(List<FloorCommentEntity> floorComments) {
            this.floorComments = floorComments;
        }

        public class FloorCommentEntity {

            @SerializedName("id")
            private String id;

            @SerializedName("object_type")
            private String objectType;

            @SerializedName("content")
            private String content;

            @SerializedName("create_time")
            private String createTime;

            @SerializedName("staff_id")
            private String staffId;

            @SerializedName("created_by")
            private String createdBy;

            @SerializedName("tenant_id")
            private String tenantId;

            @SerializedName("creator")
            private CreatorEntity creator;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getObjectType() {
                return objectType;
            }

            public void setObjectType(String objectType) {
                this.objectType = objectType;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getStaffId() {
                return staffId;
            }

            public void setStaffId(String staffId) {
                this.staffId = staffId;
            }

            public String getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(String createdBy) {
                this.createdBy = createdBy;
            }

            public String getTenantId() {
                return tenantId;
            }

            public void setTenantId(String tenantId) {
                this.tenantId = tenantId;
            }

            public CreatorEntity getCreator() {
                return creator;
            }

            public void setCreator(CreatorEntity creator) {
                this.creator = creator;
            }
        }
    }
}
