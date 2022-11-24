package ga.cv3sarato.android.im_sdk.im_client;

import com.google.gson.Gson;
import ga.cv3sarato.android.im_sdk.ImConfig;
import ga.cv3sarato.android.im_sdk.ImEvent;
import ga.cv3sarato.android.im_sdk.cache.Cache;
import ga.cv3sarato.android.im_sdk.cache.Cb;
import ga.cv3sarato.android.im_sdk.cache.HylaaIMObserver;
import ga.cv3sarato.android.im_sdk.conversation.*;
import ga.cv3sarato.android.im_sdk.im_file.ImFile;
import ga.cv3sarato.android.im_sdk.messages.Message;
import ga.cv3sarato.android.im_sdk.messages.MessageFactory;
import ga.cv3sarato.android.im_sdk.messages.MessageInfo;
import ga.cv3sarato.android.im_sdk.net.ConversationIdRequest;
import ga.cv3sarato.android.im_sdk.net.NetApi;
import ga.cv3sarato.android.im_sdk.net.base.Request;
import ga.cv3sarato.android.im_sdk.net.base.Response;
import ga.cv3sarato.android.im_sdk.net.base.ServerCallback;
import ga.cv3sarato.android.im_sdk.utils.event.EventEmitter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.socket.client.IO;
import io.socket.client.Socket;
import okhttp3.OkHttpClient;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ImClient extends EventEmitter {
    public static NetApi api;
    private ImConfig config;
    private Cache cache;
    private Socket socket;

    private Logger Log = Logger.getLogger(this.getClass());

    private List<Conversation> currentConversationList = new ArrayList<>();

    public ImClient(ImConfig config, Cache cache) {
        api = new NetApi(config.getUser_info(), config.getEnv(), config.getOid(), config.getTokenGetter());
        this.config = config;
        this.cache = cache;

        createWs();
    }

    public static NetApi getApi() {
        return api;
    }

    public ImConfig getConfig() {
        return config;
    }

    public Cache getCache() {
        return cache;
    }

    public Socket getSocket() {
        return socket;
    }

    public List<Conversation> getCurrentConversationList() {
        return currentConversationList;
    }

    public void setCurrentConversationList(List<Conversation> conversations) {
        this.currentConversationList = conversations;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    private void createWs() {
        Gson gson = new Gson();
        try {
            IO.Options options = new IO.Options();
            options.query = this.config.getWsQueryConfig();

            this.socket = IO.socket(this.config.getWsUrl(), options);
            this.socket
                    .on(Socket.EVENT_CONNECT, (Object... objects) -> {
                        Log.debug("connected");
                        this.emit(ImEvent.Connect, "connected");
                    })
                    .on(Socket.EVENT_RECONNECT, (Object... objects) -> {
                        Log.debug("reconnected");
                        this.emit(ImEvent.Connect, "reconnected");
                    })
                    .on(Socket.EVENT_PING, (Object... objects) -> {
                        Log.debug("ping");
                    })
                    .on(Socket.EVENT_PONG, (Object... objects) -> {
                        Log.debug("pong");
                    })
                    .on("receive_message", (Object... objects) -> {
                        postOnlineMessage(gson.fromJson(objects[0].toString(), MessageInfo.class));
                    })
                    .on("offline_message", (Object... objects) -> {
                        try {
                            List<MessageInfo> data = new ArrayList<>();
                            for (int i = 0; i < ((JSONArray) objects[0]).length(); i++) {
                                data.add(gson.fromJson(((JSONArray) objects[0]).get(i).toString(), MessageInfo.class));
                            }
                            postOfflineMessage(data);
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    })
                    .on("message_state", (Object... objects) -> {
                        this.emit(ImEvent.MessageSent, gson.fromJson(objects[0].toString(), MessageInfo.class).getMessageId());
                    })
                    .on("error_message", (Object... objects) -> {

                    })
                    .on(Socket.EVENT_CONNECT_ERROR, (Object... objects) -> {
                        this.emit(ImEvent.Error, objects[0]);
                    })
                    .on(Socket.EVENT_DISCONNECT, (Object... objects) -> {
                        this.emit(ImEvent.Close, objects[0]);
                    })
                    .on("notify_conversation_created", (Object... objects) -> {

                    })
                    .on("notify_conversation_updated", (Object... objects) -> {

                    })
                    .on("notify_conversation_silent", (Object... objects) -> {

                    })
                    .on("notify_conversation_ended", (Object... objects) -> {

                    })
                    .on("notify_conversation_new_manager", (Object... objects) -> {

                    })
                    .on("notify_conversation_member_join", (Object... objects) -> {

                    })
                    .on("notify_conversation_member_leave", (Object... objects) -> {

                    });
            this.socket.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addConversation(Conversation conversation) {
        if (this.currentConversationList == null) {
            return;
        }

        int conversationIndex = this.currentConversationList.indexOf(conversation);
        if (conversationIndex == -1) {
            this.currentConversationList.add(conversation);
            refreshConversations();
        }
    }

    private void removeConversation(Conversation conversation) {
        if (this.currentConversationList == null) {
            return;
        }

        int conversationIndex = this.currentConversationList.indexOf(conversation);
        if (conversationIndex == -1) {
            this.currentConversationList.remove(conversation);
            refreshConversations();
        }
    }

    private void updateConversation(Conversation conversation) {
        if (this.currentConversationList == null) {
            return;
        }

        int conversationIndex = this.currentConversationList.indexOf(conversation);
        if (conversationIndex == -1) {
            this.currentConversationList.remove(conversation);
            this.currentConversationList.add(conversationIndex, conversation);
            refreshConversations();
        }
    }

    private void mapConversations(List<ConversationInfo> conversations) {
        List<Conversation> result = new ArrayList<>();
        for (ConversationInfo conversation : conversations) {
            Conversation target = new Conversation(this);
            target.setConversation(conversation);
            result.add(target);
        }

        this.currentConversationList = result;
        refreshConversations();
    }

    private void postOnlineMessage(MessageInfo message) {
        Message messageAction = MessageFactory.updateMessageMentioned2IoFalse(this, message, this.config.getUid());
        this.cache.cacheMessage(messageAction)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(cb -> {
                    if (cb.getCode() == Cb.CodeOk) {
                        String cid = message.getConversationId();
                        Conversation conversation = this.getConversation(cid);
                        conversation.emit(ImEvent.Message, messageAction);
                        this.emit(ImEvent.Message, messageAction, conversation);
                        this.getUnreadMsgCount();
                    }
                });
    }

    private void postOfflineMessage(List<MessageInfo> messages) {
        if (messages != null && messages.size() > 0) {
            List<Message> typedMessages = new ArrayList<>();
            for (MessageInfo message : messages) {
                Message tempMessage = MessageFactory.updateMessageMentioned2IoFalse(this, message, this.config.getUid());
                typedMessages.add(tempMessage);
            }

            this.cache.cacheMessages(typedMessages)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(cb -> {
                        if (cb.getCode() == Cb.CodeOk) {
                            this.getUnreadMsgCount();
                            if (this.currentConversationList != null) {
                                for (Conversation conversation : currentConversationList) {
                                    conversation.emit(ImEvent.ManualGetLastMessage);
                                }
                            }
                        } else {
                            throw new Error(cb.getMessage());
                        }
                    });
        }
    }

    public void refreshConversations() {
        List<Conversation> chatPrivates = new ArrayList<>();
        List<Conversation> chatGroups = new ArrayList<>();
        for (Conversation conversation : this.currentConversationList) {
            if (conversation.getConversation().getType() == ConversationType.SINGLE) {
                chatPrivates.add(conversation);
            }

            if (conversation.getConversation().getType() == ConversationType.GROUP) {
                chatGroups.add(conversation);
            }
        }

        List<Conversation> totalConversation = chatGroups;
        totalConversation.addAll(chatPrivates);
        this.currentConversationList = totalConversation;
        this.emit(ImEvent.Conversations, this.currentConversationList);
    }

    private Conversation createConversation(Object params) {
        if (params == null) {
            throw new Error("The object parameters for creating the session can not be empty");
        }

        Request request = new Request(params);
        Conversation conversation = new Conversation(this);
        this.api.createConversation(request)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(conversationInfoResponse -> {
                    if (conversationInfoResponse.getCode() == 200) {
                        this.cache.cacheConversation((ConversationInfo) conversationInfoResponse.getBody())
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe(cb -> {
                                    if (cb.getCode() == Cb.CodeOk) {
                                        conversation.setConversation((ConversationInfo) cb.getData());
                                        this.addConversation(conversation);
                                    }
                                });
                    } else {
                        throw new Error(conversationInfoResponse.getMessage());
                    }
                });

        return conversation;
    }

    private Conversation modifyConversation(Object params) {
        Request request = new Request(params);
        Conversation conversation = new Conversation(this);
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
                                        conversation.setConversation((ConversationInfo) cb.getData());
                                        this.updateConversation(conversation);
                                    }
                                });
                    } else {
                        throw new Error(conversationInfoResponse.getMessage());
                    }
                });

        return conversation;
    }

    private Conversation saveConversation(Object params) {
        Request request = new Request(params);
        Conversation conversation = new Conversation(this);
        this.api.saveConversation(request)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(conversationInfoResponse -> {
                    if (conversationInfoResponse.getCode() == 200) {
                        this.cache.cacheConversation((ConversationInfo) conversationInfoResponse.getBody())
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe(cb -> {
                                    if (cb.getCode() == Cb.CodeOk) {
                                        conversation.setConversation((ConversationInfo) cb.getData());
                                        this.updateConversation(conversation);
                                    }
                                });
                    } else {
                        throw new Error(conversationInfoResponse.getMessage());
                    }
                });
        return conversation;
    }

    private Conversation disbandConversation(Object params) {
        Request request = new Request(params);
        Conversation conversation = new Conversation(this);
        this.api.disbandGroup(request)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(conversationInfoResponse -> {
                    if (conversationInfoResponse.getCode() == 200) {
                        this.cache.deleteConversation((ConversationInfo) conversationInfoResponse.getBody())
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe(cb -> {
                                    if (cb.getCode() == Cb.CodeOk) {
                                        conversation.setConversation((ConversationInfo) cb.getData());
                                        this.removeConversation(conversation);
                                        this.refreshConversations();
                                    } else {
                                        throw new Error(cb.getMessage());
                                    }
                                });
                    } else {
                        throw new Error(conversationInfoResponse.getMessage());
                    }
                });
        return conversation;
    }

    private Conversation addMembers(Object params) {
        Request request = new Request(params);
        Conversation conversation = new Conversation(this);
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
                                        conversation.setConversation((ConversationInfo) cb.getData());
                                        this.updateConversation(conversation);
                                    } else {
                                        throw new Error(cb.getMessage());
                                    }
                                });
                    } else {
                        throw new Error(conversationInfoResponse.getMessage());
                    }
                });
        return conversation;
    }

    private Conversation removeMembers(Object params) {
        Request request = new Request(params);
        Conversation conversation = new Conversation(this);
        this.api.removeMembers(request)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(conversationInfoResponse -> {
                    if (conversationInfoResponse.getCode() == 200) {
                        this.cache.cacheConversation((ConversationInfo) conversationInfoResponse.getBody())
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe(cb -> {
                                    if (cb.getCode() == Cb.CodeOk) {
                                        conversation.setConversation((ConversationInfo) cb.getData());
                                        this.updateConversation(conversation);
                                    } else {
                                        throw new Error(cb.getMessage());
                                    }
                                });
                    } else {
                        throw new Error(conversationInfoResponse.getMessage());
                    }
                });
        return conversation;
    }

    public Conversation getConversation(String cid) {
        if (this.currentConversationList != null) {
            Conversation conversation = null;
            for (Conversation currentConversation : this.currentConversationList) {
                if (currentConversation.getConversation().getConversationId().equals(cid)) {
                    conversation = currentConversation;
                    break;
                }
            }
            if (conversation != null) {
                return conversation;
            } else {
                Request request = new Request(new ConversationIdRequest(cid));
                Conversation conversationTarget = new Conversation(this);
                this.api.getConversation(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe(conversationInfoResponse -> {
                            if (conversationInfoResponse.getCode() == 200) {
                                this.cache.cacheConversation((ConversationInfo) conversationInfoResponse.getBody())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(Schedulers.io())
                                        .subscribe(cb -> {
                                            if (cb.getCode() == Cb.CodeOk) {
                                                conversationTarget.setConversation((ConversationInfo) cb.getData());
                                                this.addConversation(conversationTarget);
                                            } else {
                                                throw new Error(cb.getMessage());
                                            }
                                        });
                            } else {
                                throw new Error(conversationInfoResponse.getMessage());
                            }
                        });
                return conversationTarget;
            }
        } else {
            throw new Error();
        }
    }

    public void getConversations() {
        this.cache.getConversations()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(cb -> {
                    if ((List<ConversationInfo>) cb.getData() != null && ((List<ConversationInfo>) cb.getData()).size() > 0) {
                        this.mapConversations((List<ConversationInfo>) cb.getData());
                    }

                    Request request = new Request(new Object());
                    this.api.getConversations(request)
                            .subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                            .subscribe(listResponse -> {
                                if (listResponse.getCode() == 200) {
                                    this.cache.cacheConversations((List<ConversationInfo>) listResponse.getBody())
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(Schedulers.io())
                                            .subscribe(callback -> {
                                                this.getUnreadMsgCount();
                                            });
                                } else {
                                    throw new Error(listResponse.getMessage());
                                }
                            });

                }, (error) -> {
                    throw new Error("Error");
                });
    }

    private void clearMessages() {
        this.cache.clearMessages()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(cb -> {
                    if (cb.getCode() == Cb.CodeNo) {
                        throw new Error(cb.getMessage());
                    }
                });
    }

    private void clearOverdueMessages(int msecs) {
        this.cache.clearOverdueMessages(msecs)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(cb -> {
                    if (cb.getCode() == Cb.CodeNo) {
                        throw new Error(cb.getMessage());
                    }
                });
    }

    public void getUnreadMsgCount() {
        this.cache.getUnreadMsgCount(this.currentConversationList)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(cb -> {
                    if (cb.getCode() == Cb.CodeNo) throw new Error(cb.getMessage());
                    this.currentConversationList = (List<Conversation>) cb.getData();
                    this.refreshConversations();
                });
    }

    public ImFile createFile(HashMap<String, Object> file) {
        return new ImFile(this.api, file);
    }
}
