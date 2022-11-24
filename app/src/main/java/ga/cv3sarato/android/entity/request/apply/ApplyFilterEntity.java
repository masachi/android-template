package ga.cv3sarato.android.entity.request.apply;

public class ApplyFilterEntity {

    public ApplyFilterEntity(String type, int icon, String typeText) {
        this.type = type;
        this.icon = icon;
        this.typeText = typeText;
    }

    private String type;

    private int icon;

    private String typeText;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }
}
