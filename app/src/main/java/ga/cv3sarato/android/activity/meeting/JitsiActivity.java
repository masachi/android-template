package ga.cv3sarato.android.activity.meeting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.entity.response.meeting.MeetingEntity;
import ga.cv3sarato.android.net.OaApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.MeetingService;

import org.jitsi.meet.sdk.JitsiMeetView;
import org.jitsi.meet.sdk.JitsiMeetViewListener;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.RuntimePermissions;

@Route(value = "gtedx://jitsi")
public class JitsiActivity extends AppCompatActivity implements JitsiMeetViewListener {

    @InjectParam(key = "url")
    String url;

    private JitsiMeetView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);
        view = new JitsiMeetView(this);
        view.setWelcomePageEnabled(false);
        view.setPictureInPictureEnabled(true);

        Bundle config = new Bundle();
        config.putBoolean("startWithVideoMuted", true);
        Bundle urlObject = new Bundle();
        urlObject.putBundle("config", config);
        urlObject.putString("url", url);
        view.loadURLObject(urlObject);
        setContentView(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        view.dispose();
        view = null;

        JitsiMeetView.onHostDestroy(this);
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
