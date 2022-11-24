package ga.cv3sarato.android.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.MainApplication;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.net.interceptor.HttpHeaderInterceptor;
import ga.cv3sarato.android.entity.request.user.EmailCodeEntity;
import ga.cv3sarato.android.entity.request.user.EmailVerifyEntity;
import ga.cv3sarato.android.entity.request.user.MobileCodeEntity;
import ga.cv3sarato.android.entity.request.user.MobileVerifyEntity;
import ga.cv3sarato.android.entity.response.user.VerifyEntity;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.AuthService;


import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@Route(value = "gtedx://verifyAccount")
public class AccountVerifyActivity extends BaseToolbarActivity {

    @BindView(R.id.verify_account_input)
    EditText verifyAccountInput;
    @BindView(R.id.verify_code_input)
    EditText verifyCodeInput;
    @BindView(R.id.verify_code_btn)
    Button verifyCodeBtn;
    @BindView(R.id.verify_next_btn)
    Button verifyNextBtn;
    @InjectParam(key = "country_code")
    String countryCode;

    @InjectParam(key = "mobile")
    String mobile;

    @InjectParam(key = "email")
    String email;

    private long time = 0;
    private int VERIFY_COUNT = 90;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() throws Exception {
        if (!email.equals("")) {
            verifyAccountInput.setText(email);
        } else {
//            String account = countryCode + "" + mobile;
//            verifyAccountInput.setText(account);
            verifyAccountInput.setText(mobile);
        }
        verifyCodeBtn.requestFocus();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_verify;
    }

    @OnClick({R.id.verify_code_btn, R.id.verify_next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.verify_code_btn:
                verifyCodeBtn.setClickable(false);
                Observable.intervalRange(0, VERIFY_COUNT + 1, 0, 1, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((along) -> {
                            time = VERIFY_COUNT - along;
                            verifyCodeBtn.setText(String.valueOf(time));
                            if (time == 0) {
                                verifyCodeBtn.setText("获取验证码");
                                verifyCodeBtn.setClickable(true);
                            }
                        });
                if (time > 0) {
                    return;
                }
                Request codeRequest;
                AuthService codeService = ServerApi.defaultInstance().create(AuthService.class);
                if (verifyAccountInput.getText().toString().contains("@")) {
                    codeRequest = new Request<EmailCodeEntity>(new EmailCodeEntity("UPDATE_EMAIL", verifyAccountInput.getText().toString()));
                    codeService.getEmailAuthCode(codeRequest)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new HylaaObserver<Object>() {
                                @Override
                                public void onNext(Object o) {
                                    Toast.setText("发送成功")
                                            .setDuration(10)
                                            .show();
                                }
                            });
                } else {
                    codeRequest = new Request<MobileCodeEntity>(new MobileCodeEntity("UPDATE_MOBILE", countryCode, verifyAccountInput.getText().toString()));
                    codeService.getMobileAuthCode(codeRequest)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new HylaaObserver<Object>() {
                                @Override
                                public void onNext(Object o) {
                                    Toast.setText("发送成功")
                                            .setDuration(10)
                                            .show();
                                }
                            });
                }
                break;
            case R.id.verify_next_btn:
                Request request;
                AuthService service = ServerApi.getInstance(new HttpHeaderInterceptor.Builder().addHeaderParams("Content-Type", "application/json").build()).create(AuthService.class);
                if (verifyAccountInput.getText().toString().contains("@")) {
                    request = new Request<EmailVerifyEntity>(new EmailVerifyEntity("FORGET_PASSWORD", verifyAccountInput.getText().toString(), verifyCodeInput.getText().toString()));
                    service.verifyEmail(request)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new HylaaObserver<VerifyEntity>() {
                                @Override
                                public void onNext(VerifyEntity verifyEntity) {
                                    Router.build("").with("key", verifyEntity.getKey()).go(getBaseContext());
                                }
                            });
                } else {
                    request = new Request<MobileVerifyEntity>(new MobileVerifyEntity("FORGET_PASSWORD", countryCode, verifyAccountInput.getText().toString(), verifyCodeInput.getText().toString()));
                    service.verifyMobile(request)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new HylaaObserver<VerifyEntity>() {
                                @Override
                                public void onNext(VerifyEntity verifyEntity) {
                                    Router.build("").with("key", verifyEntity.getKey()).go(getBaseContext());
                                }
                            });
                }

                break;
        }
    }
}
