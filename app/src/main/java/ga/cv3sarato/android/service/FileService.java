package ga.cv3sarato.android.service;

import ga.cv3sarato.android.entity.request.common.FileRequestEntity;
import ga.cv3sarato.android.entity.request.common.IdEntity;
import ga.cv3sarato.android.entity.response.common.FileInfoEntity;
import ga.cv3sarato.android.entity.file.FileUploadEntity;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface FileService {

    @POST("file-upload-requests")
    Observable<FileUploadEntity> requestFileUpload(@Body FileRequestEntity request);

    @Multipart
    @POST("/")
    Observable<ResponseBody> uploadFile(@PartMap Map<String, RequestBody> requestBodyMap, @Part MultipartBody.Part file);

    @POST("files")
    Observable<FileInfoEntity> getFileInfo(@Body IdEntity request);
}
