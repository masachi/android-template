package ga.cv3sarato.android.adapter.apply;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ga.cv3sarato.android.MainApplication;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import ga.cv3sarato.android.constant.ApplicationType;
import ga.cv3sarato.android.constant.ReviewStatus;
import ga.cv3sarato.android.entity.response.apply.ApplysEntity;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;

import java.util.List;

public class ApplyMineAdapter extends BaseRecyclerAdapter<ApplysEntity.ApplyItem> {

    private Context context;
    private List<ApplysEntity.ApplyItem> data;

    public ApplyMineAdapter(Context context, List<ApplysEntity.ApplyItem> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, ApplysEntity.ApplyItem itemData, int position) {
        ImageView applyReviewsIconImageView = holder.getView(R.id.apply_reviews_icon_imageView);
        TextView applyReviewsTypeTextView = holder.getView(R.id.apply_reviews_type_textView);
        RelativeLayout applyReviewsStatusFrame = holder.getView(R.id.apply_reviews_status_frame);
        ImageView applyReviewsAvatarImageView = holder.getView(R.id.apply_reviews_avatar_imageView);
        TextView applyReviewsNameTextView = holder.getView(R.id.apply_reviews_name_textView);
        TextView applyReviewsCodeTextView = holder.getView(R.id.apply_reviews_code_textView);
        TextView applyReviewsTimeTextView = holder.getView(R.id.apply_reviews_time_textView);
        LinearLayout applyReviewsTimeFrame = holder.getView(R.id.apply_reviews_time_frame);
        TextView applyReviewsContentTypeTextView = holder.getView(R.id.apply_reviews_content_type_textView);
        TextView applyReviewsContentTextView = holder.getView(R.id.apply_reviews_content_textView);
        LinearLayout applyReviewsContentFrame = holder.getView(R.id.apply_reviews_content_frame);

        GlideUtils.newInstance(context)
                .setImageView(applyReviewsAvatarImageView)
                .setPath(MainApplication.getInstance().getUser().getAvatar())
                .withDefaultHeaders()
                .loadImage();

        applyReviewsNameTextView.setText(MainApplication.getInstance().getUser().getName());
        applyReviewsTimeTextView.setText(itemData.getPublishTime());
        applyReviewsCodeTextView.setText(itemData.getCode());
        applyReviewsTimeTextView.setText(itemData.getPublishTime());
        applyReviewsContentTextView.setText(itemData.getContent());
        switch (itemData.getType()) {
            case ApplicationType.LEAVE:
                applyReviewsTypeTextView.setText("请假");
                break;
            case ApplicationType.TRIP:
                applyReviewsTypeTextView.setText("出差");
                break;
            case ApplicationType.WORK_CREATE:
                applyReviewsTypeTextView.setText("创建工作");
                break;
            case ApplicationType.WORK_COMPLETE:
                applyReviewsTypeTextView.setText("完成工作");
                break;
            default:
                break;
        }

        switch (itemData.getReviewStatus()) {
            case ReviewStatus.PENDING:
                applyReviewsIconImageView.setImageResource(R.drawable.pending);
                break;
            case ReviewStatus.APPROVED:
                applyReviewsIconImageView.setImageResource(R.drawable.approve);
                break;
            case ReviewStatus.REFUSED:
                applyReviewsIconImageView.setImageResource(R.drawable.deny);
                break;
            case ReviewStatus.RETURNED:
                applyReviewsIconImageView.setImageResource(R.drawable.reject);
                break;
            case ReviewStatus.CLOSED:
                applyReviewsIconImageView.setImageResource(R.drawable.close);
                break;
            default:
                break;
        }

        holder.getRootView().setOnClickListener(view -> {
            if(onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });
    }
}
