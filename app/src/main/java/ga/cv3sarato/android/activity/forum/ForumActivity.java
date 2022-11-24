package ga.cv3sarato.android.activity.forum;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.fragment.ForumAllFragment;
import ga.cv3sarato.android.fragment.ForumFollowFragment;
import ga.cv3sarato.android.fragment.ForumMineFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;

@Route(value = "gtedx://forum")
public class ForumActivity extends BaseToolbarActivity implements View.OnClickListener{

    @BindView(R.id.forum_tabBar)
    SmartTabLayout forumTabBar;
    @BindView(R.id.forum_viewPager)
    ViewPager forumViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {
        FragmentPagerItemAdapter forumAdapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                .add("所有", ForumAllFragment.class)
                .add("我的", ForumMineFragment.class)
                .add("收藏", ForumFollowFragment.class)
                .create()
        );

        forumViewPager.setAdapter(forumAdapter);
        forumTabBar.setViewPager(forumViewPager);
        forumViewPager.setOffscreenPageLimit(2);

        Button newPostBtn = new Button(this);
        newPostBtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        newPostBtn.setText("新建");
        toolbar.setRightView(newPostBtn);
        newPostBtn.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forum;
    }

    @Override
    public void onClick(View view) {
        Router.build("gtedx://forumNewPost").go(this);
    }
}
