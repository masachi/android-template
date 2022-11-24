package ga.cv3sarato.android.adapter.common;

import android.content.Context;
import android.widget.ImageButton;

import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import ga.cv3sarato.android.entity.file.FileEntity;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;

import java.util.List;

public class CommonImageAdapter extends BaseRecyclerAdapter<FileEntity> {
    private Context context;
    private List<FileEntity> files;

    public CommonImageAdapter(Context context, List<FileEntity> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
        this.files = data;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, FileEntity itemData, int position) {
        ImageButton commonImageBtn = holder.getView(R.id.common_image_btn);

        GlideUtils.newInstance(context)
                .setImageView(commonImageBtn)
                .setPath(itemData.getViewUrl())
                .withDefaultHeaders()
                .loadImage();

        commonImageBtn.setOnClickListener(view -> {
            if(onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });
    }
}
