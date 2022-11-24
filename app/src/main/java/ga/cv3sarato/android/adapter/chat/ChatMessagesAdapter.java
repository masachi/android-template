package ga.cv3sarato.android.adapter.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import ga.cv3sarato.android.im_sdk.conversation.UserInfo;
import ga.cv3sarato.android.im_sdk.messages.Message;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;

import java.util.List;

public class ChatMessagesAdapter extends BaseRecyclerAdapter<Message> {
    private Context context;
    private int leftLayoutId;
    private int rightLayoutId;
    private List<UserInfo> conversationUsers;
    private List<Message> data;

    public ChatMessagesAdapter(Context context, List<Message> data, int leftLayoutId, int rightLayoutId, List<UserInfo> conversationUsers) {
        super(context, data, leftLayoutId);
        this.context = context;
        this.leftLayoutId = leftLayoutId;
        this.rightLayoutId = rightLayoutId;
        this.conversationUsers = conversationUsers;
        this.data = data;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseRecyclerViewHolder holder;
        if(viewType == 0) {
            holder = new BaseRecyclerViewHolder(LayoutInflater.from(context).inflate(leftLayoutId, parent, false));
        }
        else {
            holder = new BaseRecyclerViewHolder(LayoutInflater.from(context).inflate(rightLayoutId, parent, false));
        }

        return holder;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, Message itemData, int position) {
        ImageView avatar = holder.getView(R.id.message_avatar);
        TextView message = holder.getView(R.id.message_textView);

        message.setText(itemData.getMessage().getMessage());
        for(UserInfo user : conversationUsers) {
            if(itemData.getMessage().getSendBy().equals(user.getUserId())) {
                GlideUtils.newInstance(context)
                        .setPath(user.getAvatar())
                        .setImageView(avatar)
                        .loadImage();
                break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getMessage().isIo() ? 1: 0;
    }
}
