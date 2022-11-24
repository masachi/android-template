package ga.cv3sarato.android.activity.review;

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
import ga.cv3sarato.android.adapter.review.ReviewFiltersAdapter;
import ga.cv3sarato.android.adapter.review.ReviewMineAdapter;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.constant.ApplicationType;
import ga.cv3sarato.android.constant.Constant;
import ga.cv3sarato.android.entity.common.PaginationEntity;
import ga.cv3sarato.android.entity.common.SortEntity;
import ga.cv3sarato.android.entity.request.review.ReviewFilterEntity;
import ga.cv3sarato.android.entity.request.review.ReviewPageEntity;
import ga.cv3sarato.android.entity.response.review.ReviewsEntity;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.ReviewService;
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

@Route(value = "gtedx://review")
public class ReviewMineActivity extends BaseToolbarActivity implements OnRefreshLoadMoreListener, BaseRecyclerAdapter.OnItemClickListener {

    @BindView(R.id.review_item_recyclerView)
    RecyclerView reviewItemRecyclerView;
    @BindView(R.id.review_ptr_frame)
    SmartRefreshLayout reviewPtrFrame;
    @BindView(R.id.drawer_type_textView)
    TextView drawerTypeTextView;
    @BindView(R.id.drawer_type_recyclerView)
    RecyclerView drawerTypeRecyclerView;
    @BindView(R.id.drawer_status_textView)
    TextView drawerStatusTextView;
    @BindView(R.id.drawer_status_recyclerView)
    RecyclerView drawerStatusRecyclerView;
    @BindView(R.id.drawer_filter_confirm_btn)
    Button drawerFilterConfirmBtn;
    @BindView(R.id.review_right_drawer_menu)
    FlowingMenuLayout reviewRightDrawerMenu;
    @BindView(R.id.review_right_drawer)
    FlowingDrawer reviewRightDrawer;

    private ReviewMineAdapter reviewAdapter;
    private ReviewFiltersAdapter typeAdapter;
    private ReviewFiltersAdapter statusAdapter;
    private List<ReviewsEntity.ReviewItem> data = new ArrayList<>();
    private HashMap<String, Object> filters = new HashMap<>();

    private List<ReviewFilterEntity> defaultFilterType = new ArrayList<ReviewFilterEntity>() {{
        add(new ReviewFilterEntity("FOLDER", R.drawable.folder, "文档"));
        add(new ReviewFilterEntity("LEAVE_REQUEST", R.drawable.leave, "请假"));
        add(new ReviewFilterEntity("BUSINESS_TRIP", R.drawable.trip, "出差"));
    }};
    private List<ReviewFilterEntity> defaultFilterStatus = new ArrayList<ReviewFilterEntity>() {{
        add(new ReviewFilterEntity("REFUSED", R.drawable.deny, "拒绝"));
        add(new ReviewFilterEntity("APPROVED", R.drawable.approve, "批准"));
        add(new ReviewFilterEntity("RETURNED", R.drawable.pending, "审核"));
        add(new ReviewFilterEntity("PENDING", R.drawable.reject, "退回"));
        add(new ReviewFilterEntity("CLOSED", R.drawable.close, "关闭"));
    }};

    private int pageNum = 5;
    private int pageOffset = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {
        reviewRightDrawer.setMenuSize(this.getWindowManager().getDefaultDisplay().getWidth());
        reviewAdapter = new ReviewMineAdapter(this, data, R.layout.item_apply_reviews);
        typeAdapter = new ReviewFiltersAdapter(this, defaultFilterType, R.layout.item_review_filter);
        statusAdapter = new ReviewFiltersAdapter(this, defaultFilterStatus, R.layout.item_review_filter);
        reviewItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        drawerTypeRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        drawerStatusRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        reviewItemRecyclerView.setAdapter(reviewAdapter);
        drawerTypeRecyclerView.setAdapter(typeAdapter);
        drawerStatusRecyclerView.setAdapter(statusAdapter);
        reviewPtrFrame.setOnRefreshLoadMoreListener(this);
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
        getMyReviews(Constant.LoadingType.REFRESH, filters);
        reviewAdapter.setOnItemClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_review_mine;
    }

    private void getMyReviews(int type, HashMap<String, Object> filters) {
        Request<ReviewPageEntity> request = new Request<>();
        ReviewPageEntity pageEntity = new ReviewPageEntity(new PaginationEntity(pageNum, pageOffset), filters, new ArrayList<SortEntity>() {{
            add(new SortEntity("create", 0));
        }});
        request.setBody(pageEntity);
        ServerApi.defaultInstance()
                .create(ReviewService.class)
                .getReviews(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<ReviewsEntity>() {
                    @Override
                    public void onNext(ReviewsEntity reviewsEntity) {
                        if (type == Constant.LoadingType.REFRESH) {
                            data.clear();
                            data.addAll(reviewsEntity.getReviews());
                            reviewPtrFrame.finishRefresh();
                            reviewAdapter.notifyDataSetChanged();
                            if (reviewsEntity.getPagination().getRemain() <= 0) {
                                reviewPtrFrame.setNoMoreData(true);
                            } else {
                                reviewPtrFrame.setNoMoreData(false);
                            }
                        } else {
                            data.addAll(reviewsEntity.getReviews());
                            reviewAdapter.notifyDataSetChanged();
                            if (reviewsEntity.getPagination().getRemain() <= 0) {
                                reviewPtrFrame.finishLoadMoreWithNoMoreData();
                            } else {
                                reviewPtrFrame.finishLoadMore();
                                reviewPtrFrame.setNoMoreData(false);
                            }
                        }
                        pageOffset += pageNum;
                    }
                });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getMyReviews(Constant.LoadingType.LOADMORE, filters);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageOffset = 0;
        getMyReviews(Constant.LoadingType.REFRESH, filters);
    }

    @OnClick(R.id.drawer_filter_confirm_btn)
    public void onViewClicked() {
        filters.put("type_list", typeAdapter.getSelecedItem());
        filters.put("review_status_list", statusAdapter.getSelecedItem());
        pageOffset = 0;
        getMyReviews(Constant.LoadingType.REFRESH, filters);
        reviewRightDrawer.closeMenu(true);
    }

    @Override
    public void onItemClickListener(View v, int position) {
        switch (v.getId()) {
            case R.id.apply_reviews_frame:
                switch (data.get(position).getType()) {
                    case ApplicationType.LEAVE:
                        Router.build("gtedx://leaveReview")
                                .with("id", data.get(position).getId())
                                .go(this);
                        break;
                    case ApplicationType.TRIP:
                        Router.build("gtedx://tripReview")
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
