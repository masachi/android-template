package ga.cv3sarato.android.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.MainApplication;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseActivity;
import ga.cv3sarato.android.base.BaseFragmentPagerAdapter;
import ga.cv3sarato.android.entity.common.UserEntity;
import ga.cv3sarato.android.fragment.ChatFragment;
import ga.cv3sarato.android.fragment.HomeFragment;
import ga.cv3sarato.android.fragment.MailFragment;
import ga.cv3sarato.android.fragment.ProfileFragment;
import ga.cv3sarato.android.im_sdk.ImConfig;
import ga.cv3sarato.android.im_sdk.ImEvent;
import ga.cv3sarato.android.im_sdk.ImService;
import ga.cv3sarato.android.im_sdk.model.UserInfo;
import ga.cv3sarato.android.im_sdk.net.ImTokenGetter;
import ga.cv3sarato.android.im_sdk.type.Env;
import ga.cv3sarato.android.im_sdk.type.Platform;
import ga.cv3sarato.android.utils.cache.CacheApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import devlight.io.library.ntb.NavigationTabBar;

@Route(value = "gtedx://main")
public class MainActivity extends BaseActivity {

    private ImService imService = null;

    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;
    @BindView(R.id.main_tab)
    NavigationTabBar mainTab;

    @InjectParam(key = "page")
    int page;

    private long backPressTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

        initIM(MainApplication.getInstance().getUser());

        setSwipeBackEnable(false);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new MailFragment());
        fragments.add(new ChatFragment());
        fragments.add(new ProfileFragment());
        BaseFragmentPagerAdapter mainViewPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments);

        mainViewpager.setAdapter(mainViewPagerAdapter);

        ArrayList<NavigationTabBar.Model> tabModels = new ArrayList<>();
        tabModels.add(new NavigationTabBar.Model.Builder(
                null,
                Color.parseColor("#ffffff"))
                .title("首页")
                .build());

        tabModels.add(new NavigationTabBar.Model.Builder(
                null,
                Color.parseColor("#ffffff"))
                .title("邮箱")
                .build());

        tabModels.add(new NavigationTabBar.Model.Builder(
                null,
                Color.parseColor("#ffffff"))
                .title("聊天")
                .build());

        tabModels.add(new NavigationTabBar.Model.Builder(
                null,
                Color.parseColor("#ffffff"))
                .title("个人")
                .build());

        mainTab.setModels(tabModels);
        mainViewpager.setOffscreenPageLimit(2);
        mainViewpager.setCurrentItem(page);
        mainTab.setViewPager(mainViewpager);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(System.currentTimeMillis() - backPressTime > 2000) {
                Toast.setText("再按一次退出程序")
                        .setTintColor(Color.parseColor("#ff0000"))
                        .show();
                backPressTime = System.currentTimeMillis();
            }
            else {
                System.exit(0);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void initIM(UserEntity userEntity) {
        ImConfig options = new ImConfig();
        options.setPlatform(Platform.APP);
        options.setEnv(Env.Development);
        options.setClient_id(UUID.randomUUID().toString());
        options.setOid(MainApplication.getInstance().getTenantID());
        options.setUser_info(new UserInfo(userEntity.getUid(), userEntity.getName(), userEntity.getAvatar()));
        options.setTokenGetter(MainApplication.getInstance());


        this.imService = new ImService(options);
        MainApplication.getInstance().imClient = this.imService.createImClient(userEntity.getUid(), new CacheApp(userEntity.getUid()));

        MainApplication.getInstance().imClient.on(ImEvent.Connect, (params -> {
            System.err.println("connect");
        }));

        MainApplication.getInstance().imClient.on(ImEvent.Message, (params -> {
            System.err.println("message");
        }));

        MainApplication.getInstance().imClient.on(ImEvent.Error, (params -> {
            System.err.println("error");
        }));

        MainApplication.getInstance().imClient.on(ImEvent.End, (params -> {
            System.err.println("end");
        }));

        MainApplication.getInstance().imClient.on(ImEvent.Close, (params -> {
            System.err.println("close");
        }));
    }
}
