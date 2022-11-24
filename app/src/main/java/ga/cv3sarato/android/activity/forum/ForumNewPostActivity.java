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
import android.widget.CheckBox;
import android.widget.EditText;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.common.ImageSelectAdapter;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.constant.RequestCode;
import ga.cv3sarato.android.entity.file.FileEntity;
import ga.cv3sarato.android.entity.request.forum.ForumNewPostsEntity;
import ga.cv3sarato.android.net.FileUpload;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.ForumService;
import ga.cv3sarato.android.utils.textInput.MentionEditText;
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
@Route(value = "gtedx://forumNewPost")
public class ForumNewPostActivity extends BaseToolbarActivity implements View.OnClickListener, MentionEditText.OnAtInputListener, BaseRecyclerAdapter.OnItemClickListener, OnClickListener {

    @BindView(R.id.new_post_title_editText)
    EditText newPostTitleEditText;
    @BindView(R.id.new_post_anonymous_checkBox)
    CheckBox newPostAnonymousCheckBox;
    @BindView(R.id.new_post_content_editText)
    MentionEditText newPostContentEditText;
    @BindView(R.id.new_post_image_recyclerView)
    RecyclerView newPostImageRecyclerView;

    private List<String> selectedImages = new ArrayList<String>() {{
        add("ADD");
    }};
    private ImageSelectAdapter selectAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {
        Button sendNewPostBtn = new Button(this);
        sendNewPostBtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        sendNewPostBtn.setText("发送");
        toolbar.setRightView(sendNewPostBtn);
        sendNewPostBtn.setOnClickListener(this);
        newPostContentEditText.setOnAtInputListener(this);

        selectAdapter = new ImageSelectAdapter(this, selectedImages, R.layout.item_selected_image);
        newPostImageRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        newPostImageRecyclerView.setAdapter(selectAdapter);
        selectAdapter.setOnItemClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forum_new_post;
    }

    @Override
    public void onClick(View view) {
        sendNewPosts();
    }

    private void sendNewPosts() {
        FileUpload.uploadFiles(this, selectedImages)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<List<FileEntity>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(List<FileEntity> fileEntities) throws Exception {
                        Request<ForumNewPostsEntity> request = new Request<>();
                        ForumNewPostsEntity newPost = new ForumNewPostsEntity();
                        newPost.setTitle(newPostTitleEditText.getText().toString());
                        newPost.setContent(newPostContentEditText.getText().toString());
                        newPost.setAnonymous(newPostAnonymousCheckBox.isChecked() ? 1 : 0);
                        newPost.setFiles(fileEntities);
                        newPost.setMentions(newPostContentEditText.getAtList());
                        request.setBody(newPost);
                        return ServerApi.defaultInstance()
                                .create(ForumService.class)
                                .createNewPost(request)
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

    @Override
    public void OnAtCharacterInput() {
        Router.build("gtedx://contact")
                .requestCode(RequestCode.AT_PERSON_SELECT)
                .with("mode", RequestCode.AT_PERSON_SELECT)
                .go(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RequestCode.AT_PERSON_SELECT:
                if (resultCode == RESULT_OK) {
                    newPostContentEditText.addAtContent(data.getStringExtra("id"), data.getStringExtra("name"));
                }
                break;
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
                if(resultCode == RESULT_OK) {
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
                    ForumNewPostActivityPermissionsDispatcher.showDialogWithPermissionCheck(this);
                } else {

                }
                break;
            case R.id.delete_selected_btn:
                selectedImages.remove(position);
                if(!selectedImages.contains("ADD")) {
                    selectedImages.add("ADD");
                }
                selectAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ForumNewPostActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
