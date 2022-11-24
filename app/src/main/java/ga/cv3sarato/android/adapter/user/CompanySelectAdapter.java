package ga.cv3sarato.android.adapter.user;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import ga.cv3sarato.android.entity.response.user.CompanyEntity;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;

import java.util.List;

import butterknife.BindView;

public class CompanySelectAdapter extends BaseRecyclerAdapter<CompanyEntity> {
    private Context context;
    private List<CompanyEntity> data;

    public CompanySelectAdapter(Context context, List<CompanyEntity> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, CompanyEntity itemData, int position) {
        ImageView companySelectIconImageView = holder.getView(R.id.company_select_icon_imageView);
        TextView companySelectNameTextView = holder.getView(R.id.company_select_name_textView);
        LinearLayout companySelectFrame = holder.getView(R.id.company_select_frame);

        GlideUtils.newInstance(context)
                .setPath(itemData.getIcon())
                .setImageView(companySelectIconImageView)
                .withDefaultHeaders()
                .loadImage();

        companySelectNameTextView.setText(itemData.getName());

        companySelectFrame.setOnClickListener(view -> {
            if(onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });
    }
}
