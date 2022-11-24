package ga.cv3sarato.android.adapter.attendance;

import android.content.Context;
import android.widget.TextView;

import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import ga.cv3sarato.android.entity.response.attendance.CheckInHistoryEntity;

import java.util.List;

public class CheckInHistoryAdapter extends BaseRecyclerAdapter<CheckInHistoryEntity> {
    private Context context;
    private List<CheckInHistoryEntity> data;

    public CheckInHistoryAdapter(Context context, List<CheckInHistoryEntity> data, int layoutId) {
        super(context, data, layoutId);
        this.data = data;
        this.context = context;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, CheckInHistoryEntity itemData, int position) {
        TextView checkInTimeTextView = holder.getView(R.id.check_in_time_textView);
        TextView checkInPlaceTextView = holder.getView(R.id.check_in_place_textView);

        checkInTimeTextView.setText(itemData.getTime().substring(11));
        checkInPlaceTextView.setText(itemData.getPlace());
    }
}
