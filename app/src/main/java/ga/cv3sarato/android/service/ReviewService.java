package ga.cv3sarato.android.service;

import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.entity.response.apply.LeaveTripDetailEntity;
import ga.cv3sarato.android.entity.response.review.ReviewsEntity;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReviewService {

    @POST("application/list")
    Observable<ReviewsEntity> getReviews(@Body Request request);

    @POST("leaveRequest/approve")
    Observable<LeaveTripDetailEntity> approveLeave(@Body Request request);

    @POST("leaveRequest/refuse")
    Observable<LeaveTripDetailEntity> refuseLeave(@Body Request request);

    @POST("leaveRequest/return")
    Observable<LeaveTripDetailEntity> returnLeave(@Body Request request);

    @POST("leaveRequest/comment")
    Observable<LeaveTripDetailEntity> commentLeave(@Body Request request);

    @POST("businessTrip/approve")
    Observable<LeaveTripDetailEntity> approveBusinessTrip(@Body Request request);

    @POST("businessTrip/refuse")
    Observable<LeaveTripDetailEntity> refuseBusinessTrip(@Body Request request);

    @POST("businessTrip/return")
    Observable<LeaveTripDetailEntity> returnBusinessTrip(@Body Request request);

    @POST("businessTrip/comment")
    Observable<LeaveTripDetailEntity> commentBusinessTrip(@Body Request request);
}
