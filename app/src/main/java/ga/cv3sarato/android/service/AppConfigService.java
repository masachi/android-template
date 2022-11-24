package ga.cv3sarato.android.service;

import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.entity.response.user.AppConfigEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AppConfigService {

    @POST("tenant/appHome/list")
    Observable<List<AppConfigEntity>> getAppConfig(@Body Request request);
}
