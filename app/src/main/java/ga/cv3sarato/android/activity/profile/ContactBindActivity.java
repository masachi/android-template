package ga.cv3sarato.android.activity.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.MainApplication;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.entity.request.user.EmailCodeEntity;
import ga.cv3sarato.android.entity.request.user.EmailVerifyEntity;
import ga.cv3sarato.android.entity.request.user.MobileCodeEntity;
import ga.cv3sarato.android.entity.request.user.MobileVerifyEntity;
import ga.cv3sarato.android.entity.response.user.UserDetailEntity;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.AuthService;
import ga.cv3sarato.android.service.UserService;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@Route(value = "gtedx://contactBind")
public class ContactBindActivity extends BaseToolbarActivity {

    @BindView(R.id.bind_account_input)
    EditText bindAccountInput;
    @BindView(R.id.bind_code_input)
    EditText bindCodeInput;
    @BindView(R.id.bind_code_btn)
    Button bindCodeBtn;
    @BindView(R.id.bind_btn)
    Button bindBtn;

    @InjectParam(key = "type")
    String type;

    @InjectParam(key = "country_code")
    String countryCode;

    private long time = 0;
    private int VERIFY_COUNT = 90;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);
        TextView centerText = new TextView(this);
        centerText.setText(type);
        toolbar.setCenterView(centerText);
    }

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_bind;
    }

    @OnClick({R.id.bind_code_btn, R.id.bind_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bind_code_btn:
                bindCodeBtn.setClickable(false);
                Observable.intervalRange(0, VERIFY_COUNT + 1, 0, 1, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((along) -> {
                            time = VERIFY_COUNT - along;
                            bindCodeBtn.setText(String.valueOf(time));
                            if (time == 0) {
                                bindCodeBtn.setText("获取验证码");
                                bindCodeBtn.setClickable(true);
                            }
                        });
                if (time > 0) {
                    return;
                }
                Request codeRequest;
                AuthService codeService = ServerApi.defaultInstance().create(AuthService.class);
                if (type.toUpperCase().equals("EMAIL")) {
                    codeRequest = new Request<EmailCodeEntity>(new EmailCodeEntity("UPDATE_EMAIL", bindAccountInput.getText().toString()));
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
                    codeRequest = new Request<MobileCodeEntity>(new MobileCodeEntity("UPDATE_MOBILE", countryCode, bindAccountInput.getText().toString()));
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
            case R.id.bind_btn:
                Request request;
                UserService service = ServerApi.defaultInstance().create(UserService.class);
                if (type.toUpperCase().equals("EMAIL")) {
                    request = new Request<EmailVerifyEntity>(new EmailVerifyEntity("", bindAccountInput.getText().toString(), bindCodeInput.getText().toString()));
                    service.updateEmail(request)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new HylaaObserver<UserDetailEntity>() {
                                @Override
                                public void onNext(UserDetailEntity userDetailEntity) {
                                    Router.build("gtedx://main")
                                            .with("page", 3)
                                            .go(getBaseContext());
                                }
                            });
                } else {
                    request = new Request<MobileVerifyEntity>(new MobileVerifyEntity("", countryCode, bindAccountInput.getText().toString(), bindCodeInput.getText().toString()));
                    service.updateMobile(request)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new HylaaObserver<UserDetailEntity>() {
                                @Override
                                public void onNext(UserDetailEntity userDetailEntity) {
                                    Router.build("gtedx://main")
                                            .with("page", 3)
                                            .go(getBaseContext());
                                }
                            });
                }
                finishAffinity();
                break;
        }
    }
}
