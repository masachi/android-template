package ga.cv3sarato.android.activity.apply;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


@Route(value = "gtedx://applyHome")
public class ApplyHomeActivity extends BaseActivity {

    @BindView(R.id.apply_my_btn)
    Button applyMyBtn;
    @BindView(R.id.apply_add_leave_btn)
    Button applyAddLeaveBtn;
    @BindView(R.id.apply_add_trip_btn)
    Button applyAddTripBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_home;
    }

    @OnClick({R.id.apply_my_btn, R.id.apply_add_leave_btn, R.id.apply_add_trip_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.apply_my_btn:
                Router.build("gtedx://apply").go(this);
                break;
            case R.id.apply_add_leave_btn:
                Router.build("gtedx://leaveCreate").go(this);
                break;
            case R.id.apply_add_trip_btn:
                Router.build("gtedx://tripCreate").go(this);
                break;
        }
    }
}
