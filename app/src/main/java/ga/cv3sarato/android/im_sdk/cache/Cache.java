package ga.cv3sarato.android.im_sdk.cache;

import ga.cv3sarato.android.im_sdk.conversation.Conversation;
import ga.cv3sarato.android.im_sdk.conversation.ConversationInfo;
import ga.cv3sarato.android.im_sdk.messages.Message;
import ga.cv3sarato.android.im_sdk.messages.MessageInfo;

import java.util.List;

import io.reactivex.Observable;

public interface Cache {

    Observable<Cb> cacheConversation(ConversationInfo conversation);

    Observable<Cb> deleteConversation(ConversationInfo conversation);

    Observable<Cb> clearConversationMessages(Conversation conversation);

    Observable<Cb> cacheConversations(List<ConversationInfo> conversations);

    Observable<Cb> getConversations();

    Observable<Cb> getConversation(String cid);

    Observable<Cb> cacheMessage(Message message);

    Observable<Cb> cacheMessages(List<Message> messages);

    Observable<Cb> getMessages(String cid);

    Observable<Cb> clearMessages();

    Observable<Cb> getLastMessageByCid(String cid);

    Observable<Cb> readMessageByCid(String cid);

    Observable<Cb> getUnreadMsgCount(List<Conversation> conversations);

    Observable<Cb> clearOverdueMessages(int msecs);
}
