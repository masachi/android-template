package ga.cv3sarato.android.im_sdk.net;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ImTokenInterceptor implements Interceptor {
    private ImTokenGetter tokenGetter;

    public ImTokenInterceptor(ImTokenGetter tokenGetter) {
        this.tokenGetter = tokenGetter;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        Request.Builder newRequestBuilder = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .addHeader("Authorization", "Bearer " + tokenGetter.getImAccessToken());

        return chain.proceed(newRequestBuilder.build());
    }
}
