package ga.cv3sarato.android.net;

import ga.cv3sarato.android.MainApplication;
import ga.cv3sarato.android.net.interceptor.HttpHeaderInterceptor;
import ga.cv3sarato.android.constant.Constant;
import ga.cv3sarato.android.constant.Env;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DomainApi {
    private static DomainApi api;
    private Retrofit retrofit;

    private DomainApi() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Constant.Http.HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constant.Http.HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constant.Http.HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new HttpHeaderInterceptor.Builder()
                        .addHeaderParams("Authorization", "Bearer " + MainApplication.getInstance().getAccessToken())
                        .addHeaderParams("Content-Type", "application/json")
                        .build())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Env.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

    }

    public static DomainApi getInstance(){
        if(api == null) {
            api = new DomainApi();
        }

        return api;
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}
