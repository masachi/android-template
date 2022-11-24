package ga.cv3sarato.android.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chenenyu.router.Router;
import ga.cv3sarato.android.MainApplication;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseFragment;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.constant.RequestCode;
import ga.cv3sarato.android.entity.file.FileEntity;
import ga.cv3sarato.android.entity.request.common.IdEntity;
import ga.cv3sarato.android.entity.request.user.AvatarUpdateEntity;
import ga.cv3sarato.android.entity.response.user.CompanyEntity;
import ga.cv3sarato.android.entity.response.user.ProfileEntity;
import ga.cv3sarato.android.net.FileUpload;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.UserService;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;
import ga.cv3sarato.android.utils.persistence.SharedPreferenceUtils;
import ga.cv3sarato.android.view.ProfileItem;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */

@RuntimePermissions
public class ProfileFragment extends BaseFragment implements OnClickListener {

    @BindView(R.id.phone_item)
    ProfileItem phoneItem;
    @BindView(R.id.mail_item)
    ProfileItem mailItem;
    @BindView(R.id.credential_typeItem)
    ProfileItem credentialTypeItem;
    @BindView(R.id.credential_item)
    ProfileItem credentialItem;
    @BindView(R.id.avatar_imageBtn)
    ImageButton avatarImageBtn;
    @BindView(R.id.name_text)
    TextView nameText;

    Unbinder unbinder;
    @BindView(R.id.scan_imageBtn)
    ImageButton scanImageBtn;
    @BindView(R.id.change_password_item)
    ProfileItem changePasswordItem;
    @BindView(R.id.switch_company_item)
    ProfileItem switchCompanyItem;
    @BindView(R.id.switch_language_item)
    ProfileItem switchLanguageItem;
    @BindView(R.id.switch_theme_item)
    ProfileItem switchThemeItem;

    String countryCode;
    @BindView(R.id.quit_login_btn)
    Button quitLoginBtn;

    private List<String> selectedAvatar = new ArrayList<>();

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        initData();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initData() {
        ServerApi.defaultInstance()
                .create(UserService.class)
                .getUserDetail(new Request(new IdEntity(MainApplication.getInstance().getUser().getId())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<ProfileEntity>() {
                    @Override
                    public void onNext(ProfileEntity profileEntity) {
                        GlideUtils.newInstance(getContext())
                                .setPath(profileEntity.getAvatar())
                                .setImageView(avatarImageBtn)
                                .loadImage();

                        nameText.setText(profileEntity.getName());
                        phoneItem.setRightText(profileEntity.getCountryCode() + "-" + profileEntity.getMobile());
                        mailItem.setRightText(profileEntity.getEmail());
                        credentialTypeItem.setRightText(profileEntity.getCredentialType());
                        credentialItem.setRightText(profileEntity.getCredential());
                    }
                });
    }

    @OnClick({R.id.scan_imageBtn, R.id.avatar_imageBtn, R.id.phone_item, R.id.mail_item, R.id.change_password_item, R.id.switch_company_item, R.id.switch_language_item, R.id.switch_theme_item, R.id.quit_login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.scan_imageBtn:
                ProfileFragmentPermissionsDispatcher.goToScanWithPermissionCheck(this);
                break;
            case R.id.avatar_imageBtn:
                ProfileFragmentPermissionsDispatcher.showDialogWithPermissionCheck(this);
                break;
            case R.id.phone_item:
                Router.build("gtedx://contactUpdate")
                        .with("type", "MOBILE")
                        .with("original", phoneItem.getRightText())
                        .with("country_code", phoneItem.getRightText().split("-")[0])
                        .go(this);
                break;
            case R.id.mail_item:
                Router.build("gtedx://contactUpdate")
                        .with("type", "EMAIL")
                        .with("original", mailItem.getRightText())
                        .with("country_code", phoneItem.getRightText().split("-")[0])
                        .go(this);
                break;
            case R.id.change_password_item:
                Router.build("gtedx://updatePassword")
                        .go(this);
                break;
            case R.id.switch_company_item:
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", new ArrayList<CompanyEntity>());
                Router.build("gtedx://companySwitch")
                        .with("company", bundle)
                        .go(this);
                break;
            case R.id.switch_language_item:
                break;
            case R.id.switch_theme_item:
                break;
            case R.id.quit_login_btn:
                deleteSensitiveData();
                break;
            default:
                break;
        }
    }

    @NeedsPermission({Manifest.permission.CAMERA})
    public void goToScan() {
        Router.build("gtedx://qrcode").go(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ProfileFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void deleteSensitiveData() {
        SharedPreferenceUtils.newInstance(getContext()).removeData("username");
        SharedPreferenceUtils.newInstance(getContext()).removeData("password");
        SharedPreferenceUtils.newInstance(getContext()).removeData("tenantID");
        MainApplication.getInstance().setAccessToken(null);
        MainApplication.getInstance().setRefreshToken(null);
        Router.build("gtedx://login").go(this);
        getActivity().finish();
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    public void showDialog() {
        selectedAvatar.clear();
        DialogPlus dialog = DialogPlus.newDialog(getContext())
                .setContentHolder(new ViewHolder(R.layout.dialog_select_image))
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOnClickListener(this)
                .setExpanded(true)
                .setGravity(Gravity.BOTTOM)
                .create();
        dialog.show();
    }

    @Override
    public void onClick(DialogPlus dialog, View view) {
        switch (view.getId()) {
            case R.id.select_from_album_btn:
                Router.build("gtedx://multiImagePicker")
                        .requestCode(RequestCode.IMAGE_GALLERY)
                        .with("selectMode", MultiImageSelectorActivity.MODE_SINGLE)
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RequestCode.IMAGE_CAMERA:
                if (resultCode == RESULT_OK) {
                    selectedAvatar.add(data.getStringExtra("uri"));
                    updateAvatar();
                }
                break;
            case RequestCode.IMAGE_GALLERY:
                if (resultCode == RESULT_OK) {
                    selectedAvatar.addAll(Arrays.asList(data.getStringArrayExtra("selected")));
                    updateAvatar();
                }
                break;
            default:
                break;
        }
    }

    private void updateAvatar() {
        FileUpload.uploadFiles(getContext(), selectedAvatar)
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<List<FileEntity>, ObservableSource<ProfileEntity>>() {
                    @Override
                    public ObservableSource<ProfileEntity> apply(List<FileEntity> fileEntities) throws Exception {
                        return ServerApi.defaultInstance()
                                .create(UserService.class)
                                .updateAvatar(new Request(new AvatarUpdateEntity(fileEntities.get(0).getDownloadUrl())))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(new HylaaObserver<ProfileEntity>() {
                    @Override
                    public void onNext(ProfileEntity profileEntity) {
                        GlideUtils.newInstance(getContext())
                                .setPath(profileEntity.getAvatar())
                                .setImageView(avatarImageBtn)
                                .loadImage();
                    }
                });

    }
}
