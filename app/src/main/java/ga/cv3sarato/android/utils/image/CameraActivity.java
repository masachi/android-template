package ga.cv3sarato.android.utils.image;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.base.BaseActivity;
import ga.cv3sarato.android.constant.ToastEnum;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@Route(value = "gtedx://camera")
public class CameraActivity extends BaseActivity {

    public final int TYPE_TAKE_PHOTO = 1;//Uri获取类型判断

    public final int CODE_TAKE_PHOTO = 1;//相机RequestCode

    public final int SUCCESS_TAKE_PHOTO = 200;

    private String uri = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        callSystemCamera();
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    public void callSystemCamera() {
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoUri = getMediaFileUri(TYPE_TAKE_PHOTO);
        photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        photoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(photoIntent, CODE_TAKE_PHOTO);
    }

    private Uri getMediaFileUri(int type) {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "");
        if(!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if( type == TYPE_TAKE_PHOTO){
            mediaFile = new File(mediaStorageDir.getPath() + File.pathSeparator + "IMG_" + timestamp + ".jpg");
        }
        else{
            return null;
        }

        if(Build.VERSION.SDK_INT >= 24){
            uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", mediaFile).toString();
            return FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", mediaFile);
        }
        else{
            uri = Uri.fromFile(mediaFile).toString();
            return Uri.fromFile(mediaFile);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CODE_TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("uri", uri);
                    this.setResult(RESULT_OK, resultIntent);
                }
                break;
        }
        finish();
    }
}
