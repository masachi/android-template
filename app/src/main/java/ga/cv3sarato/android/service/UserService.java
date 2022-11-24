package ga.cv3sarato.android.service;

import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.entity.request.user.QREntity;
import ga.cv3sarato.android.entity.request.common.RefreshTokenEntity;
import ga.cv3sarato.android.entity.response.user.CompanyEntity;
import ga.cv3sarato.android.entity.response.user.ProfileEntity;
import ga.cv3sarato.android.entity.response.common.TokenEntity;
import ga.cv3sarato.android.entity.response.user.StartPageEntity;
import ga.cv3sarato.android.entity.response.user.UserDetailEntity;
import ga.cv3sarato.android.entity.common.UserEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("login")
    Observable<TokenEntity> loginIAM(@Body Request request);

    @POST("staff/tenant/info")
    Observable<UserEntity> login(@Body Request request);

    @POST("staff/tenant/trees")
    Observable<ArrayList<CompanyEntity>> getCompanyList(@Body Request request);

    @POST("token/renew")
    Observable<TokenEntity> refreshToken(@Body RefreshTokenEntity refreshTokenEntity);

    @POST("user/detail")
    Observable<ProfileEntity> getUserDetail(@Body Request request);

    @POST("qrcode/authorize")
    Observable<Object> authorizeQRCode (@Body QREntity qrEntity);

    @POST("verify/credential")
    Observable<UserDetailEntity> verifyCredential(@Body Request request);

    @POST("password/update/forget")
    Observable<Object> resetPassword(@Body Request request);

    @POST("user/update/email")
    Observable<UserDetailEntity> updateEmail(@Body Request request);

    @POST("user/update/mobile")
    Observable<UserDetailEntity> updateMobile(@Body Request request);

    @POST("password/update")
    Observable<Object> updatePassword(@Body Request request);

    @POST("tenant/startPage/get")
    Observable<StartPageEntity> getStartPages(@Body Request request);

    @POST("user/update/avatar")
    Observable<ProfileEntity> updateAvatar(@Body Request request);

}
