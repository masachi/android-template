package ga.cv3sarato.android.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenenyu.router.Router;

import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.forum.ForumAllAdapter;
import ga.cv3sarato.android.base.BaseFragment;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.constant.Constant;
import ga.cv3sarato.android.entity.common.PaginationEntity;
import ga.cv3sarato.android.entity.common.SortEntity;
import ga.cv3sarato.android.entity.request.forum.ForumPageEntity;
import ga.cv3sarato.android.entity.request.common.IdEntity;
import ga.cv3sarato.android.entity.response.forum.ForumPostsEntity;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.ForumService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForumAllFragment extends BaseFragment implements OnRefreshLoadMoreListener, BaseRecyclerAdapter.OnItemClickListener {


    @BindView(R.id.forum_all_recyclerView)
    RecyclerView forumAllRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.forum_all_ptr_frame)
    SmartRefreshLayout forumAllPtrFrame;

    private ForumAllAdapter forumPostsAdapter;
    private List<ForumPostsEntity.ForumItem> posts = new ArrayList<>();


    public ForumAllFragment() {
        // Required empty public constructor
    }
    private int pageNum = 1;
    private int pageOffset = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_forum_all, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        forumPostsAdapter = new ForumAllAdapter(getContext(), posts, R.layout.item_forum_post);
        forumAllRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        forumAllRecyclerView.setAdapter(forumPostsAdapter);
        ((SimpleItemAnimator) forumAllRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        forumAllPtrFrame.setOnRefreshLoadMoreListener(this);
        forumPostsAdapter.setOnItemClickListener(this);
        getForumPosts(Constant.LoadingType.REFRESH);
    }

    private void getForumPosts(int type) {
        Request<ForumPageEntity> request = new Request<>();
        ForumPageEntity forumPage = new ForumPageEntity(new PaginationEntity(pageNum, pageOffset), new HashMap<String, String>(){{put("scope", "ALL");}}, new ArrayList<SortEntity>(){{add(new SortEntity("update", 0));}});
        request.setBody(forumPage);
        ServerApi.defaultInstance()
                .create(ForumService.class)
                .getForumPosts(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<ForumPostsEntity>() {
                    @Override
                    public void onNext(ForumPostsEntity forumPostsEntity) {
                        if(type == Constant.LoadingType.REFRESH) {
                            posts.clear();
                            posts.addAll(forumPostsEntity.getPosts());
                            forumAllPtrFrame.finishRefresh();
                            forumPostsAdapter.notifyDataSetChanged();
                            if(forumPostsEntity.getPagination().getRemain() <= 0) {
                                forumAllPtrFrame.setNoMoreData(true);
                            }
                            else {
                                forumAllPtrFrame.setNoMoreData(false);
                            }
                        }
                        else {
                            posts.addAll(forumPostsEntity.getPosts());
                            forumPostsAdapter.notifyDataSetChanged();
                            if(forumPostsEntity.getPagination().getRemain() <= 0) {
                                forumAllPtrFrame.finishLoadMoreWithNoMoreData();
                            }
                            else {
                                forumAllPtrFrame.finishLoadMore();
                                forumAllPtrFrame.setNoMoreData(false);
                            }
                        }
                        pageOffset += pageNum;
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            pageOffset = 0;
//            getForumPosts(Constant.LoadingType.REFRESH);
//        } else {
//            //相当于Fragment的onPause
//        }
//    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageOffset = 0;
        getForumPosts(Constant.LoadingType.REFRESH);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getForumPosts(Constant.LoadingType.LOADMORE);
    }

    @Override
    public void onItemClickListener(View v, int position) {
        try {
            switch (v.getId()) {
                case R.id.forum_item_top_btn:
                    topPost(position);
                    break;
                case R.id.forum_item_delete_btn:
                    deletePost(position);
                    break;
                case R.id.forum_item_follow_btn:
                    followPost(position);
                    break;
                case R.id.forum_item_comment_btn:
                    Router.build("gtedx://postDetail").with("id", posts.get(position).getId()).go(this);
                    break;
                case R.id.forum_item_like_btn:
                    likePost(position);
                    break;
                default:
                    Router.build("gtedx://postDetail").with("id", posts.get(position).getId()).go(this);
                    break;
            }
        }
        catch (IndexOutOfBoundsException e) {
            Toast.setText("请勿频繁操作")
                    .setDuration(5)
                    .setTintColor(Color.RED)
                    .show();
        }
    }

    private void topPost(int position) {
        List<ForumPostsEntity.ForumItem> originPosts = posts;
        Request<IdEntity> request = new Request<>(new IdEntity(originPosts.get(position).getId()));
        ServerApi.defaultInstance()
                .create(ForumService.class)
                .topPost(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<Object>() {
                    @Override
                    public void onNext(Object o) {
                        originPosts.get(position).setIsTop(1);
                        posts.clear();
                        posts.add(originPosts.get(position));
                        originPosts.remove(position);
                        posts.addAll(originPosts);
                        forumPostsAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void deletePost(int position) {
        List<ForumPostsEntity.ForumItem> originPosts = posts;
        Request<IdEntity> request = new Request<>(new IdEntity(originPosts.get(position).getId()));
        ServerApi.defaultInstance()
                .create(ForumService.class)
                .deletePost(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<Object>() {
                    @Override
                    public void onNext(Object o) {
                        posts.remove(position);
                        forumPostsAdapter.notifyItemRemoved(position);
                        forumPostsAdapter.notifyItemRangeChanged(position, posts.size());
                    }
                });
    }

    private void likePost(int position) {
        Request<IdEntity> request = new Request<>(new IdEntity(posts.get(position).getId()));
        ServerApi.defaultInstance()
                .create(ForumService.class)
                .likePost(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<ForumPostsEntity.ForumItem>() {
                    @Override
                    public void onNext(ForumPostsEntity.ForumItem forumItem) {
                        posts.get(position).setIsLike(forumItem.getIsLike());
                        posts.get(position).setLikeCount(forumItem.getLikeCount());
                        forumPostsAdapter.notifyItemChanged(position);
                    }
                });
    }

    private void followPost(int position) {
        Request<IdEntity> request = new Request<>(new IdEntity(posts.get(position).getId()));
        ServerApi.defaultInstance()
                .create(ForumService.class)
                .followPost(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<ForumPostsEntity.ForumItem>() {
                    @Override
                    public void onNext(ForumPostsEntity.ForumItem forumItem) {
                        posts.get(position).setIsFollow(forumItem.getIsFollow());
                        posts.get(position).setFollowCount(forumItem.getFollowCount());
                        forumPostsAdapter.notifyItemChanged(position);
                    }
                });
    }
}
