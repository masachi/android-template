package ga.cv3sarato.android.utils.toast;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;



import ga.cv3sarato.android.constant.ToastEnum;

import es.dmoral.toasty.Toasty;

public class ToastUtils extends Toast {

    private Context context;
    private String text;
    private int duration = android.widget.Toast.LENGTH_SHORT;
    private boolean withIcon = false;
    private int icon;
    private int color = Color.GRAY;


    public ToastUtils(Context context) {
        this.context = context;
    }

    private Drawable getDrawable(@DrawableRes int id) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                return context.getDrawable(id);
            else
                return context.getResources().getDrawable(id);
        }
        catch (Exception e){
            return null;
        }

    }

    @Override
    public void show() {
        Toasty.custom(context, text, getDrawable(icon), color, duration, withIcon, true).show();
    }

    @Override
    public Toast setDuration(int seconds) {
        this.duration = seconds;
        return this;
    }

    @Override
    public Toast setIcon(int resId) {
        this.icon = resId;
        return this;
    }

    @Override
    public Toast withIcon(boolean icon) {
        this.withIcon = icon;
        return this;
    }

    @Override
    public Toast setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public Toast setTintColor(int color) {
        this.color = color;
        return this;
    }
}
