package ga.cv3sarato.android.entity.request.forum;

import com.google.gson.annotations.SerializedName;
import ga.cv3sarato.android.entity.common.PaginationEntity;
import ga.cv3sarato.android.entity.common.SortEntity;

import java.util.HashMap;
import java.util.List;

public class ForumPageEntity {

    public ForumPageEntity(PaginationEntity pagination, HashMap<String, String> filters, List<SortEntity> sorts) {
        this.pagination = pagination;
        this.filters = filters;
        this.sorts = sorts;
    }

    public ForumPageEntity(PaginationEntity pagination, String keywords, HashMap<String, String> filters, List<SortEntity> sorts) {
        this.pagination = pagination;
        this.keywords = keywords;
        this.filters = filters;
        this.sorts = sorts;
    }

    @SerializedName("pagination")
    private PaginationEntity pagination;

    @SerializedName("keywords")
    private String keywords;

    @SerializedName("filters")
    private HashMap<String, String> filters;

    @SerializedName("sorts")
    private List<SortEntity> sorts;

    public PaginationEntity getPagination() {
        return pagination;
    }

    public void setPagination(PaginationEntity pagination) {
        this.pagination = pagination;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public HashMap<String, String> getFilters() {
        return filters;
    }

    public void setFilters(HashMap<String, String> filters) {
        this.filters = filters;
    }

    public List<SortEntity> getSorts() {
        return sorts;
    }

    public void setSorts(List<SortEntity> sorts) {
        this.sorts = sorts;
    }
}
