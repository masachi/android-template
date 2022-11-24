package ga.cv3sarato.android.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenenyu.router.Router;
import ga.cv3sarato.android.MainApplication;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.chat.ChatAdapter;
import ga.cv3sarato.android.base.BaseFragment;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.im_sdk.ImEvent;
import ga.cv3sarato.android.im_sdk.conversation.Conversation;
import ga.cv3sarato.android.im_sdk.conversation.ConversationType;
import ga.cv3sarato.android.im_sdk.conversation.UserInfo;
import ga.cv3sarato.android.im_sdk.im_client.ImClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChatFragment extends BaseFragment implements BaseRecyclerAdapter.OnItemClickListener {


    @BindView(R.id.chat_recyclerView)
    RecyclerView chatRecyclerView;
    Unbinder unbinder;

    List<Conversation> conversations = new ArrayList<>();

    ImClient imClient = MainApplication.getInstance().imClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        ChatAdapter chatAdapter = new ChatAdapter(getContext(), conversations, R.layout.item_chat_recyclerview);
        chatAdapter.setOnItemClickListener(this);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatRecyclerView.setAdapter(chatAdapter);
        this.imClient.getConversations();
        this.imClient.on(ImEvent.Conversations, params -> {
            conversations.clear();
            conversations.addAll((List<Conversation>) params[0]);
            modifyTitleAndIcon();
            getActivity().runOnUiThread(() -> {
                chatAdapter.notifyDataSetChanged();
            });
        });
        return rootView;
    }

    private void modifyTitleAndIcon() {
        for(Conversation conversation : conversations) {
            if(conversation.getConversation().getType() == ConversationType.SINGLE) {
                for (UserInfo user : conversation.getConversation().getConversationUsers()) {
                    if (!user.getUserId().equals(((MainApplication)getActivity().getApplicationContext()).getUser().getUid())) {
                        conversation.getConversation().setTitle(user.getName());
                        conversation.getConversation().setIcon(user.getAvatar());
                    }
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClickListener(View v, int position) {
        Router.build("gtedx://chatDetail")
                .with("conversationId", conversations.get(position).getConversation().getConversationId())
                .with("title", conversations.get(position).getConversation().getTitle())
                .go(this);
    }
}
