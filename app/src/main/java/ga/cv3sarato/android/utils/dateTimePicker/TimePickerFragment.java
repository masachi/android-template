package ga.cv3sarato.android.utils.dateTimePicker;

import android.app.DialogFragment;
import android.app.FragmentManager;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class TimePickerFragment extends DateTimePicker {

    private static Callback mCallback;
    private static TimePickerFragment timePicker;
    private static TimePickerDialog mTimePicker;

    private static TimePickerListenerAdapter mListener = new TimePickerListenerAdapter() {
        @Override
        public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
            if(mCallback != null) {
                mCallback.onTimeSet(view, hourOfDay, minute, second);
            }
        }
    };

    public TimePickerFragment() { }

    public static DateTimePicker newInstance(Calendar calendar) {
        mTimePicker = TimePickerDialog.newInstance(
                mListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        mTimePicker.setVersion(TimePickerDialog.Version.VERSION_2);
        return new TimePickerFragment();
    }


    @Override
    public DateTimePicker setTimeCallback(Callback callback) {
        mCallback = callback;
        return this;
    }

    @Override
    public DateTimePicker setDateCallback(DatePickerFragment.Callback callback) {
        return this;
    }

    public interface Callback {
        void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        mTimePicker.show(manager, tag);
    }


    //Params

    @Override
    public DateTimePicker setThemeDark(boolean themeDark) {
        mTimePicker.setThemeDark(themeDark);
        return this;
    }

    @Override
    public DateTimePicker setAccentColor(String color){
        mTimePicker.setAccentColor(color);
        return this;
    }

    @Override
    public DateTimePicker setAccentColor(int color){
        mTimePicker.setAccentColor(color);
        return this;
    }

    @Override
    public DateTimePicker setOKColor(String color) {
        mTimePicker.setOkColor(color);
        return this;
    }

    @Override
    public DateTimePicker setOKColor(int color) {
        mTimePicker.setOkColor(color);
        return this;
    }

    @Override
    public DateTimePicker setCancelColor(String color) {
        mTimePicker.setCancelColor(color);
        return this;
    }

    @Override
    public DateTimePicker setCancelColor(int color) {
        mTimePicker.setCancelColor(color);
        return this;
    }

    @Override
    public DateTimePicker setTitle(String title) {
        mTimePicker.setTitle(title);
        return this;
    }

    @Override
    public DateTimePicker setOKText(int resId) {
        mTimePicker.setOkText(resId);
        return this;
    }

    @Override
    public DateTimePicker setCancelText(int resId) {
        mTimePicker.setCancelText(resId);
        return this;
    }
}
