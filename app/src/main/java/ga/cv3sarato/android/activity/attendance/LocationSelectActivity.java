package ga.cv3sarato.android.activity.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.attendance.LocationSelectAdapter;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.utils.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Route(value = "gtedx://addressSelect")
public class LocationSelectActivity extends BaseToolbarActivity implements BaiduMap.OnMarkerClickListener, OnGetPoiSearchResultListener, BDLocationListener, BaseRecyclerAdapter.OnItemClickListener {

    @BindView(R.id.baidu_mapView)
    MapView baiduMapView;
    @BindView(R.id.poi_recyclerView)
    RecyclerView poiRecyclerView;

    private BaiduMap baiduMap;
    private PoiSearch poiSearch = PoiSearch.newInstance();
    private LocationClient locationClient;

    private List<PoiInfo> locationData = new ArrayList<>();
    private LocationSelectAdapter locationSelectAdapter;

    PoiInfo clickedInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {
        locationSelectAdapter = new LocationSelectAdapter(this, locationData, R.layout.item_location_select);
        poiRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        poiRecyclerView.setAdapter(locationSelectAdapter);
        poiRecyclerView.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        locationSelectAdapter.setOnItemClickListener(this);
        initMap();
        getCurrentLocation();
        poiSearch.setOnGetPoiSearchResultListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_location_select;
    }

    private void initMap() {
        baiduMap = baiduMapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        baiduMap.setMyLocationEnabled(true);
        baiduMap.showMapPoi(true);
        baiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null));
    }

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
        options.setNeedDeviceDirect(true);
        locationClient.setLocOption(options);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            return;
        }

        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
            baiduMap.clear();
            PoiOverlay overlay = new PoiOverlay(baiduMap);
            baiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(poiResult);
            overlay.addToMap();
            overlay.zoomToSpan();
            locationData.clear();
            locationData.addAll(poiResult.getAllPoi());
            locationSelectAdapter.notifyDataSetChanged();
            return;
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.COMMON);
        converter.coord(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()));

        LatLng targetLatLng = converter.convert();

        MyLocationData data = new MyLocationData.Builder()
                .direction(bdLocation.getDirection())
                .accuracy(bdLocation.getRadius())
                .latitude(targetLatLng.latitude)
                .longitude(targetLatLng.longitude)
                .build();

        baiduMap.setMyLocationData(data);
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().target(targetLatLng).zoom(18.0f).build()));

        poiSearch.searchNearby(new PoiNearbySearchOption()
                .keyword("办公楼")
                .sortType(PoiSortType.distance_from_near_to_far)
                .location(targetLatLng)
                .radius(1000)
                .pageCapacity(10));
    }

    @Override
    protected void onDestroy() {
        locationClient.stop();
        baiduMap.setMyLocationEnabled(false);
        baiduMapView.onDestroy();
        baiduMapView = null;
        super.onDestroy();
    }

    @Override
    public void onItemClickListener(View v, int position) {
        clickedInfo = locationData.get(position);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("location", clickedInfo);
        this.setResult(RESULT_OK, resultIntent);
        finish();
    }
}
