package ga.cv3sarato.android.activity.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.net.interceptor.HttpHeaderInterceptor;
import ga.cv3sarato.android.entity.request.user.PasswordResetEntity;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.UserService;

import java.util.HashMap;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@Route(value = "gtedx://resetPassword")
public class ResetPasswordActivity extends BaseToolbarActivity {

    @InjectParam(key = "key")
    String key;
    @BindView(R.id.password_input)
    EditText passwordInput;
    @BindView(R.id.password_repeat_input)
    EditText passwordRepeatInput;
    @BindView(R.id.confirm_btn)
    Button confirmBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @OnClick(R.id.confirm_btn)
    public void onViewClicked() {
        if(validatePassword()) {
            String uuid = UUID.randomUUID().toString();
            Request<PasswordResetEntity> request = new Request<>(new PasswordResetEntity(key, uuid));
            request.setSecure(new HashMap<String, String>() {{
                put(uuid, passwordInput.getText().toString());
            }});
            ServerApi.getInstance(new HttpHeaderInterceptor.Builder().addHeaderParams("Content-Type", "application/json").build())
                    .create(UserService.class)
                    .resetPassword(request)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new HylaaObserver<Object>() {
                        @Override
                        public void onNext(Object o) {
                            Router.build("gtedx://login").go(getBaseContext());
                            finish();
                        }
                    });
        }

    }

    private boolean validatePassword() {
        return !passwordInput.getText().toString().equals("") &&
               !passwordRepeatInput.getText().toString().equals("") &&
                passwordInput.getText().toString().equals(passwordRepeatInput.getText().toString());
    }
}
