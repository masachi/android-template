package ga.cv3sarato.android.im_sdk.conversation;

import com.google.gson.Gson;
import ga.cv3sarato.android.im_sdk.ImConfig;
import ga.cv3sarato.android.im_sdk.ImEvent;
import ga.cv3sarato.android.im_sdk.cache.Cache;
import ga.cv3sarato.android.im_sdk.cache.Cb;
import ga.cv3sarato.android.im_sdk.im_client.ImClient;
import ga.cv3sarato.android.im_sdk.messages.Message;
import ga.cv3sarato.android.im_sdk.messages.MessageFactory;
import ga.cv3sarato.android.im_sdk.messages.MessageInfo;
import ga.cv3sarato.android.im_sdk.net.NetApi;
import ga.cv3sarato.android.im_sdk.net.base.Request;
import ga.cv3sarato.android.im_sdk.net.base.Response;
import ga.cv3sarato.android.im_sdk.utils.event.EventEmitter;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.socket.client.Ack;
import io.socket.client.Socket;

import java.util.ArrayList;
import java.util.List;

public class Conversation extends EventEmitter {
    private ImClient imClient;
    private Socket socket;
    private ImConfig config;
    private Cache cache;
    private NetApi api;
    private ConversationInfo conversation;

    public Conversation(ImClient imClient) {
        if (imClient == null)
            throw new NullPointerException();
        this.imClient = imClient;
        this.socket = imClient.getSocket();
        this.config = imClient.getConfig();
        this.cache = imClient.getCache();
        this.api = ImClient.getApi();
    }

    public void setConversation(ConversationInfo conversation) {
        this.conversation = conversation;
    }

    public ConversationInfo getConversation() {
        return conversation;
    }

    public Message send(Message message) {
        message.getMessage().setConversationId(this.conversation.getConversationId());
        message.getMessage().setSendBy(this.config.getUid());
        message.getMessage().setIo(true);

        this.cache.cacheMessage(message)
                .subscribe(cb -> {
                    if (cb.getCode() == Cb.CodeOk) {
                        this.emit(ImEvent.Message, message);
                        Gson gson = new Gson();
                        String wsMsg = gson.toJson(message.getMessage());
                        try {
                            this.socket.emit("send_message", new JSONObject(wsMsg));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        throw new Error(cb.getMessage());
                    }
                }, (error) -> {
                    error.printStackTrace();
                });

        return message;
    }

    public List<Message> getMessages() {
        String cid = this.conversation.getConversationId();
        List<Message> messages = new ArrayList<>();
        this.cache.getMessages(cid)
                .subscribe(cb -> {
                    if (cb.getData() != null) {
                        messages.addAll((List<Message>)cb.getData());
                    } else {
                        throw new Error(cb.getMessage());
                    }
                }, (error) -> {
                    error.printStackTrace();
                });
        return messages;
    }

    public Message getLastMessage() {
        String cid = this.conversation.getConversationId();
        Message messageTemp = new Message();
        this.cache.getLastMessageByCid(cid)
                .subscribe(cb -> {
                    if (cb.getData() != null) {
                        messageTemp.setMessage(((Message) cb.getData()).getMessage());
                    } else {
                        throw new Error(cb.getMessage());
                    }
                }, (error) -> {
                    error.printStackTrace();
                });
        if(messageTemp.getMessage().getConversationId() != null) {
            return MessageFactory.createMessage(this.imClient, messageTemp.getMessage());
        }
        else {
            return null;
        }
    }

    public Conversation clearMessages() {
        this.cache.clearConversationMessages(this)
                .subscribe(cb -> {
                    if (cb.getCode() == Cb.CodeOk) {
                        this.emit(ImEvent.ConversationClearMessages);
                    } else {
                        throw new Error(cb.getMessage());
                    }
                }, (error) -> {
                    error.printStackTrace();
                });
        return this;
    }

    public Conversation read() {
        String cid = this.conversation.getConversationId();
        this.cache.readMessageByCid(cid)
                .subscribe(cb -> {
                    if (cb.getCode() == Cb.CodeOk) {
                        this.imClient.getUnreadMsgCount();
                    } else {
                        throw new Error(cb.getMessage());
                    }
                }, (error) -> {
                    error.printStackTrace();
                });
        return this;
    }

    public Conversation add(List<String> memberIds) {
        Request request = new Request(new ConversationMembers(this.conversation.getConversationId(), memberIds));
        this.api.addMembers(request)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(conversationInfoResponse -> {
                    if (conversationInfoResponse.getCode() == 200) {
                        this.cache.cacheConversation((ConversationInfo) conversationInfoResponse.getBody())
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe(cb -> {
                                    if (cb.getCode() == Cb.CodeOk) {
                                        List<String> members = this.conversation.getUserIds();
                                        members.addAll(memberIds);
                                        this.conversation.setUserIds(members);
                                    } else {
                                        throw new Error(cb.getMessage());
                                    }
                                })
                        ;
                    } else {
                        throw new Error(conversationInfoResponse.getMessage());
                    }
                }, (error) -> {
                    error.printStackTrace();
                });
        return this;
    }

    public Conversation remove(List<String> memberIds) {
        Request request = new Request(new ConversationMembers(this.conversation.getConversationId(), memberIds));
        this.api.addMembers(request)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(conversationInfoResponse -> {
                    if (conversationInfoResponse.getCode() == 200) {
                        this.cache.cacheConversation((ConversationInfo) conversationInfoResponse.getBody())
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe(cb -> {
                                    if (cb.getCode() == Cb.CodeOk) {
                                        List<String> members = this.conversation.getUserIds();
                                        members.removeAll(memberIds);
                                        this.conversation.setUserIds(members);
                                    } else {
                                        throw new Error(cb.getMessage());
                                    }
                                })
                        ;
                    } else {
                        throw new Error(conversationInfoResponse.getMessage());
                    }
                }, (error) -> {
                    error.printStackTrace();
                });

        return this;
    }

    public Conversation save() {
        Request request = new Request(new ConversationConfig(this.conversation.getConversationId(), this.conversation.getTitle(), this.conversation.getIcon()));
        this.api.modifyConversation(request)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(conversationInfoResponse -> {
                    if (conversationInfoResponse.getCode() == 200) {
                        this.cache.cacheConversation((ConversationInfo) conversationInfoResponse.getBody())
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe(cb -> {
                                    if (cb.getCode() == Cb.CodeOk) {
                                        this.imClient.refreshConversations();
                                    } else {
                                        throw new Error(cb.getMessage());
                                    }
                                })
                        ;
                    } else {
                        throw new Error(conversationInfoResponse.getMessage());
                    }
                }, (error) -> {
                    error.printStackTrace();
                });

        return this;
    }
}
