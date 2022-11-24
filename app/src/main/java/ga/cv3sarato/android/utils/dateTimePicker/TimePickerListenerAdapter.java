package ga.cv3sarato.android.utils.dateTimePicker;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

public abstract class TimePickerListenerAdapter implements TimePickerDialog.OnTimeSetListener{

    @Override
    public abstract void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second);
}
