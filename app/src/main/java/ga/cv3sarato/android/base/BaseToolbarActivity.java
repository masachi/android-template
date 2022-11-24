package ga.cv3sarato.android.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ga.cv3sarato.android.R;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseToolbarActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    protected CommonTitleBar toolbar;

    private LinearLayout parentLinearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initContentView(R.layout.activity_base);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            ButterKnife.bind(this);
        }
        super.onCreate(savedInstanceState);
        toolbar.setStatusBarColor(Color.parseColor("#5587ea"));
    }

    private void initContentView(@LayoutRes int layoutId) {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        parentLinearLayout = new LinearLayout(this);
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        viewGroup.addView(parentLinearLayout);
        LayoutInflater.from(this).inflate(layoutId, parentLinearLayout, true);
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
    }

    protected abstract int getLayoutId();

    public void setRightView(@LayoutRes int layoutId) {
        View view = LayoutInflater.from(getBaseContext()).inflate(layoutId, null, false);
        toolbar.setRightView(view);
    }

    public void setRightView(View view) {
        toolbar.setRightView(view);
    }

    public void setLeftView(@LayoutRes int layoutId) {
        View view = LayoutInflater.from(getBaseContext()).inflate(layoutId, null, false);
        toolbar.setLeftView(view);
    }

    public void setLeftView(View view) {
        toolbar.setLeftView(view);
    }

    public void setBarColor(@ColorRes int colorRes) {
        toolbar.setBackgroundColor(getResources().getColor(colorRes));
    }

    public void setBarColor(String color) {
        toolbar.setBackgroundColor(Color.parseColor(color));
    }

    public void setStatusBarColor(@ColorRes int colorRes) {
        toolbar.setStatusBarColor(getResources().getColor(colorRes));
        setStatusBarMode();
    }

    public void setStatusBarColor(String color) {
        toolbar.setStatusBarColor(Color.parseColor(color));
        setStatusBarMode();
    }

    private void setStatusBarMode() {
//        if(r*0.299 + g*0.578 + b*0.114 >= 192){ //浅色
//
//        }
        toolbar.toggleStatusBarMode();
    }
}
