package ga.cv3sarato.android.activity.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.entity.request.user.PasswordUpdateEntity;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.UserService;
import ga.cv3sarato.android.utils.persistence.SharedPreferenceUtils;

import java.util.HashMap;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Route(value = "gtedx://updatePassword")
public class UpdatePasswordActivity extends BaseToolbarActivity {

    @BindView(R.id.old_password_input)
    EditText oldPasswordInput;
    @BindView(R.id.new_password_input)
    EditText newPasswordInput;
    @BindView(R.id.repeat_password_input)
    EditText repeatPasswordInput;
    @BindView(R.id.update_password_btn)
    Button updatePasswordBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_password;
    }

    @OnClick(R.id.update_password_btn)
    public void onViewClicked() {
        if(validatePassword()) {
            String oldUUID = UUID.randomUUID().toString();
            String newUUID = UUID.randomUUID().toString();
            Request<PasswordUpdateEntity> request = new Request<>(new PasswordUpdateEntity(oldUUID, newUUID));
            request.setSecure(new HashMap<String, String>() {{
                put(oldUUID, oldPasswordInput.getText().toString());
                put(newUUID, repeatPasswordInput.getText().toString());

            }});
            ServerApi.defaultInstance()
                    .create(UserService.class)
                    .updatePassword(request)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new HylaaObserver<Object>() {
                        @Override
                        public void onNext(Object o) {
                            SharedPreferenceUtils.newInstance(getBaseContext()).removeData("username");
                            SharedPreferenceUtils.newInstance(getBaseContext()).removeData("password");
                            Router.build("gtedx://login").go(getBaseContext());
                        }
                    });
        }
    }

    private boolean validatePassword() {
        return !newPasswordInput.getText().toString().equals("") &&
               !repeatPasswordInput.getText().toString().equals("") &&
                newPasswordInput.getText().toString().equals(repeatPasswordInput.getText().toString());
    }
}
