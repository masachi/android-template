package ga.cv3sarato.android.service;

import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.entity.response.contact.ContactDetailEntity;
import ga.cv3sarato.android.entity.response.contact.ContactSimpleEntity;
import ga.cv3sarato.android.entity.response.contact.ContactStaffEntity;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ContactService {

    @POST("staff/simple/sortedList")
    Observable<ContactStaffEntity> getSortedContact(@Body Request request);

    @POST("staff/contact/detail")
    Observable<ContactDetailEntity> getContactDetail(@Body Request request);

    @POST("staff/simple/list")
    Observable<ContactSimpleEntity> getSimpleContact(@Body Request request);
}
