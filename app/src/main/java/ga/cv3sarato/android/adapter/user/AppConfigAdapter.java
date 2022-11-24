package ga.cv3sarato.android.adapter.user;

import android.content.Context;
import android.widget.Button;

import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import ga.cv3sarato.android.entity.response.user.AppConfigEntity;

import java.util.List;

public class AppConfigAdapter extends BaseRecyclerAdapter<AppConfigEntity> {

    private Context context;
    private List<AppConfigEntity> appConfig;

    public AppConfigAdapter(Context context, List<AppConfigEntity> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
        this.appConfig = data;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, AppConfigEntity itemData, int position) {
        Button configBtn = holder.getView(R.id.config_btn);
        configBtn.setText(itemData.getName());

        configBtn.setOnClickListener(view -> {
            if(onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });
    }
}
