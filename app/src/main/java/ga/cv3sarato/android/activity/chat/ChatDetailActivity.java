package ga.cv3sarato.android.activity.chat;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import com.google.gson.Gson;
import ga.cv3sarato.android.MainApplication;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.chat.ChatMessagesAdapter;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.entity.common.ChatAttrsEntity;
import ga.cv3sarato.android.im_sdk.ImEvent;
import ga.cv3sarato.android.im_sdk.conversation.Conversation;
import ga.cv3sarato.android.im_sdk.im_client.ImClient;
import ga.cv3sarato.android.im_sdk.messages.Message;
import ga.cv3sarato.android.im_sdk.messages.TextMessage;
import com.wuhenzhizao.titlebar.utils.KeyboardConflictCompat;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@Route(value = "gtedx://chatDetail")
public class ChatDetailActivity extends BaseToolbarActivity implements View.OnLayoutChangeListener{

    @BindView(R.id.chat_detail_recyclerView)
    RecyclerView chatDetailRecyclerView;
    @BindView(R.id.chat_detail_messageInput)
    EditText chatDetailMessageInput;
    @BindView(R.id.chat_detail_sendBtn)
    Button chatDetailSendBtn;
    @BindView(R.id.chat_detail_rootView)
    LinearLayout chatDetailRootView;

    private Conversation conversation;
    private List<Message> messages = new ArrayList<>();
    ChatMessagesAdapter messageAdapter;

    ImClient imClient = MainApplication.getInstance().imClient;

    @InjectParam(key = "conversationId")
    String conversationId;
    @InjectParam(key = "title")
    String title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        TextView centerView = new TextView(this);
        centerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        centerView.setText(title);
        toolbar.setCenterView(centerView);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        KeyboardConflictCompat.assistWindow(getWindow());
    }

    @Override
    protected void init() throws Exception {
        initConversation();
        messageAdapter = new ChatMessagesAdapter(this, messages, R.layout.item_chat_message_left, R.layout.item_chat_message_right, conversation.getConversation().getConversationUsers());
        chatDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatDetailRecyclerView.setAdapter(messageAdapter);
        chatDetailRecyclerView.smoothScrollToPosition(messages.size() - 1);
        chatDetailRootView.addOnLayoutChangeListener(this);

    }

    private void initConversation() {
        conversation = this.imClient.getConversation(conversationId);
        conversation.read();
        conversation.on(ImEvent.Message, params -> {
            receiveMessage((Message) params[0]);
        });

        messages = conversation.getMessages();
    }

    public void receiveMessage(Message message) {
        ArrayList<Message> newData = new ArrayList<>(messages);
        if(!newData.contains(message)) {
            newData.add(message);
        }
        messages.clear();
        messages.addAll(newData);
        runOnUiThread(() -> {
            messageAdapter.notifyDataSetChanged();
            chatDetailRecyclerView.smoothScrollToPosition(messages.size() - 1);
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_detail;
    }

    @OnClick(R.id.chat_detail_sendBtn)
    public void onViewClicked() {
        TextMessage textMessage = new TextMessage(chatDetailMessageInput.getText().toString());
        textMessage.getMessage().setAttrs(new Gson().toJson(new ChatAttrsEntity(((MainApplication) getApplicationContext()).getUser().getName(), ((MainApplication) getApplicationContext()).getUser().getAvatar())));
        this.conversation.send(textMessage);
        chatDetailMessageInput.setText("");
    }

    @Override
    public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if(oldBottom != -1 && oldBottom > bottom) {
            chatDetailRecyclerView.requestLayout();
            chatDetailRecyclerView.post(() -> {
                chatDetailRecyclerView.smoothScrollToPosition(messages.size() - 1);
            });
        }
    }
}
