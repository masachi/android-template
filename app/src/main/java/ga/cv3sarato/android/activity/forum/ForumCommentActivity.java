package ga.cv3sarato.android.activity.forum;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.common.ImageSelectAdapter;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.constant.RequestCode;
import ga.cv3sarato.android.entity.file.FileEntity;
import ga.cv3sarato.android.entity.request.forum.FloorCommentCreateEntity;
import ga.cv3sarato.android.entity.request.forum.FloorCreateEntity;
import ga.cv3sarato.android.net.FileUpload;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.ForumService;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
@Route(value = "gtedx://forumComment")
public class ForumCommentActivity extends BaseToolbarActivity implements BaseRecyclerAdapter.OnItemClickListener, View.OnClickListener, OnClickListener {

    @BindView(R.id.comment_content_editText)
    EditText commentContentEditText;
    @BindView(R.id.comment_image_recyclerView)
    RecyclerView commentImageRecyclerView;
    @BindView(R.id.comment_anonymous_switcher)
    Switch commentAnonymousSwitcher;

    @InjectParam(key = "id")
    String id;

    @InjectParam(key = "type")
    String type;

    @InjectParam(key = "pid")
    String pid;

    private List<String> selectedImages = new ArrayList<String>() {{
        add("ADD");
    }};
    private ImageSelectAdapter selectAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {
        Button sendCommentBtn = new Button(this);
        sendCommentBtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        sendCommentBtn.setText("发送");
        toolbar.setRightView(sendCommentBtn);

        if (type.toUpperCase().equals("FLOOR")) {
            selectAdapter = new ImageSelectAdapter(this, selectedImages, R.layout.item_selected_image);
            commentImageRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            commentImageRecyclerView.setAdapter(selectAdapter);
        } else {
            commentImageRecyclerView.setVisibility(View.GONE);
        }

        sendCommentBtn.setOnClickListener(this);
        selectAdapter.setOnItemClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forum_comment;
    }

    private void sendFloor() {
        FileUpload.uploadFiles(this, selectedImages)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<List<FileEntity>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(List<FileEntity> fileEntities) throws Exception {
                        Request<FloorCreateEntity> request = new Request<>();
                        FloorCreateEntity floor = new FloorCreateEntity();
                        floor.setPostId(id);
                        floor.setContent(commentContentEditText.getText().toString());
                        floor.setFiles(fileEntities);
                        floor.setIsAnonymous(commentAnonymousSwitcher.isChecked() ? 1 : 0);
                        request.setBody(floor);
                        return ServerApi.defaultInstance()
                                .create(ForumService.class)
                                .createFloor(request)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(new HylaaObserver<Object>() {
                    @Override
                    public void onNext(Object o) {
                        finish();
                    }
                });

    }

    private void sendComment() {
        Request<FloorCommentCreateEntity> request = new Request<>();
        FloorCommentCreateEntity floor = new FloorCommentCreateEntity();
        floor.setFloorId(id);
        floor.setContent(commentContentEditText.getText().toString());
        floor.setPid(pid);
        floor.setIsAnonymous(commentAnonymousSwitcher.isChecked() ? 1 : 0);
        request.setBody(floor);
        ServerApi.defaultInstance()
                .create(ForumService.class)
                .createComment(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<Object>() {
                    @Override
                    public void onNext(Object o) {
                        finish();
                    }
                });
    }

    @Override
    public void onClick(DialogPlus dialog, View view) {
        switch (view.getId()) {
            case R.id.select_from_album_btn:
                Router.build("gtedx://multiImagePicker")
                        .requestCode(RequestCode.IMAGE_GALLERY)
                        .go(this);
                break;
            case R.id.select_from_camera_btn:
                Router.build("gtedx://camera").requestCode(RequestCode.IMAGE_CAMERA).go(this);
                break;
            default:
                break;
        }
        dialog.dismiss();
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    public void showDialog() {
        DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.dialog_select_image))
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOnClickListener(this)
                .setExpanded(true)
                .setGravity(Gravity.BOTTOM)
                .create();
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        if (type.toUpperCase().equals("FLOOR")) {
            sendFloor();
        } else {
            sendComment();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RequestCode.IMAGE_CAMERA:
                if (resultCode == RESULT_OK) {
                    selectedImages.add(0, data.getStringExtra("uri"));
                    if (selectedImages.size() > 3) {
                        selectedImages.remove("ADD");
                    }
                    selectAdapter.notifyDataSetChanged();
                }
                break;
            case RequestCode.IMAGE_GALLERY:
                if (resultCode == RESULT_OK) {
                    selectedImages.addAll(0, Arrays.asList(data.getStringArrayExtra("selected")));
                    if (selectedImages.size() > 3) {
                        selectedImages.remove("ADD");
                    }
                    selectAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClickListener(View v, int position) {
        switch (v.getId()) {
            case R.id.selected_imageView:
                if (selectedImages.get(position).equals("ADD")) {
                    ForumCommentActivityPermissionsDispatcher.showDialogWithPermissionCheck(this);
                } else {

                }
                break;
            case R.id.delete_selected_btn:
                selectedImages.remove(position);
                if (!selectedImages.contains("ADD")) {
                    selectedImages.add("ADD");
                }
                selectAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ForumCommentActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
