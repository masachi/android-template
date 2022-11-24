package ga.cv3sarato.android.adapter.chat;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ga.cv3sarato.android.MainApplication;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import ga.cv3sarato.android.im_sdk.ImEvent;
import ga.cv3sarato.android.im_sdk.conversation.Conversation;
import ga.cv3sarato.android.im_sdk.conversation.ConversationType;
import ga.cv3sarato.android.im_sdk.conversation.UserInfo;
import ga.cv3sarato.android.im_sdk.messages.Message;
import ga.cv3sarato.android.im_sdk.messages.MessageInfo;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;
import ga.cv3sarato.android.utils.imageLoader.ImageLoader;

import org.w3c.dom.Text;

import java.util.List;

public class ChatAdapter extends BaseRecyclerAdapter<Conversation> {
    private Context context;

    public ChatAdapter(Context context, List<Conversation> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
    }


    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, Conversation itemData, int position) {
        ImageView chatIcon = holder.getView(R.id.item_chat_icon);
        TextView chatTitle = holder.getView(R.id.item_chat_title);
        TextView chatLastMessage = holder.getView(R.id.item_chat_lastMessage);

        Message lastMessage = itemData.getLastMessage();

        if (lastMessage != null) {
            chatLastMessage.setText(lastMessage.getMessage().getMessage());
        } else {
            chatLastMessage.setText("");
        }

        itemData.on(ImEvent.Message, (params) -> {
            ((Activity) context).runOnUiThread( () -> {
                this.notifyItemChanged(position);
            });
        });

        GlideUtils.newInstance(context)
                .setPath(itemData.getConversation().getIcon())
                .setImageView(chatIcon)
                .loadImage();
        chatTitle.setText(itemData.getConversation().getTitle());

        holder.getRootView().setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });
    }
}
