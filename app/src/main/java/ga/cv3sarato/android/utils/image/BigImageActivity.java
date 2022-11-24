package ga.cv3sarato.android.utils.image;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseActivity;
import ga.cv3sarato.android.base.BaseFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

@Route(value = "gtedx://bigImage")
public class BigImageActivity extends BaseActivity {

    @InjectParam(key = "uri")
    List<String> uri;
    @InjectParam(key = "index")
    int index;
    @BindView(R.id.bigImageViewPager)
    ViewPager bigImageViewPager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        List<Fragment> fragments = new ArrayList<>();
        for (String image : uri) {
            fragments.add(BigImageFragment.newInstance(image));
        }

        BaseFragmentPagerAdapter bigImageViewPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        bigImageViewPager.setAdapter(bigImageViewPagerAdapter);
        bigImageViewPager.setCurrentItem(index);
        bigImageViewPager.setOffscreenPageLimit(uri.size() - 1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_big_image;
    }
}
