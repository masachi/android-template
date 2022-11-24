package ga.cv3sarato.android.service;

import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.entity.response.user.VerifyEntity;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("authCode/send/mobile")
    Observable<Object> getMobileAuthCode(@Body Request request);

    @POST("authCode/send/email")
    Observable<Object> getEmailAuthCode(@Body Request request);

    @POST("authCode/verify/mobile")
    Observable<VerifyEntity> verifyMobile(@Body Request request);

    @POST("authCode/verify/email")
    Observable<VerifyEntity> verifyEmail(@Body Request request);
}
