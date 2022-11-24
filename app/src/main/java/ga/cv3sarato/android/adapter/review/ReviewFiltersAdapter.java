package ga.cv3sarato.android.adapter.review;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import ga.cv3sarato.android.entity.request.review.ReviewFilterEntity;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;

import java.util.ArrayList;
import java.util.List;

public class ReviewFiltersAdapter extends BaseRecyclerAdapter<ReviewFilterEntity> {

    private Context context;
    private List<ReviewFilterEntity> data;
    private SparseBooleanArray selectedPositions = new SparseBooleanArray();

    public ReviewFiltersAdapter(Context context, List<ReviewFilterEntity> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
        this.data = data;

        for(int i = 0; i< data.size(); i++) {
            selectedPositions.put(i, true);
        }
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, ReviewFilterEntity itemData, int position) {
        ImageView reviewFilterIconImageView = holder.getView(R.id.review_filter_icon_imageView);
        CheckBox reviewFilterCheckbox = holder.getView(R.id.review_filter_checkbox);
        TextView reviewFilterTypeTextView = holder.getView(R.id.review_filter_type_textView);

        GlideUtils.newInstance(context)
                .setPath(itemData.getIcon())
                .setImageView(reviewFilterIconImageView)
                .loadImage();

        reviewFilterTypeTextView.setText(itemData.getTypeText());
        reviewFilterCheckbox.setChecked(isItemChecked(position));

        holder.getRootView().setOnClickListener(view -> {
            if(onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });

        reviewFilterCheckbox.setOnClickListener(view -> {
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

    public ArrayList<String> getSelecedItem() {
        ArrayList<String> selectedItems = new ArrayList<>();
        for(int i = 0; i<data.size(); i++) {
            if(isItemChecked(i)) {
                selectedItems.add(data.get(i).getType());
            }
        }
        return selectedItems;
    }
}
