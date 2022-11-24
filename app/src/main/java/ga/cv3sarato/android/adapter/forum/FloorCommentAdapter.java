package ga.cv3sarato.android.adapter.forum;

import android.content.Context;
import android.widget.TextView;

import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import ga.cv3sarato.android.entity.response.forum.ForumFloorsEntity;

import java.util.List;

import butterknife.BindView;

public class FloorCommentAdapter extends BaseRecyclerAdapter<ForumFloorsEntity.Floor.FloorCommentEntity> {

    private Context context;
    private List<ForumFloorsEntity.Floor.FloorCommentEntity> comments;

    public FloorCommentAdapter(Context context, List<ForumFloorsEntity.Floor.FloorCommentEntity> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
        this.comments = data;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, ForumFloorsEntity.Floor.FloorCommentEntity itemData, int position) {
        TextView commentNameTextView = holder.getView(R.id.comment_name_textView);
        TextView commentTimeTextView = holder.getView(R.id.comment_time_textView);
        TextView commentContentTextView = holder.getView(R.id.comment_content_textView);

        commentNameTextView.setText(itemData.getCreator().getName());
        commentTimeTextView.setText(itemData.getCreateTime());
        commentContentTextView.setText(itemData.getContent());

        holder.getRootView().setOnClickListener(view -> {
            if(onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });
    }
}
