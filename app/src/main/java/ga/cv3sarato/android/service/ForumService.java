package ga.cv3sarato.android.service;

import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.entity.response.forum.FloorCommentsEntity;
import ga.cv3sarato.android.entity.response.forum.ForumFloorsEntity;
import ga.cv3sarato.android.entity.response.forum.ForumPostsEntity;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ForumService {

    @POST("post/list")
    Observable<ForumPostsEntity> getForumPosts(@Body Request request);

    @POST("post/create")
    Observable<Object> createNewPost(@Body Request request);

    @POST("post/follow")
    Observable<ForumPostsEntity.ForumItem> followPost(@Body Request request);

    @POST("post/like")
    Observable<ForumPostsEntity.ForumItem> likePost(@Body Request request);

    @POST("post/top")
    Observable<Object> topPost(@Body Request request);

    @POST("post/delete")
    Observable<Object> deletePost(@Body Request request);

    @POST("post/detail")
    Observable<ForumPostsEntity.ForumItem> postDetail(@Body Request request);

    @POST("floor/create")
    Observable<Object> createFloor(@Body Request request);

    @POST("floor/comment/create")
    Observable<Object> createComment(@Body Request request);

    @POST("floor/list")
    Observable<ForumFloorsEntity> getFloors(@Body Request request);

    @POST("floor/comment/list")
    Observable<FloorCommentsEntity> getComments(@Body Request request);
}
