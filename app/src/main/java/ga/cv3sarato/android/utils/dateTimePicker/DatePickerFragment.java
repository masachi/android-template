package ga.cv3sarato.android.utils.dateTimePicker;

import android.app.FragmentManager;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class DatePickerFragment extends DateTimePicker {

    private static Callback mCallback;
    private static DateTimePicker datePicker;
    private static DatePickerDialog mDatePicker;

    private static DatePickerListenerAdapter mListener = new DatePickerListenerAdapter() {
        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            if(mCallback != null){
                mCallback.onDateSet(view, year, monthOfYear, dayOfMonth);
            }
        }
    };

    public DatePickerFragment() {}


    public static DateTimePicker newInstance(Calendar calendar) {
        mDatePicker = DatePickerDialog.newInstance(mListener, calendar);
        mDatePicker.setVersion(DatePickerDialog.Version.VERSION_2);
        return new DatePickerFragment();
    }


    @Override
    public DateTimePicker setDateCallback(Callback callback) {
        mCallback = callback;
        return this;
    }

    @Override
    public DateTimePicker setTimeCallback(TimePickerFragment.Callback callback) {
        return this;
    }

    public interface Callback {
        void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        mDatePicker.show(manager,tag);
    }

    //Params

    @Override
    public DateTimePicker setThemeDark(boolean themeDark) {
        mDatePicker.setThemeDark(themeDark);
        return this;
    }

    @Override
    public DateTimePicker setAccentColor(String color){
        mDatePicker.setAccentColor(color);
        return this;
    }

    @Override
    public DateTimePicker setAccentColor(int color){
        mDatePicker.setAccentColor(color);
        return this;
    }

    @Override
    public DateTimePicker setOKColor(String color) {
        mDatePicker.setOkColor(color);
        return this;
    }

    @Override
    public DateTimePicker setOKColor(int color) {
        mDatePicker.setOkColor(color);
        return this;
    }

    @Override
    public DateTimePicker setCancelColor(String color) {
        mDatePicker.setCancelColor(color);
        return this;
    }

    @Override
    public DateTimePicker setCancelColor(int color) {
        mDatePicker.setCancelColor(color);
        return this;
    }

    @Override
    public DateTimePicker setTitle(String title) {
        mDatePicker.setTitle(title);
        return this;
    }

    @Override
    public DateTimePicker setOKText(int resId) {
        mDatePicker.setOkText(resId);
        return this;
    }

    @Override
    public DateTimePicker setCancelText(int resId) {
        mDatePicker.setCancelText(resId);
        return this;
    }
}
