package ga.cv3sarato.android.utils.tabBar;

import android.content.res.ColorStateList;
import android.support.v4.view.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabIndicationInterpolator;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public interface TabBar {

    void setIndicatorInterpolation(SmartTabIndicationInterpolator interpolator);

    void setTabColorizer(SmartTabLayout.TabColorizer tabColorizer);

    void setDefaultTextColor(int color);

    void setDefaultTextColor(ColorStateList colors);

    void setEvenDistribute(boolean even);

    void setSelectIndicatorColors(int... colors);

    void setDivideColors(int... colors);

    void setPageChangeListener(ViewPager.OnPageChangeListener listener);

    void setScrollChangeListener(SmartTabLayout.OnScrollChangeListener listener);

    void setTabClickListener(SmartTabLayout.OnTabClickListener listener);

    void setTabView(int layoutResId, int textViewId);

    void setTabView(SmartTabLayout.TabProvider provider);
}
