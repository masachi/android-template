package ga.cv3sarato.android.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseActivity;
import ga.cv3sarato.android.base.BaseToolbarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@Route(value = "gtedx://drawer")
public class DrawerActivity extends BaseToolbarActivity {

    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.processed_commit)
    TextView processedCommit;
    @BindView(R.id.drawer_content)
    LinearLayout drawerContent;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_drawer;
    }

    @Override
    protected void init() {
        ViewGroup.LayoutParams params = drawerContent.getLayoutParams();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        params.height = metrics.heightPixels;
        params.width = metrics.widthPixels;
        drawerContent.setLayoutParams(params);
    }

    @OnClick({R.id.btn, R.id.processed_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                drawerLayout.openDrawer(drawerContent);
                break;
            case R.id.processed_commit:
                drawerLayout.closeDrawers();
                break;
        }
    }
}
