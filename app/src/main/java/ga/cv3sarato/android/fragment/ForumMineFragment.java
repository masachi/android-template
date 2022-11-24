package ga.cv3sarato.android.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenenyu.router.Router;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.forum.ForumMineAdapter;
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
public class ForumMineFragment extends BaseFragment implements OnRefreshLoadMoreListener, BaseRecyclerAdapter.OnItemClickListener {


    @BindView(R.id.forum_mine_recyclerView)
    RecyclerView forumMineRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.forum_mine_ptr_frame)
    SmartRefreshLayout forumMinePtrFrame;


    private ForumMineAdapter minePostsAdapter;
    private List<ForumPostsEntity.ForumItem> posts = new ArrayList<>();
    private int pageNum = 3;
    private int pageOffset = 0;

    public ForumMineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_forum_mine, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        minePostsAdapter = new ForumMineAdapter(getContext(), posts, R.layout.item_forum_post);
        forumMineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        forumMineRecyclerView.setAdapter(minePostsAdapter);
        forumMinePtrFrame.setOnRefreshLoadMoreListener(this);
        minePostsAdapter.setOnItemClickListener(this);
        getMinePosts(Constant.LoadingType.REFRESH);
    }

    private void getMinePosts(int type) {
        Request<ForumPageEntity> request = new Request<>();
        ForumPageEntity forumPage = new ForumPageEntity(new PaginationEntity(pageNum, pageOffset), new HashMap<String, String>(){{put("scope", "OWN");}}, new ArrayList<SortEntity>(){{add(new SortEntity("update", 0));}});
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
                            forumMinePtrFrame.finishRefresh();
                            minePostsAdapter.notifyDataSetChanged();
                            if(forumPostsEntity.getPagination().getRemain() <= 0) {
                                forumMinePtrFrame.setNoMoreData(true);
                            }
                            else {
                                forumMinePtrFrame.setNoMoreData(false);
                            }
                        }
                        else {
                            posts.addAll(forumPostsEntity.getPosts());
                            minePostsAdapter.notifyDataSetChanged();
                            if(forumPostsEntity.getPagination().getRemain() <= 0) {
                                forumMinePtrFrame.finishLoadMoreWithNoMoreData();
                            }
                            else {
                                forumMinePtrFrame.finishLoadMore();
                                forumMinePtrFrame.setNoMoreData(false);
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
////            getMinePosts(Constant.LoadingType.REFRESH);
//        } else {
//            //相当于Fragment的onPause
//        }
//    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageOffset = 0;
        getMinePosts(Constant.LoadingType.REFRESH);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getMinePosts(Constant.LoadingType.LOADMORE);
    }

    @Override
    public void onItemClickListener(View v, int position) {
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
                        minePostsAdapter.notifyDataSetChanged();
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
                        minePostsAdapter.notifyItemRemoved(position);
                        minePostsAdapter.notifyItemRangeChanged(position, posts.size());
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
                        minePostsAdapter.notifyItemChanged(position);
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
                        minePostsAdapter.notifyItemChanged(position);
                    }
                });
    }
}
