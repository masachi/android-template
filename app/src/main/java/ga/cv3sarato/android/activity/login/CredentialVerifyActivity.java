package ga.cv3sarato.android.activity.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.net.interceptor.HttpHeaderInterceptor;
import ga.cv3sarato.android.entity.request.user.CredentialEntity;
import ga.cv3sarato.android.entity.response.user.UserDetailEntity;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.UserService;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@Route(value = "gtedx://credentialVerify")
public class CredentialVerifyActivity extends BaseToolbarActivity {

    @BindView(R.id.credential_selector)
    NiceSpinner credentialSelector;
    @BindView(R.id.credential_input)
    EditText credentialInput;
    @BindView(R.id.credential_next_btn)
    Button credentialNextBtn;

    private List<String> selectorData = new LinkedList<>(Arrays.asList("身份证", "护照"));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() throws Exception {
        credentialSelector.attachDataSource(selectorData);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_credential_verify;
    }

    @OnClick(R.id.credential_next_btn)
    public void onViewClicked() {
        Request<CredentialEntity> request = new Request<>(new CredentialEntity("idcard", credentialInput.getText().toString()));
        ServerApi.getInstance(new HttpHeaderInterceptor.Builder().addHeaderParams("Content-Type", "application/json").build())
                .create(UserService.class)
                .verifyCredential(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<UserDetailEntity>() {
                    @Override
                    public void onNext(UserDetailEntity userDetailEntity) {
                        if(!userDetailEntity.getEmail().equals("") || !userDetailEntity.getMobile().equals("")) {
                            Router.build("gtedx://verifyAccount")
                                    .with("country_code", userDetailEntity.getCountry_code())
                                    .with("email", userDetailEntity.getEmail())
                                    .with("mobile", userDetailEntity.getMobile())
                                    .go(getBaseContext());
                        }
                    }
                });
    }
}
