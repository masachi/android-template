package ga.cv3sarato.android.net;

import android.content.Context;

import ga.cv3sarato.android.entity.file.FileEntity;
import ga.cv3sarato.android.entity.request.common.FileRequestEntity;
import ga.cv3sarato.android.entity.request.common.IdEntity;
import ga.cv3sarato.android.entity.response.common.FileInfoEntity;
import ga.cv3sarato.android.entity.file.FileUploadEntity;
import ga.cv3sarato.android.service.FileService;
import ga.cv3sarato.android.utils.image.FileRealPath;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class FileUpload {

    public static Observable<List<FileEntity>> uploadFiles(Context context, List<String> images) {
        List<Observable<?>> uploadRequests = new ArrayList<>();
        for (String image : images) {
            try {
                if(!image.equals("ADD")) {
                    uploadRequests.add(uploadFile(context, image));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(uploadRequests.size() > 0) {
            return Observable.zip(
                    Observable.fromIterable(uploadRequests),
                    new Function<Object[], List<FileEntity>>() {
                        @Override
                        public List<FileEntity> apply(Object[] objects) throws Exception {
                            List<FileEntity> result = new ArrayList<>();
                            for(int i = 0;i< objects.length; i++) {
                                result.add((FileEntity) objects[i]);
                            }
                            return result;
                        }
                    }
            );
        }
        return Observable.just(new ArrayList<>());

    }

    private static Observable<FileEntity> uploadFile(Context context, String image) {
        return FileApi.defaultInstance()
                .create(FileService.class)
                .requestFileUpload(new FileRequestEntity(image.substring(image.lastIndexOf("/") + 1)))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(fileUploadEntity -> {
                    Map<String, RequestBody> requestBodyMap = new HashMap<>();
                    for (String key : fileUploadEntity.getUploadParams().keySet()) {
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                                fileUploadEntity.getUploadParams().get(key) == null ? "" : fileUploadEntity.getUploadParams().get(key));
                        requestBodyMap.put(key, requestBody);
                    }
//                        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), new File(image));
                    requestBodyMap.put("success_action_status", RequestBody.create(MediaType.parse("multipart/form-data"), "200"));
                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), new File(FileRealPath.getFilePathFromURI(context, image)));
                    MultipartBody.Part file = MultipartBody.Part.createFormData("file", fileUploadEntity.getFileName(), fileBody);

                    return Observable.zip(
                            OSSFileApi.getInstance(fileUploadEntity.getUploadUrl())
                                    .create(FileService.class)
                                    .uploadFile(requestBodyMap, file),
                            Observable.just(fileUploadEntity),
                            new BiFunction<ResponseBody, FileUploadEntity, FileUploadEntity>() {
                                @Override
                                public FileUploadEntity apply(ResponseBody responseBody, FileUploadEntity fileUploadEntity) throws Exception {
                                    if (responseBody != null) {
                                        return fileUploadEntity;
                                    } else {
                                        throw new ServerException(10000, "Upload Failed");
                                    }
                                }
                            }
                    );
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<FileUploadEntity, ObservableSource<FileEntity>>() {
                    @Override
                    public ObservableSource<FileEntity> apply(FileUploadEntity fileUploadEntity) throws Exception {
                        return Observable.zip(
                                FileApi.defaultInstance()
                                        .create(FileService.class)
                                        .getFileInfo(new IdEntity(fileUploadEntity.getId()))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(Schedulers.io()),
                                Observable.just(fileUploadEntity),
                                new BiFunction<FileInfoEntity, FileUploadEntity, FileEntity>() {
                                    @Override
                                    public FileEntity apply(FileInfoEntity fileInfoEntity, FileUploadEntity fileUploadEntity) throws Exception {
                                        return new FileEntity(
                                                fileUploadEntity.getId(),
                                                fileUploadEntity.getName(),
                                                fileUploadEntity.getDownloadUrl(),
                                                fileUploadEntity.getViewUrl(),
                                                fileUploadEntity.getFileName().substring(fileUploadEntity.getFileName().lastIndexOf(".") + 1),
                                                fileInfoEntity.getMime()
                                        );
                                    }
                                }
                        );
                    }
                });
    }

    private byte[] getBytes(String image) {
        ByteArrayOutputStream out = null;
        try {
            InputStream in = new FileInputStream(new File(image));
            out = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = in.read(b)) != -1) {
                out.write(b, 0, b.length);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
}
