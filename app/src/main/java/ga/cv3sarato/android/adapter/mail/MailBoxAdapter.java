package ga.cv3sarato.android.adapter.mail;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import ga.cv3sarato.android.entity.response.mail.MailEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MailBoxAdapter extends BaseRecyclerAdapter<MailEntity> {
    private Context context;
    private List<MailEntity> data;

    public MailBoxAdapter(Context context, List<MailEntity> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, MailEntity itemData, int position) {
        ImageView itemMailAvatar = holder.getView(R.id.item_mail_avatar);
        ImageView redDotImage = holder.getView(R.id.red_dot_image);
        TextView mailToTextView = holder.getView(R.id.mail_to_textView);
        TextView mailSendTimeTextView = holder.getView(R.id.mail_send_time_textView);
        TextView mailTitleTextView = holder.getView(R.id.mail_title_textView);
        TextView mailAbstractTextView = holder.getView(R.id.mail_abstract_textView);

        redDotImage.setVisibility(View.GONE);
        mailToTextView.setText(itemData.getTo().size() > 0 ? itemData.getTo().get(0).email : "");
        mailSendTimeTextView.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(itemData.getDate() * 1000)));
        mailTitleTextView.setText(itemData.getSubject());
        mailAbstractTextView.setText(itemData.getSnippet());

        holder.getRootView().setOnClickListener((view) -> {
            if(onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });
    }

}
