package ga.cv3sarato.android.adapter.apply;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import ga.cv3sarato.android.entity.request.apply.ApplyFilterEntity;
import ga.cv3sarato.android.entity.response.contact.ContactEntity;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ApplyFiltersAdapter extends BaseRecyclerAdapter<ApplyFilterEntity> {

    private Context context;
    private List<ApplyFilterEntity> data;
    private SparseBooleanArray selectedPositions = new SparseBooleanArray();

    public ApplyFiltersAdapter(Context context, List<ApplyFilterEntity> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
        this.data = data;

        for(int i = 0; i< data.size(); i++) {
            selectedPositions.put(i, true);
        }
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, ApplyFilterEntity itemData, int position) {
        ImageView applyFilterIconImageView = holder.getView(R.id.apply_filter_icon_imageView);
        CheckBox applyFilterCheckbox = holder.getView(R.id.apply_filter_checkbox);
        TextView applyFilterTypeTextView = holder.getView(R.id.apply_filter_type_textView);

        GlideUtils.newInstance(context)
                .setPath(itemData.getIcon())
                .setImageView(applyFilterIconImageView)
                .loadImage();

        applyFilterTypeTextView.setText(itemData.getTypeText());
        applyFilterCheckbox.setChecked(isItemChecked(position));

        holder.getRootView().setOnClickListener(view -> {
            if(onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });

        applyFilterCheckbox.setOnClickListener(view -> {
            if(onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });
    }

    public void setItemChecked(int position, boolean isChecked) {
        selectedPositions.put(position, isChecked);
    }

    public boolean isItemChecked(int position) {
        return selectedPositions.get(position);
    }

    public ArrayList<String> getSelectedItem() {
        ArrayList<String> selectedItems = new ArrayList<>();
        for(int i = 0; i<data.size(); i++) {
            if(isItemChecked(i)) {
                selectedItems.add(data.get(i).getType());
            }
        }
        return selectedItems;
    }
}
