package ga.cv3sarato.android.service;

import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.entity.response.apply.ApplysEntity;
import ga.cv3sarato.android.entity.response.apply.LeaveTripDetailEntity;
import ga.cv3sarato.android.entity.response.apply.LeaveTypeEntity;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApplyService {

    @POST("application/list")
    Observable<ApplysEntity> getApplicationList(@Body Request request);

    @POST("application/list/own")
    Observable<ApplysEntity> getOwnApplicationList(@Body Request request);

    @POST("leaveRequest/type/list")
    Observable<LeaveTypeEntity> getLeaveType(@Body Request request);

    @POST("leaveRequest/create")
    Observable<Object> createLeaveRequest(@Body Request request);

    @POST("businessTrip/create")
    Observable<Object> createBusinessTrip(@Body Request request);

    @POST("leaveRequest/detail")
    Observable<LeaveTripDetailEntity> getLeaveRequestDetail(@Body Request request);

    @POST("businessTrip/detail")
    Observable<LeaveTripDetailEntity> getBusinessTripDetail(@Body Request request);
}
