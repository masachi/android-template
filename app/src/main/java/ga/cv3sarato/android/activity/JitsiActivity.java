package ga.cv3sarato.android.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.base.BaseActivity;
import ga.cv3sarato.android.entity.response.meeting.MeetingEntity;
import ga.cv3sarato.android.net.OaApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.MeetingService;

import org.jitsi.meet.sdk.JitsiMeetView;
import org.jitsi.meet.sdk.JitsiMeetViewListener;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

//@Route(value = "gtedx://jitsi")
@RuntimePermissions
public class JitsiActivity extends BaseActivity implements JitsiMeetViewListener {

    @InjectParam(key = "code")
    String code;

    private JitsiMeetView view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {
        view = new JitsiMeetView(this);
        view.setWelcomePageEnabled(false);
        view.setPictureInPictureEnabled(true);

        OaApi.defaultInstance()
                .create(MeetingService.class)
                .getMeetingInfo(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<MeetingEntity>() {
                    @Override
                    public void onNext(MeetingEntity meetingEntity) {
                        Bundle config = new Bundle();
                        config.putBoolean("startWithVideoMuted", true);
                        Bundle urlObject = new Bundle();
                        urlObject.putBundle("config", config);
                        urlObject.putString("url", meetingEntity.vars.url + "?jwt=" + meetingEntity.vars.ticket);
                        view.loadURLObject(urlObject);
//                        view.loadURLString(meeting.vars.url + "?jwt=" + meeting.vars.ticket);
                        setContentView(view);
                    }
                });


        JitsiActivityPermissionsDispatcher.callJitsiWithPermissionCheck(this);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        view.dispose();
        view = null;

        JitsiMeetView.onHostDestroy(this);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR})
    public void callJitsi() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        JitsiActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnPermissionDenied({Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS})
    public void onMicroPhoneDenied() {

    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    public void onCameraDenied() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        JitsiMeetView.onNewIntent(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        JitsiMeetView.onHostResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        JitsiMeetView.onHostPause(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onConferenceFailed(Map<String, Object> map) {
        Log.e("Failed", map.get("error").toString());
    }

    @Override
    public void onConferenceJoined(Map<String, Object> map) {
        Log.e("Success", "Success");
    }

    @Override
    public void onConferenceLeft(Map<String, Object> map) {

    }

    @Override
    public void onConferenceWillJoin(Map<String, Object> map) {

    }

    @Override
    public void onConferenceWillLeave(Map<String, Object> map) {

    }

    @Override
    public void onLoadConfigError(Map<String, Object> map) {
        Log.e("Failed", map.get("error").toString());
    }
}
