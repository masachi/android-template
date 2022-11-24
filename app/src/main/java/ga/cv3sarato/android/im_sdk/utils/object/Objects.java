package ga.cv3sarato.android.im_sdk.utils.object;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Objects<T> {
    public static <T> T assign (T base, HashMap<String, Object>... args) {
        List<String> fields = new ArrayList<String>();
        Arrays.asList(base.getClass().getDeclaredFields()).forEach(field -> fields.add(field.getName()));
        for(HashMap<String, Object> map : args) {
            map.forEach((key, value) -> {
                if(fields.contains(key)) {
                    set(base, key, value);
                }
            });
        }

        return base;
    }

    public static boolean set(Object object, String fieldName, Object fieldValue) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, fieldValue);
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }
}
