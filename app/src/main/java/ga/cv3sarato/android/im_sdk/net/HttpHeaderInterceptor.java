package ga.cv3sarato.android.im_sdk.net;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpHeaderInterceptor implements Interceptor {
    private Map<String,String> headerParams = new HashMap<>();

    public HttpHeaderInterceptor() {}

    @Override
    public Response intercept(Chain chain) throws IOException{
        Request oldRequest = chain.request();
        Request.Builder newRequestBuilder = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body());

        if(headerParams.size() > 0){
            for(Map.Entry<String, String> params : headerParams.entrySet()){
                newRequestBuilder.header(params.getKey(), params.getValue());
            }
        }

        return chain.proceed(newRequestBuilder.build());
    }

    public static class Builder {
        HttpHeaderInterceptor httpHeaderInterceptor;

        public Builder () {
            httpHeaderInterceptor = new HttpHeaderInterceptor();
        }

        public Builder addHeaderParams(String key, int value){
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, float value){
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, double value){
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, long value){
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, String value){
            httpHeaderInterceptor.headerParams.put(key, value);
            return this;
        }

        public HttpHeaderInterceptor build() {
            return httpHeaderInterceptor;
        }
    }
}
