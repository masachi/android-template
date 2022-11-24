package ga.cv3sarato.android.entity.request.review;

import com.google.gson.annotations.SerializedName;
import ga.cv3sarato.android.entity.common.PaginationEntity;
import ga.cv3sarato.android.entity.common.SortEntity;

import java.util.HashMap;
import java.util.List;

public class ReviewPageEntity {

    public ReviewPageEntity(PaginationEntity pagination, String keywords) {
        this.pagination = pagination;
        this.keywords = keywords;
    }

    public ReviewPageEntity(PaginationEntity pagination, String keywords, List<SortEntity> sorts) {
        this.pagination = pagination;
        this.keywords = keywords;
        this.sorts = sorts;
    }

    public ReviewPageEntity(PaginationEntity pagination, HashMap<String, Object> filters, List<SortEntity> sorts) {
        this.pagination = pagination;
        this.filters = filters;
        this.sorts = sorts;
    }

    public ReviewPageEntity(PaginationEntity pagination, String keywords, HashMap<String, Object> filters, List<SortEntity> sorts) {
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
    private HashMap<String, Object> filters;

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

    public HashMap<String, Object> getFilters() {
        return filters;
    }

    public void setFilters(HashMap<String, Object> filters) {
        this.filters = filters;
    }

    public List<SortEntity> getSorts() {
        return sorts;
    }

    public void setSorts(List<SortEntity> sorts) {
        this.sorts = sorts;
    }
}
