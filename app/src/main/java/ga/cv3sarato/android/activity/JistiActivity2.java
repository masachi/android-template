//package ga.cv3sarato.android.activity;
//
//import android.os.Bundle;
//import android.webkit.WebChromeClient;
//import android.webkit.WebView;
//
//import com.chenenyu.router.Router;
//import com.chenenyu.router.annotation.InjectParam;
//import com.chenenyu.router.annotation.Route;
//import ga.cv3sarato.android.R;
//import ga.cv3sarato.android.base.BaseActivity;
//import ga.cv3sarato.android.entity.response.MeetingEntity;
//import ga.cv3sarato.android.net.ServerApi;
//import ga.cv3sarato.android.service.MeetingService;
//
//import butterknife.BindView;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//@Route(value = "gtedx://jitsi2")
//public class JistiActivity2 extends BaseActivity implements Callback {
//
//    @InjectParam(key = "code")
//    String code;
//    @BindView(R.id.jitsi_webview)
//    WebView jitsiWebview;
//
//    private MeetingEntity meeting;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        Router.injectParams(this);
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    protected void init() throws Exception {
//        ServerApi.defaultInstance(this)
//                .create(MeetingService.class)
//                .getMeetingInfo(code)
//                .enqueue(this);
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_jisti2;
//    }
//
//    @Override
//    public void onResponse(Call call, Response response) {
//        meeting = ((ga.cv3sarato.android.common.Response<MeetingEntity>) response.body()).getBody();
//        jitsiWebview.setWebChromeClient(new WebChromeClient());
//        jitsiWebview.loadUrl(meeting.vars.url + "#ticket=" + meeting.vars.ticket);
//    }
//
//    @Override
//    public void onFailure(Call call, Throwable t) {
//
//    }
//}
