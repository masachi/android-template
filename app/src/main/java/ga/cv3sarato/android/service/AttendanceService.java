package ga.cv3sarato.android.service;


import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.entity.response.attendance.CheckInHistoryEntity;
import ga.cv3sarato.android.entity.response.attendance.CheckInRecordsEntity;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AttendanceService {

    @POST("attendance/create")
    Observable<CheckInHistoryEntity> createCheckInHistory(@Body Request request);

    @POST("attendance/get")
    Observable<CheckInRecordsEntity> getCheckInRecords(@Body Request request);
}
