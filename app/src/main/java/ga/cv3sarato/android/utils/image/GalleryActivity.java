package ga.cv3sarato.android.utils.image;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.base.BaseActivity;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;


@Route(value = "gtedx://gallery")
public class GalleryActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Router.injectParams(this);
    }

    @Override
    protected void init() {
        callGallery();
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    public void callGallery() {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        this.setResult(200, data);
                    }
                }
                break;
        }

        finish();
    }
}
