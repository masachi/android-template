package ga.cv3sarato.android.utils.toast;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;

import ga.cv3sarato.android.constant.ToastEnum;

public abstract class Toast {

    public abstract void show();

    public abstract Toast setDuration(int seconds);

    public abstract Toast setIcon(@DrawableRes int resId);

    public abstract Toast withIcon(boolean icon);

    public abstract Toast setText(String text);

    public abstract Toast setTintColor(@ColorInt int color);

}
