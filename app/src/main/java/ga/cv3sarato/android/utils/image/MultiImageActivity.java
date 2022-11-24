package ga.cv3sarato.android.utils.image;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import me.nereo.multi_image_selector.MultiImageSelectorFragment;

@Route(value = "gtedx://multiImagePicker")
public class MultiImageActivity extends BaseActivity implements MultiImageSelectorFragment.Callback {
    @BindView(R.id.commit)
    Button commit;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private int DEFAULT_SELECT_COUNT = 3;
    private int DEFAULT_MODE = MultiImageSelectorActivity.MODE_MULTI;
    private boolean DEFAULT_SHOW_CAMERA = false;
    private ArrayList<String> DEFAULT_SELECTED = new ArrayList<>();

    private List<String> selectedImages = new ArrayList<>();

    @InjectParam(key = "selectMode")
    int selectMode = MultiImageSelectorActivity.MODE_MULTI;

    @InjectParam(key = "selectCount")
    int selectCount = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void init() {
        Bundle bundle = new Bundle();
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, selectCount);
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, selectMode);
        bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, DEFAULT_SHOW_CAMERA);
        bundle.putStringArrayList(MultiImageSelectorFragment.EXTRA_DEFAULT_SELECTED_LIST, DEFAULT_SELECTED);

        if(selectMode == MultiImageSelectorActivity.MODE_SINGLE) {
            toolbar.setVisibility(View.GONE);
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.image_grid, Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle))
                .commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_multi_image_picker;
    }

    @Override
    public void onSingleImageSelected(String s) {
        selectedImages.add(s);
        Intent result = new Intent();
        result.putExtra("selected", selectedImages.toArray(new String[selectedImages.size()]));
        this.setResult(RESULT_OK, result);
        finish();
    }

    @Override
    public void onImageSelected(String s) {
        selectedImages.add(s);
    }

    @Override
    public void onImageUnselected(String s) {
        selectedImages.remove(s);
    }

    @Override
    public void onCameraShot(File file) {

    }

    @OnClick(R.id.commit)
    public void onViewClicked() {
        Intent result = new Intent();
        result.putExtra("selected", selectedImages.toArray(new String[selectedImages.size()]));
        this.setResult(RESULT_OK, result);
        finish();
    }
}
