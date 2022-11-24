package ga.cv3sarato.android.activity.login;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.MainApplication;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.service.LoginService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@Route(value = "gtedx://login")
public class LoginActivity extends BaseToolbarActivity {


    @BindView(R.id.username_input)
    EditText usernameInput;
    @BindView(R.id.password_input)
    EditText passwordInput;
    @BindView(R.id.login_Btn)
    Button loginBtn;
    @BindView(R.id.forget_password_btn)
    Button forgetPasswordBtn;

    private long backPressTime = 0;

    @Override
    protected void init() throws Exception {
        toolbar.setBackgroundColor(Color.parseColor("#f5f5f5"));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.login_Btn, R.id.forget_password_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_Btn:
                LoginService.login(this, usernameInput.getText().toString(), passwordInput.getText().toString(), false, this);
                break;
            case R.id.forget_password_btn:
                Router.build("gtedx://credentialVerify").go(this);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(System.currentTimeMillis() - backPressTime > 2000) {
                Toast
                        .setText("再按一次退出程序")
                        .setTintColor(Color.parseColor("#ff0000"))
                        .show();
                backPressTime = System.currentTimeMillis();
            }
            else {
                System.exit(0);
                finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
