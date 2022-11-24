package ga.cv3sarato.android.entity.request.apply;

import com.google.gson.annotations.SerializedName;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.entity.file.FileEntity;
import ga.cv3sarato.android.entity.request.common.IdEntity;
import ga.cv3sarato.android.utils.annotation.Required;

import java.util.List;

public class LeaveCreateEntity {

    @SerializedName("content")
    @Required(field = R.string.leave_create_field_content)
	private String content;

    @SerializedName("leave_type_id")
    @Required(field = R.string.leave_create_field_leave_type_id)
	private String leaveTypeId;

    @SerializedName("is_published")
	private int isPublished = 1;

    @SerializedName("start_time")
    @Required(field = R.string.leave_create_field_start_time)
	private String startTime;

    @SerializedName("end_time")
    @Required(field = R.string.leave_create_field_end_time)
	private String endTime;

    @SerializedName("days")
    @Required(field = R.string.leave_create_field_days)
	private float days;

    @SerializedName("files")
	private List<FileEntity> files;

    @SerializedName("managers")
    @Required(field = R.string.leave_create_field_managers)
	private List<IdEntity> managers;

    @SerializedName("coordinators")
	private List<IdEntity> coordinators;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(String leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public int getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(int isPublished) {
        this.isPublished = isPublished;
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

    public float getDays() {
        return days;
    }

    public void setDays(float days) {
        this.days = days;
    }

    public List<FileEntity> getFiles() {
        return files;
    }

    public void setFiles(List<FileEntity> files) {
        this.files = files;
    }

    public List<IdEntity> getManagers() {
        return managers;
    }

    public void setManagers(List<IdEntity> managers) {
        this.managers = managers;
    }

    public List<IdEntity> getCoordinators() {
        return coordinators;
    }

    public void setCoordinators(List<IdEntity> coordinators) {
        this.coordinators = coordinators;
    }
}
