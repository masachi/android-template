package ga.cv3sarato.android.utils.dateTimePicker;

import android.app.DialogFragment;
import android.app.FragmentManager;

import java.util.Calendar;

public abstract class DateTimePicker extends DialogFragment{

    @Override
    public abstract void show(FragmentManager manager, String tag);

    public abstract DateTimePicker setDateCallback(DatePickerFragment.Callback callback);

    public abstract DateTimePicker setTimeCallback(TimePickerFragment.Callback callback);

    public abstract DateTimePicker setThemeDark(boolean themeDark);

    public abstract DateTimePicker setAccentColor(String color);

    public abstract DateTimePicker setAccentColor(int color);

    public abstract DateTimePicker setOKColor(String color);

    public abstract DateTimePicker setOKColor(int color);

    public abstract DateTimePicker setCancelColor(String color);

    public abstract DateTimePicker setCancelColor(int color);

    public abstract DateTimePicker setTitle(String title);

    public abstract DateTimePicker setOKText(int resId);

    public abstract DateTimePicker setCancelText(int resId);
}
