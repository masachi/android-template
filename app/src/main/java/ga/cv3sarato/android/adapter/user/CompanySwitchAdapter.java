package ga.cv3sarato.android.adapter.user;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import ga.cv3sarato.android.entity.response.user.CompanyEntity;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;

import java.util.List;

import butterknife.BindView;

public class CompanySwitchAdapter extends BaseRecyclerAdapter<CompanyEntity> {
    private Context context;
    private List<CompanyEntity> data;
    private int selectedPosition = -1;

    public CompanySwitchAdapter(Context context, List<CompanyEntity> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, CompanyEntity itemData, int position) {
        ImageView companySwitchIconImageView = holder.getView(R.id.company_switch_icon_imageView);
        TextView companySwitchNameTextView = holder.getView(R.id.company_switch_name_textView);
        RadioButton companySelectRadioBtn = holder.getView(R.id.company_select_radio_btn);
        ImageButton companySelectSubCompanyBtn = holder.getView(R.id.company_select_sub_company_btn);

        GlideUtils.newInstance(context)
                .setPath(itemData.getIcon())
                .setImageView(companySwitchIconImageView)
                .withDefaultHeaders()
                .loadImage();

        companySwitchNameTextView.setText(itemData.getName());
        if(itemData.getSubsets().size() > 0) {
            companySelectSubCompanyBtn.setVisibility(View.VISIBLE);
            companySelectSubCompanyBtn.setOnClickListener(view -> {
                if(onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(view, position);
                }
            });
        }
        else {
            companySelectRadioBtn.setVisibility(View.VISIBLE);
            companySelectRadioBtn.setOnClickListener(view -> {
                if(onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(view, position);
                }
            });

            companySelectRadioBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(isChecked) {
                    selectedPosition = position;
                }
            });

            if(position == selectedPosition) {
                companySelectRadioBtn.setChecked(true);
            }
            else {
                companySelectRadioBtn.setChecked(false);
            }
        }
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}
