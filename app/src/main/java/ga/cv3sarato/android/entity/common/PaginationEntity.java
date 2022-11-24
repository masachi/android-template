package ga.cv3sarato.android.entity.common;

import com.google.gson.annotations.SerializedName;

public class PaginationEntity {

    public PaginationEntity(int number, int offset) {
        this.number = number;
        this.offset = offset;
    }

    @SerializedName("take")
    private int number;

    @SerializedName("offset")
    private int offset;

    @SerializedName("count")
    private int count;

    @SerializedName("remain")
    private int remain;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }
}

