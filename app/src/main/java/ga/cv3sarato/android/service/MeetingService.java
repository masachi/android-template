package ga.cv3sarato.android.service;

import ga.cv3sarato.android.entity.response.meeting.MeetingEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface MeetingService {
    @GET("meeting/{code}")
    Observable<MeetingEntity> getMeetingInfo(@Path("code") String code);
}
