package ga.cv3sarato.android.net;

import ga.cv3sarato.android.constant.Constant;
import ga.cv3sarato.android.net.interceptor.HttpHeaderInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class OSSFileApi {

    private Retrofit retrofit;

    private OSSFileApi(String url) {
        initRetrofit(url);
    }

    private void initRetrofit(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Constant.Http.HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constant.Http.HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constant.Http.HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    public static OSSFileApi getInstance(String url) {
        return new OSSFileApi(url);
    }


    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }

}
