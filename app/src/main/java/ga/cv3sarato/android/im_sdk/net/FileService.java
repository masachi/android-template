package ga.cv3sarato.android.im_sdk.net;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.util.HashMap;

public interface FileService {

    @FormUrlEncoded
    @POST(ApiName.FileUpload)
    Call fileUpload(@FieldMap HashMap<String, Object> params);
}
