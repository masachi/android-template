package ga.cv3sarato.android.net.converter;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import ga.cv3sarato.android.common.Response;
import ga.cv3sarato.android.net.ServerException;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class HylaaResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    HylaaResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
//        JsonReader jsonReader = gson.newJsonReader(value.charStream());
//        try {
//            T result = adapter.read(jsonReader);
//            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
//                throw new JsonIOException("JSON document was not fully consumed.");
//            }
//            return result;
//        } finally {
//            value.close();
//        }

        String responseString = value.string();
        try {
            Response response = gson.fromJson(responseString, Response.class);
            switch (response.getCode()) {
                case 200:
                    return adapter.read(gson.newJsonReader(new StringReader(gson.toJson(response.getBody()))));
                default:
                    throw new ServerException(response.getCode(), response.getMessage());
            }

        } finally {
            value.close();
        }
    }
}
