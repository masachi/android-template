package ga.cv3sarato.android.entity.request.forum;

import com.google.gson.annotations.SerializedName;
import ga.cv3sarato.android.entity.common.PaginationEntity;

public class FloorCommentPageEntity {

    public FloorCommentPageEntity(String floorId, PaginationEntity pagination) {
        this.floorId = floorId;
        this.pagination = pagination;
    }

    @SerializedName("floor_id")
    private String floorId;

    @SerializedName("pagination")
    private PaginationEntity pagination;

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public PaginationEntity getPagination() {
        return pagination;
    }

    public void setPagination(PaginationEntity pagination) {
        this.pagination = pagination;
    }
}
