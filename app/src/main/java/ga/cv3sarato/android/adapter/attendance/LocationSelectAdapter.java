package ga.cv3sarato.android.adapter.attendance;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;

import java.util.List;

public class LocationSelectAdapter extends BaseRecyclerAdapter<PoiInfo> {
    private List<PoiInfo> data;

    public LocationSelectAdapter(Context context, List<PoiInfo> data, int layoutId) {
        super(context, data, layoutId);
        this.data = data;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, PoiInfo itemData, int position) {
        TextView locationName = holder.getView(R.id.location_name_textView);
        TextView locationAddress = holder.getView(R.id.location_address_textView);

        locationName.setText(itemData.name);
        locationAddress.setText(itemData.address);

        holder.getRootView().setOnClickListener((view) -> {
            if(onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });
    }
}
