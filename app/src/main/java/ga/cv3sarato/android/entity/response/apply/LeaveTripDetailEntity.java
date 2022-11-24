package ga.cv3sarato.android.entity.response.apply;

import com.google.gson.annotations.SerializedName;
import ga.cv3sarato.android.entity.common.CreatorEntity;
import ga.cv3sarato.android.entity.file.FileEntity;
import ga.cv3sarato.android.entity.response.contact.ContactEntity;

import java.util.List;

public class LeaveTripDetailEntity {

    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type;

    @SerializedName("code")
    private String code;

    @SerializedName("address")
    private String address;

    @SerializedName("content")
    private String content;

    @SerializedName("date")
    private String date;

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("end_time")
    private String endTime;

    @SerializedName("days")
    private double days;

    @SerializedName("create_time")
    private String createTime;

    @SerializedName("publish_time")
    private String publishTime;

    @SerializedName("is_published")
    private int isPublished;

    @SerializedName("review_status")
    private String reviewStatus;

    @SerializedName("histories")
    private List<ReviewProgress> histories;

    @SerializedName("creator")
    private CreatorEntity creator;

    @SerializedName("managers")
    private List<ContactEntity.ContactItem> managers;

    @SerializedName("coordinator")
    private List<ContactEntity.ContactItem> coordinator;

    @SerializedName("leave_type")
    private LeaveTypeEntity.LeaveTypeItem leaveType;

    @SerializedName("files")
    private List<FileEntity> files;

    @SerializedName("operations")
    private List<String> operations;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getDays() {
        return days;
    }

    public void setDays(double days) {
        this.days = days;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public int getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(int isPublished) {
        this.isPublished = isPublished;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public List<ReviewProgress> getHistories() {
        return histories;
    }

    public void setHistories(List<ReviewProgress> histories) {
        this.histories = histories;
    }

    public CreatorEntity getCreator() {
        return creator;
    }

    public void setCreator(CreatorEntity creator) {
        this.creator = creator;
    }

    public List<ContactEntity.ContactItem> getManagers() {
        return managers;
    }

    public void setManagers(List<ContactEntity.ContactItem> managers) {
        this.managers = managers;
    }

    public List<ContactEntity.ContactItem> getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(List<ContactEntity.ContactItem> coordinator) {
        this.coordinator = coordinator;
    }

    public LeaveTypeEntity.LeaveTypeItem getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveTypeEntity.LeaveTypeItem leaveType) {
        this.leaveType = leaveType;
    }

    public List<FileEntity> getFiles() {
        return files;
    }

    public void setFiles(List<FileEntity> files) {
        this.files = files;
    }

    public List<String> getOperations() {
        return operations;
    }

    public void setOperations(List<String> operations) {
        this.operations = operations;
    }

    public class ReviewProgress {
        @SerializedName("id")
        private String id;

        @SerializedName("remark")
        private String remark;

        @SerializedName("operation")
        private String operation;

        @SerializedName("create_time")
        private String createTime;

        @SerializedName("creator")
        private CreatorEntity creator;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public CreatorEntity getCreator() {
            return creator;
        }

        public void setCreator(CreatorEntity creator) {
            this.creator = creator;
        }
    }
}
