package ga.cv3sarato.android.adapter.apply;

import android.content.Context;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import ga.cv3sarato.android.constant.ApplicationType;
import ga.cv3sarato.android.constant.Operation;
import ga.cv3sarato.android.entity.response.apply.LeaveTripDetailEntity;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;

import java.util.List;

import butterknife.BindView;

public class ReviewProgressAdapter extends BaseRecyclerAdapter<LeaveTripDetailEntity.ReviewProgress> {

    private Context context;
    private List<LeaveTripDetailEntity.ReviewProgress> data;
    private String type;

    public ReviewProgressAdapter(Context context, List<LeaveTripDetailEntity.ReviewProgress> data, int layoutId, String type) {
        super(context, data, layoutId);
        this.context = context;
        this.data = data;
        this.type = type;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, LeaveTripDetailEntity.ReviewProgress itemData, int position) {
        ImageView reviewProgressStatusImageView = holder.getView(R.id.review_progress_status_imageView);
        ImageView reviewProgressAvatarImageView = holder.getView(R.id.review_progress_avatar_imageView);
        TextView reviewProgressNameTextView = holder.getView(R.id.review_progress_name_textView);
        TextView reviewProgressTimeTextView = holder.getView(R.id.review_progress_time_textView);
        TextView reviewProgressRemarkTextView = holder.getView(R.id.review_progress_remark_textView);

        GlideUtils.newInstance(context)
                .setPath(itemData.getCreator().getAvatar())
                .setImageView(reviewProgressAvatarImageView)
                .withDefaultHeaders()
                .loadImage();

        reviewProgressNameTextView.setText(itemData.getCreator().getName());
        reviewProgressTimeTextView.setText(itemData.getCreateTime());

        String remark = "";
        int icon = 0;
        switch (itemData.getOperation()) {
            case Operation.CREATE:
                remark = "提交";
                switch (type) {
                    case ApplicationType.LEAVE:
                        remark += "请假";
                        break;
                    case ApplicationType.TRIP:
                        remark += "出差";
                        break;
                    case ApplicationType.WORK_CREATE:
                        break;
                    case ApplicationType.WORK_COMPLETE:
                        break;
                    default:
                        break;
                }
                remark += "申请";
                break;
            case Operation.APPROVE:
                remark = "意见: " + itemData.getRemark();
                break;
            case Operation.REFUSE:
                remark = "意见: " + itemData.getRemark();
                break;
            case Operation.RETURN:
                remark = "意见: " + itemData.getRemark();
                break;
            case Operation.CLOSE:
                remark = "意见: " + itemData.getRemark();
                break;
            default:
                break;
        }
        reviewProgressRemarkTextView.setText(remark);
    }

}
