package ga.cv3sarato.android.adapter.common;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;

import java.util.List;

import butterknife.BindView;

public class ImageSelectAdapter extends BaseRecyclerAdapter<String> {
    private Context context;
    private List<String> data;

    public ImageSelectAdapter(Context context, List<String> data, int layoutId) {
        super(context, data, layoutId);
        this.data = data;
        this.context = context;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, String itemData, int position) {
        ImageButton selectedImageView = holder.getView(R.id.selected_imageView);
        Button deleteSelectedBtn = holder.getView(R.id.delete_selected_btn);

        if(!itemData.toUpperCase().equals("ADD")) {
            GlideUtils.newInstance(context)
                    .setPath(itemData)
                    .setImageView(selectedImageView)
                    .loadImage();

            deleteSelectedBtn.setVisibility(View.VISIBLE);
        }
        else {
            GlideUtils.newInstance(context)
                    .setPath(R.drawable.image_add)
                    .setImageView(selectedImageView)
                    .loadImage();
            deleteSelectedBtn.setVisibility(View.GONE);
        }

        deleteSelectedBtn.setOnClickListener(view -> {
            if(onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });

        selectedImageView.setOnClickListener(view -> {
            if(onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });
    }
}
