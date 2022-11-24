package ga.cv3sarato.android.net;

import ga.cv3sarato.android.MainApplication;
import ga.cv3sarato.android.net.interceptor.HttpHeaderInterceptor;
import ga.cv3sarato.android.constant.Constant;
import ga.cv3sarato.android.constant.Env;
import ga.cv3sarato.android.net.interceptor.TokenInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MailApi {
    private static MailApi api;
    private Retrofit retrofit;

    private MailApi() {
        initRetrofit(new TokenInterceptor());
    }

    private MailApi(Interceptor interceptor){
        initRetrofit(interceptor);
    }

    public void initRetrofit(Interceptor interceptor){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Constant.Http.HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constant.Http.HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constant.Http.HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Env.MAIL_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    public static MailApi defaultInstance(){
        if(api == null) {
            api = new MailApi();
        }

        return api;
    }

    public static MailApi getInstance(HttpHeaderInterceptor interceptor) {
        api = null;
        return new MailApi(interceptor);
    }


    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}
