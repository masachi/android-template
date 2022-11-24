package ga.cv3sarato.android.im_sdk;

public class ImEvent {

    /**
     * 连接成功事件
     *
     * @type {string}
     */
    public static String Connect = "Connect";

    /**
     * 连接打开事件.
     *
     * @type {string}
     * @private
     */
    public static String Open = "Open";

    /**
     * 连接关闭事件.
     *
     * @type {string}
     */
    public static String Close = "Close";

    /**
     *各种错误事件.
     *
     * @type {string}
     */
    public static String Error = "Error";

    /**
     *同端登录事件.
     *
     * @type {string}
     */
    public static String End = "End";

    /**
     * 消息收发事件.
     *
     * @type {string}
     */
    public static String Message = "MessageCache";

    /**
     * 消息成功发出事件
     *
     * @type {string}
     */
    public static String MessageSent = "MessageSent";

    /**
     * 获取最后一条消息的事件.
     *
     * @type {string}
     */
    public static String ManualGetLastMessage = "ManualGetLastMessage";

    /**
     * 会话自动刷新事件.
     *
     * @type {string}
     */
    public static String Conversations = "Conversations";


    /**
     *清除当前会话下的消息事件.
     *
     * @type {string}
     */
    public static String ConversationClearMessages = "ConversationsClearMessages";


    //------------------------------------------------------------------------------------

    /**
     * 未读消息数更新通知事件.
     *
     * @type {string}
     */
    public static String UnreadMessagesCountUpdate = "UnreadMessagesCountUpdate";

    //------------------------------------------------------------------------------------

    /**
     * 有用户被添加至某个对话.
     *
     * @type {string}
     * @private
     */
    public static String MembersJoined = "MembersJoined";


    /**
     * 有成员被从某个对话中移除.
     *
     * @type {string}
     * @private
     */
    public static String MembersLeft = "MembersLeft";


    /**
     * 当前用户被添加至某个对话.
     *
     * @type {string}
     * @private
     */
    public static String Invited = "Invited";


    /**
     * 当前用户被从某个对话中移除.
     *
     * @type {string}
     * @private
     */
    public static String Kicked = "Kicked";
    
}
