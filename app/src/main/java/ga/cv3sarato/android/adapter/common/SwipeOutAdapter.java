package ga.cv3sarato.android.adapter.common;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import ga.cv3sarato.android.entity.response.contact.ContactEntity;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;

import java.util.List;

import butterknife.BindView;

public class SwipeOutAdapter extends BaseRecyclerAdapter<ContactEntity.ContactItem> {

    private Context context;
    private List<ContactEntity.ContactItem> data;

    public SwipeOutAdapter(Context context, List<ContactEntity.ContactItem> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, ContactEntity.ContactItem itemData, int position) {
        LinearLayout swipeOutBottomFrame = holder.getView(R.id.swipe_out_bottom_frame);
        ImageView swipeOutAvatarImageView = holder.getView(R.id.swipe_out_avatar_imageView);
        TextView swipeOutNameTextView = holder.getView(R.id.swipe_out_name_textView);
        SwipeLayout swipeLayout = holder.getView(R.id.swipe_out_frame);

        GlideUtils.newInstance(context)
                .setPath(itemData.getAvatar())
                .setImageView(swipeOutAvatarImageView)
                .withDefaultHeaders()
                .loadImage();
        swipeOutNameTextView.setText(itemData.getName());
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeOutBottomFrame.setOnClickListener(view -> {
            if(onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });
    }
}
