package ga.cv3sarato.android.service;


import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.entity.response.user.DefaultMailEntity;
import ga.cv3sarato.android.entity.response.mail.MailEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MailService {

    @POST("email/default/get")
    Observable<DefaultMailEntity> getDefaultMailAddress(@Body Request request);

    @GET("accounts/{email}/messages")
    Observable<List<MailEntity>> getInboxMails(@Path("email") String mailAddress);
}
