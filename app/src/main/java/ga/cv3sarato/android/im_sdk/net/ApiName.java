package ga.cv3sarato.android.im_sdk.net;

public class ApiName {

    /**
     * 创建会话-[群聊|私聊].
     * @type {string}
     */
    public final static String ConversationCreate = "conversation/create";


    /**
     * 将成员移动到给定的群聊中.
     * @type {string}
     */
    public final static String GroupMoveIn = "conversation/member/join";


    /**
     * 从给定的群聊中移出成员.
     * @type {string}
     */
    public final static String GroupMoveOut = "conversation/member/leave";


    /**
     * 获取会话列表.
     * @type {string}
     */
    public final static String ConversationList = "conversation/list";


    /**
     * 根据会话ID获取单个会话.
     * @type {string}
     */
    public final static String ConversationDetail = "conversation/detail";


    /**
     * 上传文件(图片)的接口.
     * @type {string}
     */
    public final static String FileUpload = "file/upload";


    /**
     * 会话禁言
     * @type {string}
     */
    public final static String ConversationSlient = "conversation/silent";


    /**
     * 会话解散
     * @type {string}
     */
    public final static String ConversationDissolution = "conversation/end";

    /**
     * 会话修改
     * @type {string}
     */
    public final static String ConversationModify = "conversation/modify";


    /**
     * 会话管理员列表
     * @type {string}
     */
    public final static String OrgConversationList = "conversation/manage/list";


    /**
     * 会话保存
     * @type {string}
     */
    public final static String ConversationSave = "conversation/save";
}
