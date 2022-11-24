package ga.cv3sarato.android.entity.response.apply;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeaveTypeEntity {

    @SerializedName("list")
    private List<LeaveTypeItem> types;

    public List<LeaveTypeItem> getTypes() {
        return types;
    }

    public void setTypes(List<LeaveTypeItem> types) {
        this.types = types;
    }

    public class LeaveTypeItem {
        @SerializedName("id")
        private String id;

        @SerializedName("name")
        private String name;

        @SerializedName("initial_amount")
        private double initialAmount;

        @SerializedName("remain_amount")
        private double remainAmount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getInitialAmount() {
            return initialAmount;
        }

        public void setInitialAmount(double initialAmount) {
            this.initialAmount = initialAmount;
        }

        public double getRemainAmount() {
            return remainAmount;
        }

        public void setRemainAmount(double remainAmount) {
            this.remainAmount = remainAmount;
        }
    }
}
