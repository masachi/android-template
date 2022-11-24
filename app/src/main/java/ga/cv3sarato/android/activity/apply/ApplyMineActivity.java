package ga.cv3sarato.android.activity.apply;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.apply.ApplyFiltersAdapter;
import ga.cv3sarato.android.adapter.apply.ApplyMineAdapter;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.constant.ApplicationType;
import ga.cv3sarato.android.constant.Constant;
import ga.cv3sarato.android.entity.common.PaginationEntity;
import ga.cv3sarato.android.entity.common.SortEntity;
import ga.cv3sarato.android.entity.request.apply.ApplyFilterEntity;
import ga.cv3sarato.android.entity.request.apply.ApplyPageEntity;
import ga.cv3sarato.android.entity.response.apply.ApplysEntity;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.ApplyService;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingMenuLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Route(value = "gtedx://apply")
public class ApplyMineActivity extends BaseToolbarActivity implements OnRefreshLoadMoreListener, BaseRecyclerAdapter.OnItemClickListener {

    @BindView(R.id.apply_right_drawer)
    FlowingDrawer applyRightDrawer;
    @BindView(R.id.apply_item_recyclerView)
    RecyclerView applyItemRecyclerView;
    @BindView(R.id.apply_ptr_frame)
    SmartRefreshLayout applyPtrFrame;
    @BindView(R.id.drawer_type_textView)
    TextView drawerTypeTextView;
    @BindView(R.id.drawer_type_recyclerView)
    RecyclerView drawerTypeRecyclerView;
    @BindView(R.id.drawer_status_textView)
    TextView drawerStatusTextView;
    @BindView(R.id.drawer_status_recyclerView)
    RecyclerView drawerStatusRecyclerView;
    @BindView(R.id.apply_right_drawer_menu)
    FlowingMenuLayout applyRightDrawerMenu;
    @BindView(R.id.drawer_filter_confirm_btn)
    Button drawerFilterConfirmBtn;

    private ApplyMineAdapter applyAdapter;
    private ApplyFiltersAdapter typeAdapter;
    private ApplyFiltersAdapter statusAdapter;
    private List<ApplysEntity.ApplyItem> data = new ArrayList<>();
    private HashMap<String, Object> filters = new HashMap<>();

    private List<ApplyFilterEntity> defaultFilterType = new ArrayList<ApplyFilterEntity>() {{
        add(new ApplyFilterEntity("FOLDER", R.drawable.folder, "文档"));
        add(new ApplyFilterEntity("LEAVE_REQUEST", R.drawable.leave, "请假"));
        add(new ApplyFilterEntity("BUSINESS_TRIP", R.drawable.trip, "出差"));
    }};
    private List<ApplyFilterEntity> defaultFilterStatus = new ArrayList<ApplyFilterEntity>() {{
        add(new ApplyFilterEntity("REFUSED", R.drawable.deny, "拒绝"));
        add(new ApplyFilterEntity("APPROVED", R.drawable.approve, "批准"));
        add(new ApplyFilterEntity("RETURNED", R.drawable.pending, "审核"));
        add(new ApplyFilterEntity("PENDING", R.drawable.reject, "退回"));
        add(new ApplyFilterEntity("CLOSED", R.drawable.close, "关闭"));
    }};

    private int pageNum = 5;
    private int pageOffset = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {
        applyRightDrawer.setMenuSize(this.getWindowManager().getDefaultDisplay().getWidth());
        applyAdapter = new ApplyMineAdapter(this, data, R.layout.item_apply_reviews);
        typeAdapter = new ApplyFiltersAdapter(this, defaultFilterType, R.layout.item_apply_filter);
        statusAdapter = new ApplyFiltersAdapter(this, defaultFilterStatus, R.layout.item_apply_filter);
        applyItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        drawerTypeRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        drawerStatusRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        applyItemRecyclerView.setAdapter(applyAdapter);
        drawerTypeRecyclerView.setAdapter(typeAdapter);
        drawerStatusRecyclerView.setAdapter(statusAdapter);
        applyPtrFrame.setOnRefreshLoadMoreListener(this);
        typeAdapter.setOnItemClickListener((view, position) -> {
            if (typeAdapter.isItemChecked(position)) {
                typeAdapter.setItemChecked(position, false);
            } else {
                typeAdapter.setItemChecked(position, true);
            }
        });
        statusAdapter.setOnItemClickListener((view, position) -> {
            if (statusAdapter.isItemChecked(position)) {
                statusAdapter.setItemChecked(position, false);
            } else {
                statusAdapter.setItemChecked(position, true);
            }
        });
        getMyApplys(Constant.LoadingType.REFRESH, filters);
        applyAdapter.setOnItemClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_mine;
    }

    private void getMyApplys(int type, HashMap<String, Object> filters) {
        Request<ApplyPageEntity> request = new Request<>();
        ApplyPageEntity pageEntity = new ApplyPageEntity(new PaginationEntity(pageNum, pageOffset), filters, new ArrayList<SortEntity>() {{
            add(new SortEntity("create ", 0));
        }});
        request.setBody(pageEntity);
        ServerApi.defaultInstance()
                .create(ApplyService.class)
                .getOwnApplicationList(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<ApplysEntity>() {
                    @Override
                    public void onNext(ApplysEntity applysEntity) {
                        if (type == Constant.LoadingType.REFRESH) {
                            data.clear();
                            data.addAll(applysEntity.getApplys());
                            applyPtrFrame.finishRefresh();
                            applyAdapter.notifyDataSetChanged();
                            if (applysEntity.getPagination().getRemain() <= 0) {
                                applyPtrFrame.setNoMoreData(true);
                            } else {
                                applyPtrFrame.setNoMoreData(false);
                            }
                        } else {
                            data.addAll(applysEntity.getApplys());
                            applyAdapter.notifyDataSetChanged();
                            if (applysEntity.getPagination().getRemain() <= 0) {
                                applyPtrFrame.finishLoadMoreWithNoMoreData();
                            } else {
                                applyPtrFrame.finishLoadMore();
                                applyPtrFrame.setNoMoreData(false);
                            }
                        }
                        pageOffset += pageNum;
                    }
                });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getMyApplys(Constant.LoadingType.LOADMORE, filters);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageOffset = 0;
        getMyApplys(Constant.LoadingType.REFRESH, filters);
    }

    @OnClick(R.id.drawer_filter_confirm_btn)
    public void onViewClicked() {
        filters.put("type_list", typeAdapter.getSelectedItem());
        filters.put("review_status_list", statusAdapter.getSelectedItem());
        pageOffset = 0;
        getMyApplys(Constant.LoadingType.REFRESH, filters);
        applyRightDrawer.closeMenu(true);
    }

    @Override
    public void onItemClickListener(View v, int position) {
        switch (v.getId()) {
            case R.id.apply_reviews_frame:
                switch (data.get(position).getType()) {
                    case ApplicationType.LEAVE:
                        Router.build("gtedx://leaveDetail")
                                .with("id", data.get(position).getId())
                                .go(this);
                        break;
                    case ApplicationType.TRIP:
                        Router.build("gtedx://tripDetail")
                                .with("id", data.get(position).getId())
                                .go(this);
                        break;
                    case ApplicationType.WORK_CREATE:
                        break;
                    case ApplicationType.WORK_COMPLETE:
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}
