package ga.cv3sarato.android.adapter.review;

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
import ga.cv3sarato.android.entity.response.review.ReviewsEntity;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;

import java.util.List;

public class ReviewMineAdapter extends BaseRecyclerAdapter<ReviewsEntity.ReviewItem> {

    private Context context;
    private List<ReviewsEntity.ReviewItem> data;

    public ReviewMineAdapter(Context context, List<ReviewsEntity.ReviewItem> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, ReviewsEntity.ReviewItem itemData, int position) {
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
                .setPath(itemData.getCreator().getAvatar())
                .withDefaultHeaders()
                .loadImage();

        applyReviewsNameTextView.setText(itemData.getCreator().getName());
        applyReviewsTimeTextView.setText(itemData.getPublishTime());
        applyReviewsCodeTextView.setText(itemData.getCode());
        applyReviewsTimeTextView.setText(itemData.getPublishTime());
        applyReviewsContentTextView.setText(itemData.getContent());
        switch (itemData.getType()) {
            case ApplicationType.LEAVE:
                applyReviewsTypeTextView.setText("??????");
                break;
            case ApplicationType.TRIP:
                applyReviewsTypeTextView.setText("??????");
                break;
            case ApplicationType.WORK_CREATE:
                applyReviewsTypeTextView.setText("????????????");
                break;
            case ApplicationType.WORK_COMPLETE:
                applyReviewsTypeTextView.setText("????????????");
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
            if (onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });
    }
}
