package ga.cv3sarato.android.activity.meeting;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.entity.response.meeting.MeetingEntity;
import ga.cv3sarato.android.net.OaApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.MeetingService;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@Route(value = "gtedx://meetingRoom")
@RuntimePermissions
public class MeetingRoomActivity extends BaseToolbarActivity {

    @BindView(R.id.meeting_input)
    EditText meetingInput;
    @BindView(R.id.enter_btn)
    Button enterBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {

    }

    private void getMeetingUrl() {
        OaApi.defaultInstance()
                .create(MeetingService.class)
                .getMeetingInfo(meetingInput.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<MeetingEntity>() {
                    @Override
                    public void onNext(MeetingEntity meetingEntity) {
                        MeetingRoomActivityPermissionsDispatcher.moveToJitsiWithPermissionCheck(MeetingRoomActivity.this, meetingEntity);
                    }
                });
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR})
    public void moveToJitsi (MeetingEntity meetingEntity) {
        String url = meetingEntity.vars.url + "?jwt=" + meetingEntity.vars.ticket;
        Router.build("gtedx://jitsi")
                .with("url", url)
                .go(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MeetingRoomActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_meeting_room;
    }

    @OnClick(R.id.enter_btn)
    public void onViewClicked() {
        getMeetingUrl();
    }
}
