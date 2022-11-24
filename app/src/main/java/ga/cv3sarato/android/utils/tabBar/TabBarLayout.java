package ga.cv3sarato.android.utils.tabBar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.ogaclejapan.smarttablayout.SmartTabIndicationInterpolator;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class TabBarLayout extends SmartTabLayout implements TabBar{

    public TabBarLayout(Context context) {
        super(context);
    }

    public TabBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabBarLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setTabTextColor(int color){
        this.setDefaultTabTextColor(color);
    }

    public void setTabTextColor(ColorStateList color){
        this.setDefaultTabTextColor(color);
    }

    @Override
    public void setIndicatorInterpolation(SmartTabIndicationInterpolator interpolator) {
        this.setIndicationInterpolator(interpolator);
    }

    @Override
    public void setTabColorizer(TabColorizer tabColorizer) {
        this.setCustomTabColorizer(tabColorizer);
    }

    @Override
    public void setDefaultTextColor(int color) {
        this.setDefaultTabTextColor(color);
    }

    @Override
    public void setDefaultTextColor(ColorStateList colors) {
        this.setDefaultTabTextColor(colors);
    }

    @Override
    public void setEvenDistribute(boolean even) {
        this.setDistributeEvenly(even);
    }

    @Override
    public void setSelectIndicatorColors(int... colors) {
        this.setSelectedIndicatorColors(colors);
    }

    @Override
    public void setDivideColors(int... colors) {
        this.setDividerColors(colors);
    }

    @Override
    public void setPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.setOnPageChangeListener(listener);
    }

    @Override
    public void setScrollChangeListener(OnScrollChangeListener listener) {
        this.setOnScrollChangeListener(listener);
    }

    @Override
    public void setTabClickListener(OnTabClickListener listener) {
        this.setOnTabClickListener(listener);
    }

    @Override
    public void setTabView(int layoutResId, int textViewId) {
        this.setCustomTabView(layoutResId, textViewId);
    }

    @Override
    public void setTabView(TabProvider provider) {
        this.setCustomTabView(provider);
    }
}
