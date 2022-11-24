package ga.cv3sarato.android.im_sdk.net;

import ga.cv3sarato.android.im_sdk.conversation.ConversationInfo;
import ga.cv3sarato.android.im_sdk.net.base.Request;
import ga.cv3sarato.android.im_sdk.net.base.Response;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.List;

public interface ConversationService {

    @POST(ApiName.ConversationCreate)
    Observable<Response<ConversationInfo>> conversationCreate(@Body Request request);


    @POST(ApiName.GroupMoveIn)
    Observable<Response<ConversationInfo>> groupMoveIn(@Body Request request);


    @POST(ApiName.GroupMoveOut)
    Observable<Response<ConversationInfo>> groupMoveOut(@Body Request request);


    @POST(ApiName.ConversationList)
    Observable<Response<List<ConversationInfo>>> conversationList(@Body Request request);


    @POST(ApiName.ConversationDetail)
    Observable<Response<ConversationInfo>> conversationDetail(@Body Request request);


    @POST(ApiName.ConversationSlient)
    Observable<Response<ConversationInfo>> conversationSilent(@Body Request request);


    @POST(ApiName.ConversationDissolution)
    Observable<Response<ConversationInfo>> conversationDissolve(@Body Request request);


    @POST(ApiName.ConversationModify)
    Observable<Response<ConversationInfo>> conversationModify(@Body Request request);


    @POST(ApiName.ConversationSave)
    Observable<Response<ConversationInfo>> conversationSave(@Body Request request);
}
