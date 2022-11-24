package ga.cv3sarato.android;

import android.app.Application;
import android.content.Context;

import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate;
import com.baidu.mapapi.SDKInitializer;
import com.chenenyu.router.Configuration;
import com.chenenyu.router.Router;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import ga.cv3sarato.android.entity.request.common.RefreshTokenEntity;
import ga.cv3sarato.android.entity.response.common.TokenEntity;
import ga.cv3sarato.android.entity.common.UserEntity;
import ga.cv3sarato.android.im_sdk.im_client.ImClient;
import ga.cv3sarato.android.im_sdk.net.ImTokenGetter;
import ga.cv3sarato.android.net.DomainApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.UserService;
import ga.cv3sarato.android.utils.alive.DaemonReceiver1;
import ga.cv3sarato.android.utils.alive.DaemonReceiver2;
import ga.cv3sarato.android.utils.alive.DaemonService;
import ga.cv3sarato.android.utils.alive.JobAliveService;
import ga.cv3sarato.android.utils.toast.ToastUtils;
import com.marswin89.marsdaemon.DaemonClient;
import com.marswin89.marsdaemon.DaemonConfigurations;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import skin.support.SkinCompatManager;

public class MainApplication extends Application implements ImTokenGetter {
    //保活
    private DaemonClient mDaemonClient;

    private String accessToken = null;
    private String tenantID = null;
    private String refreshToken = null;

    private UserEntity user;

    public ImClient imClient = null;

    public ToastUtils Toast = new ToastUtils(this);
    private static MainApplication application;

    public static MainApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        //Toast
        Toasty.Config.getInstance()
                .apply();

        //Router
        Router.initialize(new Configuration.Builder()
                // 调试模式，开启后会打印log
                .setDebuggable(BuildConfig.DEBUG)
                // 模块名(即project.name)，每个使用Router的module都要在这里注册
                .registerModules("app")
                .build());
        //Skin
        SkinCompatManager.withoutActivity(this)                         // 基础控件换肤初始化
                .setSkinStatusBarColorEnable(true)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(true)                   // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();

        //Realm
        Realm.init(this);
        //大图查看
        BigImageViewer.initialize(GlideImageLoader.with(this));
        //Baidu 地图
        SDKInitializer.initialize(this);

        Observable.interval(180, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        if(MainApplication.getInstance().getAccessToken() != null) {
                            DomainApi.getInstance()
                                    .create(UserService.class)
                                    .refreshToken(new RefreshTokenEntity(MainApplication.getInstance().getRefreshToken()))
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new HylaaObserver<TokenEntity>() {
                                        @Override
                                        public void onNext(TokenEntity tokenEntity) {
                                            MainApplication.getInstance().setAccessToken(tokenEntity.getAccessToken());
                                            MainApplication.getInstance().setRefreshToken(tokenEntity.getRefreshToken());
                                        }
                                    });
                        }
                    }
                });
    }

    LocalizationApplicationDelegate localizationDelegate = new LocalizationApplicationDelegate(this);

    //刷新
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(localizationDelegate.attachBaseContext(base));

        //保活
        mDaemonClient = new DaemonClient(createDaemonConfigurations());
        mDaemonClient.onAttachBaseContext(base);
    }

    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        localizationDelegate.onConfigurationChanged(this);
    }

    @Override
    public Context getApplicationContext() {
        return localizationDelegate.getApplicationContext(super.getApplicationContext());
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    //保活
    private DaemonConfigurations createDaemonConfigurations(){
        DaemonConfigurations.DaemonConfiguration aliveConfiguration = new DaemonConfigurations.DaemonConfiguration(
                "ga.cv3sarato.android:daemon_service",
                JobAliveService.class.getCanonicalName(),
                DaemonReceiver1.class.getCanonicalName()
        );
        DaemonConfigurations.DaemonConfiguration daemonConfiguration = new DaemonConfigurations.DaemonConfiguration(
                "ga.cv3sarato.android:daemon_service",
                DaemonService.class.getCanonicalName(),
                DaemonReceiver2.class.getCanonicalName()
        );

        DaemonConfigurations.DaemonListener listener = new DaemonConfigurations.DaemonListener() {
            @Override
            public void onPersistentStart(Context context) {

            }

            @Override
            public void onDaemonAssistantStart(Context context) {

            }

            @Override
            public void onWatchDaemonDaed() {

            }
        };
        return new DaemonConfigurations(aliveConfiguration, daemonConfiguration, listener);
    }

    @Override
    public String getImAccessToken() {
        return accessToken;
    }
}
