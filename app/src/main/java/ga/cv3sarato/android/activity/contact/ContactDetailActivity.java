package ga.cv3sarato.android.activity.contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.entity.request.common.IdEntity;
import ga.cv3sarato.android.entity.response.contact.ContactDetailEntity;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.ContactService;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;
import ga.cv3sarato.android.view.ProfileItem;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Route(value = "gtedx://contactDetail")
public class ContactDetailActivity extends BaseToolbarActivity {

    @InjectParam(key = "id")
    String id;

    @BindView(R.id.contact_avatar_imageView)
    ImageView contactAvatarImageView;
    @BindView(R.id.contact_detail_mail_btn)
    Button contactDetailMailBtn;
    @BindView(R.id.contact_detail_chat_btn)
    Button contactDetailChatBtn;
    @BindView(R.id.contact_detail_call_btn)
    Button contactDetailCallBtn;
    @BindView(R.id.contact_detail_phone_item)
    ProfileItem contactDetailPhoneItem;
    @BindView(R.id.contact_detail_mail_item)
    ProfileItem contactDetailMailItem;
    @BindView(R.id.contact_detail_level_item)
    ProfileItem contactDetailLevelItem;
    @BindView(R.id.contact_detail_level_frame)
    LinearLayout contactDetailLevelFrame;
    @BindView(R.id.contact_detail_job_item)
    ProfileItem contactDetailJobItem;
    @BindView(R.id.contact_detail_job_frame)
    LinearLayout contactDetailJobFrame;
    @BindView(R.id.contact_detail_frame)
    LinearLayout contactDetailFrame;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {
        getStaffDetail();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_detail;
    }

    private void getStaffDetail() {
        ServerApi.defaultInstance()
                .create(ContactService.class)
                .getContactDetail(new Request(new IdEntity(id)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<ContactDetailEntity>() {
                    @Override
                    public void onNext(ContactDetailEntity contactDetailEntity) {
                        updateContactDetail(contactDetailEntity);
                    }
                });
    }

    @OnClick({R.id.contact_detail_mail_btn, R.id.contact_detail_chat_btn, R.id.contact_detail_call_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contact_detail_mail_btn:
                break;
            case R.id.contact_detail_chat_btn:
                break;
            case R.id.contact_detail_call_btn:
                if(!contactDetailPhoneItem.getRightText().equals("")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contactDetailPhoneItem.getRightText()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                break;
        }
    }

    private void updateContactDetail(ContactDetailEntity contactDetailEntity) {
        GlideUtils.newInstance(this)
                .setPath(contactDetailEntity.getAvatar())
                .setImageView(contactAvatarImageView)
                .withDefaultHeaders()
                .loadImage();

        contactDetailPhoneItem.setRightText(contactDetailEntity.getMobile());
        contactDetailMailItem.setRightText(contactDetailEntity.getEmail());
        if(contactDetailEntity.getRanks().size() == 1) {
            contactDetailLevelItem.setRightText(contactDetailEntity.getRanks().get(0).getName());
        }
        else {
            for(int i = 1; i< contactDetailEntity.getRanks().size(); i++) {
                ProfileItem item = new ProfileItem(this);
                item.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                item.setRightText(contactDetailEntity.getRanks().get(i).getName());
                if(i == contactDetailEntity.getRanks().size() - 1) {
                    item.setNeedSeparator(false);
                }
                contactDetailLevelFrame.addView(item);
            }
        }

        if(contactDetailEntity.getJobs().size() == 1) {
            contactDetailJobItem.setRightText(contactDetailEntity.getJobs().get(0).getName());
        }
        else {
            for(int i = 1; i< contactDetailEntity.getRanks().size(); i++) {
                ProfileItem item = new ProfileItem(this);
                item.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                item.setRightText(contactDetailEntity.getJobs().get(i).getName());
                if(i == contactDetailEntity.getJobs().size() - 1) {
                    item.setNeedSeparator(false);
                }
                contactDetailJobFrame.addView(item);
            }
        }
    }
}
