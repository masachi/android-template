package ga.cv3sarato.android.activity.attendance;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.search.core.PoiInfo;
import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.attendance.CheckInHistoryAdapter;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.constant.RequestCode;
import ga.cv3sarato.android.entity.request.attendance.CheckInCreateEntity;
import ga.cv3sarato.android.entity.request.common.DateEntity;
import ga.cv3sarato.android.entity.response.attendance.CheckInHistoryEntity;
import ga.cv3sarato.android.entity.response.attendance.CheckInRecordsEntity;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.AttendanceService;
import ga.cv3sarato.android.utils.DateUtils;
import ga.cv3sarato.android.view.CircleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@Route(value = "gtedx://attendance")
@RuntimePermissions
public class CheckInActivity extends BaseToolbarActivity implements BDLocationListener {


    @BindView(R.id.check_in_icon_imageView)
    ImageView checkInIconImageView;
    @BindView(R.id.check_in_location_textView)
    TextView checkInLocationTextView;
    @BindView(R.id.check_in_location)
    LinearLayout checkInLocation;
    @BindView(R.id.check_in_textView)
    TextView checkInTextView;
    @BindView(R.id.time_textView)
    TextView timeTextView;
    @BindView(R.id.attendance_btn)
    CircleView attendanceBtn;
    @BindView(R.id.check_in_history_recyclerView)
    RecyclerView checkInHistoryRecyclerView;

    private LocationClient locationClient;
    private BDLocation currentLocation = null;
    private List<CheckInHistoryEntity> data = new ArrayList<>();
    private CheckInHistoryAdapter historyAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {
        historyAdapter = new CheckInHistoryAdapter(this, data, R.layout.item_check_in_history);
        checkInHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        checkInHistoryRecyclerView.setAdapter(historyAdapter);
        getCheckInHistory();
        CheckInActivityPermissionsDispatcher.getCurrentLocationWithPermissionCheck(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_in;
    }


    @NeedsPermission({Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION})
    public void getCurrentLocation() {
        locationClient = new LocationClient(this);
        initLocation();
        locationClient.registerNotifyLocationListener(this);
        locationClient.start();
        locationClient.requestLocation();
    }

    private void initLocation() {
        LocationClientOption options = new LocationClientOption();
        options.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        options.setCoorType("BD09LL");
        options.setScanSpan(1000);
        options.setLocationNotify(true);
        options.setIsNeedAddress(true);
        options.setOpenGps(true);
        options.setIsNeedLocationDescribe(true);
        options.setIsNeedLocationPoiList(true);
        options.setIgnoreKillProcess(false);
        options.SetIgnoreCacheException(true);
        locationClient.setLocOption(options);
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        switch (bdLocation.getLocType()) {
            case 61:
                currentLocation = bdLocation;
                checkInLocationTextView.setText(bdLocation.getPoiList().get(0).getName());
                break;
            case 161:
                currentLocation = bdLocation;
                checkInLocationTextView.setText(bdLocation.getPoiList().get(0).getName());
                break;
            default:
                locationClient.restart();
                locationClient.requestLocation();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CheckInActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(locationClient != null) {
            locationClient.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RequestCode.LOCATION_SELECT:
                if (resultCode == RESULT_OK) {
                    checkInLocationTextView.setText(((PoiInfo) data.getExtras().get("location")).name);
                }
                break;
        }
    }

    @OnClick({R.id.check_in_location, R.id.attendance_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.check_in_location:
                Router.build("gtedx://addressSelect").requestCode(RequestCode.LOCATION_SELECT).go(this);
                break;
            case R.id.attendance_btn:
                clickCheckIn();
                break;
        }
    }

    private void clickCheckIn() {
        if (!checkInLocationTextView.getText().toString().equals("") && currentLocation != null) {
            CheckInCreateEntity checkInCreate = new CheckInCreateEntity();
            checkInCreate.setPlace(checkInLocationTextView.getText().toString());
            checkInCreate.setRemark("");
            checkInCreate.setLatitude(currentLocation.getLatitude());
            checkInCreate.setLongitude(currentLocation.getLongitude());

            Request<CheckInCreateEntity> request = new Request<>();
            ServerApi.defaultInstance()
                    .create(AttendanceService.class)
                    .createCheckInHistory(new Request(checkInCreate))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new HylaaObserver<CheckInHistoryEntity>() {
                        @Override
                        public void onNext(CheckInHistoryEntity checkInHistoryEntity) {
                            data.add(checkInHistoryEntity);
                            historyAdapter.notifyItemInserted(data.size() - 1);
                            checkInHistoryRecyclerView.scrollToPosition(data.size() - 1);
                        }
                    });
        }
    }

    private void getCheckInHistory() {
        Request<DateEntity> request = new Request<>(new DateEntity(DateUtils.getFormatDate(DateUtils.FORMAT_DATE)));
        ServerApi.defaultInstance()
                .create(AttendanceService.class)
                .getCheckInRecords(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<CheckInRecordsEntity>() {
                    @Override
                    public void onNext(CheckInRecordsEntity checkInRecordsEntity) {
                        System.out.println(233);
                    }
                });

    }
}
