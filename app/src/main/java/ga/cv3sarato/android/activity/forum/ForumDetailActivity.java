package ga.cv3sarato.android.activity.forum;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.common.CommonImageAdapter;
import ga.cv3sarato.android.adapter.forum.ForumFloorAdapter;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.constant.Constant;
import ga.cv3sarato.android.entity.common.PaginationEntity;
import ga.cv3sarato.android.entity.file.FileEntity;
import ga.cv3sarato.android.entity.request.common.IdEntity;
import ga.cv3sarato.android.entity.request.forum.FloorCommentPageEntity;
import ga.cv3sarato.android.entity.request.forum.FloorPageEntity;
import ga.cv3sarato.android.entity.response.forum.FloorCommentsEntity;
import ga.cv3sarato.android.entity.response.forum.ForumFloorsEntity;
import ga.cv3sarato.android.entity.response.forum.ForumPostsEntity;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.ForumService;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@Route(value = "gtedx://postDetail")
public class ForumDetailActivity extends BaseToolbarActivity implements BaseRecyclerAdapter.OnItemClickListener, OnRefreshLoadMoreListener {

    @BindView(R.id.forum_detail_title_textView)
    TextView forumDetailTitleTextView;
    @BindView(R.id.forum_detail_avatar_imageView)
    ImageView forumDetailAvatarImageView;
    @BindView(R.id.forum_detail_name_textView)
    TextView forumDetailNameTextView;
    @BindView(R.id.forum_detail_time_textView)
    TextView forumDetailTimeTextView;
    @BindView(R.id.forum_detail_follow_imageBtn)
    ImageButton forumDetailFollowImageBtn;
    @BindView(R.id.forum_detail_follow_count_TextView)
    TextView forumDetailFollowCountTextView;
    @BindView(R.id.forum_detail_like_imageBtn)
    ImageButton forumDetailLikeImageBtn;
    @BindView(R.id.forum_detail_like_count_TextView)
    TextView forumDetailLikeCountTextView;
    @BindView(R.id.forum_detail_creator_info)
    LinearLayout forumDetailCreatorInfo;
    @BindView(R.id.forum_detail_content_textView)
    TextView forumDetailContentTextView;
    @BindView(R.id.forum_detail_comment_count_textView)
    TextView forumDetailCommentCountTextView;
    @BindView(R.id.forum_detail_comment_recyclerView)
    RecyclerView forumDetailCommentRecyclerView;
    @BindView(R.id.forum_detail_comment_btn)
    ImageButton forumDetailCommentBtn;
    @BindView(R.id.forum_detail_image_recyclerView)
    RecyclerView forumDetailImageRecyclerView;
    @BindView(R.id.forum_detail_floor_ptr_frame)
    SmartRefreshLayout forumDetailFloorPtrFrame;

    @InjectParam(key = "id")
    String id;

    private CommonImageAdapter imageAdapter;
    private ForumFloorAdapter floorAdapter;
    private List<ForumFloorsEntity.Floor> floors = new ArrayList<>();

    private int pageNum = 3;
    private int pageOffset = 0;

