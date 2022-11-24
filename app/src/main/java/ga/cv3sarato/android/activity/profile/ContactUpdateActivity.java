package ga.cv3sarato.android.activity.profile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseToolbarActivity;

import butterknife.BindView;
import butterknife.OnClick;


@Route(value = "gtedx://contactUpdate")
public class ContactUpdateActivity extends BaseToolbarActivity {

    @BindView(R.id.original_contact_textView)
    TextView originalContactTextView;
    @BindView(R.id.confirm_update_btn)
    Button confirmUpdateBtn;

    @InjectParam(key = "original")
    String originalContact;

    @InjectParam(key = "type")
    String type;

    @InjectParam(key = "country_code")
    String countryCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {
        originalContactTextView.setText(originalContact);
        if(type.toUpperCase().equals("EMAIL")) {
            confirmUpdateBtn.setText("更换邮箱");
        }
        else {
            confirmUpdateBtn.setText("更换手机号");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_update;
    }

    @OnClick(R.id.confirm_update_btn)
    public void onViewClicked() {
        Router.build("gtedx://contactBind")
                .with("type", type)
                .with("country_code", countryCode)
                .go(this);
    }
}
