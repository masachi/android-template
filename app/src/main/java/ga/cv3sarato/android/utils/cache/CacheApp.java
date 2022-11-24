package ga.cv3sarato.android.utils.cache;

import ga.cv3sarato.android.im_sdk.cache.Cache;
import ga.cv3sarato.android.im_sdk.cache.Cb;
import ga.cv3sarato.android.im_sdk.conversation.Conversation;
import ga.cv3sarato.android.im_sdk.conversation.ConversationInfo;
import ga.cv3sarato.android.im_sdk.conversation.ConversationType;
import ga.cv3sarato.android.im_sdk.conversation.UserInfo;
import ga.cv3sarato.android.im_sdk.messages.Message;
import ga.cv3sarato.android.im_sdk.messages.MessageInfo;
import ga.cv3sarato.android.im_sdk.messages.MessageType;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class CacheApp implements Cache {
    private String cacheName;

    public CacheApp(String cacheName) {
        this.cacheName = cacheName;
    }

    private Realm getRealm() {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(this.cacheName + ".realm")
                .schemaVersion(1)
                .build();
        return Realm.getInstance(configuration);
    }

    private ConversationCache castToConversationCache (ConversationInfo conversationInfo) {
        ConversationCache cache = new ConversationCache();
        cache.setId(conversationInfo.getConversationId());
        cache.setType(conversationInfo.getType().toString());
        cache.setTitle(conversationInfo.getTitle());
        cache.setIcon(conversationInfo.getIcon());
        cache.setRemark(conversationInfo.getRemark());
        cache.setManager_id(conversationInfo.getManagerId());
        cache.setAccess(conversationInfo.getAccess());
        cache.setIs_silent(conversationInfo.getIsSilent());
        cache.setConversation_user(castToUserCache(conversationInfo.getConversationUser()));
        RealmList<UserCache> users = new RealmList<>();
        for(UserInfo user : conversationInfo.getConversationUsers()) {
            users.add(castToUserCache(user));
        }
        cache.setConversation_users(users);
        cache.setAttrs(conversationInfo.getAttrs());
        return cache;
    }

    private ConversationInfo castToConversationInfo (ConversationCache cache) {
        ConversationInfo conversationInfo = new ConversationInfo();
        conversationInfo.setConversationId(cache.getId());
        conversationInfo.setType(cache.getType().equals("SINGLE") ? ConversationType.SINGLE : ConversationType.GROUP);
        conversationInfo.setTitle(cache.getTitle());
        conversationInfo.setIcon(cache.getIcon());
        conversationInfo.setRemark(cache.getRemark());
        conversationInfo.setManagerId(cache.getManager_id());
        conversationInfo.setAccess(cache.getAccess());
        conversationInfo.setIsSilent(cache.getIs_silent());
        conversationInfo.setConversationUser(castToUserInfo(cache.getConversation_user()));
        ArrayList<UserInfo> users = new ArrayList<>();
        for(UserCache user : cache.getConversation_users()) {
            users.add(castToUserInfo(user));
        }
        conversationInfo.setConversationUsers(users);
        conversationInfo.setAttrs(cache.getAttrs());
        return conversationInfo;
    }

    private UserCache castToUserCache(UserInfo user) {
        UserCache cache = new UserCache();
        cache.setUser_id(user.getUserId());
        cache.setConversation_id(user.getConversationId());
        cache.setNickname(user.getNickname());
        cache.setIs_mute(user.getIsMute());
        cache.setIs_top(user.getIsTop());
        cache.setName(user.getName());
        cache.setAvatar(user.getAvatar());
        return cache;
    }

    private UserInfo castToUserInfo(UserCache cache) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(cache.getUser_id());
        userInfo.setConversationId(cache.getConversation_id());
        userInfo.setNickname(cache.getNickname());
        userInfo.setIsMute(cache.getIs_mute());
        userInfo.setIsTop(cache.getIs_top());
        userInfo.setName(cache.getName());
        userInfo.setAvatar(cache.getAvatar());
        return userInfo;
    }

    private MessageCache castToMessageCache (Message message) {
        MessageCache cache = new MessageCache();
        cache.setMessage_id(message.getMessage().messageId);
        cache.setMessage_type(message.getMessage().messageType.toString());
        cache.setMessage(message.getMessage().message);
        cache.setFile(message.getMessage().file);
        cache.setUri(message.getMessage().uri);
        cache.setConversation_id(message.getMessage().conversationId);
        cache.setSend_time(Long.parseLong(message.getMessage().sendTime));
        cache.setSend_by(message.getMessage().sendBy);
        cache.setIo(message.getMessage().io);
        cache.setIs_read(message.getMessage().getIsRead());
        cache.setAttrs(message.getMessage().attrs);
        cache.setMention_list(new RealmList<>(message.getMetionList().toArray(new String[message.getMetionList().size()])));
        cache.setMentioned_me(message.getMessage().mentionedMe);
        cache.setMentioned_all(message.getMessage().mentionedAll);
        return cache;
    }

    private Message castToMessage (MessageCache cache) {
        Message message = new Message();
        message.getMessage().setMessageId(cache.getMessage_id());
        message.getMessage().setMessageType(cache.getMessage_type().equals("TEXT") ? MessageType.TEXT : cache.getMessage_type().equals("IMAGE") ? MessageType.IMAGE : MessageType.FILE);
        message.getMessage().setMessage(cache.getMessage());
        message.getMessage().setFile(cache.getFile());
        message.getMessage().setUri(cache.getUri());
        message.getMessage().setConversationId(cache.getConversation_id());
        message.getMessage().setSendTime(String.valueOf(cache.getSend_time()));
        message.getMessage().setSendBy(cache.getSend_by());
        message.getMessage().setIo(cache.isIo());
        message.getMessage().setIsRead(cache.getIs_read());
        message.getMessage().setAttrs(cache.getAttrs());
        message.getMessage().setMentionList(cache.getMention_list());
        message.getMessage().setMentionedMe(cache.isMentioned_me());
        message.metionAll(cache.isMentioned_all());
        return message;
    }

    private Observable<Cb> execute(Callback callback) {
        try {
            Realm realm = getRealm();
            Cb cb = Cb.Ok();

            realm.beginTransaction();

            callback.execute(realm, cb);
            realm.commitTransaction();

            return Observable.just(cb);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Observable.just(Cb.No());
        }
    }

    @Override
    public Observable<Cb> cacheConversation(ConversationInfo conversation) {
        return this.execute((realm, cb) -> {
            realm.copyToRealmOrUpdate(castToConversationCache(conversation));
            cb.setData(conversation);
            cb.setMessage("success");
        });
    }

    @Override
    public Observable<Cb> deleteConversation(ConversationInfo conversation) {
        return this.execute((realm, cb) -> {
            realm.where(ConversationCache.class).equalTo("id", conversation.getConversationId()).findAll().deleteFromRealm(0);
            cb.setData(conversation);
            cb.setMessage("delete conversation succeed");
        });
    }

    @Override
    public Observable<Cb> clearConversationMessages(Conversation conversation) {
        return this.execute((realm, cb) -> {
            realm.where(MessageCache.class).equalTo("conversation_id", conversation.getConversation().getConversationId()).findAll().deleteAllFromRealm();
            cb.setMessage("clear messages of current conversation succeed");
        });
    }

    @Override
    public Observable<Cb> cacheConversations(List<ConversationInfo> conversations) {
        return this.execute((realm, cb) -> {
            for(ConversationInfo info : conversations) {
                realm.copyToRealmOrUpdate(castToConversationCache(info));
            }
            cb.setData(conversations);
            cb.setMessage("cache conversations succeed");
        });
    }

    @Override
    public Observable<Cb> getConversations() {
        return this.execute((realm, cb) -> {
            List<ConversationInfo> data = new ArrayList<>();
            RealmResults<ConversationCache> results = realm.where(ConversationCache.class).findAll();
            for(ConversationCache cache : results) {
                data.add(castToConversationInfo(cache));
            }
            cb.setData(data);
        });
    }

    @Override
    public Observable<Cb> getConversation(String cid) {
        return this.execute((realm, cb) -> {
            ConversationCache result = realm.where(ConversationCache.class).equalTo("id", cid).findFirst();
            if(result != null) {
                cb.setData(castToConversationInfo(result));
                cb.setMessage("query single conversation succeed");
            }
            else {
                cb.setMessage("not found the conversation");
            }
        });
    }

    @Override
    public Observable<Cb> cacheMessage(Message message) {
        return this.execute((realm, cb) -> {
            realm.copyToRealmOrUpdate(castToMessageCache(message));
            cb.setMessage("insert or update a message succeed");
            cb.setData(message);
        });
    }

    @Override
    public Observable<Cb> cacheMessages(List<Message> messages) {
        return this.execute((realm, cb) -> {
            for(Message message : messages) {
                realm.copyToRealmOrUpdate(castToMessageCache(message));
            }
            cb.setMessage("insert or update some messages succeed");
            cb.setData(messages);
        });
    }

    @Override
    public Observable<Cb> getMessages(String cid) {
        return this.execute((realm, cb) -> {
            List<Message> data = new ArrayList<>();
            RealmResults<MessageCache> results = realm.where(MessageCache.class).equalTo("conversation_id", cid).findAll();
            for(MessageCache cache : results) {
                data.add(castToMessage(cache));
            }

            cb.setData(data);
        });
    }

    @Override
    public Observable<Cb> clearMessages() {
        return this.execute((realm, cb) -> {
            realm.where(MessageCache.class).findAll().deleteAllFromRealm();
            cb.setMessage("clear all messages");
        });
    }

    @Override
    public Observable<Cb> getLastMessageByCid(String cid) {
        return this.execute((realm, cb) -> {
            RealmResults<MessageCache> results = realm.where(MessageCache.class).equalTo("conversation_id", cid).findAll();
            if(results.size() > 0) {
                cb.setData(castToMessage(results.get(results.size() - 1)));
            }
            else {
                cb.setMessage("There is no message in the current conversation");
            }
        });
    }

    @Override
    public Observable<Cb> readMessageByCid(String cid) {
        return this.execute((realm, cb) -> {
            List<Message> data = new ArrayList<>();
            RealmResults<MessageCache> results = realm.where(MessageCache.class).equalTo("conversation_id", cid).equalTo("is_read", 0).findAll();
            if(results.size() > 0) {
                for(MessageCache cache : results) {
                    cache.setIs_read(1);
                    data.add(castToMessage(cache));
                }
                cb.setData(data);
            }
        });
    }

    @Override
    public Observable<Cb> getUnreadMsgCount(List<Conversation> conversations) {
        return this.execute((realm, cb) -> {
            for(Conversation conversation : conversations) {
                RealmResults<MessageCache> results = realm.where(MessageCache.class).equalTo("conversation_id", conversation.getConversation().getConversationId()).equalTo("is_read", 0).findAll();
                if(results.size() > 0) {
                    conversation.getConversation().setUnreadCount(results.size());
                }
                else {
                    conversation.getConversation().setUnreadCount(0);
                }
            }

            cb.setData(conversations);
        });
    }

    @Override
    public Observable<Cb> clearOverdueMessages(int msecs) {
        return Observable.just(Cb.No());
    }
}
