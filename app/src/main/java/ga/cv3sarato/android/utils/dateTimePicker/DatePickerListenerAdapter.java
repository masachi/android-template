package ga.cv3sarato.android.utils.dateTimePicker;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

public abstract class DatePickerListenerAdapter implements DatePickerDialog.OnDateSetListener{

    @Override
    public abstract void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth);
}
