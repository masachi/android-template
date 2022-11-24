package ga.cv3sarato.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.MainApplication;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseActivity;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.LoginService;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;
import ga.cv3sarato.android.utils.persistence.SharedPreferenceUtils;


import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class SplashActivity extends BaseActivity {
    @BindView(R.id.splashImg)
    ImageView splashImg;
    @BindView(R.id.skip_btn)
    TextView skipBtn;
    @BindView(R.id.timeAttack_textView)
    TextView timeAttackTextView;
    private Handler userServiceHandler;
    private Runnable redirectRunnable;
    private int duration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String splashURL = (String) SharedPreferenceUtils.newInstance(this).getData("splash", "");
        duration = (int) SharedPreferenceUtils.newInstance(this).getData("duration", 5000);
        if (!splashURL.equals("")) {
            GlideUtils.newInstance(this)
                    .setImageView(splashImg)
                    .setPath(splashURL)
                    .loadImage();
        }

        if(duration < 5000) {
            duration = 5000;
        }

        timeAttackTextView.setText(String.valueOf(duration / 1000));
        Observable.intervalRange(0, duration / 1000, 0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        timeAttackTextView.setText(String.valueOf((duration / 1000) - aLong));
                    }
                });
        jumpTo(duration);
    }

    public void jumpTo(long duration) {
        String username = (String) SharedPreferenceUtils.newInstance(this).getData("username", new String());
        String password = (String) SharedPreferenceUtils.newInstance(this).getData("password", new String());
        String tenantID = (String) SharedPreferenceUtils.newInstance(this).getData("tenantID", "");

        MainApplication.getInstance().setTenantID(tenantID);

        if (username.equals("") && password.equals("")) {
            skipBtn.setVisibility(View.VISIBLE);
        } else {
            LoginService.login(this, username, password, true, this);
        }

        userServiceHandler = new Handler();
        redirectRunnable = () -> {
            if (MainApplication.getInstance().getAccessToken() == null) {
                Router.build("gtedx://login").go(getBaseContext());
            } else {
                Router.build("gtedx://main")
                        .with("page", 0)
                        .go(this);
            }
            finishAffinity();
        };
        userServiceHandler.postDelayed(redirectRunnable, duration);
    }

    @Override
    public void init() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @OnClick(R.id.skip_btn)
    public void onViewClicked() {
        userServiceHandler.removeCallbacks(redirectRunnable);
        if (MainApplication.getInstance().getAccessToken() == null) {
            Router.build("gtedx://login").go(getBaseContext());
        } else {
            Router.build("gtedx://main")
                    .with("page", 0)
                    .go(this);
        }
        finishAffinity();
    }

    @Override
    public void onBackPressed() {

    }
}