    private int commentPageNum = 5;
    private int commentPageOffset = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        pageOffset = 0;
        getPostsDetail();
        getFloorList(Constant.LoadingType.REFRESH);
    }

    @Override
    protected void init() throws Exception {
        setFloorData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forum_detail;
    }

    private void getPostsDetail() {
        Request<IdEntity> request = new Request<>(new IdEntity(id));
        ServerApi.defaultInstance()
                .create(ForumService.class)
                .postDetail(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<ForumPostsEntity.ForumItem>() {
                    @Override
                    public void onNext(ForumPostsEntity.ForumItem forumItem) {
                        setPostData(forumItem);
                    }
                });
    }

    private void getFloorList(int type) {
        Request<FloorPageEntity> request = new Request<>();
        FloorPageEntity floorPage = new FloorPageEntity(id);
        floorPage.setPagination(new PaginationEntity(pageNum, pageOffset));
        request.setBody(floorPage);
        ServerApi.defaultInstance()
                .create(ForumService.class)
                .getFloors(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<ForumFloorsEntity>() {
                    @Override
                    public void onNext(ForumFloorsEntity forumFloorsEntity) {
                       if(type == Constant.LoadingType.REFRESH) {
                           floors.clear();
                           floors.addAll(forumFloorsEntity.getFloors());
                           forumDetailFloorPtrFrame.finishRefresh();
                           floorAdapter.notifyDataSetChanged();
                           if(forumFloorsEntity.getPagination().getRemain() <= 0) {
                               forumDetailFloorPtrFrame.setNoMoreData(true);
                           }
                           else {
                               forumDetailFloorPtrFrame.setNoMoreData(false);
                           }
                       }
                       else {
                           floors.addAll(forumFloorsEntity.getFloors());
                           floorAdapter.notifyDataSetChanged();
                           if(forumFloorsEntity.getPagination().getRemain() <= 0) {
                               forumDetailFloorPtrFrame.finishLoadMoreWithNoMoreData();
                           }
                           else {
                               forumDetailFloorPtrFrame.finishLoadMore();
                               forumDetailFloorPtrFrame.setNoMoreData(false);
                           }
                       }
                       pageOffset += pageNum;
                    }
                });
    }

    private void getFloorCommentList(String floorId, int position) {
        Request<FloorCommentPageEntity> request = new Request<>();
        request.setBody(new FloorCommentPageEntity(floorId, new PaginationEntity(commentPageNum, commentPageOffset)));
        ServerApi.defaultInstance()
                .create(ForumService.class)
                .getComments(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<FloorCommentsEntity>() {
                    @Override
                    public void onNext(FloorCommentsEntity floorCommentsEntity) {
                        floors.get(position).getFloorComments().addAll(floorCommentsEntity.getComments());
                        floors.get(position).setPagination(floorCommentsEntity.getPagination());
                        floorAdapter.notifyItemChanged(position);
                    }
                });
    }

    private void setPostData(ForumPostsEntity.ForumItem forumItem) {
        forumDetailTitleTextView.setText(forumItem.getTitle());
        GlideUtils.newInstance(this)
                .setImageView(forumDetailAvatarImageView)
                .setPath(forumItem.getCreator().getAvatar())
                .withDefaultHeaders()
                .loadImage();

        forumDetailNameTextView.setText(forumItem.getCreator().getName() == null ? "匿名" : forumItem.getCreator().getName());
        forumDetailTimeTextView.setText(forumItem.getCreateTime());
        forumDetailFollowCountTextView.setText(String.valueOf(forumItem.getFollowCount()));
        forumDetailLikeCountTextView.setText(String.valueOf(forumItem.getLikeCount()));
        forumDetailContentTextView.setText(forumItem.getContent());
        forumDetailCommentCountTextView.setText(String.valueOf(forumItem.getFloorCount()));

        imageAdapter = new CommonImageAdapter(this, forumItem.getFiles(), R.layout.item_image_forum_detail);
        forumDetailImageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        forumDetailImageRecyclerView.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener((view, position) -> {
            ArrayList<String> images = new ArrayList<>();
            for (FileEntity file : forumItem.getFiles()) {
                images.add(file.getViewUrl());
            }

            Bundle bundle = new Bundle();
            bundle.putStringArrayList("uri", images);
            Router.build("gtedx://bigImage")
                    .with(bundle)
                    .with("index", position)
                    .go(this);
        });
    }

    private void setFloorData() {
        floorAdapter = new ForumFloorAdapter(this, floors, R.layout.item_forum_floor);
        forumDetailCommentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        forumDetailCommentRecyclerView.setAdapter(floorAdapter);
        floorAdapter.setOnItemClickListener(this);
        forumDetailFloorPtrFrame.setOnRefreshLoadMoreListener(this);
    }

    @OnClick({R.id.forum_detail_follow_imageBtn, R.id.forum_detail_like_imageBtn, R.id.forum_detail_comment_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forum_detail_follow_imageBtn:
                followPost();
                break;
            case R.id.forum_detail_like_imageBtn:
                likePost();
                break;
            case R.id.forum_detail_comment_btn:
                Router.build("gtedx://forumComment")
                        .with("id", id)
                        .with("type", "FLOOR")
                        .with("pid", "")
                        .go(this);
                break;
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getFloorList(Constant.LoadingType.LOADMORE);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageOffset = 0;
        getFloorList(Constant.LoadingType.REFRESH);
    }

    private void topPost() {
        Request<IdEntity> request = new Request<>(new IdEntity(id));
        ServerApi.defaultInstance()
                .create(ForumService.class)
                .topPost(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<Object>() {
                    @Override
                    public void onNext(Object o) {

                    }
                });
    }

    private void deletePost() {
        Request<IdEntity> request = new Request<>(new IdEntity(id));
        ServerApi.defaultInstance()
                .create(ForumService.class)
                .deletePost(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<Object>() {
                    @Override
                    public void onNext(Object o) {

                    }
                });
    }

    private void likePost() {
        Request<IdEntity> request = new Request<>(new IdEntity(id));
        ServerApi.defaultInstance()
                .create(ForumService.class)
                .likePost(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<ForumPostsEntity.ForumItem>() {
                    @Override
                    public void onNext(ForumPostsEntity.ForumItem forumItem) {
                        forumDetailLikeCountTextView.setText(String.valueOf(forumItem.getLikeCount()));
                    }
                });
    }

    private void followPost() {
        Request<IdEntity> request = new Request<>(new IdEntity(id));
        ServerApi.defaultInstance()
                .create(ForumService.class)
                .followPost(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<ForumPostsEntity.ForumItem>() {
                    @Override
                    public void onNext(ForumPostsEntity.ForumItem forumItem) {
                        forumDetailFollowCountTextView.setText(String.valueOf(forumItem.getFollowCount()));
                    }
                });
    }

    @Override
    public void onItemClickListener(View v, int position) {
        switch (v.getId()) {
            case R.id.forum_floor_item:
                Router.build("gtedx://forumComment")
                        .with("id", floors.get(position).getId())
                        .with("type", "COMMENT")
                        .with("pid", "")
                        .go(this);
                break;
            case R.id.floor_comment_more_btn:
                commentPageOffset = 3;
                commentPageNum = floors.get(position).getPagination().getRemain();
                getFloorCommentList(floors.get(position).getId(), position);
                break;
            default:
                break;
        }
    }
}
