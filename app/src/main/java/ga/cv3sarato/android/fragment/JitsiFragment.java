package ga.cv3sarato.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.base.BaseFragment;
import ga.cv3sarato.android.entity.response.meeting.MeetingEntity;
import ga.cv3sarato.android.net.OaApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.MeetingService;

import org.jitsi.meet.sdk.JitsiMeetView;
import org.jitsi.meet.sdk.JitsiMeetViewListener;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@Route(value = "gtedx://jitsi_fragment")
public class JitsiFragment extends BaseFragment implements JitsiMeetViewListener {

    @InjectParam(key = "code")
    String code;

    private JitsiMeetView view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = new JitsiMeetView(getActivity());
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
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view.dispose();
        view = null;
    }

    @Override
    public void onConferenceFailed(Map<String, Object> map) {

    }

    @Override
    public void onConferenceJoined(Map<String, Object> map) {

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

    }
}
