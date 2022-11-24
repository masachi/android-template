package ga.cv3sarato.android.net.interceptor;

import ga.cv3sarato.android.MainApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    private Map<String,String> headerParams = new HashMap<>();

    public TokenInterceptor() {}

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        Request.Builder newRequestBuilder = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .addHeader("Authorization", "Bearer " + MainApplication.getInstance().getAccessToken())
                .addHeader("X-Hylaa-TenantId", MainApplication.getInstance().getTenantID())
                .addHeader("Content-Type", "application/json");

        return chain.proceed(newRequestBuilder.build());
    }

}
