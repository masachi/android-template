package ga.cv3sarato.android.entity.response.review;

import com.google.gson.annotations.SerializedName;
import ga.cv3sarato.android.entity.common.CreatorEntity;
import ga.cv3sarato.android.entity.common.PaginationEntity;

import java.util.List;

public class ReviewsEntity {

    @SerializedName("pagination")
    private PaginationEntity pagination;

    @SerializedName("list")
    private List<ReviewItem> reviews;

    public PaginationEntity getPagination() {
        return pagination;
    }

    public void setPagination(PaginationEntity pagination) {
        this.pagination = pagination;
    }

    public List<ReviewItem> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewItem> reviews) {
        this.reviews = reviews;
    }

    public class ReviewItem {

        @SerializedName("id")
        private String id;

        @SerializedName("type")
        private String type;

        @SerializedName("code")
        private String code;

        @SerializedName("content")
        private String content;

        @SerializedName("staff_type")
        private String staffType;

        @SerializedName("publish_time")
        private String publishTime;

        @SerializedName("review_status")
        private String reviewStatus;

        @SerializedName("operations")
        private List<String> operations;

        @SerializedName("creator")
        private CreatorEntity creator;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStaffType() {
            return staffType;
        }

        public void setStaffType(String staffType) {
            this.staffType = staffType;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getReviewStatus() {
            return reviewStatus;
        }

        public void setReviewStatus(String reviewStatus) {
            this.reviewStatus = reviewStatus;
        }

        public List<String> getOperations() {
            return operations;
        }

        public void setOperations(List<String> operations) {
            this.operations = operations;
        }

        public CreatorEntity getCreator() {
            return creator;
        }

        public void setCreator(CreatorEntity creator) {
            this.creator = creator;
        }
    }

}
