package ga.cv3sarato.android.entity.common;

import com.google.gson.annotations.SerializedName;

public class SortEntity {

    public SortEntity(String column, int ascend) {
        this.column = column;
        this.ascend = ascend;
    }

    @SerializedName("column")
    private String column;

    @SerializedName("ascend")
    private int ascend;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public int getAscend() {
        return ascend;
    }

    public void setAscend(int ascend) {
        this.ascend = ascend;
    }
}
