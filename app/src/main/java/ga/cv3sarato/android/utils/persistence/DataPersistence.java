package ga.cv3sarato.android.utils.persistence;

public abstract class DataPersistence {

    public abstract void setData(String key, Object object);

    public abstract Object getData(String key, Object defaultObject);

    public abstract void removeData(String key);
}
