package ga.cv3sarato.android.net.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;
import ga.cv3sarato.android.common.Request;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

public class HylaaRequestBodyConverter<T> implements Converter<T, RequestBody> {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    HylaaRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        ((Request)value).setBody(trimRequest(((Request) value).getBody()));
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        JsonWriter jsonWriter = gson.newJsonWriter(writer);
        adapter.write(jsonWriter, value);
        jsonWriter.close();
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }

    private Object trimRequest (Object object) {
        if(object == null) return null;
        Field[] fields = object.getClass().getDeclaredFields();
        for(Field field : fields) {
            Class<?> type = field.getType();
            if("class java.lang.String".equals(type.toString())) {
                String fieldName = field.getName();
                Object value = getFieldValueByName(fieldName, object);
                if(value != null) {
                    setTrimedValue(object, type, fieldName, value.toString().trim());
                }
            }
        }

        return object;
    }

    private Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    private static void setTrimedValue(Object obj,Class<?> classType,String fieldName,String value){
        String firstLetter=fieldName.substring(0,1).toUpperCase();
        String setter = "set"+firstLetter+fieldName.substring(1);
        try{
            Method method = obj.getClass().getMethod(setter, new Class[]{classType});
            method.invoke(obj, new Object[] {value});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
