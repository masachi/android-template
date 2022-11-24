package ga.cv3sarato.android.im_sdk.net;

import com.google.gson.Gson;
import ga.cv3sarato.android.im_sdk.conversation.ConversationInfo;
import ga.cv3sarato.android.im_sdk.model.UserInfo;
import ga.cv3sarato.android.im_sdk.net.base.Request;
import ga.cv3sarato.android.im_sdk.net.base.Response;
import ga.cv3sarato.android.im_sdk.net.config.DevConfig;
import ga.cv3sarato.android.im_sdk.net.config.NetConfig;
import ga.cv3sarato.android.im_sdk.net.config.PrdConfig;
import ga.cv3sarato.android.im_sdk.net.config.TestConfig;
import ga.cv3sarato.android.im_sdk.type.Env;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class NetApi {
    private Retrofit serverRetrofit;
    private Retrofit fileRetrofit;
    private ConversationService conversationService;
    private FileService fileService;

    public NetApi(UserInfo userInfo, Env env, String oid, ImTokenGetter tokenGetter) {
        NetConfig config;
        switch (env) {
            case Development:
                config = new DevConfig();
                break;
            case Test:
                config = new TestConfig();
                break;
            case Prd:
                config = new PrdConfig();
                break;
            default:
                config = new DevConfig();
                break;
        }

        StringBuffer str = new StringBuffer();
        for (int i = 0, length = userInfo.getName().length(); i < length; i++) {
            char c = userInfo.getName().charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                str.append( String.format ("\\u%04x", (int)c) );
            } else {
                str.append(c);
            }
        }

        userInfo.setName(str.toString());

        HttpHeaderInterceptor httpHeaderInterceptor = new HttpHeaderInterceptor.Builder()
                .addHeaderParams("Content-Type", "application/json")
                .addHeaderParams("X-Hylaa-UserInfo", new Gson().toJson(userInfo))
                .addHeaderParams("X-Hylaa-TenantId", oid)
                .build();

        OkHttpClient serverClient = new OkHttpClient.Builder()
                .connectTimeout((long) config.getTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout((long) config.getTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout((long) config.getTimeout(), TimeUnit.MILLISECONDS)
                .addInterceptor(httpHeaderInterceptor)
                .addInterceptor(new ImTokenInterceptor(tokenGetter))
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        serverRetrofit = new Retrofit.Builder()
                .baseUrl(config.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(serverClient)
                .build();

        HttpHeaderInterceptor fileHttpHeaderInterceptor = new HttpHeaderInterceptor.Builder()
                .addHeaderParams("Content-Type", "multipart/form-data")
                .addHeaderParams("Accept", "Application/json")
                .build();

        OkHttpClient fileClient = new OkHttpClient.Builder()
                .connectTimeout((long) config.getTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout((long) config.getTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout((long) config.getTimeout(), TimeUnit.MILLISECONDS)
                .addInterceptor(fileHttpHeaderInterceptor)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        fileRetrofit = new Retrofit.Builder()
                .baseUrl(config.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(fileClient)
                .build();

        conversationService = serverRetrofit.create(ConversationService.class);
        fileService = fileRetrofit.create(FileService.class);
    }


    public Observable<Response<List<ConversationInfo>>> getConversations(Request params) {
        return conversationService.conversationList(params);
    }

    public Observable<Response<ConversationInfo>>  getConversation(Request params) {
        return conversationService.conversationDetail(params);
    }

    /**
     * 创建会话-【私聊】【群聊】.
     */
    public Observable<Response<ConversationInfo>>  createConversation(Request params) {
        return conversationService.conversationCreate(params);
    }

    /**
     * 会话修改
     *
     * @param params
     */
    public Observable<Response<ConversationInfo>>  modifyConversation(Request params) {
        return conversationService.conversationModify(params);
    }


    /**
     * 群主-添加其他成员
     */
    public Observable<Response<ConversationInfo>>  addMembers(Request params) {
        return conversationService.groupMoveIn(params);
    }


    /**
     * 群主-移除其他成员
     */
    public Observable<Response<ConversationInfo>>  removeMembers(Request params) {
        return conversationService.groupMoveOut(params);
    }


    /**
     * 解散群聊
     * <p>
     * { conversationId : xxx }
     */
    public Observable<Response<ConversationInfo>>  disbandGroup(Request params) {
        return conversationService.conversationDissolve(params);
    }

    public Observable<Response<ConversationInfo>>  saveConversation(Request params) {
        return conversationService.conversationSave(params);
    }
}
